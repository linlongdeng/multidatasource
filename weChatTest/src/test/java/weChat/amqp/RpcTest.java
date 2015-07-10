package weChat.amqp;

/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import weChat.amqp.RpcTest.FixedReplyQueueConfig;
import weChat.core.metatype.BaseDto;
import weChat.parameter.amqp.AmqpReqParam;
import weChat.parameter.amqp.AmqpRespParam;

/**
 * <b>NOTE:</b> This class is referenced in the reference documentation; if it
 * is changed/moved, be sure to update that documentation.
 *
 * @author Gary Russell
 * @since 1.3
 */

@ContextConfiguration(classes = FixedReplyQueueConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class RpcTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * Sends a message to a service that upcases the String and returns as a
	 * reply using a {@link RabbitTemplate} configured with a fixed reply queue
	 * and reply listener, configured with JavaConfig.
	 */
	@Test
	public void test() {
		AmqpReqParam param = new AmqpReqParam();
		param.setCmdid("WJ007");
		param.setCompanycode("01103");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000011");
		param.setParams(dto);
		//String corrId = UUID.randomUUID().toString();
		 String corrId =FixedReplyQueueConfig.responseQueue;
		Object resp = rabbitTemplate
				.convertSendAndReceive(
						FixedReplyQueueConfig.routing,
						param,
						(message) -> {
							MessageProperties properities = message
									.getMessageProperties();
					
							System.out.println("corrId:" + corrId);
							properities.setCorrelationId(corrId.getBytes());
							properities
									.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
							properities.setTimestamp(new Date());
							long currentTimeMillis = System.currentTimeMillis();
							properities.setMessageId(String
									.valueOf(currentTimeMillis));
							properities.setExpiration(String.valueOf(50000));
							properities.setContentEncoding(corrId);

							return message;
						});
		System.out.println("返回消息是" + resp);
	}

	@Configuration
	public static class FixedReplyQueueConfig {
		String host = "192.168.73.158";
		int port = 5672;
		String user = "openrpc";
		String password = "openrpc";
		String exchage = "excharge_jtrpc";
		String vhost = "/openrpc";
		public static String routing = "request_company_01103";
		// String requestQueue ="request_company_00111";
		public static String responseQueue = "reply_company_01103_123458999999999999999";

		@Bean
		public ConnectionFactory rabbitConnectionFactory() {

			CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
					host, port);
			connectionFactory.setConnectionTimeout(5000);
			connectionFactory.setUsername(user);
			connectionFactory.setPassword(password);
			connectionFactory.setVirtualHost(vhost);
			return connectionFactory;
		}

		@Bean
		public SimpleMessageListenerContainer replyListenerContainer(
				RabbitTemplate rabbitTemplate) {
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
			container.setAcknowledgeMode(AcknowledgeMode.NONE);
			container.setConnectionFactory(rabbitConnectionFactory());
			container.setQueues(replyQueue());
			container.setMessageListener(rabbitTemplate);
			return container;
		}

		/**
		 * @return Rabbit template with fixed reply queue.
		 */
		@Bean
		public RabbitTemplate fixedReplyQRabbitTemplate(Queue replyQueue) {
			RabbitTemplate template = new RabbitTemplate(
					rabbitConnectionFactory());
			template.setExchange(exchage);
			template.setRoutingKey(routing);
			template.setReplyQueue(replyQueue);
			template.setReplyTimeout(10000);
			template.setMessageConverter(messageConverter());
			return template;
		}

		/**
		 * @return an anonymous (auto-delete) queue.
		 */
		/*
		 * @Bean public Queue requestQueue() { return new
		 * Queue(requestQueue,false, false,false); }
		 * 
		 * @Bean public Binding binding() { return
		 * BindingBuilder.bind(requestQueue()).to(ex()).with(routing); }
		 */

		/**
		 * @return an anonymous (auto-delete) queue.
		 */
		@Bean
		public Queue replyQueue() {
			return new Queue(responseQueue, false, false, true);
		}

		@Bean
		public Binding bindingReply() {
			return BindingBuilder.bind(replyQueue()).to(ex())
					.with(responseQueue);
		}

		@Bean
		public DirectExchange ex() {
			return new DirectExchange(exchage, false, false);
		}

		/**
		 * @return an admin to handle the declarations.
		 */
		@Bean
		public RabbitAdmin admin() {
			return new RabbitAdmin(rabbitConnectionFactory());
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
	}

}
