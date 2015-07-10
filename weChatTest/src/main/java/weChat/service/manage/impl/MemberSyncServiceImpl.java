package weChat.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Gradecollect;
import weChat.domain.primary.MemberCache;
import weChat.domain.primary.Sendmsgdata;
import weChat.domain.primary.Sendmsgdatadetail;
import weChat.domain.primary.Sendmsglist;
import weChat.domain.primary.Wechatpubinfo;
import weChat.domain.primary.WeixinUserbind;
import weChat.parameter.IRespParam;
import weChat.repository.dao.MemberDao;
import weChat.repository.dao.TemplateDao;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.GradecollectRepository;
import weChat.repository.primary.MemberCacheRepository;
import weChat.repository.primary.OnlinememberRepository;
import weChat.repository.primary.WechatpubinfoRepository;
import weChat.repository.primary.WeixinUserbindRepository;
import weChat.service.common.ValidationService;
import weChat.service.km.AsyncService;
import weChat.service.manage.MemberSyncService;
import weChat.service.weixin.WeixinInfoService;
import weChat.utils.AppConstants;
import weChat.utils.DateUtil;
import weChat.utils.RespUtils;

@Service
public class MemberSyncServiceImpl implements MemberSyncService {

	@Autowired
	private GradecollectRepository gradecollectRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private MemberCacheRepository memberCacheRepository;
	@Autowired
	private AsyncService asyncService;
	@Autowired
	private WechatpubinfoRepository wechatpubinfoRepository;
	@Autowired
	private WeixinUserbindRepository weixinUserbindRepository;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private TemplateDao templateDao;
	@Autowired
	private WeixinInfoService weixinInfoService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ValidationService validationService;

	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	@Autowired
	private OnlinememberRepository onlinememberRepository;
		
	@Override
	public IRespParam memberLevel(Company company, int wechatpubinfoid, List<Dto> data) {
		String companyCode = company.getCompanyCode();
		if (data != null) {
			for (Dto dto : data) {
				Integer gradeid = dto.getAsInteger("gradeid");
				Gradecollect gradecollect = gradecollectRepository
						.findFirstByCompanyCodeAndWechatPubInfoIDAndGradeID(
								companyCode, wechatpubinfoid, gradeid);
				String gradecode = dto.getAsString("gradecode");
				String gradename = dto.getAsString("gradename");
				Integer status = dto.getAsInteger("status");
				if (gradecollect == null) {
					gradecollect = new Gradecollect();
					// 有问题
					gradecollect.setCompanyID(company.getCompanyID());
					// 格式不对
					gradecollect.setWechatPubInfoID(wechatpubinfoid);
					// 格式不对
					gradecollect.setGradeID(gradeid);
					gradecollect.setCreateTime(new Date());
				}
				gradecollect.setGradeCode(gradecode);
				gradecollect.setGradeName(gradename);
				gradecollect.setStatus(status.byteValue());
				gradecollect.setUpdateTime(new Date());
				//新增可以储值、可以积分、是否可以在线申请会员
				gradecollect.setUseStorage(dto.getAsByte("usestorage"));
				gradecollect.setUseIntegral(dto.getAsByte("useintegral"));
				//是否可以在线申请会员线下还没有开发，暂时屏蔽
				//gradecollect.setUseOnlineApp(dto.getAsByte("useonlineapp"));
				gradecollectRepository.save(gradecollect);
			}
		}
		return RespUtils.successMR();
	}

