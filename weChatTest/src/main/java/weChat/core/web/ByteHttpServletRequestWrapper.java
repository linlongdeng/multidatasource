package weChat.core.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import weChat.core.utils.CommonUtils;

/**
 * HttpReqeuest包装类,用于重复读取请求内容
 * 
 * @author deng
 * @date 2015年6月25日
 * @version 1.0.0
 */
public class ByteHttpServletRequestWrapper extends HttpServletRequestWrapper {
	/**默认的contentType**/
	private static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

	@Override
	public String getContentType() {
		//return super.getContentType() != null ? DEFAULT_CONTENT_TYPE: null;
		return DEFAULT_CONTENT_TYPE;
	}

	private final byte[] body; // 报文

	public ByteHttpServletRequestWrapper(HttpServletRequest request)
			throws IOException {
		super(request);
		body = CommonUtils.readBytes(request.getInputStream());
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);
		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// TODO Auto-generated method stub

			}
		};
	}

}