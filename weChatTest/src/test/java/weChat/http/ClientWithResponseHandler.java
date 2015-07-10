package weChat.http;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.core.utils.HttpClientUtils;
import weChat.parameter.manager.MInfoReqParam;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientWithResponseHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(ClientWithResponseHandler.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static RequestConfig requestConfig;
	/**Defines the socket timeout (SO_TIMEOUT) in milliseconds,**/
	public static final int SOCKETTIMEOUT= 6000;
	/**Determines the timeout in milliseconds until a connection is established.*/
	public static final int CONNECTTIMEOUT = 6000;
	
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
	 * 简单的生成RequestConfig实例
	 * 
	 * @return
	 */
	public static RequestConfig getSimleRequestConfig() {
		if (requestConfig == null) {
			requestConfig = RequestConfig.custom().setSocketTimeout(SOCKETTIMEOUT)
					.setConnectTimeout(CONNECTTIMEOUT).build();
		}
		return requestConfig;
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
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		};
		return responseHandler;
	}
	@Test
	public void testResponseHandler() throws Exception{
		String ip = "http://192.168.74.35:3003";
		String actionPath ="/Company/company_info";
		MInfoReqParam param = new MInfoReqParam();
		param.setCompanycode("01103");
		param.setAccess_token("e24df12a81fd814017980d0c1fb2f968");
		param.setWechatpubinfoid(43423432);
		BaseDto pDto = post(ip + actionPath, param,
				BaseDto.class, getSimleRequestConfig());
		System.out.println(pDto);
	}
	@Test
	public void testGet() throws Exception{
		String ip ="https://api.weixin.qq.com/cgi-bin/token";
		Dto pDto = new BaseDto();
		pDto.put("grant_type", "client_credential");
		pDto.put("appid", "wx3f9686ecfb0ae084");
		pDto.put("secret", "995de6ecd18727d7d6d0e7fb8afc32e4");
		BaseDto respDto = get(ip, pDto, BaseDto.class, null);
		System.out.println("返回参数是" + respDto);
	}
}
