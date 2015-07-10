package weChat.web.manager.advice;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import weChat.parameter.manager.MReqParam;
import weChat.service.common.CompanyService;
import weChat.utils.AppConstants;
import weChat.web.manager.controller.MemberSyncController;

@ControllerAdvice(assignableTypes={MemberSyncController.class})
public class MemberSyncControllerAdvice {

	@Autowired
	private CompanyService companyService;
	@ModelAttribute
	public void populateModel(@RequestBody @Valid MReqParam param, Model model){
		String companycode = param.getCompanycode();
		String accessToken = param.getAccess_token();
		int wechatpubinfoid = param.getWechatpubinfoid();
		companyService.validateCompany(companycode, accessToken, wechatpubinfoid, model);
		model.addAttribute(AppConstants.DATA, param.getData());
	}
}
