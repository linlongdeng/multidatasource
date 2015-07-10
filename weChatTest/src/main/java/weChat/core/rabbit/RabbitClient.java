package weChat.core.rabbit;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import weChat.core.utils.CommonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RabbitClient {

	private final AmqpAdmin amqpAdmin;

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private RabbitClientConfig rabbitClientConfig;
	@Autowired
	private MessageConverter messageConverter;
	@Autowired
	private DirectExchange directExchange;
	/** 进程休眠时间 **/
	private static final int interval = 500;

	@Autowired
	public RabbitClient(AmqpAdmin amqpAdmin, RabbitTemplate rabbitTemplate) {
		this.amqpAdmin = amqpAdmin;
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * 创建Message
	 * 
	 * @param reqQueueName
	 * @param replyQueueName
	 * @param messageObject
	 * @param uuid
	 * @return
	 * @throws JsonProcessingException
	 */
	private Message createSendMsg(final String reqQueueName,
			final String replyQueueName, Object messageObject, String uuid,
			long timeout) throws JsonProcessingException {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setTimestamp(new Date());
		messageProperties.setMessageId(uuid);
		messageProperties.setExpiration(String.valueOf(timeout));
		messageProperties.setReplyTo(replyQueueName);
		// 现在线下MQ存在问题，才这么搞
		messageProperties.setContentEncoding(uuid);
		messageProperties.setCorrelationId(replyQueueName.getBytes());
		byte[] body = objectMapper.writeValueAsBytes(messageObject);
		messageProperties.setContentLength(body.length);
		Message requestMessage = new Message(body, messageProperties);
		return requestMessage;
	}

	/**
	 * declare临时队列
	 * 
	 * @param excharge
	 * @param reqQueueName
	 * @param replyQueueName
	 */
	private void declareQueue(final String excharge, final String reqQueueName,
			final String replyQueueName) {
		Binding binding = null;
		Queue requestQueue = new Queue(reqQueueName, false, false, true);
		amqpAdmin.declareQueue(requestQueue);
		binding = BindingBuilder.bind(requestQueue).to(directExchange)
				.with(reqQueueName);
		amqpAdmin.declareBinding(binding);
		Queue replyQueue = new Queue(replyQueueName, false, false, true);
		amqpAdmin.declareQueue(replyQueue);
		binding = BindingBuilder.bind(replyQueue).to(directExchange)
				.with(replyQueueName);
		amqpAdmin.declareBinding(binding);
	}
	/**
	 * 删除队列
	 * @param replyQueueName
	 */
	private void deleteQueue(String replyQueueName){
		amqpAdmin.deleteQueue(replyQueueName);
	}

	public <T> T convertSendAndReceiver(final String reqQueueName,
			final String replyQueueName, Object messageObject, String uuid,
			Class<T> targetClass) throws Exception {
		String excharge = rabbitClientConfig.getExcharge();
		final long timeout = rabbitClientConfig.getReplyTimeout();
		// 生成消息
		Message requestMessage = createSendMsg(reqQueueName, replyQueueName,
				messageObject, uuid, timeout);
		//declare queue
		declareQueue(excharge, reqQueueName, replyQueueName);
		//发送消息
		rabbitTemplate.send(excharge, reqQueueName, requestMessage);
		Message receiveMessage = null;
		long i = timeout;
		//接收消息
		while ((receiveMessage = rabbitTemplate.receive(replyQueueName)) == null
				&& i >= 0) {
			// 先休眠一会儿，再接收消息
			Thread.sleep(interval);
			i -= interval;
		}
		//删除队列
		deleteQueue(replyQueueName);
		if (receiveMessage != null) {
			T receiveObject = objectMapper.readValue(receiveMessage.getBody(),
					targetClass);
			return receiveObject;
		}
		return null;
	}

	public Object convertSendAndReceive(final String routingKey,
			final Object message) {
		Object result = rabbitTemplate.convertSendAndReceive(routingKey,
				message, messagePostProcessor());
		if (result == null) {
			throw new AmqpException("MQ服务异常");
		}
		return result;
	}

	@Bean()
	public MessagePostProcessor messagePostProcessor() {
		return (message) -> {
			MessageProperties properities = message.getMessageProperties();
			String corrId = UUID.randomUUID().toString();
			properities.setCorrelationId(corrId.getBytes());
			properities.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
			properities.setTimestamp(new Date());
			long currentTimeMillis = System.currentTimeMillis();
			properities.setMessageId(String.valueOf(currentTimeMillis));

			return message;
		};
	}

}
