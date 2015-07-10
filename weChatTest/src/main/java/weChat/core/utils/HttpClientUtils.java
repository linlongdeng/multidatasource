package weChat.core.utils;

import java.net.URI;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weChat.core.metatype.Dto;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * HTTP工具类
 * 
 * @author deng
 * @date 2015年5月28日
 * @version 1.0.0
 */
public class HttpClientUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(HttpClientUtils.class);
	/** UTF-8 **/
	public static final String UTF_8 = "utf-8";
	private static ObjectMapper mapper = new ObjectMapper();;

	private static RequestConfig requestConfig;
	/** Defines the socket timeout (SO_TIMEOUT) in milliseconds, **/
	public static final int SOCKETTIMEOUT = 6000;
	/**
	 * Determines the timeout in milliseconds until a connection is established.
	 */
	public static final int CONNECTTIMEOUT = 6000;
	static{
		if (requestConfig == null) {
			requestConfig = RequestConfig.custom()
					.setSocketTimeout(SOCKETTIMEOUT)
					.setConnectTimeout(CONNECTTIMEOUT).build();
		}
	}

	/**
	 * 把请求发送fiddler代理服务器
	 * 
	 * @param url
	 * @param requestParam
	 * @param valueType
	 * @return
	 * @throws Exception
	 */
	public static <T> T postProxy(String url, Object requestParam,
			Class<T> valueType) throws Exception {
		HttpHost proxyHost = new HttpHost("127.0.0.1", 8888);
		RequestConfig config = RequestConfig.custom().setProxy(proxyHost)
				.build();
		return post(url, requestParam, valueType, config);
	}

	/**
	 * 发送http post application/json请求
	 * 
	 * @param url
	 * @param requestParam
	 * @param valueType
	 * @return
	 * @throws Exception
	 */
	public static <T> T post(String url, Object requestParam, Class<T> valueType)
			throws Exception {
		// 设置超时时间
		return post(url, requestParam, valueType, getSimleRequestConfig());
	}

	/**
	 * 简单的生成RequestConfig实例
	 * 
	 * @return
	 */
	public static  RequestConfig getSimleRequestConfig() {
		return requestConfig;
	}

	public static <T> T post(String url, Object requestParam,
			Class<T> valueType, RequestConfig config) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			// 设置自定义请求配置
			httpPost.setConfig(config);
			String sParam = mapper.writeValueAsString(requestParam);
			logger.debug("发送http请求，url:{} ,参数: {}", url, sParam);
			HttpEntity entity = new StringEntity(sParam,
					ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			String responseBody = httpclient.execute(httpPost,
					getStringResponseHandler());
			logger.debug("发送http请求成功，返回参数是:{}", responseBody);
			T value = mapper.readValue(responseBody, valueType);
			logger.debug("Http返回参数转换成对象成功");
			return value;
		} finally {
			httpclient.close();
		}

	}

	public static <T> T get(String url, Dto pDto, Class<T> valueType,
			RequestConfig config) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			if (CommonUtils.isNotEmpty(pDto)) {
				Set<String> keySet = pDto.keySet();
				for (String name : keySet) {
					String value = pDto.getAsString(name);
					uriBuilder.setParameter(name, value);
				}
			}
			URI uri = uriBuilder.build();
			logger.debug("发送http请求，url:{} ", uri);
			HttpGet httpGet = new HttpGet(uri);
			httpGet.setConfig(config);
			String responseBody = httpclient.execute(httpGet,
					getStringResponseHandler());
			logger.debug("发送http请求成功，返回参数是:{}", responseBody);
			T value = mapper.readValue(responseBody, valueType);
			logger.debug("Http返回参数转换成对象成功");
			return value;

		} finally {
			httpclient.close();
		}
	}

	/**
	 * 发送http协议服务
	 * @author deng
	 * @param url
	 * @param pDto
	 * @param valueType
	 * @return
	 * @throws Exception
	 */
	public static <T> T get(String url, Dto pDto, Class<T> valueType)
			throws Exception {
		return get(url, pDto, valueType, getSimleRequestConfig());
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
				return entity != null ? CommonUtils.readString(entity.getContent()) : null;
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		};
		return responseHandler;
	}
}
