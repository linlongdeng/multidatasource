package weChat.web;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.km.KSmsReqParam;

public class InvokeKmControllerTest {
	private String ip = "http://192.168.82.119:3003";
	
	@Test
	public void testsendSms() throws Exception{
		String actionPath = "/InvokeKm/sendSms";
		KSmsReqParam param = new KSmsReqParam();
		param.setPhoneno("18960863890");
		param.setValidcode("123456");
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println("返回参数是：" + pDto );
		
	}
	@Test
	public void testGetAccessTokenDirect() throws Exception{
		String ip ="http://192.168.84.176:8103";
		String actionPath="/Auth/access_token";
		Dto pDto = new BaseDto();
		pDto.put("granttype", "clientcredentials");
		pDto.put("appid", "11");
		pDto.put("appkey", "8930ec48a3fed32047a9fcda127db378");
		pDto.put("scope", "");
		
		DynamicRespParam resp = HttpClientUtils.postProxy(ip + actionPath,
				pDto, DynamicRespParam.class);
		System.out.println(resp);
	}
	@Test
	public void testSMS() throws Exception{
		String ip ="http://192.168.84.176:8103";
		String actionPath="/Sms/send_template";
		Dto pDto = new BaseDto();
		pDto.put("access_token", "efa810814017980d0c1fb2f968423152");
		pDto.put("phoneno", "18960863890");
		Dto nestedDto = new BaseDto();
		nestedDto.put("validcode","123456");
		pDto.put("sms_content", nestedDto);
		
		DynamicRespParam resp = HttpClientUtils.postProxy(ip + actionPath,
				pDto, DynamicRespParam.class);
		System.out.println(resp);
	}
}
