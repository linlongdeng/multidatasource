package weChat.web.manager.advice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import weChat.core.utils.ValidationUtils;
import weChat.parameter.manager.MpQrCodeReqParam;
import weChat.service.common.CompanyService;
import weChat.utils.AppConstants;
import weChat.web.manager.controller.QRCodeController;

@ControllerAdvice(assignableTypes = { QRCodeController.class })
public class QRCodeControllerAdvice {
	@Autowired
	private CompanyService companyService;

	@ModelAttribute
	public void populateModel(@RequestBody @Valid MpQrCodeReqParam param,
			HttpServletRequest request, Model model) {
		String requestURI = request.getRequestURI();
		String companycode = param.getCompanycode();
		String accessToken = param.getAccess_token();
		int wechatpubinfoid = param.getWechatpubinfoid();
		companyService.validateCompany(companycode, accessToken,
				wechatpubinfoid, model);
		if (requestURI.lastIndexOf("parameQrCode") >= 0) {
			parameQrCode(param, model);
		}
	}
	
	private void parameQrCode(MpQrCodeReqParam param,Model model){
		// 校验参数非空
		ValidationUtils.rejectEmpty(new Object[] { param.getCardnum() },
				new String[] { "cardnum" });
		model.addAttribute("cardnum",param.getCardnum());
	}
}
