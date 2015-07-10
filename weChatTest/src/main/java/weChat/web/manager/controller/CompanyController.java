package weChat.web.manager.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.core.utils.TypeCaseHelper;
import weChat.domain.primary.Company;
import weChat.domain.primary.Parameter;
import weChat.parameter.IRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.repository.primary.ParameterRepository;
import weChat.service.common.RespService;
import weChat.utils.AppConstants;
import weChat.utils.RespUtils;

@RestController
@RequestMapping("/Company")
public class CompanyController {
	

	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private RespService respService;

	/**
	 * 获取场所服务是否开启
	 * @param company
	 * @param wechatpubinfoid
	 * @return
	 */
	@RequestMapping("/company_info")
	public IRespParam companyInfo(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid) {
		int companyID = company.getCompanyID();
		String parameterName = "iscustomer";
		Parameter parameter = parameterRepository.findFirstByCompanyIDAndParameterName(companyID, parameterName);
		parameterName = "wechattemplate";
		Parameter pismessagemodel = parameterRepository.findFirstByCompanyIDAndParameterName(companyID, parameterName);
		DynamicRespParam respParam = new DynamicRespParam();
		String iscustomer = "0";
		String ismessagemodel ="0";
		if(parameter != null){
			iscustomer = parameter.getParameterValue();
			//是否开启消息模版服务 0-未开启 1-已开启
		}
		respParam.set("iscustomer", TypeCaseHelper.convert2Integer(iscustomer));
		if(pismessagemodel !=null){
			ismessagemodel = pismessagemodel.getParameterValue();
		}
		respParam.set("ismessagemodel", TypeCaseHelper.convert2Integer(ismessagemodel));
		return respParam;
		
	}
}
