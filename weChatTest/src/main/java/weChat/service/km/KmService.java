package weChat.service.km;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import weChat.core.metatype.Dto;
import weChat.domain.primary.Company;
import weChat.parameter.IRespParam;

/**
 * @author deng
 * @date 2015年6月23日
 * @version 1.0.0
 */
public interface KmService {
/**
 * K米APP获取绑卡信息
 * @param company
 * @param otherParam
 * @param wechatpubinfoid
 * @return
 */
	public IRespParam bindCardInfo(Company company, Dto otherParam,
			int wechatpubinfoid) throws Exception;
	
	/**
	 * K米APP批量获取会员信息
	 * @param customerid
	 * @return
	 */
	public IRespParam memberInfo(int customerid);
	
	
	
	
	/**
	 * K米APP绑卡
	 * @param wechatpubinfoid
	 * @param customerid
	 * @param otherParam
	 * @return
	 */
	@Transactional
	public IRespParam bindCard(int wechatpubinfoid,
			 int customerid,
		 Dto otherParam)  throws Exception;

	
	/**
	 * K米APP获取会员消费记录
	 * @param company
	 * @param wechatpubinfoid
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	public IRespParam memberConsumeInfo(Company company,
			int wechatpubinfoid,Dto otherParam) throws Exception;
	
	
	/**K米APP完善会员资料
	 * @param company
	 * @param wechatpubinfoid
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam updateMemberInfo(Company company,
			int wechatpubinfoid,Dto otherParam) throws Exception;
	
	/**根据电子会员卡（KMID）获取会员信息
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	public IRespParam memberInfoByKmID( String kmid) throws Exception;

	/**获取参数信息
	 * @param paramers 参数名称
	 * @return
	 * @throws Exception
	 */
	public IRespParam getParamer(ArrayList<String> paramers) throws Exception;
	
	/**更新参数
	 * @return
	 * @throws Exception
	 */
	public IRespParam updateParamer(int companyID,ArrayList<Map<String, String>> paramerEntrys) throws Exception;
	
	
	/**商家密码更新
	 * @param companyID	商家ID
	 * @param newcompanypsw	新密码
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam updateCompanyPsw(int companyID,String newcompanypsw) throws Exception;
	
	
	/**在线申请会员
	 * @param company
	 * @param otherParam
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public IRespParam applyForMember(Company company,int wechatPubinfoID,Dto otherParam) throws Exception;
	
	
	/**获取在线申请会员等级
	 * @param companyID
	 * @return
	 * @throws Exception
	 */
	public IRespParam applyMemberLevel(int companyID) throws Exception;
}
