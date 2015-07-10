package weChat.service.weixin.impl;

import static weChat.utils.AppConstants.WEIXIN_ERRCODE;
import static weChat.utils.AppConstants.WEXIN_ERRMSG;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.core.utils.HttpClientUtils;
import weChat.domain.primary.Sendmsgdata;
import weChat.domain.primary.Sendmsglist;
import weChat.domain.primary.Wechatpubinfo;
import weChat.parameter.IRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.repository.primary.WechatpubinfoRepository;
import weChat.service.weixin.WeixinInfoService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;
import weChat.utils.RespUtils;

@Service
@ConfigurationProperties(prefix = WeixinInfoServiceImpl.WEIXIN_PREFIX)
public class WeixinInfoServiceImpl implements WeixinInfoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String WEIXIN_PREFIX = "weixin";
	/** 微信的access_token **/
	private String accessTokenURI;
	/** 微信的jsapi_ticket **/
	private String jsTicketUR;
	/** 模板地址 **/
	private String MODEL_MESSAGE_URL;
	/** 参数二维码地址 **/
	private String QRCODE_URL;
	@Autowired
	private WechatpubinfoRepository wechatpubinfoRepository;

	/** access_token距离过期时间需要多久 **/
	public static final int EXPIRE_TIME = 10 * 60 ;

	@Override
	public synchronized IRespParam getAccessToken(Wechatpubinfo wechatpubinfo)
			throws Exception {
		String appID = wechatpubinfo.getAppID();
		String appSecret = wechatpubinfo.getAppSecret();
		// 判断access_token是否过期
		if (wechatpubinfo.getExpiretokentime() - CommonUtils.getCurrentTime()
				- EXPIRE_TIME > 0) {
			DynamicRespParam respParam = new DynamicRespParam();
			respParam.set("accesstoken", wechatpubinfo.getAccess_token());
			respParam.set("ticket", wechatpubinfo.getJsTicket());
			return respParam;
		}
		// 已经过期
		else {
			// 获取accessToken
			Dto rDto = getAccessToken(appID, appSecret);
			DynamicRespParam respParam = new DynamicRespParam();
			// 先判断接口调用是否成功
			if (AppUtils.checkWeixinApi(rDto)) {
				String access_token = rDto.getAsString("access_token");
				wechatpubinfo.setAccess_token(access_token);
				// 超时时间,这里头时间是秒
				int expire_in = rDto.getAsInteger("expires_in");
				int expireTimeStamp =  CommonUtils.getCurrentTime() + expire_in;
				wechatpubinfo.setExpiretokentime(expireTimeStamp);
				respParam.set("accesstoken", access_token);
				// 获取ApiTicket
				rDto = getApiTicket(access_token);
				// 判断接口调用是否成功
				if (AppUtils.checkWeixinApi(rDto)) {
					String ticket = rDto.getAsString("ticket");
					respParam.set("ticket", ticket);
					wechatpubinfo.setJsTicket(ticket);
					// 保存access_token到数据库
					wechatpubinfoRepository.save(wechatpubinfo);
					return respParam;
				}
			}
			throw new RuntimeException("出现未知错误");
		}

	}

	public String getAccessTokenURI() {
		return accessTokenURI;
	}

	public void setAccessTokenURI(String accessTokenURI) {
		this.accessTokenURI = accessTokenURI;
	}

	public String getJsTicketUR() {
		return jsTicketUR;
	}

	public void setJsTicketUR(String jsTicketUR) {
		this.jsTicketUR = jsTicketUR;
	}

	public String getQRCODE_URL() {
		return QRCODE_URL;
	}

	public void setQRCODE_URL(String qRCODE_URL) {
		QRCODE_URL = qRCODE_URL;
	}

	@Override
	public Dto getAccessToken(String appid, String secret) throws Exception {
		BaseDto pDto = new BaseDto();
		pDto.put("grant_type", "client_credential");
		pDto.put("appid", appid);
		pDto.put("secret", secret);
		BaseDto rDto = HttpClientUtils.get(accessTokenURI, pDto, BaseDto.class,
				HttpClientUtils.getSimleRequestConfig());
		return rDto;
	}

	@Override
	public Dto getApiTicket(String accessToken) throws Exception {

		BaseDto pDto = new BaseDto();
		pDto.put("access_token", accessToken);
		pDto.put("type", "jsapi");
		BaseDto rDto = HttpClientUtils.get(jsTicketUR, pDto, BaseDto.class,
				HttpClientUtils.getSimleRequestConfig());
		return rDto;
	}

	public String getMODEL_MESSAGE_URL() {
		return MODEL_MESSAGE_URL;
	}

	public void setMODEL_MESSAGE_URL(String mODEL_MESSAGE_URL) {
		MODEL_MESSAGE_URL = mODEL_MESSAGE_URL;
	}

	@Override
	public List<Sendmsglist> sendModelMessage(List<BaseDto> dtoList,
			List<Sendmsgdata> msgData, Wechatpubinfo wechatpubinfo)
			throws Exception {
		List<Sendmsglist> sendmsglist = new ArrayList<Sendmsglist>();
		if (!CommonUtils.isEmpty(dtoList)) {
			DynamicRespParam accessTokenParam = (DynamicRespParam) getAccessToken(wechatpubinfo);
			String accessTokenString = accessTokenParam.getAsString("accesstoken");
			MODEL_MESSAGE_URL += "?access_token=" + accessTokenString;
			for (BaseDto baseDto : dtoList) {
				Sendmsglist sendmsg = new Sendmsglist();
				for (Sendmsgdata msgdata : msgData) {
					if (msgdata.getUniqueCode().equals(baseDto.get("uuid"))) {
						baseDto.remove("uuid");
						BaseDto rDto = HttpClientUtils.post(MODEL_MESSAGE_URL,
								baseDto, BaseDto.class);
						sendmsg.setCompanyID(msgdata.getCompanyID());
						sendmsg.setKmid(msgdata.getKmid());
						Timestamp now = CommonUtils.currentTimestamp();
						sendmsg.setSendDateTime(now);
						sendmsg.setSendType(msgdata.getDataType());
						sendmsg.setErrCode((int) rDto.get("errcode"));
						if (AppConstants.WEIXIN_SUCCESS==((int)rDto.get("errcode"))) {
							sendmsg.setMsgID((int) rDto.get("msgid"));
						}
						sendmsglist.add(sendmsg);
						msgdata.setSendDateTime(now);
						msgdata.setStatus(AppConstants.SEND);
					}
				}
			}
		}
		return sendmsglist;
	}

	@Override
	public Dto getParameQrCode(BaseDto baseDto, Wechatpubinfo wechatpubinfo)
			throws Exception {
		DynamicRespParam accessTokenParam = (DynamicRespParam) getAccessToken(wechatpubinfo);
		String accessTokenString = accessTokenParam.getAsString("accesstoken");
		QRCODE_URL += "?access_token=" + accessTokenString;
		BaseDto rDto = HttpClientUtils.post(QRCODE_URL,baseDto, BaseDto.class);
		return rDto;
	}

	

}
