package weChat.web;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;

public class MyControllerTest {
	private String ip = "http://127.0.0.1:3003";
	
	@Test
	public void testSecondData() throws Exception{
		String actionPath = "/test/testSecondData";
		Dto param = new BaseDto();
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
	}
}
