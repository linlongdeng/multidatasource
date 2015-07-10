package weChat.web;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import weChat.core.metatype.BaseDto;
import weChat.core.utils.HttpClientUtils;
import weChat.json.PostJsonUtils;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.amqp.AmqpRespParam;
import weChat.parameter.common.CommonParam;

public class WechatMqControllerTest {

	private String ip = "http://192.168.74.35:3003";
	
	@Test
	public void testController() throws JsonGenerationException, JsonMappingException, IOException{
		PostJsonUtils.ip = ip;
		String actionPath ="/RabbitmqRpc";
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ007");
		//param.setCompanycode("06375"); 
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000028");
		param.setParams(dto);
		Map<String, Object> result = PostJsonUtils.postObject(actionPath, param);
		System.out.println(result);
	}
	@Test
	public void testHttp() throws JsonGenerationException, JsonMappingException, IOException{
		PostJsonUtils.ip ="http://192.168.82.67:3002";
		String actionPath ="";
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ007");
		param.setCompanycode("06375"); 
		//param.setCompanycode("01197");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000011");
		param.setParams(dto);
		Map<String, Object> result = PostJsonUtils.postObject(actionPath, param);
		System.out.println(result);
	}
	@Test
	public void testWJ001() throws Exception{
		String actionPath ="/RabbitmqRpc";
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ001");
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000028");
		dto.put("memberid", "20000021");
		param.setParams(dto);
		 AmqpRespParam resp = HttpClientUtils.post(ip + actionPath,param, AmqpRespParam.class);
		 System.out.println("返回参数是： "  + resp);
	}
	
	@Test
	public void testWJ002() throws Exception{
		String actionPath ="/RabbitmqRpc";
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ002");
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000028");
		dto.put("memberid", "20000021");
		dto.put("begintime", "2014-01-01 00:00:00");
		dto.put("endtime", "2015-06-05 00:00:00");
		param.setParams(dto);
		CommonParam resp = HttpClientUtils.post(ip + actionPath,param, CommonParam.class);
		 System.out.println("返回参数是： "  + resp);
	}
	
	@Test
	public void testWJ005() throws Exception{
		String actionPath ="/RabbitmqRpc";
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ005");
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000028");
		dto.put("memberid", "20000021"); 
		dto.put("membername", "林龙灯2"); 
		dto.put("sex", "男");
		dto.put("papernumber", "434324");
		dto.put("mobile", "13500001111");
		dto.put("birthday", "1990-01-01");
		dto.put("address", "福建省福州市");
		dto.put("email", "test@star-net.cn");
		dto.put("kmid", "000000036");
		param.setParams(dto);
		CommonParam resp = HttpClientUtils.post(ip + actionPath,param, CommonParam.class);
		 System.out.println("返回参数是： "  + resp);
	}
	
	@Test
	public void testWJ008() throws Exception{
		String actionPath ="/RabbitmqRpc";
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ008");
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000028");
		dto.put("kmid", "");
		dto.put("validatetype", "1");
		dto.put("mobile", "15359137245");
		param.setParams(dto);
		CommonParam resp = HttpClientUtils.post(ip + actionPath,param, CommonParam.class);
		 System.out.println("返回参数是： "  + resp);
	}
}
