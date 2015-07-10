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
@Service("WJ006" + AppConstants.WJMQ_SUFFIX)
public class WechatMqWJ006ServiceImpl extends WechatMqService {
	@Autowired
	public WechatMqWJ006ServiceImpl(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		super(rabbitClient, config, validationService);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void validate(Object target, Errors errors) {
		BaseDto params = ((AmqpReqParam) target).getParams();
		ValidationUtils.rejectIfEmpty(params, "param",errors, "memberid",
				"memberpsw", "newmemberpsw");
	}

}
