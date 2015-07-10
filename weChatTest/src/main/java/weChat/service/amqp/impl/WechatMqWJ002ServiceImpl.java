package weChat.service.amqp.impl;

import static weChat.core.utils.CommonUtils.isEmpty;
import static weChat.utils.AppConstants.ARGUMENT_NOT_EMPTY_INFO;

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

@Service("WJ002" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ002ServiceImpl extends WechatMqService {
	@Autowired
	public WechatMqWJ002ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
	}

	@Override
	public void validate(Object target, Errors e) {
		AmqpReqParam rreqParam = (AmqpReqParam) target;
		BaseDto params = rreqParam.getParams();
		ValidationUtils.rejectIfEmpty(params, "param", e, "cardnum",
				"memberid", "begintime", "endtime");

	}

}
