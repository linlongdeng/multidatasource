package weChat.service.amqp.impl;

import static weChat.core.utils.CommonUtils.isEmpty;
import static weChat.utils.AppConstants.ARGUMENT_NOT_EMPTY_INFO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import weChat.core.metatype.BaseDto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.service.amqp.WechatMqService;
import weChat.service.common.ValidationService;
import weChat.utils.AppConstants;
import static weChat.core.utils.ValidationUtils.*;

@Service("WJ001" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ001ServiceImpl extends WechatMqService {
	@Autowired
	public WechatMqWJ001ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(Object target, Errors e) {
		AmqpReqParam rreqParam = (AmqpReqParam) target;
		BaseDto params = rreqParam.getParams();
		rejectIfEmpty(params,  "params", e, "cardnum", "memberid");
	}

}
