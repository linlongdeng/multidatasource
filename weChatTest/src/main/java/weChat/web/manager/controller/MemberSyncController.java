package weChat.web.manager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Parameter;
import weChat.parameter.IRespParam;
import weChat.repository.primary.ParameterRepository;
import weChat.service.common.CompanyService;
import weChat.service.manage.MemberSyncService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

/**
 * @author deng
 * @date 2015年4月23日
 * @version 1.0.0
 */
@RestController
@RequestMapping("/Membersync")
public class MemberSyncController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberSyncService memberSyncService;
	@Autowired
	private CompanyService companyService;
	
	/**
	 * 会员等级同步
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/member_level")
	public IRespParam memberLevel(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) {
		return memberSyncService.memberLevel(company, wechatpubinfoid, data);

	}

	/**
	 * 会员信息同步
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping("/member_info")
	public IRespParam memberInfo(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) {
		logger.debug("开始同步会员信息");
		IRespParam memberInfo = memberSyncService.memberInfo(company,
				wechatpubinfoid, data);
		logger.debug("结束同步会员信息");
		return memberInfo;
	}
	
	
	/**
	 * 会员消费数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/memberConsume")
	public IRespParam memberConsume(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		checkParamer(company, AppConstants.WECHAT_TEMPLATE, AppConstants.PARAMER_ACTIVE);
		IRespParam respParam = memberSyncService.memberConsume(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**会员充值数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberRecharge")
	public IRespParam memberRecharge(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		IRespParam respParam = memberSyncService.memberRecharge(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**会员积分赠送数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberGivingPoints")
	public IRespParam memberGivingPoints(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		IRespParam respParam = memberSyncService.memberGivingPoints(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**会员积分扣除数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberPointsDeduction")
	public IRespParam memberPointsDeduction(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		IRespParam respParam = memberSyncService.memberPointsDeduction(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**会员积分兑换物品数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberPointsToGift")
	public IRespParam memberPointsToGift(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		IRespParam respParam = memberSyncService.memberPointsToGift(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**会员寄存数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberChecked")
	public IRespParam memberChecked(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		IRespParam respParam = memberSyncService.memberChecked(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**会员寄存数据上传
	 * @param company
	 * @param wechatpubinfoid
	 * @param data
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberDraw")
	public IRespParam memberDraw(@ModelAttribute Company company,
			@ModelAttribute(AppConstants.WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(AppConstants.DATA) List<Dto> data) throws Exception {
		IRespParam respParam = memberSyncService.memberDraw(company,
				wechatpubinfoid,data);
		return respParam;
	}
	
	/**参数检查,不同过时直接返回
	 * @param paramerName	参数名
	 * @param trueValue		为真时的参数值
	 * @return	
	 */
	public void checkParamer(Company company,String paramerName,String trueValue){
		if (!companyService.validateParamActive(company.getCompanyID(), paramerName, trueValue)) {
			AppUtils.notActive();
		}
	}
}

