package weChat.km;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;
import weChat.json.PostJsonUtils;
import weChat.parameter.common.DynamicRespParam;

public class KmInvokeTest {

	final String ip = "http://127.0.0.1:8080/weChat";
	public static final String TOKEN_NAME = "access_token";
	@Test
	public void testAccess_token() throws JsonGenerationException,
			JsonMappingException, IOException {
		PostJsonUtils.ip = ip;
		String actionPath = "/getKmAuth";
		Dto dto = new BaseDto();
		Map<String, Object> map = PostJsonUtils.postObject(actionPath, dto);
		System.out.println(map);
	}
	@Test
	public void testGetKmAuth() throws Exception{
		String ip ="http://127.0.0.1:8080/3003";
		String actionPath="/getKmAuth";
		Dto dto = new BaseDto();
		DynamicRespParam resp = HttpClientUtils.post(ip + actionPath, dto, DynamicRespParam.class);
		System.out.println("返回结果是" + resp);
	}

	@Test
	public void Company_lists() throws JsonGenerationException,
			JsonMappingException, IOException {
		PostJsonUtils.ip = ip;
		String actionPath = "/Company/lists";
		Dto dto = new BaseDto();
		dto.put("access_token", "efa810814017980d0c1fb2f968423152");
		Map<String, Object> map = PostJsonUtils.postObject(actionPath, dto);
	}

	@Test
	public void testPostCompany_lists() throws Exception {
		long startTime = System.currentTimeMillis();
		String ip ="http://192.168.84.176:8103/";
		String actionPath = "/Company/lists";
		String url = ip + actionPath;
		Dto param = new BaseDto();
		param.put("access_token", "efa810814017980d0c1fb2f968423152");
		BaseDto result = HttpClientUtils.<BaseDto> postProxy(url, param, BaseDto.class);
		List list = result.getAsList("res");
		System.out.println("list 大小是" + list.size());
		long endTime = System.currentTimeMillis();
		System.out.println("花费的时间是" + (endTime - startTime) / 1000 + "s");
	}
	@Test
	public void testphpRegisterByPhone() throws Exception{
		String ip ="http://192.168.74.35:3003";
		String actionPath = "/InvokeKm/registerByPhone";
		Dto param = new BaseDto();
		param.put("phoneno", "18960863890");
		String url = ip + actionPath;
		BaseDto result = HttpClientUtils.<BaseDto> postProxy(url, param, BaseDto.class);
		System.out.println(result);
	}
	@Test
	public void testSaveAllCompanyFromKm() throws Exception{
		long startTime = System.currentTimeMillis();
		String actionPath = "/saveAllCompanyFromKm";
		String url = ip + actionPath;
		Dto param = new BaseDto();
		Map result = HttpClientUtils.post(url, param, Map.class);
		long endTime = System.currentTimeMillis();
		System.out.println("花费的时间是" + (endTime - startTime) / 1000 + "s");
		System.out.println(result);
	}
	
	@Test
	public void testRegisterByPhone() throws Exception{
		String actionPath="Customer/register";
		String ip ="http://192.168.84.176:8103/";
		Dto pDto = new BaseDto();
		pDto.put("phoneno", "18960863891");
		//pDto.put("kmid", "123456789");
		pDto.put(TOKEN_NAME, "efa810814017980d0c1fb2f968423152");
		DynamicRespParam resp = HttpClientUtils.post(
				ip + actionPath, pDto, DynamicRespParam.class);
		System.out.println(resp);
	}

}
