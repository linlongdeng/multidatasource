package weChat.service.amqp.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import weChat.core.metatype.BaseDto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.core.utils.ValidationUtils;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.service.amqp.WechatMqService;
import weChat.service.common.ValidationService;
import weChat.utils.AppConstants;
@Service("WJ003" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ003ServiceImpl extends WechatMqService{
	@Autowired
	public WechatMqWJ003ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
	}

	@Override
	public void validate(Object target, Errors e) {
		AmqpReqParam rreqParam = (AmqpReqParam) target;
		BaseDto params = rreqParam.getParams();
		ValidationUtils.rejectIfEmpty(params, "param", e, "memberid",
				"begintime", "endtime");
		
	}

}
