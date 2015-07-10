package weChat.http;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.http.PoolingHttpClientConnectionManagerTest.GetThread;

public class HttpClientTest {
	public static final int SOCKETTIMEOUT = 6000;
	/**
	 * Determines the timeout in milliseconds until a connection is established.
	 */
	public static final int CONNECTTIMEOUT = 6000;

	public static final int REQUEST_TIME_OUT = 6000;
	
	private static RequestConfig requestConfig;
	public static void main(String[] args) {
		long begintime = System.currentTimeMillis();

		requestConfig = RequestConfig.custom().setSocketTimeout(SOCKETTIMEOUT)
				.setConnectTimeout(CONNECTTIMEOUT)
				.setConnectionRequestTimeout(REQUEST_TIME_OUT).build();

		// URIs to perform GETs on
		String[] urisToGet = { "http://www.baidu.com/"};

		// create a thread for each URI
		int times = 1000;
		for (int i = 0; i < times; i++) {
			HttpGet httpget = new HttpGet(urisToGet[i % urisToGet.length]);
			httpget.setConfig(requestConfig);
			try {
				get(httpget);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	

		System.out.println("程序运行结束");
		long endtime = System.currentTimeMillis();
		System.out.println("总的运行时间为" + (endtime - begintime)/1000);
	}
	public static void get(HttpGet httpGet)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			String responseBody = httpclient.execute(httpGet,
					getStringResponseHandler());

		} finally {
			 httpclient.close();
		}
	}

	/**
	 * 返回String类型的ResponseHanlder
	 * 
	 * @author deng
	 * @return
	 */
	private static ResponseHandler<String> getStringResponseHandler() {
		ResponseHandler<String> responseHandler = null;
		// 使用lambda表达式
		responseHandler = (response) -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? CommonUtils.readString(entity
						.getContent()) : null;
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		};
		return responseHandler;
	}
	static class GetThread extends Thread {

		private final HttpGet httpget;

		private final int num;

		public GetThread(HttpGet httpget,
				int num) {
			this.httpget = httpget;
			this.num = num;
		}

		@Override
		public void run() {
			try {
				get(httpget);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
