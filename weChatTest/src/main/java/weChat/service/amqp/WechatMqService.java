package weChat.service.amqp;

import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import weChat.core.rabbit.RabbitClient;
import weChat.core.rabbit.RabbitClientConfig;
import weChat.parameter.IRespParam;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.amqp.AmqpRespParam;
import weChat.parameter.common.CommonParam;
import weChat.service.common.ValidationService;

public abstract class WechatMqService implements Validator {

	private final RabbitClient rabbitClient;

	private final RabbitClientConfig config;

	private final ValidationService validationService;

	public WechatMqService(RabbitClient rabbitClient,
			RabbitClientConfig config, ValidationService validationService) {
		this.rabbitClient = rabbitClient;
		this.config = config;
		this.validationService = validationService;
	}

	public IRespParam sendMQ(AmqpReqParam param) throws Exception {
		String dot = config.getQueuedot();
		String uuid = UUID.randomUUID().toString();
		// 请求消息队列
		String routingKey = "request" + dot + "company" + dot
				+ param.getCompanycode();
		// TODO 返回消息队列，这所以用uuid，是因为和线下系统调试出现了BUG，待解决
		String replyQueueName = uuid;
		CommonParam resp = (CommonParam) rabbitClient.convertSendAndReceiver(
				routingKey, replyQueueName, param, uuid, CommonParam.class);
		if (resp != null) {
			return resp;
		}
		throw new AmqpException("MQ服务异常");

	}

	/**
	 * 处理数据, 提供默认实现,验证参数，发送消息并接收消息
	 * 
	 * @param param
	 */
	public IRespParam handle(AmqpReqParam param) throws Exception {
		//校验数据
		validate(param);
		// 发送MQ
		return sendMQ(param);
	}
	/**
	 * 校验数据
	 * @param param
	 */
	public void validate(AmqpReqParam param){
		// 校验数据
		validationService.validate(param, "param", this);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return AmqpReqParam.class.equals(clazz);
	}

}
