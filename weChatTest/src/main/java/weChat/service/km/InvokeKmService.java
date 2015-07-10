package weChat.service.km;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.km.KSmsReqParam;

/**
 * 调用KM的服务
 * 
 * @author deng
 * @date 2015年5月28日
 * @version 1.0.0
 */
public interface InvokeKmService {

	/**
	 * 获取授权码
	 * 
	 * @return
	 */
	public String getKmAccessToken() throws Exception;

	/**
	 * 获取授权码
	 * 
	 * @return
	 * @throws Exception
	 */
	public DynamicRespParam getKmAuthDirect() throws Exception;

	/**
	 * 获取所有商家信息,并保存到数据库
	 * 
	 * @return
	 */
	@Transactional
	public void saveAllCompanyFromKm() throws Exception;

	/**
	 * 根据商家ID，获取KM商家信息
	 * 
	 * @param companyid
	 * @return
	 */
	public IRespParam getKmCompanyById(int companyid);

	/**
	 * 根据手机号码生成K米会员
	 * 
	 * @param pDto
	 * @return
	 */
	public IRespParam registerByPhone(Dto pDto) throws Exception;

	/**
	 * 完善K米会员资料
	 * 
	 * @param pDto
	 */
	public IRespParam fillCustomerInfo(Dto pDto) throws Exception;

	/**
	 * 发送短信
	 * 
	 * @param pDto
	 * @return
	 */
	public IRespParam sendsms(KSmsReqParam param) throws Exception;
	
	
	/**模版消息推送
	 * @param kmTemplateList
	 * @return
	 * @throws Exception
	 */
	public IRespParam pushTemplate(List<BaseDto> kmTemplateList) throws Exception;
}
