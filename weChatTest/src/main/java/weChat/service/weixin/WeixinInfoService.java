package weChat.service.weixin;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.domain.primary.Sendmsgdata;
import weChat.domain.primary.Sendmsglist;
import weChat.domain.primary.Wechatpubinfo;
import weChat.parameter.IRespParam;
import weChat.parameter.weixin.WReqParam;
import weChat.utils.AppConstants;

public interface WeixinInfoService {

	/**
	 * 获取微信access_token和jsapi_ticket
	 * 
	 * @param param
	 * @return
	 */
	@Transactional(propagation=Propagation.NESTED)
	@Cacheable(value = AppConstants.CACHE_WEIXIN, key = "#p0.wechatPubInfoID")
	public IRespParam getAccessToken(Wechatpubinfo wechatpubinfo)  throws Exception;

	/**
	 * 通过Https协议向微信接口请求access_token
	 * 
	 * @param appid
	 * @param secret
	 * @return
	 */
	public Dto getAccessToken(String appid, String secret) throws Exception;

	/**
	 * 通过https协议向微信请求请求jsapi_ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public Dto getApiTicket(String accessToken) throws Exception;
	
	
	
	/**向微信发送模板消息
	 * @param dtoList	微信要求格式化的数据
	 * @param msgData	日志信息
	 * @return
	 * @throws Exception
	 */
	public List<Sendmsglist> sendModelMessage(List<BaseDto> dtoList,List<Sendmsgdata> msgData,Wechatpubinfo wechatpubinfo)throws Exception;
	
	/**获取带参数的二维码
	 * @param baseDto	按微信要求的数据
	 * @param wechatpubinfo	公众号信息
	 * @return
	 * @throws Exception
	 */
	public Dto getParameQrCode(BaseDto baseDto,Wechatpubinfo wechatpubinfo) throws Exception;
}
