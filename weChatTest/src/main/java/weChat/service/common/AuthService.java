package weChat.service.common;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import weChat.parameter.IRespParam;
import weChat.parameter.common.AuthReqParam;
import weChat.utils.AppConstants;

public interface AuthService {
	/**
	 * 获取获取授权码
	 * 
	 * @param param
	 * @return
	 */
	@Transactional
	public IRespParam getAccessToken(AuthReqParam param);

	/**
	 * 检查accessToken是否合法，如果合法返回true,否则返回false，使用了缓存，缓存有效时间为5分钟
	 * 
	 * @param accessToken
	 * @return
	 */
	@Cacheable(AppConstants.CACHE_WJ_TOKEN)
	public boolean checkAccessToken(String accessToken);

}
