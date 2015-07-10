package weChat.web.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.core.metatype.BaseDto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.domain.primary.Company;
import weChat.parameter.IRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.manager.MInfoReqParam;
import weChat.parameter.manager.MRespDataParam;
import weChat.utils.AppConstants;
import weChat.utils.RespMsgCode;

@RestController
@RequestMapping("/Rabbitmq")
public class RabbitmqController {

	@Autowired
	private RabbitClientConfig config;

	@RequestMapping("/info")
	public IRespParam info(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid) {
		BaseDto data = new BaseDto();
		data.put("scheme", config.getScheme());
		data.put("host", config.getHost());
		//port改成整数
		data.put("port", config.getPort());
		data.put("login", config.getLogin());
		data.put("password", config.getPassword());
		data.put("vhost", config.getVhost());
		data.put("excharge", config.getExcharge());
		String dot = config.getQueuedot();
		String queue = "request" + dot + "company" + dot
				+ company.getCompanyCode();
		data.put("queue", queue);
		IRespParam resp = new MRespDataParam(data);
		return resp;
	}

}
