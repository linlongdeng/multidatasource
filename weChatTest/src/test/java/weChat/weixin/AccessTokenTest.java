package weChat.weixin;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;

public class AccessTokenTest {

	@Test
	public void testAccessToken() throws Exception{
		String ip ="https://api.weixin.qq.com/cgi-bin/token";
		Dto pDto = new BaseDto();
		pDto.put("grant_type", "client_credential");
		pDto.put("appid", "wx3f9686ecfb0ae084");
		pDto.put("secret", "995de6ecd18727d7d6d0e7fb8afc32e4");
		BaseDto respDto = HttpClientUtils.get(ip, pDto, BaseDto.class, null);
		System.out.println("返回参数是" + respDto);
	}
}
