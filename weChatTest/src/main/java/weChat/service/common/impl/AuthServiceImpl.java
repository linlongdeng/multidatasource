package weChat.service.common.impl;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Interfacecheck;
import weChat.parameter.IRespParam;
import weChat.parameter.common.AuthReqParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.InterfacecheckRepository;
import weChat.service.common.AuthService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

@Service
public class AuthServiceImpl implements AuthService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** token长度 **/
	private static final int TOKEN_LENGTH = 30;
	/** token有效时间 **/
	private static final int EXPIRE_HOUR = 12;

	private static final int EXPIRE_TIME = EXPIRE_HOUR * 60 * 60;
	@Autowired
	private InterfacecheckRepository interfacecheckRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Override
	public IRespParam getAccessToken(AuthReqParam param) {
		int appid = 0;
		//管理系统
		if(AppConstants.GRANT_TYPE_MANAGE.equals(param.getGranttype())){
			Company company = companyRepository.findFirstByCompanyCode(param.getCompanycode());
			//商家不能为空
			AppUtils.assertCompanyNotNull(company);
			appid= company.getCompanyID();
		}
		//K米
		else{
			appid = param.getAppid();
		}
		String appkey = param.getAppkey();
		Interfacecheck interfacecheck = interfacecheckRepository
				.findFirstByAppIDAndAppKey(appid, appkey);
		// interfacecheck不能为空
		AppUtils.assertInterfacecheckNotNull(interfacecheck);
		//采用随机数字方式生成token
		String accessToken = CommonUtils.getRandomString(TOKEN_LENGTH);
		interfacecheck.setAccessToken(accessToken);
		Timestamp expireTime = CommonUtils.getTimestampAddHour(EXPIRE_HOUR);
		interfacecheck.setKeyExpireTime(expireTime);
		interfacecheck.setUpdateDateTime(CommonUtils.currentTimestamp());
		DynamicRespParam resp = new DynamicRespParam();
		resp.set("access_token", accessToken);
		resp.set("expires_in", EXPIRE_TIME);
		logger.debug("获取token,结果是:{}",resp);
		return resp;
	}

	@Override
	public boolean checkAccessToken(String accessToken) {
		logger.debug("真正进入校验过程, accesstoken:{}",accessToken );
		//遇到这个token就放过
		if("e24df12a81fd814017980d0c1fb2f968".equals(accessToken)){
			return true;
		}
		Interfacecheck interfacecheck = interfacecheckRepository
				.findFirstByAccessToken(accessToken);
		if (interfacecheck != null) {
			Timestamp expireTime = interfacecheck.getKeyExpireTime();
			// 先判断是否失效
			if (expireTime.compareTo(CommonUtils.currentTimestamp()) >= 0) {
				return interfacecheck.getAccessToken().equals(accessToken);
			}
		}

		return false;
	}

}
