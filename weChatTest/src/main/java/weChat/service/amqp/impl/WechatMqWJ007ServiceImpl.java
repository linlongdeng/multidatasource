package weChat.service.amqp.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.core.utils.ValidationUtils;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.amqp.AmqpRespParam;
import weChat.service.amqp.WechatMqService;
import weChat.service.common.ValidationService;
import weChat.utils.AppConstants;
import static weChat.core.utils.CommonUtils.*;
import static weChat.utils.AppConstants.*;

@Service("WJ007" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ007ServiceImpl extends WechatMqService {

	@Autowired
	public WechatMqWJ007ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
	}

	@Override
	public void validate(Object target, Errors e) {
		AmqpReqParam rreqParam = (AmqpReqParam) target;
		BaseDto params = rreqParam.getParams();
		ValidationUtils.rejectIfEmpty(params, "param", e, "cardnum");
	
	}

}
