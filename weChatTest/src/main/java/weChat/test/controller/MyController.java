package weChat.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.parameter.IRespParam;
import weChat.test.service.MyService;
import weChat.utils.RespUtils;

@RestController
@RequestMapping("/test")
public class MyController {
	@Autowired
	private MyService myService;
	@RequestMapping("/testSecondData")
	public IRespParam testSecondData(){
		myService.testSecondData();
		return RespUtils.successMR();
	}
}
