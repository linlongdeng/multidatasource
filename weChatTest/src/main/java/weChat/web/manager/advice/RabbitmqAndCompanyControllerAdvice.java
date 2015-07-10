package weChat.web.manager.advice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import weChat.parameter.manager.MInfoReqParam;
import weChat.service.common.CompanyService;
import weChat.web.manager.controller.CompanyController;
import weChat.web.manager.controller.RabbitmqController;

@ControllerAdvice(assignableTypes = { RabbitmqController.class,
		CompanyController.class })
public class RabbitmqAndCompanyControllerAdvice {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CompanyService companyService;

	@ModelAttribute
	public void populateModel(@RequestBody @Valid MInfoReqParam param,
			HttpServletRequest request, Model model) {
		logger.debug("客户端请求地址是,{}, 参数是{}, ", request.getRequestURI(),param );
		String companycode = param.getCompanycode();
		String accessToken = param.getAccess_token();
		int wechatpubinfoid = param.getWechatpubinfoid();
		companyService.validateCompany(companycode, accessToken,
				wechatpubinfoid, model);
	}

}
