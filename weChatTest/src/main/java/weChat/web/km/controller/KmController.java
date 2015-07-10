package weChat.web.km.controller;


import static weChat.utils.AppConstants.COMPANY;
import static weChat.utils.AppConstants.CUSTOMERID;
import static weChat.utils.AppConstants.KMID;
import static weChat.utils.AppConstants.OTHER_PARAM;
import static weChat.utils.AppConstants.WECHATPUBINFOID;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.core.metatype.Dto;
import weChat.domain.primary.Company;
import weChat.parameter.IRespParam;
import weChat.service.km.KmService;

@RestController
@RequestMapping("/Km")
public class KmController {
	@Autowired
	private KmService kmService;

	/**K米APP获取绑卡信息
	 * @param company
	 * @param otherParam
	 * @param wechatpubinfoid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bindCardInfo")
	public IRespParam bindCardInfo(@ModelAttribute(COMPANY) Company company,
			@ModelAttribute(OTHER_PARAM) Dto otherParam,
			@ModelAttribute(WECHATPUBINFOID) int wechatpubinfoid)
			throws Exception {
		IRespParam resp = kmService.bindCardInfo(company, otherParam,
				wechatpubinfoid);
		return resp;

	}

	/**K米APP批量获取会员信息
	 * @param customerid
	 * @return
	 */
	@RequestMapping("/memberInfo")
	public IRespParam memberInfo(@ModelAttribute(CUSTOMERID) int customerid) {
		IRespParam resp = kmService.memberInfo(customerid);
		return resp;
	}
/**
 * K米App绑卡
 * @param wechatpubinfoid
 * @param customerid
 * @param otherParam
 * @return
 * @throws Exception 
 */
	@RequestMapping("/bindCard")
	public IRespParam bindCard(
			@ModelAttribute(WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(CUSTOMERID) int customerid,
			@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception {
		return kmService.bindCard(wechatpubinfoid, customerid, otherParam);
	}
	
	/**
	 * K米APP获取会员消费记录
	 * @param wechatpubinfoid
	 * @param customerid
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberConsumeInfo")
	public IRespParam memberConsumeInfo(
			@ModelAttribute(COMPANY) Company company,
			@ModelAttribute(WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception {
		return kmService.memberConsumeInfo(company, wechatpubinfoid, otherParam);
	}
	
	/**
	 * K米APP完善会员资料
	 * @param company
	 * @param wechatpubinfoid
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMemberInfo")
	public IRespParam updateMemberInfo(
			@ModelAttribute(COMPANY) Company company,
			@ModelAttribute(WECHATPUBINFOID) int wechatpubinfoid,
			@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception {
		return kmService.updateMemberInfo(company, wechatpubinfoid, otherParam);
	}
	
	/**根据电子会员卡（KMID）获取会员信息
	 * @param kmid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/memberInfoByKmID")
	public IRespParam memberInfoByKmID(@ModelAttribute(KMID) String kmid)
			throws Exception {
		IRespParam resp = kmService.memberInfoByKmID(kmid);
		return resp;
	}	
	
	
	/**获取参数
	 * @param paramers 参数集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getParamer")
	public IRespParam getParamer(@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception{
		ArrayList<Map<String,String>> data =  (ArrayList<Map<String, String>>) otherParam.getAsList("data");
		ArrayList<String> parames = new ArrayList<String>(); 
		for (int i = 0; i < data.size(); i++) {
			parames.add(data.get(i).get("parametername"));
		}
		IRespParam resp = kmService.getParamer(parames);
		return resp;
	}
	
	/**参数更新
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateParamer")
	public IRespParam updateParamer(@ModelAttribute(COMPANY) Company company,@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception{
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, String>> paramList = (ArrayList<Map<String, String>>) otherParam.getAsList("data");
		IRespParam resp = kmService.updateParamer(company.getCompanyID(),paramList);
		return resp;
	}
	
	/**更新密码
	 * @param company
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCompanyPsw")
	public IRespParam updateCompanyPSWD(@ModelAttribute(COMPANY) Company company,@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception{
		IRespParam resp = kmService.updateCompanyPsw(company.getCompanyID(), (String)otherParam.get("newcompanypsw"));
		return resp;
	}
	
	/**在线会员申请
	 * @param wechatPubinfoID
	 * @param company
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/applyForMember")
	public IRespParam applyForMember(@ModelAttribute(WECHATPUBINFOID) int wechatPubinfoID,@ModelAttribute(COMPANY) Company company,@ModelAttribute(OTHER_PARAM) Dto otherParam) throws Exception{
		IRespParam resp = kmService.applyForMember(company, wechatPubinfoID, otherParam);
		return resp;
	}
	
	/**获取在线申请会员等级
	 * @param company
	 * @return
	 */
	@RequestMapping("/applyMemberLevel")
	public IRespParam applyMemberLevel(@ModelAttribute(COMPANY) Company company) throws Exception{
		IRespParam resp = kmService.applyMemberLevel(company.getCompanyID());
		return resp;
	}
}
