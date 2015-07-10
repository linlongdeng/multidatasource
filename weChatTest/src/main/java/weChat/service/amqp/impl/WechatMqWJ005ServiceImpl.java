package weChat.service.amqp.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import weChat.core.metatype.BaseDto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.core.utils.CommonUtils;
import weChat.core.utils.ValidationUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Kmbindcard;
import weChat.domain.primary.MemberCache;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.common.CommonParam;
import weChat.parameter.km.KRespResParam;
import weChat.repository.primary.CompanyRepository;
import weChat.repository.primary.KmbindcardRepository;
import weChat.repository.primary.MemberCacheRepository;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;
import weChat.service.amqp.WechatMqService;
import weChat.service.common.ValidationService;
import weChat.service.km.AsyncService;
import weChat.service.km.InvokeKmService;

@Service("WJ005" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ005ServiceImpl extends WechatMqService {

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
	private AsyncService asyncService;

	@Autowired
	public WechatMqWJ005ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
	}

	@Override
	public IRespParam handle(AmqpReqParam param) throws Exception {
		logger.debug("开始同步会员修改同步线下系统({}:{})", param.getCompanycode(), param
				.getParams().get("memberid"));
		CommonParam resp = (CommonParam) super.handle(param);
		Integer ret = resp.getAsInteger("ret");
		// 判断MQ结果是否成功
		if (AppUtils.checkSuccess(ret)) {
			// 会员信息同步到K米
			asyncService.syncKM(param);
		}
		logger.debug("完成同步会员修改同步线下系统({}:{})", param.getCompanycode(), param
				.getParams().get("memberid"));
		return resp;

	}

	@Override
	public void validate(Object target, Errors e) {
		AmqpReqParam rreqParam = (AmqpReqParam) target;
		BaseDto params = rreqParam.getParams();
		ValidationUtils.rejectIfEmpty(params, "param", e, "memberid",
				"membername", "sex", "mobile", "birthday");
	}

}
