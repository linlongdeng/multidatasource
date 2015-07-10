package weChat.web;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;
import weChat.parameter.km.KDynamicReqParam;

public class TemplateControllerTest {
	private String ip = "http://192.168.82.119:3003";
	@Test
	public void testTemplate() throws Exception{
		String actionPath = "/template/test";
		Dto param = new BaseDto();
		param.put("myMsgTemplateItemID", 1);
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
	}
}
