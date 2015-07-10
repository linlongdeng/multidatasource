package weChat.web.km.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.core.metatype.BaseDto;
import weChat.parameter.IRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.km.KRespResParam;
import weChat.parameter.km.KSmsReqParam;
import weChat.service.km.InvokeKmService;
import weChat.utils.RespUtils;

@RestController
@RequestMapping("/InvokeKm")
public class InvokeKmController {

	@Autowired
	private InvokeKmService invokeKmService;

	@RequestMapping("/getKmAuth")
	public IRespParam getKmAuth() throws Exception {
		String token = invokeKmService.getKmAccessToken();
		DynamicRespParam resp = new DynamicRespParam();
		resp.set("access_token", token);
		return resp;

	}

	@RequestMapping("/saveAllCompanyFromKm")
	public IRespParam saveAllCompanyFromKm() throws Exception {
		invokeKmService.saveAllCompanyFromKm();
		return RespUtils.successMR();
	}

	@RequestMapping("/Company/lists")
	public IRespParam testgetCompany() {
		KRespResParam resp = new KRespResParam();
		List<BaseDto> list = new ArrayList<BaseDto>();
		Random random = new Random();
		BaseDto dto = null;
		int companyid = 0;
		dto = new BaseDto();
		companyid = Math.abs(random.nextInt() % 3);
		dto.put("companyid", 0);
		dto.put("companycode", "3");
		dto.put("pass", "3");
		dto.put("companytype", "2");
		dto.put("companyname", "aaa" + random.nextInt() % 100);
		dto.put("regioncode", "333");
		dto.put("companyaddress", "333");
		dto.put("companymemo", "333");
		dto.put("companyurl", "333");
		dto.put("status", "2");
		dto.put("mappositionx", "33234");
		dto.put("mappositiony", "43243");
		list.add(dto);
		dto = new BaseDto();
		companyid = Math.abs(random.nextInt() % 3) + 5;
		dto.put("companyid", companyid);
		dto.put("companycode", "3");
		dto.put("pass", "3");
		dto.put("companytype", "2");
		dto.put("companyname", "aaa" + random.nextInt() % 100);
		dto.put("regioncode", "333");
		dto.put("companyaddress", "333");
		dto.put("companymemo", "333");
		dto.put("companyurl", "333");
		dto.put("status", "2");
		dto.put("mappositionx", "33234");
		dto.put("mappositiony", "43243");
		list.add(dto);
		resp.setRes(list);
		return resp;
	}

	@RequestMapping("/Auth/access_token")
	public IRespParam getToken() {
		DynamicRespParam resp = new DynamicRespParam();
		resp.set("access_token", "32132131");
		return resp;
	}
/**
 * 发送短信
 * @param param
 * @return
 * @throws Exception
 */
	@RequestMapping("/sendSms")
	public IRespParam sendsms(@RequestBody @Valid KSmsReqParam param)
			throws Exception {
		return invokeKmService.sendsms(param);

	}
}
