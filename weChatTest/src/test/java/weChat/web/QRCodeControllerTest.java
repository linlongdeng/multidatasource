package weChat.web;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.utils.HttpClientUtils;
import weChat.parameter.common.DynamicRespParam;

public class QRCodeControllerTest {
	private String ip = "http://127.0.0.1:3003";
	private String access_token = "e24df12a81fd814017980d0c1fb2f968";
	private int wechatpubinfoid = 1;
	private String companycode = "01103";
	
	@Test
	public void testWechatPubInfoQrCode() throws Exception{
		String actionPath = "/QRCode/wechatPubInfoQrCode";
		BaseDto dto = new BaseDto();
		dto.put("wechatpubinfoid", wechatpubinfoid);
		dto.put("access_token", access_token);
		dto.put("companycode", companycode);
		DynamicRespParam resp = HttpClientUtils.post(ip + actionPath, dto, DynamicRespParam.class);
		System.out.println(resp);
	}
	
	@Test
	public void testParamQrCode() throws Exception{
		String actionPath = "/QRCode/parameQrCode";
		BaseDto dto = new BaseDto();
		dto.put("wechatpubinfoid", wechatpubinfoid);
		dto.put("access_token", access_token);
		dto.put("companycode", companycode);
		dto.put("cardnum", "5000106");
		DynamicRespParam resp = HttpClientUtils.post(ip + actionPath, dto, DynamicRespParam.class);
		
	}
	
}
