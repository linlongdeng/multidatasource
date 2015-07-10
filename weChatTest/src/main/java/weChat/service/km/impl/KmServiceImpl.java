package weChat.service.km.impl;

import static weChat.core.utils.CommonUtils.isNotEmpty;
import static weChat.core.utils.CommonUtils.isEmpty;
import static weChat.utils.AppConstants.KM_BIND_CARD_SOURCE_KM;
import static weChat.utils.AppConstants.KM_BIND_CARD_STATUS_BIND;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Gradecollect;
import weChat.domain.primary.Kmbindcard;
import weChat.domain.primary.MemberCache;
import weChat.domain.primary.Parameter;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.common.CommonParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.manager.MRespParam;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.GradecollectRepository;
import weChat.repository.primary.KmbindcardRepository;
import weChat.repository.primary.MemberCacheRepository;
import weChat.repository.primary.ParameterRepository;
import weChat.service.amqp.WechatMqService;
import weChat.service.amqp.WechatMqUtilsService;
import weChat.service.common.RespService;
import weChat.service.km.KmService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;
import weChat.utils.RespUtils;

@Service
public class KmServiceImpl implements KmService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberCacheRepository memberCacheRepository;
	@Autowired
	private RespService respService;
	
	@Autowired
	private WechatMqUtilsService wechatMqUtilsService;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private KmbindcardRepository kmbindcardRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private GradecollectRepository gradecollectRepository;
	
	@Override
	public IRespParam bindCardInfo(Company company, Dto otherParam,
			int wechatpubinfoid) throws Exception {
		String cardnum = otherParam.getAsString("cardnum");
		MemberCache memberCache = memberCacheRepository
				.findTopByCardnumAndStatusAndWechatPubInfoID(cardnum,
						AppConstants.MEMBER_STATUS_USER, wechatpubinfoid);
		// 验证商家会员信息
		AppUtils.assertMemberCacheNull(memberCache);
		// 要根据商家公众号和卡号来获取实际绑卡的商家，在总分店模式下，K米传上来的商家可能有误
		int companyID = memberCache.getCompanyID();
		Company memberCompany = companyRepository.findOne(companyID);
		// 会员建卡分店信息查询校验
		AppUtils.assertCompanyNotNull(memberCompany);
		// 后面用会员建卡分店的商家信息

		String cmdid="WJ007";
		BaseDto mqDto = new BaseDto();
		mqDto.put("cardnum", cardnum);
		// 调用MQ服务
		CommonParam mqResp = wechatMqUtilsService.invokeMqService(cmdid, memberCompany.getCompanyCode(), wechatpubinfoid, mqDto);
		Integer ret = mqResp.getAsInteger("ret");
		// 判断请求是否成功
		if (AppUtils.checkSuccess(ret)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) mqResp.get("data");
			Object mobile = map.get("mobile");
			DynamicRespParam resp = new DynamicRespParam();
			resp.set("mobile", mobile);
			return resp;
		} else {
			return new MRespParam(ret, mqResp.getMsg());
		}

	}

	@Override
	public IRespParam memberInfo(int customerid) {
		// 采用原生JDBC执行SQL语句
		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				MemberCacheRepository.MEMBER_INFO_SQL, customerid);
		//如果有空，从线申请会员缓存表获取会员信息获取信息
		if(isEmpty(list)){
			list = jdbcTemplate.queryForList(
					MemberCacheRepository.ON_LINE_MEMBER_INFO_SQL, customerid);
		}
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (isNotEmpty(list)) {
			Parameter parameter = parameterRepository
					.findFirstByParameterName(AppConstants.FILE_DWONLOAD_PATH);
			String fileDownload = parameter.getParameterValue();
			for (Map<String, Object> memberCacheInfo : list) {
				Map<String, Object> item = new HashMap<>();
				String cardpicurl = "";
				if (isNotEmpty(memberCacheInfo.get("CardPicID"))) {
					cardpicurl = fileDownload + "?fileid="
							+ memberCacheInfo.get("CardPicID");
				}
				item.put("cardpicurl", cardpicurl);
				item.put("kmid", memberCacheInfo.get("KMID"));
				item.put("companyid", memberCacheInfo.get("CompanyID"));
				item.put("membername", memberCacheInfo.get("MemberName"));
				item.put("birthday", memberCacheInfo.get("birthday"));
				item.put("sex", memberCacheInfo.get("sex"));
				item.put("papertype", memberCacheInfo.get("PaperType"));
				item.put("papernumber", memberCacheInfo.get("PaperNumber"));
				item.put("address", "");
				item.put("cardnum", memberCacheInfo.get("Cardnum"));
				item.put("createcardtime",
						memberCacheInfo.get("CreateCardTime"));
				item.put("gradename", memberCacheInfo.get("GradeName"));
				item.put("status", memberCacheInfo.get("status"));
				item.put("mobile", memberCacheInfo.get("mobile"));
				item.put("email", "");// 暂时没有‘邮箱’信息，默认为‘’
				item.put("uselimitdate", memberCacheInfo.get("UseLimitDate"));
				item.put("cardintegral", memberCacheInfo.get("IntegralBalance"));
				item.put("accountbalance",
						memberCacheInfo.get("AccountBalance"));
				item.put("accountcash", memberCacheInfo.get("AccountCash"));
				item.put("accountpresent",
						memberCacheInfo.get("AccountPresent"));
				item.put("accountoverdraft", 0);// 暂时没有‘可透支限额’信息，默认为0
				item.put("lastconsumetime",
						memberCacheInfo.get("LastConsumeTime"));
				item.put("updatetime", memberCacheInfo.get("UpdateTime"));
				item.put("note", ""); // 备注，默认为空
				dataList.add(item);
			}
		}
		DynamicRespParam resp = new DynamicRespParam();
		resp.set("data", dataList);
		return resp;
	}

	@Override
	public IRespParam bindCard(int wechatpubinfoid, int customerid,
			Dto otherParam) throws Exception {
		String cardnum = otherParam.getAsString("cardnum");
		MemberCache memberCache = memberCacheRepository
				.findTopByCardnumAndStatusAndWechatPubInfoID(cardnum,
						AppConstants.MEMBER_STATUS_USER, wechatpubinfoid);
		// 验证商家会员信息
		AppUtils.assertMemberCacheNull(memberCache);
		int companyid = memberCache.getCompanyID();
		Company memberCompany = companyRepository.findOne(companyid);
		// 会员建卡分店信息查询校验
		AppUtils.assertCompanyNotNull(memberCompany);
		String companycode = memberCompany.getCompanyCode();
		String kmid = memberCache.getKmid();
		// 已经绑过卡，只维护绑卡关系
		if (CommonUtils.isNotEmpty(kmid)) {
			// 该卡其他人是否绑过，和该会员是否绑过其他卡,全部解绑,然后新增
			bindMemberRelation(companyid, customerid, kmid);
			DynamicRespParam resp = new DynamicRespParam();
			resp.set("kmid", kmid);
			return resp;

		}
		// 未绑过卡，通知线下系统绑卡
		else {
			//调用MQ，通知线下绑卡
			CommonParam mqResp = invokeMQBindCard(companycode, wechatpubinfoid,
					cardnum, otherParam.getAsString("moblie"));
			Integer ret = mqResp.getAsInteger("ret");
			// 判断请求是否成功
			if (AppUtils.checkSuccess(ret)) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) mqResp
						.get("data");
				kmid = (String) map.get("kmid");
				// 该卡其他人是否绑过，和该会员是否绑过其他卡,全部解绑,然后新增
				bindMemberRelation(companyid, customerid, kmid);
				// 更新会员K米ID
				updateMemberKmId(cardnum, companyid, kmid);
				// 更新会员表的K米ID
				DynamicRespParam resp = new DynamicRespParam();
				resp.set("kmid", kmid);
				return resp;
			} else {
				return new MRespParam(ret, mqResp.getMsg());
			}

		}
	}

	/**
	 * 解绑原来的绑定关系，新增K米APP绑卡关系信息
	 * 
	 * @param companyID
	 * @param customerid
	 * @param kmid
	 */
	private void bindMemberRelation(int companyid, int customerid, String kmid) {
		// 该卡其他人是否绑过，和该会员是否绑过其他卡,全部解绑,然后新增
		kmbindcardRepository.setUnbindByKmid(companyid, kmid);
		kmbindcardRepository.setUnbindByOrCustomerID(companyid, customerid);
		Kmbindcard kmbindcard = new Kmbindcard();
		kmbindcard.setOpenID("");
		kmbindcard.setKmid(kmid);
		kmbindcard.setBindTime(CommonUtils.currentTimestamp());
		kmbindcard.setCompanyID(companyid);
		kmbindcard.setCustomerID(customerid);
		kmbindcard.setStatus(KM_BIND_CARD_STATUS_BIND);
		// K米绑卡
		kmbindcard.setSource(KM_BIND_CARD_SOURCE_KM);
		// 保存K米APP绑卡关系信息
		kmbindcardRepository.save(kmbindcard);
	}

	

	/**
	 * 通知MQ绑卡
	 * 
	 * @param companycode
	 * @param wechatpubinfoid
	 * @param cardnum
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	private CommonParam invokeMQBindCard(String companycode,
			int wechatpubinfoid, String cardnum, String mobile)
			throws Exception {
		String cmdid="WJ008";;
		BaseDto mqDto = new BaseDto();
		mqDto.put("cardnum", cardnum);
		mqDto.put("kmid", "");
		mqDto.put("validatetype", "1");
		mqDto.put("mobile", mobile);
		// 说明是K米绑卡
		mqDto.put("iskm", true);
		CommonParam mqResp = wechatMqUtilsService.invokeMqService(cmdid, companycode, wechatpubinfoid, mqDto);
		return mqResp;
	}
	
	

	

	

	@Override
	public IRespParam memberConsumeInfo(Company company, int wechatpubinfoid,
			Dto otherParam) throws Exception {
		String kmid = otherParam.getAsString("kmid");
		MemberCache memberCache = memberCacheRepository.findTopByKmid(kmid);
		AppUtils.assertMemberNotExits(memberCache, kmid);
		String memberid = memberCache.getMemberid();
		String cardnum = memberCache.getCardnum();
		String memberPsw = memberCache.getMemberPsw();
		BaseDto mqDto = new BaseDto();
		mqDto.put("memberid", memberid);
		mqDto.put("cardnum", cardnum);
		mqDto.put("memberpsw", memberPsw);
		mqDto.put("begintime", otherParam.getAsString("begintime"));
		mqDto.put("endtime", otherParam.getAsString("endtime"));
		CommonParam mqResp = wechatMqUtilsService.invokeMqService("WJ002",company.getCompanyCode(), wechatpubinfoid, mqDto);
		Integer ret = mqResp.getAsInteger("ret");
		// 判断请求是否成功
		if (AppUtils.checkSuccess(ret)) {
			List data = mqResp.getAsList("data");
			// 更新会员表的K米ID
			DynamicRespParam resp = new DynamicRespParam();
			resp.set("data", data);
			return resp;
		}else{
			return new MRespParam(ret, mqResp.getMsg());
		}
	}

	@Override
	public IRespParam updateMemberInfo(Company company, int wechatpubinfoid,
			Dto otherParam) throws Exception {
		String kmid = otherParam.getAsString("kmid");
		MemberCache memberCache = memberCacheRepository.findTopByKmid(kmid);
		AppUtils.assertMemberNotExits(memberCache, kmid);
		String membername = otherParam.getAsString("membername");
		String sex = otherParam.getAsString("sex");
		String papernumber = otherParam.getAsString("papernumber");
		String mobile = otherParam.getAsString("mobile");
		String birthday = otherParam.getAsString("birthday");
		String address = otherParam.getAsString("address");
		String email = otherParam.getAsString("email");
		BaseDto mqDto = new BaseDto();
		mqDto.put("membername", membername);
		mqDto.put("sex", sex);
		mqDto.put("papernumber", papernumber);
		mqDto.put("mobile", mobile);
		mqDto.put("birthday", birthday);
		mqDto.put("address", address);
		mqDto.put("email", email);
		mqDto.put("memberid", memberCache.getMemberid());
		mqDto.put("cardnum", memberCache.getCardnum());
		mqDto.put("memberpsw", memberCache.getMemberPsw());
		String cmdid ="WJ005";
		CommonParam mqResp = wechatMqUtilsService.invokeMqService(cmdid,company.getCompanyCode(), wechatpubinfoid, mqDto);
		Integer ret = mqResp.getAsInteger("ret");
		// 判断请求是否成功
		if (AppUtils.checkSuccess(ret)) {
			//线上也直接更新会员，因为K米可能随时查询，线下系统更新可能有延迟
			memberCache.setMemberName(membername);
			memberCache.setSex(sex);
			//非空才写证件号
			if(CommonUtils.isNotEmpty(papernumber)){
				memberCache.setPaperNumber(papernumber);				
			}
			memberCache.setMobile(mobile);
			memberCache.setBirthday(otherParam.getAsDate("birthday"));
			return RespUtils.successMR();
		}else{
			return new MRespParam(ret, mqResp.getMsg());
		}
		
	}
	
	/**
	 * 更新会员K米ID
	 * 
	 * @param cardnum
	 * @param companyid
	 * @param kmid
	 */
	private void updateMemberKmId(String cardnum, int companyid, String kmid) {
		// 更新会员的kmid
		MemberCache memberCache = memberCacheRepository
				.findTopByCardnumAndCompanyID(cardnum, companyid);
		// 判断根据卡号和商家ID获取到的会员信息是否为空
		AppUtils.assertMemberCacheKmNull(memberCache);
		memberCache.setKmid(kmid);
	}
	
	@Override
	public IRespParam memberInfoByKmID(String kmid) throws Exception {
		String sql = "SELECT mec.*,b.cardpicid,b.gradename,b.memberrights from (\n" +
				"			SELECT a.kmid,a.companyid,a.membername,a.birthday,a.sex,a.papertype,a.papernumber,a.cardnum,a.createcardtime,a.status,a.mobile,a.uselimitdate,\n" +
				"			a.accountbalance,a.integralbalance,a.accountcash,a.accountpresent,a.lastconsumetime,a.updatetime,a.gradeID\n" +
				"			from wj_tbl_member_cache a where a.kmid=? and a.Status='启用') as mec\n" +
				"			LEFT JOIN wj_tbl_gradecollect b ON mec.GradeID = b.GradeID and mec.CompanyID = b.CompanyID";
		 List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,kmid);
		if(isEmpty(list)){
			list = jdbcTemplate.queryForList(MemberCacheRepository.ON_LINE_MIMBER_INFO_KMID_SQL, kmid);
		}
		Map<String, Object> memberMap = new HashMap<String, Object>();
		if(isNotEmpty(list)){
			Map<String, Object> res = list.get(0);
			//获取文件服务器地址
			Parameter parameter = parameterRepository.findFirstByParameterName(AppConstants.FILE_DWONLOAD_PATH);
			String fileDownload = parameter.getParameterValue();
			String cardpicurl = "";
			if (isNotEmpty(res.get("CardPicID"))) {
				cardpicurl = fileDownload + "?fileid="+ res.get("CardPicID");
			}
			memberMap.put("kmid", res.get("kmid"));
			memberMap.put("companyid", res.get("companyid"));
			memberMap.put("membername", res.get("membername"));
			memberMap.put("birthday", res.get("birthday"));
			memberMap.put("sex", res.get("sex"));
			memberMap.put("papertype", res.get("papertype"));
			memberMap.put("papernumber", res.get("papernumber"));
			memberMap.put("address", "");
			memberMap.put("cardnum", res.get("cardnum"));
			memberMap.put("createcardtime", res.get("createcardtime"));
			memberMap.put("gradename", res.get("gradename"));
			memberMap.put("cardpicurl", cardpicurl);
			memberMap.put("memberrights", res.get("memberrights"));
			memberMap.put("status", res.get("status"));
			memberMap.put("mobile", res.get("mobile"));
			memberMap.put("uselimitdate", res.get("uselimitdate"));
			memberMap.put("cardintegral", res.get("integralbalance"));
			memberMap.put("accountbalance", res.get("accountbalance"));
			memberMap.put("accountcash", res.get("accountcash"));
			memberMap.put("accountpresent", res.get("accountpresent"));
			memberMap.put("accountoverdraft", 0);
			memberMap.put("lastconsumetime", res.get("lastconsumetime"));
			memberMap.put("updatetime", res.get("updatetime"));
			memberMap.put("note", "");
		}
		DynamicRespParam resp = new DynamicRespParam();
		resp.set("data", memberMap);
		return resp;
	}

	@Override
	public IRespParam getParamer(ArrayList<String> paramers) throws Exception {
		DynamicRespParam resp = new DynamicRespParam();
		String sql ="SELECT ParameterName,ParameterValue from wj_tbl_parameter where CompanyID = 2 and ParameterName in (";
		for (String string : paramers) {
			sql +='"'+ string+'"'+",";
		}
		sql=sql.substring(0, sql.length()-1)+")";
		List<Map<String, Object>> res = jdbcTemplate.queryForList(sql);
		if (paramers.size()!=res.size()) {
			 logger.error("参数名错误>>"+sql);
			 AppUtils.paramerNotExist();
		}
		resp.set("data", res);
		return resp;
	}

	@Override
	public IRespParam updateParamer(int companyID,ArrayList<Map<String, String>> paramerEntrys)
			throws Exception {
		ArrayList<String> paramNameList = new ArrayList<String>();
		ArrayList<String> paramValueList = new ArrayList<String>();
		for (Map<String, String> entry : paramerEntrys) {
			paramNameList.add(entry.get("parametername"));
			paramValueList.add(entry.get("parametervalue"));
		}
		List<Parameter> parameters = parameterRepository.findByCompanyIDAndParameterNameIn(companyID,paramNameList);
		if (parameters.size()!=paramNameList.size()) {
			 logger.error("参数名错误>>"+paramNameList.toString());
			 AppUtils.paramerNotExist();
		}
		for (int i = 0; i < parameters.size(); i++) {
			int index = paramNameList.indexOf(parameters.get(i).getParameterName());
			parameters.get(i).setParameterValue(paramValueList.get(index));
			parameters.get(i).setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
		}
		parameterRepository.save(parameters);
		DynamicRespParam resp = new DynamicRespParam();
		return resp;
	}

	@Override
	public IRespParam updateCompanyPsw(int companyID, String newcompanypsw)
			throws Exception {
		companyRepository.setPSWDByCompanyID(companyID, newcompanypsw);
		DynamicRespParam respParam = new DynamicRespParam();
		return respParam;
	}

	@Override
	public IRespParam applyForMember(Company company,int wechatPubinfoID, Dto otherParam)
			throws Exception {
		BaseDto params = new BaseDto();
		String kmID = wechatMqUtilsService.createKmCardId();
		params.put("gradeid", otherParam.getAsInteger("gradeid"));
		params.put("kmid",kmID);
		params.put("cardnum", "");
		params.put("memberpsw", "");
		params.put("membername", otherParam.getAsString("membername"));
		params.put("sex", otherParam.getAsString("sex"));
		params.put("papertype", otherParam.getAsString("papertype"));
		params.put("papernumber", otherParam.getAsString("papernumber"));
		params.put("mobile", otherParam.getAsString("mobile"));
		params.put("birthday", otherParam.getAsString("birthday"));
		params.put("address", otherParam.getAsString("address"));
		params.put("email", otherParam.getAsString("email"));
		String cmdid="WJ009";
		CommonParam mqResp = wechatMqUtilsService.invokeMqService(cmdid, company.getCompanyCode(), wechatPubinfoID, params);
		int ret = mqResp.getAsInteger("ret");
		if (AppUtils.checkSuccess(ret)) {
			//申请成功后更新关系表
			bindMemberRelation(company.getCompanyID(),otherParam.getAsInteger("customerid"),kmID);
		}
		return mqResp;
	}

	@Override
	public IRespParam applyMemberLevel(int companyID) throws Exception {
		byte onlineApp = 0;
		Gradecollect gradecollect = gradecollectRepository.findFirstByCompanyIDAndUseOnlineApp(companyID,onlineApp);
		DynamicRespParam resp = new DynamicRespParam();
		//文件服务器地址
		Parameter parameter = parameterRepository.findFirstByParameterName(AppConstants.FILE_DWONLOAD_PATH);
		String fileDownload = parameter.getParameterValue();
		String cardpicurl = "";
		if (isNotEmpty(gradecollect.getCardPicID())) {
			cardpicurl = fileDownload + "?fileid="+ gradecollect.getCardPicID();
		}
		Dto dto = new BaseDto();
		dto.put("gradeid", gradecollect.getGradeID());
		dto.put("gradename", gradecollect.getGradeName());
		dto.put("cardpicurl", cardpicurl);
		dto.put("memberrights", gradecollect.getMemberRights());
		resp.set("data", dto);
		return resp;
	}
	
}
