package weChat.http;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class PoolingHttpClientConnectionManagerTest {

	public static final int SOCKETTIMEOUT = 6000;
	/**
	 * Determines the timeout in milliseconds until a connection is established.
	 */
	public static final int CONNECTTIMEOUT = 6000;

	public static final int REQUEST_TIME_OUT = 6000;

	private static RequestConfig requestConfig;

	public static void main(String[] args) throws InterruptedException {
		long begintime = System.currentTimeMillis();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		requestConfig = RequestConfig.custom().setSocketTimeout(SOCKETTIMEOUT)
				.setConnectTimeout(CONNECTTIMEOUT)
				.setConnectionRequestTimeout(REQUEST_TIME_OUT).build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm).build();
		// URIs to perform GETs on
		String[] urisToGet = { "http://www.baidu.com/" };

		// create a thread for each URI
		int times = 1000;
		GetThread[] threads = new GetThread[times];
		for (int i = 0; i < times; i++) {
			HttpGet httpget = new HttpGet(urisToGet[i % urisToGet.length]);
			httpget.setConfig(requestConfig);
			connect(httpClient, httpget, i);
		}

		/*// start the threads
		for (int j = 0; j < threads.length; j++) {
			threads[j].start();
		}

		// join the threads
		for (int j = 0; j < threads.length; j++) {
			try {
				threads[j].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/
		System.out.println("程序运行结束");
		long endtime = System.currentTimeMillis();
		System.out.println("总的运行时间为" + (endtime - begintime) / 1000);
	}

	static class GetThread extends Thread {

		private final CloseableHttpClient httpClient;
		private final HttpContext context;
		private final HttpGet httpget;

		private final int num;

		public GetThread(CloseableHttpClient httpClient, HttpGet httpget,
				int num) {
			this.httpClient = httpClient;
			this.context = HttpClientContext.create();
			this.httpget = httpget;
			this.num = num;
		}

		@Override
		public void run() {
			try {
				CloseableHttpResponse response = httpClient.execute(httpget,
						context);
				try {
					HttpEntity entity = response.getEntity();
					String body = EntityUtils.toString(entity);
					System.out.println("第" + num + "运行完成");
				} finally {
					response.close();
				}
			} catch (ConnectionPoolTimeoutException ex) {
				System.err.println("第" + num + "运行失败");
			} catch (SocketTimeoutException ex) {
				System.err.println("第" + num + "运行Read timed out:"
						+ httpget.getURI());
			}

			catch (ClientProtocolException ex) {
				// Handle protocol errors
				ex.printStackTrace();
			} catch (IOException ex) {
				// Handle I/O errors
				ex.printStackTrace();
			}
		}

	}

	public static void connect(CloseableHttpClient httpClient, HttpGet httpget, int num) {
		try {
			HttpClientContext context = HttpClientContext.create();
			CloseableHttpResponse response = httpClient.execute(httpget,
					context);
			try {
				HttpEntity entity = response.getEntity();
				String body = EntityUtils.toString(entity);
			} finally {
				response.close();
			}
		} catch (ConnectionPoolTimeoutException ex) {
			System.err.println("第" + num + "运行失败");
		} catch (SocketTimeoutException ex) {
			System.err.println("第" + num + "运行Read timed out:"
					+ httpget.getURI());
		}

		catch (ClientProtocolException ex) {
			// Handle protocol errors
			ex.printStackTrace();
		} catch (IOException ex) {
			// Handle I/O errors
			ex.printStackTrace();
		}
	}
}
