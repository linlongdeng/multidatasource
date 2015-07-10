package weChat.web.km.advice;

import static weChat.utils.AppConstants.COMPANY;
import static weChat.utils.AppConstants.CUSTOMERID;
import static weChat.utils.AppConstants.KMID;
import static weChat.utils.AppConstants.OTHER_PARAM;
import static weChat.utils.AppConstants.WECHATPUBINFOID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import weChat.core.metatype.Dto;
import weChat.core.utils.ValidationUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Companywechatpub;
import weChat.parameter.km.KDynamicReqParam;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.CompanywechatpubRepository;
import weChat.repository.primary.WechatpubinfoRepository;
import weChat.service.common.AuthService;
import weChat.service.common.CompanyService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;
import weChat.web.km.controller.KmController;

@ControllerAdvice(assignableTypes = { KmController.class })
/**
 * KMController的通知
 * @author deng
 * @date 2015年6月16日
 * @version 1.0.0
 */
public class KmControllerAdvice {
	@Autowired
	private AuthService authService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private WechatpubinfoRepository wechatpubinfoRepository;
	@Autowired
	private CompanywechatpubRepository companywechatpubRepository;

	@ModelAttribute
	public void populateModel(@RequestBody @Valid KDynamicReqParam param,
			HttpServletRequest request, Model model) {
		String requestURI = request.getRequestURI();
		// 验证token
		AppUtils.assertTrueAccess(authService.checkAccessToken(param
				.getAccess_token()));
		// K米APP获取绑卡信息
		if (requestURI.endsWith("bindCardInfo")) {
			// 数据校验，数据处理
			handleBindCardInfo(param, model);
		}
		// 处理.K米APP批量获取会员信息
		else if (requestURI.endsWith("memberInfo")) {
			handleMemberInfo(param, model);
		}
		//处理K米APP绑卡
		else if(requestURI.endsWith("bindCard")){
			handleBindCard(param, model);
		}
		//K米APP获取会员消费记录
		else if(requestURI.endsWith("memberConsumeInfo")){
			handleMemberConsumeInfo(param,model);
		}
		//K米APP完善会员资料
		else if(requestURI.endsWith("updateMemberInfo")){
			handleUpdateMemberInfo(param,model);
		}
		// 根据电子会员卡号获取会员信息
		else if (requestURI.endsWith("memberInfoByKmID")) {
			handlememberInfoByKmID(param, model);
		}

		// 获取参数
		else if (requestURI.endsWith("getParamer")) {
			handleGetParamers(param, model);
		}
		// 更新参数
		else if (requestURI.endsWith("updateParamer")) {
			handleUpdateParamer(param, model);
		}
		// 商家密码更新
		else if (requestURI.endsWith("updateCompanyPsw")) {
			handleUpdateCompanyPSWD(param, model);
		}
		// 在线会员申请
		else if (requestURI.endsWith("applyForMember")) {
			handleApplyForMember(param, model);
		}
		//获取在线申请会员等级
		else if (requestURI.endsWith("applyMemberLevel")) {
			handleApplyMemberLevel(param, model);
		}

	}

	/**
	 * 处理K米APP获取绑卡信息
	 * 
	 * @param param
	 * @param model
	 */
	private void handleBindCardInfo(KDynamicReqParam param, Model model) {
		// 校验参数非空
		ValidationUtils.rejectEmpty(
				new Object[] { param.getCompanyid(), param.get("cardnum") },
				new String[] { "companyid", "cardnum" });
		// 获取商家信息
		getCompany(param.getCompanyid(), model);
		// 获取商家公众号
		getWechatPubInfoID(param.getCompanyid(), model);
		// 其他参数
		getOtherParam(param.any(), model);
	}

	/**
	 * 处理.K米APP批量获取会员信息
	 * 
	 * @param param
	 * @param model
	 */
	private void handleMemberInfo(KDynamicReqParam param, Model model) {
		// 校验参数非空
		ValidationUtils.rejectEmpty(new Object[] { param.get("customerid") },
				new String[] { "customerid" });
		//获取K米APP会员ID
		getCustomerid(param.any(), model);
	}

	/**
	 * 处理K米APP绑卡，校验参数，注入商家信息，微信公众号，其他参数
	 * 
	 * @param param
	 * @param model
	 */
	private void handleBindCard(KDynamicReqParam param, Model model) {
		//非空校验
		ValidationUtils.rejectEmpty(
				new Object[] { param.getCompanyid(), param.get("customerid"),
						param.get("cardnum"), param.get("moblie"),
						 }, new String[] { "companyid",
						"customerid", "cardnum","moblie" });
		
		// 获取商家信息
		getCompany(param.getCompanyid(), model);
		// 获取商家公众号
		getWechatPubInfoID(param.getCompanyid(), model);
		// 其他参数
		getOtherParam(param.any(), model);
		//获取K米APP会员ID
		getCustomerid(param.any(), model);
	}
	



	
	
	
	
	/**
	 * K米APP获取会员消费记录
	 * @param param
	 * @param model
	 */
	private void handleMemberConsumeInfo(KDynamicReqParam param, Model model){
		// 校验参数非空
				ValidationUtils.rejectEmpty(
						new Object[] { param.getCompanyid(), param.get("kmid"), param.get("begintime"), param.get("endtime") },
						new String[] { "companyid", "kmid","begintime","endtime" });
				// 获取商家信息
				getCompany(param.getCompanyid(), model);
				// 获取商家公众号
				getWechatPubInfoID(param.getCompanyid(), model);
				// 其他参数
				getOtherParam(param.any(), model);
		
	}
	
