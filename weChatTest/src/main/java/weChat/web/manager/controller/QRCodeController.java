package weChat.web.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.domain.primary.Company;
import weChat.parameter.IRespParam;
import weChat.service.common.QRCodeService;
import weChat.utils.AppConstants;


@RestController
@RequestMapping("/QRCode")
public class QRCodeController {
	@Autowired
	QRCodeService qrCodeService;
	
	@RequestMapping("/wechatPubInfoQrCode")
	public IRespParam wechatPubInfoQrCode(@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid){
		return qrCodeService.getWechatPubInfoQrCode(wechatpubinfoid);
	}
	
	@RequestMapping("/parameQrCode")
	public IRespParam parameQrCode(@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute Company company,@ModelAttribute("cardnum") String cardnum){
		return qrCodeService.getParameQrCode(wechatpubinfoid, company.getCompanyCode(),cardnum);
	}
}