	@Override
	public IRespParam memberInfo(Company company, int wechatpubinfoid, List<Dto> data) {

		StringBuffer sb = new StringBuffer();
		int companyID = company.getCompanyID();
		if (data != null) {
			List<MemberCache> list = new ArrayList<MemberCache>();
			for (Dto dto : data) {
				//TODO KM id 有问题,作兼容
				String kmid = dto.containsKey("KmID") ?  dto.getAsString("KmID") : dto.getAsString("kmid");
				String memberid = dto.getAsString("memberid");
				MemberCache memberCache = memberCacheRepository
						.findTopByCompanyIDAndMemberid(companyID, memberid);
				String status = dto.getAsString("status");
				//删除在线申请会员缓存表数据
				onlinememberRepository.deleteByKmid(kmid);
				//只有删除状态的数据只更新状态
				if("删除".equals(status)){
					//非启用状态的数据只有存在才更新，不存在不更新
					if(memberCache != null){
						memberCache.setStatus(status);
						memberCache.setUpdateTime(new Date());
						//会员创建时间如果有空，要设置，因为该字段不能为空
						if(memberCache.getCreateTime() == null){
							memberCache.setCreateTime(new Date());
						}
						list.add(memberCache);
					}
				}else{
					if (memberCache == null) {
						memberCache = new MemberCache();
						memberCache.setCompanyID(companyID);
						memberCache.setMemberid(dto.getAsString("memberid"));
						memberCache.setCreateTime(new Date());
					}
					memberCache.setWechatPubInfoID(wechatpubinfoid);
					//会员创建时间如果有空，要设置，因为该字段不能为空
					if(memberCache.getCreateTime() == null){
						memberCache.setCreateTime(new Date());
					}

					memberCache.setKmid(kmid);
					memberCache.setGradeID(dto.getAsInteger("gradeid"));
					memberCache.setCardnum(dto.getAsString("cardnum"));
					memberCache.setMemberName(dto.getAsString("membername"));
					memberCache.setBirthday(dto.getAsDate("birthday"));
					memberCache.setSex(dto.getAsString("sex"));
					memberCache.setPaperType(dto.getAsString("papertype"));
					memberCache.setPaperNumber(dto.getAsString("papernumber"));
					memberCache.setCreateCardTime(dto.getAsDate("createcardtime",dateFormat));
					memberCache.setMemberPsw(dto.getAsString("memberpsw"));
					memberCache.setStatus(dto.getAsString("status"));
					memberCache.setMobile(dto.getAsString("mobile"));
					memberCache.setUseLimitDate(dto.getAsDate("useLimitdate"));
					memberCache.setAccountCash(dto.getAsBigDecimal("accountcash"));
					memberCache.setAccountPresent(dto
							.getAsBigDecimal("accountpresent"));
					memberCache.setIntegralBalance(dto
							.getAsBigDecimal("integralbalance"));
					memberCache
							.setConsumeTotal(dto.getAsBigDecimal("consumetotal"));
					memberCache.setAccountBalance(dto
							.getAsBigDecimal("accountbalance"));
					memberCache.setIntegralBalance(dto
							.getAsBigDecimal("integralbalance"));
					memberCache.setConsumeTimes(dto.getAsInteger("consumetimes"));
					memberCache.setLastConsumeTime(dto.getAsDate("lastconsumetime",
							dateFormat));
					memberCache.setUpdateTime(new Date());
					//校验数据，如果出错的话，自动抛出异常，中止数据同步
					validationService.validate(memberCache, "data");
					list.add(memberCache);
				}
			
			}
			// 保存信息
			memberCacheRepository.save(list);
		}
		return RespUtils.successMR();
	}
	
