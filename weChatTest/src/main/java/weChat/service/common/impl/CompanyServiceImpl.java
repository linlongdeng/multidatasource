package weChat.service.common.impl;

import static weChat.utils.AppUtils.assertCompanyNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Companywechatpub;
import weChat.domain.primary.Parameter;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.CompanywechatpubRepository;
import weChat.repository.primary.ParameterRepository;
import weChat.service.common.AuthService;
import weChat.service.common.CompanyService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private ParameterRepository parameterRepository;
	@Autowired
	private CompanywechatpubRepository companywechatpubRepository;

	@Override
	public void validateCompany(String companycode, String accessToken,
			int wechatpubinfoid, Model model) {
		Company company = companyRepository.findFirstByCompanyCode(companycode);
		// 商家不能为空
		assertCompanyNotNull(company);
		// 验证权限
		AppUtils.assertTrueAccess(authService.checkAccessToken(accessToken));
		model.addAttribute(AppConstants.COMPANY, company);
		//从商家关联微信公众号表校验公众号
		Companywechatpub companywechatpub = companywechatpubRepository
				.findFirstByCompanyID(company.getCompanyID());
		//商家公众号信息验证
		AppUtils.assertCompanywechatpubNotNull(companywechatpub);
		//公众号ID从关联表取，参数传来上来的不与理会
		model.addAttribute(AppConstants.WECHATPUBINFOID, companywechatpub.getWechatPubInfoID());
	}

	@Override
	public boolean validateParamActive(int companyID, String paramerName,
			String trueValue) {
		Parameter parameter = parameterRepository.findFirstByCompanyIDAndParameterName(companyID, paramerName);
		if (CommonUtils.isEmpty(parameter)||!trueValue.equals(parameter.getParameterValue())){
			return false;
		}else {
			return true;
		}
	}
}
