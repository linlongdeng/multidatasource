package weChat.web;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import weChat.json.PostJsonUtils;
import weChat.parameter.manager.MInfoReqParam;

public class RabbitmqControllerTest {
	
	@Test
	public void testInfo() throws JsonGenerationException, JsonMappingException, IOException{
		String actionPath ="/Rabbitmq/info";
		MInfoReqParam param = new MInfoReqParam();
		param.setCompanycode("00127");
		param.setAccess_token("25d55ad283aa400af464c76d713c07ad");
		param.setWechatpubinfoid(43423432);
		Map<String, Object> result = PostJsonUtils.postObject(actionPath, param);
		System.out.println(result);
	}

}
