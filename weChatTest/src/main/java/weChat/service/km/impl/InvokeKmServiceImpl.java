package weChat.service.km.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;
import weChat.domain.primary.Company;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.km.KRespResParam;
import weChat.parameter.km.KSmsReqParam;
import weChat.parameter.manager.MRespParam;
import weChat.repository.primary.CompanyRepository;
import weChat.service.km.InvokeKmService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;
import weChat.utils.RespUtils;
import static weChat.core.utils.CommonUtils.*;

@Service
@ConfigurationProperties(prefix = InvokeKmServiceImpl.KM_PREFIX)
public class InvokeKmServiceImpl implements InvokeKmService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String KM_PREFIX = "km";

	public static final String TOKEN_NAME = "access_token";

	private String center_url;
	private String appkey;
	/** 获取受权码 **/
	private String accesstoken_path;
	/** 获取全部商家信息 **/
	private String getAllCompany_path;
	/** 根据商家ID获取商家信息 **/
	private String getCompanyById_path;
	/** 根据手机号码生成K米会员 **/
	private String registerMember_path;
	/** 完善K米会员资料 **/
	private String fillinfoMember_path;
	/** 发送短信 **/
	private String sendSMS_path;

	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * 获取URL
	 * 
	 * @param actionPath
	 * @return
	 */
	private String getUrl(String actionPath) {
		String url = center_url;
		// 中心url是否民/结尾
		if (!center_url.endsWith(AppConstants.SLASH)) {
			url += AppConstants.SLASH;
		}
		url += actionPath;
		return url;

	}

	@Override
	public DynamicRespParam getKmAuthDirect() throws Exception {
		Dto pDto = new BaseDto();
		pDto.put("granttype", "clientcredentials");
		pDto.put("appid", "11");
		pDto.put("appkey", appkey);
		pDto.put("scope", "");
		DynamicRespParam resp = HttpClientUtils.post(getUrl(accesstoken_path),
				pDto, DynamicRespParam.class);
		return resp;
	}

	@Cacheable(AppConstants.CACHE_DEFAULT)
	@Override
	public String getKmAccessToken() throws Exception {
		DynamicRespParam resp = getKmAuthDirect();
		String token = resp.getAsString("access_token");
		Assert.notNull(token, "获取授权码失败");
		return token;
	}

	@Override
	@Scheduled(cron = "0 37 0 * * *")
	public void saveAllCompanyFromKm() throws Exception {
		logger.info("开始同步所有K米商家信息");
		long startTime = System.currentTimeMillis();
		Dto param = new BaseDto();
		param.put(TOKEN_NAME, getKmAccessToken());
		KRespResParam resp = HttpClientUtils.post(getUrl(getAllCompany_path),
				param, KRespResParam.class);
		List<BaseDto> resList = resp.getRes();
		if (resList != null) {
			// 初步过滤非法数据
			fiterIllegalData(resList);
			List<Company> companyList = new ArrayList<>();
			for (BaseDto dto : resList) {
				int companyid = dto.getAsInteger("companyid");
				Company company = new Company();
				company.setCompanyID(companyid);
				company.setCompanyCode(dto.getAsString("companycode"));
				company.setCompanyPsw(dto.getAsString("pass"));
				company.setCompanyType(dto.getAsString("companytype"));
				company.setCompanyName(dto.getAsString("companyname"));
				company.setRegionCode(dto.getAsString("regioncode"));
				company.setCompanyAddress(dto.getAsString("companyaddress"));
				company.setCompanyMemo(dto.getAsString("companymemo"));
				company.setCompanyURL(dto.getAsString("companyurl"));
				company.setCompanyStatus(dto.getAsInteger("status").byteValue());
				company.setMapPositionX(dto.getAsString("mappositionx"));
				company.setMapPositionY(dto.getAsString("mappositiony"));
				companyList.add(company);
			}
			companyRepository.save(companyList);
		}
		long endtime = System.currentTimeMillis();
		logger.info("结束同步所有K米商家信息，花费时间是{} S", (endtime - startTime) / 1000);
	}

	/**
	 * 过滤非法数据
	 * 
	 * @param list
	 */
	public void fiterIllegalData(List<BaseDto> list) {
		if (!isEmpty(list)) {
			Iterator<BaseDto> it = list.iterator();
			while (it.hasNext()) {
				BaseDto dto = it.next();
				// 删除要用迭代器， 这样才不会有问题
				// 关键属性任意一个为空，作删除
				if (isEmpty(dto.get("companyid"), dto.get("pass"),
						dto.get("status"), dto.get("companytype"),
						dto.get("companycode"), dto.get("companyname"))) {
					it.remove();
				}
			}

		}
	}

	@Override
	public IRespParam getKmCompanyById(int companyid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRespParam registerByPhone(Dto pDto) throws Exception {
		logger.info("开始根据手机号码生成K米会员: 手机号{}", pDto.get("phoneno"));
		pDto.put(TOKEN_NAME, getKmAccessToken());
		DynamicRespParam resp = HttpClientUtils.post(
				getUrl(registerMember_path), pDto, DynamicRespParam.class);
		logger.info("完成根据手机号码生成K米会员,手机号{}，返回参数", pDto.get("phoneno"), resp);
		return resp;

	}

	@Override
	public IRespParam fillCustomerInfo(Dto pDto) throws Exception {
		pDto.put(TOKEN_NAME, getKmAccessToken());
		KRespResParam resp = HttpClientUtils.post(getUrl(fillinfoMember_path),
				pDto, KRespResParam.class);
		return resp;
	}

	@Override
	public IRespParam sendsms(KSmsReqParam param) throws Exception {
		Dto pDto = new BaseDto();
		pDto.put(TOKEN_NAME, getKmAccessToken());
		pDto.put("phoneno", param.getPhoneno());
		Dto nestedDto = new BaseDto();
		nestedDto.put("validcode", param.getValidcode());
		pDto.put("sms_content", nestedDto);
		MRespParam resp = HttpClientUtils.post(getUrl(sendSMS_path), pDto,
				MRespParam.class);
		if(AppUtils.checkSuccess(resp.getRet())){
			logger.debug("发送短信成功，手机号是{}", param.getPhoneno());
		}else{
			logger.error("发送短信失败，手机号是{}", param.getPhoneno(), "返回消息是：", resp );
		}
		return resp;
	}
	
	
	@Override
	public IRespParam pushTemplate(List<BaseDto> kmTemplateList)
			throws Exception {
		for (BaseDto baseDto : kmTemplateList) {
			baseDto.put(TOKEN_NAME, getKmAccessToken());
			MRespParam resp = HttpClientUtils.post(getUrl(sendSMS_path), baseDto,MRespParam.class);
			if(AppUtils.checkSuccess(resp.getRet())){
				logger.debug("向KM推送模板消息成功，{}", kmTemplateList);
			}else{
				logger.error("向KM推送模板消息失败，{}", kmTemplateList, "返回消息是：", resp );
			}
			return resp;
		}
		return null;
	}
	
	public String getCenter_url() {
		return center_url;
	}

	public void setCenter_url(String center_url) {
		this.center_url = center_url;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAccesstoken_path() {
		return accesstoken_path;
	}

	public void setAccesstoken_path(String accesstoken_path) {
		this.accesstoken_path = accesstoken_path;
	}

	public String getGetAllCompany_path() {
		return getAllCompany_path;
	}

	public void setGetAllCompany_path(String getAllCompany_path) {
		this.getAllCompany_path = getAllCompany_path;
	}

	public String getGetCompanyById_path() {
		return getCompanyById_path;
	}

	public void setGetCompanyById_path(String getCompanyById_path) {
		this.getCompanyById_path = getCompanyById_path;
	}

	public String getRegisterMember_path() {
		return registerMember_path;
	}

	public void setRegisterMember_path(String registerMember_path) {
		this.registerMember_path = registerMember_path;
	}

	public String getFillinfoMember_path() {
		return fillinfoMember_path;
	}

	public void setFillinfoMember_path(String fillinfoMember_path) {
		this.fillinfoMember_path = fillinfoMember_path;
	}

	public String getSendSMS_path() {
		return sendSMS_path;
	}

	public void setSendSMS_path(String sendSMS_path) {
		this.sendSMS_path = sendSMS_path;
	}

}