	/**K米APP完善会员资料
	 * @param param
	 * @param model
	 */
	private void handleUpdateMemberInfo(KDynamicReqParam param, Model model){
		ValidationUtils.rejectEmpty(
				new Object[] { param.getCompanyid(), param.get("kmid"), param.get("membername"), 
						param.get("sex"),param.get("mobile"),param.get("birthday") },
				new String[] { "companyid", "kmid","membername","sex","mobile","birthday" });
		// 获取商家信息
		getCompany(param.getCompanyid(), model);
		// 获取商家公众号
		getWechatPubInfoID(param.getCompanyid(), model);
		// 其他参数
		getOtherParam(param.any(), model);
	}
	
	/**商家信息校验及参数填充
	 * @param param
	 * @param model
	 */
	private void validationCompanyInfo(KDynamicReqParam param, Model model){
		ValidationUtils.rejectEmpty(
				new Object[]{param.getCompanyid()},new String[] { "companyid"});
	
		// 获取商家信息
		getCompany(param.getCompanyid(), model);
		// 获取商家公众号
		getWechatPubInfoID(param.getCompanyid(), model);
	}
	/**
	 * 获取商户
	 * 
	 * @param companyid
	 */
	private void getCompany(int companyid, Model model) {
		Company company = companyRepository.findOne(companyid);
		// 商家不能为空
		AppUtils.assertCompanyNotNull(company);
		model.addAttribute(COMPANY, company);
	}

	/**
	 * 获取微信公众号ID
	 * 
	 * @param companyid
	 * @param model
	 */
	private void getWechatPubInfoID(int companyid, Model model) {
		Companywechatpub companywechatpub = companywechatpubRepository
				.findFirstByCompanyID(companyid);
		// 商家公众号
		AppUtils.assertWechatNotNull(companywechatpub);
		model.addAttribute(WECHATPUBINFOID,
				companywechatpub.getWechatPubInfoID());
	}

	/**
	 * 获取其他参数
	 * 
	 * @param param
	 * @param model
	 */
	private void getOtherParam(Dto param, Model model) {
		// 其他参数
		model.addAttribute(OTHER_PARAM, param);
	}
	/**
	 * 获取K米APP会员ID
	 * @param param
	 * @param model
	 */
	private void getCustomerid(Dto param, Model model){
		model.addAttribute(CUSTOMERID, param.getAsInteger("customerid"));
	}
	
	/**
	 * 参数更新
	 * 
	 * @param param
	 * @param model
	 */
	private void handleUpdateParamer(KDynamicReqParam param, Model model) {
		// 校验商家信息参数
		validationCompanyInfo(param, model);
		// 其他参数信息校验
		ValidationUtils.rejectEmpty(new Object[] { param.get("data") },
				new String[] { "data" });
		// 其他参数
		model.addAttribute(OTHER_PARAM, param.any());
	}

	/**
	 * 在线会员申请
	 * 
	 * @param param
	 * @param model
	 */
	private void handleApplyForMember(KDynamicReqParam param, Model model) {
		// 校验商家信息参数
		validationCompanyInfo(param, model);
		//查询该功能是否开启
		checkParamer(param.getCompanyid(),AppConstants.KM_ONLINE_CUSTOMER, AppConstants.PARAMER_ACTIVE);
		// 其他参数信息校验
		ValidationUtils.rejectEmpty(new Object[] { param.getAsInteger("customerid"),param.getAsInteger("gradeid"),param.getAsString("membername"), 
				param.getAsString("sex"),param.getAsString("mobile"),param.getAsString("birthday")},
				new String[] { "customerid","gradeid","membername","sex","mobile","birthday"});
		// 其他参数   param.get("papertype"),param.get("papernumber"),
		model.addAttribute(OTHER_PARAM, param.any());
	}
	
	/**获取在线申请会员等级
	 * @param param
	 * @param model
	 */
	private void handleApplyMemberLevel(KDynamicReqParam param, Model model) {
		// 校验商家信息参数
		validationCompanyInfo(param, model);
	}
	
	/**
	 * 商家密码更新
	 * 
	 * @param param
	 * @param model
	 */
	private void handleUpdateCompanyPSWD(KDynamicReqParam param, Model model) {
		// 校验商家信息参数
		validationCompanyInfo(param, model);
		// 密码非空校验
		ValidationUtils.rejectEmpty(
				new Object[] { param.get("newcompanypsw") },
				new String[] { "newcompanypsw" });
		model.addAttribute(OTHER_PARAM, param.any());
	}

	/**
	 * 获取参数值
	 * 
	 * @param param
	 * @param model
	 */
	private void handleGetParamers(KDynamicReqParam param, Model model) {
		// 校验商家信息参数
		validationCompanyInfo(param, model);
		// 其他参数信息校验
		ValidationUtils.rejectEmpty(new Object[] { param.get("data") },
				new String[] { "data" });
		// 其他参数
		model.addAttribute(OTHER_PARAM, param.any());
	}

	/**
	 * 根据电子会员卡id获取会员信息
	 * 
	 * @param param
	 * @param model
	 */
	private void handlememberInfoByKmID(KDynamicReqParam param, Model model) {
		ValidationUtils.rejectEmpty(new Object[] { param.get("kmid") },
				new String[] { "kmid" });
		model.addAttribute(KMID, param.get("kmid"));
	}
	
	/**参数检查,不同过时直接返回
	 * @param paramerName	参数名
	 * @param trueValue		为真时的参数值
	 * @return	
	 */
	public void checkParamer(int companyID,String paramerName,String trueValue){
		if (!companyService.validateParamActive(companyID, paramerName, trueValue)) {
			AppUtils.notActive();
		}
	}
	
}
