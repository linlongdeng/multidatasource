package weChat.http;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;
import weChat.parameter.manager.MInfoReqParam;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConnectionManager {
	private static final Logger logger = LoggerFactory
			.getLogger(ConnectionManager.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static PoolingHttpClientConnectionManager cm = null;
	/*
	 * public static void main(String[] args) throws InterruptedException,
	 * ExecutionException, IOException { HttpClientContext context =
	 * HttpClientContext.create(); HttpClientConnectionManager connMrg = new
	 * BasicHttpClientConnectionManager(); HttpRoute route = new HttpRoute(new
	 * HttpHost("localhost", 80)); // Request new connection. This can be a long
	 * process ConnectionRequest connRequest = connMrg.requestConnection(route,
	 * null); // Wait for connection up to 10 sec HttpClientConnection conn =
	 * connRequest.get(10, TimeUnit.SECONDS); try { // If not open if
	 * (!conn.isOpen()) { // establish connection based on its route info
	 * connMrg.connect(conn, route, 1000, context); // and mark it as route
	 * complete connMrg.routeComplete(conn, route, context); } // Do useful
	 * things with the connection. } finally { connMrg.releaseConnection(conn,
	 * null, 1, TimeUnit.MINUTES); }
	 * 
	 * }
	 */
	static {
		cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);

	}

	public static <T> T post(String url, Object requestParam,
			Class<T> valueType, RequestConfig config) throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom()
				.setConnectionManager(cm).build();
		try {
			HttpPost httpPost = new HttpPost(url);
			// 设置自定义请求配置
			httpPost.setConfig(config);
			String sParam = mapper.writeValueAsString(requestParam);
			logger.debug("发送http请求，url:{} ,参数: {}", url, sParam);
			HttpEntity entity = new StringEntity(sParam,
					ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity respEntity = response.getEntity();
					InputStream inputStream = respEntity.getContent();
					T value = mapper.readValue(inputStream, valueType);
					EntityUtils.consume(respEntity);
					logger.debug("发送HTTP请求成功,返回参数是：{}", value);
					return value;
				}else{
					System.out.println("错误状态码是" + statusLine.getStatusCode());
				}
			} finally {
				response.close();
			}

		} finally {
			httpclient.close();
		}
		return null;
	}


	@Test
	public void testHttpConnect() throws Exception {
		long beginTime = System.currentTimeMillis();
		long endTime = 0;
		try {
			String ip = "http://192.168.74.35:3003";
			String actionPath ="/Company/company_info";
			MInfoReqParam param = new MInfoReqParam();
			param.setCompanycode("01103");
			param.setAccess_token("e24df12a81fd814017980d0c1fb2f968");
			param.setWechatpubinfoid(43423432);
			BaseDto pDto = post(ip + actionPath, param,
					BaseDto.class, HttpClientUtils.getSimleRequestConfig());
			System.out.println(pDto);
		} catch (Exception e) {
		e.printStackTrace();
			System.out.println("运行时间为：" + (endTime - beginTime) / 1000 + "s");
		}
	}
}
