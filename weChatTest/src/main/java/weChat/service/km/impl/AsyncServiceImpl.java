package weChat.service.km.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Kmbindcard;
import weChat.domain.primary.MemberCache;
import weChat.domain.primary.Sendmsgdata;
import weChat.domain.primary.Sendmsgdatadetail;
import weChat.domain.primary.Sendmsglist;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.parameter.km.KRespResParam;
import weChat.parameter.manager.MRespParam;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.KmbindcardRepository;
import weChat.repository.primary.MemberCacheRepository;
import weChat.repository.primary.SendmsgdataRepository;
import weChat.repository.primary.SendmsgdatadetailRepository;
import weChat.repository.primary.SendmsglistRepository;
import weChat.service.km.AsyncService;
import weChat.service.km.InvokeKmService;
import weChat.service.km.KmbindcardService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

@Service
public class AsyncServiceImpl implements AsyncService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberCacheRepository memberCacheRepository;
	@Autowired
	private KmbindcardRepository kmbindcardRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private InvokeKmService invokeKmService;
	@Autowired
	private KmbindcardService kmbindcardService;
	//模板消息日志
	@Autowired
	private SendmsgdatadetailRepository sendmsgdatadetailRepository;
	@Autowired
	private SendmsgdataRepository sendmsgdataRepository;
	@Autowired
	private SendmsglistRepository sendmsglistRepository;
	
	@Override
	public void syncKM(AmqpReqParam param) throws Exception {
		String companycode = param.getCompanycode();
		Company company = companyRepository.findFirstByCompanyCode(companycode);
		Assert.notNull(company, "商家不存在");
		BaseDto pDto = param.getParams();
		String memberid = pDto.getAsString("memberid");
		String kmid = pDto.getAsString("kmid");
		// K米不能为空
		if (CommonUtils.isNotEmpty(kmid)) {
			MemberCache member = memberCacheRepository
					.findTopByCompanyIDAndMemberid(company.getCompanyID(),
							memberid);
			if (member != null) {
				// 会员状态，必须为启用
				if (AppConstants.MEMBER_STATUS_USER.equals(member.getStatus())) {
					Kmbindcard kmbindcard = kmbindcardRepository
							.findFirstByKmid(kmid);
					if (kmbindcard != null) {
						logger.debug("开始同步会员信息修改到K米{}:{}", companycode,
								memberid);
						// K米APP用户ID
						int customerid = kmbindcard.getCustomerID();
						pDto.put("customerid", customerid);
						KRespResParam kRespResParam = (KRespResParam) invokeKmService
								.fillCustomerInfo(pDto);
						int ret = kRespResParam.getRet();
						if (AppUtils.checkSuccess(ret)) {
							logger.info("会员资料( {}:{} )同步到K米成功", companycode,
									memberid);
						} else {
							logger.warn("会员资料( {}:{} )同步到K米失败,原因{}",
									companycode, memberid, kRespResParam);
						}
					}

				}
			}
		}

	}
	/**
	 * 模拟调用K米的根据手机号码生成K米会员
	 * @return
	 */
	private DynamicRespParam simulateKmRegisterByPhoneService(){
		DynamicRespParam respParam = new DynamicRespParam();
		respParam.setRet(0);
		int customerid = CommonUtils.getRandomnum(10000, 1);
		respParam.set("customerid",customerid );
		return respParam;
	}

	@Override
	public void bindKM(AmqpReqParam param) throws Exception {
		//先休息时间3秒钟，等待微信前端处理完毕
		Thread.sleep(AppConstants.SLEEP_MILLIS);
		String companycode = param.getCompanycode();
		BaseDto pDto = param.getParams();
		Dto kmDto = new BaseDto();
		kmDto.put("companycode", companycode);
		kmDto.put("kmid", pDto.getAsString("kmid"));
		 String phoneno = pDto.getAsString("mobile");
		kmDto.put("phoneno",phoneno);
		logger.debug("开始处理微信绑定会员卡同步K米应用, phoneno={}", phoneno);
	
		DynamicRespParam respParam = (DynamicRespParam) invokeKmService
				.registerByPhone(kmDto);
		// 判断调用是否成功
		if (AppUtils.checkSuccess(respParam.getRet())) {
			int customerid = respParam.getAsInteger("customerid");
			kmDto.put("customerid", customerid);
			MRespParam kmBindResp = (MRespParam) kmbindcardService
					.kmbindcard(kmDto);
			if(AppUtils.checkSuccess(kmBindResp.getRet())){
				logger.debug("微信绑定会员卡同步K米应用成功, phoneno={}", phoneno);
			}
		}

	}
	
	/**模板消息发送成功后的日志保存
	 * @param historyParamesList
	 * @param sendmsgdatas
	 * @param sendmsglists
	 */
	@Override
	public void saveModelMessageLog(List<Sendmsgdatadetail> historyParamesList,
			List<Sendmsgdata> sendmsgdatas, List<Sendmsglist> sendmsglists){
		if (CommonUtils.isNotEmpty(historyParamesList)) {
			sendmsgdatadetailRepository.save(historyParamesList);
		}
		if (CommonUtils.isNotEmpty(sendmsgdatas)) {
			sendmsgdataRepository.save(sendmsgdatas);
		}
		if (CommonUtils.isNotEmpty(sendmsglists)) {
			sendmsglistRepository.save(sendmsglists);
		}
	}
	
	@Override
	public void pushTemplate(List<BaseDto> kmTemplateList) throws Exception {
		invokeKmService.pushTemplate(kmTemplateList);
	}
	
	
}
