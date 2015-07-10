package weChat.amqp;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weChat.core.metatype.BaseDto;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.amqp.AmqpRespParam;
import weChat.amqp.PollingConsumerTest.QueueConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(classes = QueueConfig.class)
public class PollingConsumerTest {
	static String host = "192.168.82.119";
	static int port = 5672;
	static String user = "openrpc";
	static String password = "openrpc";
	static String exchage = "excharge_jtrpc";
	static String vhost = "/openrpc";
	public static String routing = "request_company_01103";
	static String replyQueueName = "reply_company_01103_123458999999999999999";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private AmqpAdmin amqpAdmin;
	@Autowired
	private DirectExchange directExchange;
	@Autowired
	private MessageConverter messageConverter;

	private ObjectMapper jsonObjectMapper = new ObjectMapper();

	@Test
	public void test() throws JsonParseException, JsonMappingException,
			IOException {
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ007");
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000011");
		param.setParams(dto);
		Binding binding = null;
		Queue requestQueue = new Queue(routing, false, false, true);
		amqpAdmin.declareQueue(requestQueue);
		binding = BindingBuilder.bind(requestQueue).to(directExchange)
				.with(routing);
		amqpAdmin.declareBinding(binding);
		Queue replyQueue = new Queue(replyQueueName, false, false, true);
		amqpAdmin.declareQueue(replyQueue);
		binding = BindingBuilder.bind(replyQueue).to(directExchange)
				.with(replyQueueName);
		amqpAdmin.declareBinding(binding);
		rabbitTemplate.convertAndSend(exchage, routing, param, (message) -> {
			MessageProperties properities = message.getMessageProperties();
			String corrId = UUID.randomUUID().toString();
			System.out.println("corrId:" + corrId);
			properities.setCorrelationId(replyQueueName.getBytes());
			//properities.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
			// properities.setTimestamp(new Date());
				long currentTimeMillis = System.currentTimeMillis();
				properities.setMessageId(String.valueOf(currentTimeMillis));
				properities.setExpiration(String.valueOf(50000));
				properities.setReplyTo(replyQueueName);
				properities.setContentEncoding(corrId);
				properities.setType("1");
				return message;
			});
		Message receiveMessage = null;
		
		int i = 5;
		while ((receiveMessage = rabbitTemplate.receive(replyQueueName)) == null
				&& i >= 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i--;
		}
		if (receiveMessage != null) {
			MessageProperties messageProperties = receiveMessage.getMessageProperties();
			String correlationId = new String(messageProperties.getCorrelationId(),"utf-8");
			System.out.println("correlationId:" + correlationId);
			System.out.println("correlationId:" + correlationId);
			AmqpRespParam resp = jsonObjectMapper.readValue(
					receiveMessage.getBody(), AmqpRespParam.class);
			BaseDto data = resp.getData();
			System.out.println("data" + data);
			System.out.println(resp);

		}
		amqpAdmin.deleteQueue(replyQueueName);
		System.out.println("返回的消息是" + receiveMessage);

	}

	@Configuration
	public static class QueueConfig {
		@Bean
		public ConnectionFactory connectionFactory() {

			CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
					host, port);
			connectionFactory.setConnectionTimeout(5000);
			connectionFactory.setUsername(user);
			connectionFactory.setPassword(password);
			connectionFactory.setVirtualHost(vhost);
			return connectionFactory;
		}

		@Bean
		public RabbitTemplate amqpTemplate() {
			RabbitTemplate rabbitTemplate = new RabbitTemplate(
					connectionFactory());
			rabbitTemplate.setMessageConverter(messageConverter());
			rabbitTemplate.setReplyTimeout(60000);
			return rabbitTemplate;
		}

		@Bean
		public MessageConverter messageConverter() {
			// return new JsonMessageConverter();
			Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
			DefaultClassMapper mapper = new DefaultClassMapper();
			mapper.setDefaultType(AmqpRespParam.class);
			converter.setClassMapper(mapper);
			return converter;

		}

		@Bean
		public DirectExchange ex() {
			return new DirectExchange(exchage, false, false);
		}

		@Bean
		public AmqpAdmin amqpAdmin() {
			return new RabbitAdmin(connectionFactory());
		}
	}

}
