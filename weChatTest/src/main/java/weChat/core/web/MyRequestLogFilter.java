package weChat.core.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import weChat.core.utils.CommonUtils;

/**
 * 过滤请求过滤日志类
 * 
 * @author deng
 * @date 2015年6月24日
 * @version 1.0.0
 */
public class MyRequestLogFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**请求内容编码格式**/
	public static String CHARSET_NAME_UTF_8="utf-8"; 

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 只有处于调试模式才会打印请求参数
		if (logger.isDebugEnabled()) {
			logger.debug("原来的contentType是{}", request.getContentType());
			String requestURI = request.getRequestURI();
			//作request的包装类，解决request.getInputStream()只能获取一次的问题
			ByteHttpServletRequestWrapper requestWrapper = new ByteHttpServletRequestWrapper(request);
			ServletInputStream inputStream = requestWrapper.getInputStream();
			byte[] bytes = CommonUtils.readBytes(inputStream);
			 String requestParam = new String(bytes,CHARSET_NAME_UTF_8);
			logger.debug("请求地址为:{}, 请求参数为:{}", requestURI, requestParam);
			
			filterChain.doFilter(requestWrapper, response);
		}
		//非调试请求只是修改request的contenttype， 为了修改，必须作一个的reqeust的包装类
		else{
			CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper(request);
			filterChain.doFilter(requestWrapper, response);
		}

	}

}