	@Override
	public IRespParam memberConsume(Company company, int wechatpubinfoid,
			List<Dto> data) throws Exception{
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_CONSUME;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		//向KM发送的消息队列
		//List<BaseDto> kmTemplateList = new ArrayList<BaseDto>();
		if (res!=null&&res.size()!=0) {
			int expireTime = (int) res.get(0).get("ExpireTime");
			//基础数据准备
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList,sendmsgdatas,expireTime);
			//微信数据准备
			wechatList = wechatRequestData(kmIDList,sendMap,res);
			//KM数据准备
			//kmTemplateList = kmRequestData(sendMap,res,msgTemplateID,company.getCompanyID());
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//请求KM发送消息
		//pushTemplate(kmTemplateList);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}

	@Override
	public IRespParam memberRecharge(Company company, int wechatpubinfoid,
			List<Dto> data) throws Exception {
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_RECHARGE;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		if (res!=null&&res.size()!=0) {
			//数据准备
			int expireTime = (int) res.get(0).get("ExpireTime");
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList, sendmsgdatas,expireTime);
			wechatList = wechatRequestData(kmIDList,sendMap,res);
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}

	@Override
	public IRespParam memberGivingPoints(Company company, int wechatpubinfoid,
			List<Dto> data) throws Exception {
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_GIVING_POINTS;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		if (res!=null&&res.size()!=0) {
			//数据准备
			int expireTime = (int) res.get(0).get("ExpireTime");
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList, sendmsgdatas,expireTime);
			wechatList = wechatRequestData(kmIDList,sendMap,res);
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}

	@Override
	public IRespParam memberPointsDeduction(Company company,
			int wechatpubinfoid, List<Dto> data) throws Exception {
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_POINTS_DEDUCTION;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		if (res!=null&&res.size()!=0) {
			//数据准备
			int expireTime = (int) res.get(0).get("ExpireTime");
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList, sendmsgdatas,expireTime);
			wechatList = wechatRequestData(kmIDList,sendMap,res);
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}

	@Override
	public IRespParam memberPointsToGift(Company company, int wechatpubinfoid,
			List<Dto> data) throws Exception {
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_POINTS_TO_GIFT;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		
		if (res!=null&&res.size()!=0) {
			//数据准备
			int expireTime = (int) res.get(0).get("ExpireTime");
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList, sendmsgdatas,expireTime);
			wechatList = wechatRequestData(kmIDList,sendMap,res);
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}

	@Override
	public IRespParam memberChecked(Company company, int wechatpubinfoid,
			List<Dto> data) throws Exception {
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_CHECKED;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		if (res!=null&&res.size()!=0) {
			//数据准备
			int expireTime = (int) res.get(0).get("ExpireTime");
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList, sendmsgdatas,expireTime);
			wechatList = wechatRequestData(kmIDList,sendMap,res);
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}
	
	
	@Override
	public IRespParam memberDraw(Company company, int wechatpubinfoid,
			List<Dto> data) throws Exception {
		List<String> kmIDList = new ArrayList<String>();//发送消息的KM队列,为了查询openid准备
		int msgTemplateID = AppConstants.MEMBER_DRAW;//模板类型  数据类型
		//商家消息数据明细的整合
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		List<Sendmsgdatadetail> historyParamesList = new ArrayList<Sendmsgdatadetail>();
		//商家发送消息数据
		List<Sendmsgdata> sendmsgdatas = new ArrayList<Sendmsgdata>();
		//获取商家模板消息项目
		List<Map<String, Object>> res =	templateDao.getCompanyTemplate(wechatpubinfoid, msgTemplateID);
		//微信发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		if (res!=null&&res.size()!=0) {
			//数据准备
			int expireTime = (int) res.get(0).get("ExpireTime");
			prepareBaseData(company,data,kmIDList,msgTemplateID,sendMap,historyParamesList, sendmsgdatas,expireTime);
			wechatList = wechatRequestData(kmIDList,sendMap,res);
		}else {
			return null;
		}
		//请求微信发送消息
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(wechatpubinfoid);
		List<Sendmsglist> sendmsglists = weixinInfoService.sendModelMessage(wechatList, sendmsgdatas, wechatpubinfo);
		//日志数据保存
		saveDataLog(historyParamesList, sendmsgdatas, sendmsglists);
		return RespUtils.successMR();
	}

	/**基础数据准备
	 * @param company
	 * @param data
	 * @param kmIDList
	 * @param msgTemplateID
	 * @param sendMap
	 * @param historyParamesList
	 * @param sendmsgdatas
	 * @param expireTime 数据超时时长
	 */
	private void prepareBaseData(Company company, List<Dto> data,
			List<String> kmIDList, int msgTemplateID,
			Map<String, Map<String, Object>> sendMap,
			List<Sendmsgdatadetail> historyParamesList,
			List<Sendmsgdata> sendmsgdatas, int expireTime) {
		for (Dto dto : data) {
			BaseDto entity = (BaseDto) dto;
			String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(8);
			Map<String, Object> keyValue = new HashMap<String, Object>();
			String kmID = entity.getAsString("kmid");
			kmIDList.add(kmID);
			//添加模板中线上保存的数据项
			Map<String, Object> extraData = extraData(company.getCompanyID(),kmID,msgTemplateID);
			keyValue.putAll(extraData);
			keyValue.put("uuid", uuid);
			for (Entry<String, Object> entry : extraData.entrySet()) {
				Sendmsgdatadetail item = new Sendmsgdatadetail();
				item.setItemName(entry.getKey());
				item.setItemValue(""+entry.getValue());
				item.setUniqueCode(uuid);
				historyParamesList.add(item);
			}
			for (Map.Entry<String, Object> m : entity.entrySet()) {
				Sendmsgdatadetail item = new Sendmsgdatadetail();
				item.setItemName(m.getKey());
				item.setItemValue(""+m.getValue());
				item.setUniqueCode(uuid);
				historyParamesList.add(item);
				keyValue.put(m.getKey(), ""+m.getValue());
			}
			//超时的不用放入sendMap
			Date now = DateUtil.getNow();
			Date startDate = DateUtil.dateFormat((String) keyValue.get("1977-01-01 00:00:01"));
			int minDiff = 100000;
			switch (msgTemplateID) {
			case AppConstants.MEMBER_CONSUME:
				startDate = DateUtil.dateFormat((String) keyValue.get("changedatetime"));
				break;
			case AppConstants.MEMBER_RECHARGE:
				startDate = DateUtil.dateFormat((String) keyValue.get("changedatetime"));
				break;
			case AppConstants.MEMBER_GIVING_POINTS:
				startDate = DateUtil.dateFormat((String) keyValue.get("changedatetime"));
				break;
			case AppConstants.MEMBER_POINTS_DEDUCTION:
				startDate = DateUtil.dateFormat((String) keyValue.get("changedatetime"));
				break;
			case AppConstants.MEMBER_POINTS_TO_GIFT:
				startDate = DateUtil.dateFormat((String) keyValue.get("changedatetime"));
				break;
			case AppConstants.MEMBER_CHECKED:
				startDate = DateUtil.dateFormat((String) keyValue.get("consigndatetime"));
				break;
			case AppConstants.MEMBER_DRAW:
				startDate = DateUtil.dateFormat((String) keyValue.get("drawdowndatetime"));
				break;
			}
			try {
				minDiff = DateUtil.dateDiff(DateUtil.INTERVAL_MINUTE,startDate,now);
			} catch (Exception e) {
				minDiff = 100000;
			}
			if (minDiff<expireTime) {
				sendMap.put(kmID, keyValue);
			}
			//商家发送消息数据表
			Sendmsgdata msgDataSendmsgdata = new Sendmsgdata();
			msgDataSendmsgdata.setCompanyID(company.getCompanyID());
			msgDataSendmsgdata.setDataType((byte)msgTemplateID);
			msgDataSendmsgdata.setKmid(kmID);
			msgDataSendmsgdata.setUniqueCode(uuid);
			msgDataSendmsgdata.setStatus(AppConstants.NOT_SEND);
			sendmsgdatas.add(msgDataSendmsgdata);
		}
	}
	
