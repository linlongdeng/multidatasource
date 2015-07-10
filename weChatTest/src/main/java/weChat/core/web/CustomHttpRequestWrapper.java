package weChat.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.http.MediaType;

/**
 * 自定义HttpRequest的包装类，用于适应不标识的content-type
 * @author deng
 * @date 2015年6月25日
 * @version 1.0.0
 */
public class CustomHttpRequestWrapper  extends HttpServletRequestWrapper{
	
	private static final String DEFAULT_CONTENT_TYPE ="application/json;charset=UTF-8";

	public CustomHttpRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getContentType() {
		return DEFAULT_CONTENT_TYPE;
	}

}