	/**
	 * @param companyID	商家编码
	 * @param kmID	电子会员卡ID
	 * @param msgTemplateID	模板ID
	 * @return
	 */
	private Map<String, Object> extraData(int companyID,String kmID,int msgTemplateID){
		switch (msgTemplateID) {
		case AppConstants.MEMBER_CONSUME:
		case AppConstants.MEMBER_RECHARGE:
		case AppConstants.MEMBER_GIVING_POINTS:
		case AppConstants.MEMBER_POINTS_DEDUCTION:
		case AppConstants.MEMBER_POINTS_TO_GIFT:
		case AppConstants.MEMBER_CHECKED:
		case AppConstants.MEMBER_DRAW:	
			return	memberDao.findMemberWithCompany(companyID, kmID);
		default:
			return null;
		}
	}
	
	/**按模板要求进行数据填充
	 * @param sendMap	发送的数据
	 * @param res	具体的模板
	 * @param msgTemplateID 模板类型
	 */
	private List<BaseDto> kmRequestData(Map<String, Map<String, Object>> sendMap,
			List<Map<String, Object>> res,int msgTemplateID,int companyID) {
				//发送的消息队列
				List<BaseDto> kmList = new ArrayList<BaseDto>();
				String url = (String) res.get(0).get("Url");
				String title = templateDao.findCompanyTemplateTitle(msgTemplateID);
				//请求微信发送模板消息
				for (Entry<String, Map<String, Object>> m : sendMap.entrySet()){
					BaseDto kmDto = new BaseDto();
					kmDto.put("msgid", UUID.randomUUID().toString().replaceAll("-", "").substring(8));
					kmDto.put("title", title);
					kmDto.put("url", url);
					kmDto.put("fruser", companyID);
					kmDto.put("touser", m.getKey());
					//微信数据准备的时候已填充好了模板数据，故此暂时注释掉
					//fillModel(res, m);
					//格式化成KM所需格式
					Map<String, Map<String, String>> kmDataFormat = getKMData(res);
					kmDto.put("data", kmDataFormat);
					kmList.add(kmDto);
				}
				return kmList;
	}
	
	//按模板填充好项目值
	private void fillModel(List<Map<String, Object>> res,
			Entry<String, Map<String, Object>> m) {
		for (int j = 0; j < res.size(); j++) {
			Map<String, Object> map = res.get(j);
			if (!(map.get("ItemName").equals("first")||map.get("ItemName").equals("remark"))) {
				if (CommonUtils.isEmpty(map.get("ItemValue"))) {
					res.get(j).put("ItemValue", m.getValue().get(""+map.get("ItemName")));
				}
			}else {
				//分割 Item项目，取对应值
				String itemValueString="";
				StringTokenizer token = new StringTokenizer((String) map.get("ItemValue"), "[");
				while (token.hasMoreTokens()) {
					String subStr= token.nextToken();
					String valueStr =subStr;
					int position = subStr.indexOf("]");
					if (position>0) {
						valueStr = m.getValue().get(subStr.substring(0,position))+ subStr.substring(position+1);
						if (null==m.getValue().get(subStr.substring(0,position))){
							valueStr = subStr.substring(0, position)+subStr.substring(position+1);
						}
					}
					itemValueString+=valueStr;
				}
				res.get(j).put("ItemValue", itemValueString);
			}
		}
	}
	
	/**按模板要求进行数据填充
	 * @param kmIDList	发送数据的kmid
	 * @param sendMap	发送的数据
	 * @param res	具体的模板
	 */
	private List<BaseDto> wechatRequestData(List<String> kmIDList,Map<String, Map<String, Object>> sendMap,List<Map<String, Object>> res) {
		//发送的消息队列
		List<BaseDto> wechatList = new ArrayList<BaseDto>();
		//按模板格式填充好的数据
		List<List<Map<String, Object>>> modelDataList = new ArrayList<List<Map<String,Object>>>();
		//根据KMid查询openid
		Byte status = AppConstants.isBind;
		List<WeixinUserbind> listUserbinds = weixinUserbindRepository.findByStatusAndKmidIn(status, kmIDList);
		String topColor = (String) res.get(0).get("TopColour");
		String TemplateID = (String)res.get(0).get("TemplateID");
		String url = (String) res.get(0).get("Url");
		//请求微信发送模板消息
		for (Entry<String, Map<String, Object>> m : sendMap.entrySet()){
			for (WeixinUserbind weixinUserbind : listUserbinds) {
				if (m.getKey().endsWith(weixinUserbind.getKmid())) {
					BaseDto pDto = new BaseDto();
					pDto.put("touser", weixinUserbind.getOpenID());
					pDto.put("template_id", TemplateID);
					pDto.put("url", url);
					pDto.put("topcolor", topColor);
					pDto.put("uuid", m.getValue().get("uuid"));//消息唯一码，发送时去掉
					fillModel(res, m);
					modelDataList.add(res);
					//格式化成微信所需格式
					Map<String, Map<String, String>> wechatDataFormat = getWechatData(res);
					pDto.put("data", wechatDataFormat);
					wechatList.add(pDto);
				}
			}
		}
		return wechatList;
	}
	
	/**格式化成微信模板
	 * @param res 模板数据项
	 * @return
	 */
	private Map<String, Map<String, String>> getWechatData(List<Map<String, Object>> res) {
		Map<String,Map<String,String>> entityMaps = new HashMap<String,Map<String,String>>();
		for (int i = 0; i < res.size(); i++) {
			Map<String, Object> entity = res.get(i);
			Map<String,String> map = new HashMap<String, String>();
			map.put("color", (String)entity.get("ItemColour"));
			map.put("value",(String)entity.get("ItemValue"));
			entityMaps.put((String)entity.get("WechatKey"),map);
		}
		return entityMaps;
	}
	
	/**格式化成KM要去数据模板
	 * @param res 模板数据项
	 * @return
	 */
	private Map<String, Map<String, String>> getKMData(List<Map<String, Object>> res) {
		Map<String,Map<String,String>> entityMaps = new HashMap<String,Map<String,String>>();
		for (int i = 0; i < res.size(); i++) {
			Map<String, Object> entity = res.get(i);
			Map<String,String> map = new HashMap<String, String>();
			map.put("color", (String)entity.get("ItemColour"));
			map.put("value",(String)entity.get("ItemValue"));
			if (!(entity.get("WechatKey").equals("first")||entity.get("WechatKey").equals("remark"))) {
				map.put("caption", (String)entity.get("ItemNameCn"));
			}
			entityMaps.put((String)entity.get("WechatKey"),map);
		}
		return entityMaps;
	}
	
	/**日志数据保存
	 * @param historyParamesList	商家消息数据明细
	 * @param sendmsgdatas		商家发送消息数据
	 * @param sendmsglists		商家消息发送历史记录
	 */
	private void saveDataLog(List<Sendmsgdatadetail> historyParamesList,
			List<Sendmsgdata> sendmsgdatas, List<Sendmsglist> sendmsglists) {
		try {
			asyncService.saveModelMessageLog(historyParamesList, sendmsgdatas, sendmsglists);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**异步向KM推送模板消息
	 * @param kmTemplateList
	 */
	private void pushTemplate(List<BaseDto> kmTemplateList){
		try {
			asyncService.pushTemplate(kmTemplateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
