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
package weChat.amqp;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import weChat.amqp.JavaConfigFixedReplyQueueTests.FixedReplyQueueConfig;
import weChat.amqp.JavaConfigFixedReplyQueueTests.FixedReplyQueueConfig.PojoListener;
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
public class JavaConfigFixedReplyQueueTests {

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
		param.setCompanycode("00111");
		param.setWechatpubinfoid(1);
		BaseDto dto = new BaseDto();
		dto.put("cardnum", "5000028");
		param.setParams(dto);
		AmqpRespParam resp = (AmqpRespParam) rabbitTemplate.convertSendAndReceive(param,(message) -> {
			MessageProperties properities = message.getMessageProperties();
			String corrId = UUID.randomUUID().toString();
			properities.setCorrelationId(corrId.getBytes());
			properities.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
			properities.setTimestamp(new Date());
			long currentTimeMillis = System.currentTimeMillis();
			properities.setMessageId(String.valueOf(currentTimeMillis));

			return message;
		});
		System.out.println(resp);
	}

	@Configuration
	public static class FixedReplyQueueConfig {
		String host ="127.0.0.1";
		int port=5672;
		String user="openrpc";
		String password ="openrpc";
		String exchage ="excharge_jtrpc";
		String vhost="/openrpc";
		String routing ="request_company_01103";
		String requestQueue ="request_company_01103";
		String responseQueue ="response_company_01103";
		String replyRouting ="response_company_01103";
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

		/**
		 * @return Rabbit template with fixed reply queue.
		 */
		@Bean
		public RabbitTemplate fixedReplyQRabbitTemplate() {
			RabbitTemplate template = new RabbitTemplate(
					rabbitConnectionFactory());
			template.setExchange(ex().getName());
			template.setRoutingKey(routing);
			template.setReplyQueue(replyQueue());
			template.setReplyTimeout(100000);
			template.setMessageConverter(messageConverter());
			return template;
		}

		/**
		 * @return The reply listener container - the rabbit template is the
		 *         listener.
		 */
		@Bean
		public SimpleMessageListenerContainer replyListenerContainer() {
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
			container.setConnectionFactory(rabbitConnectionFactory());
			container.setQueues(replyQueue());
			container.setMessageListener(fixedReplyQRabbitTemplate());
			return container;
		}

		/**
		 * @return The listener container that handles the request and returns
		 *         the reply.
		 */
		//@Bean
		public SimpleMessageListenerContainer serviceListenerContainer() {
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
			container.setConnectionFactory(rabbitConnectionFactory());
			container.setQueues(requestQueue());
			container.setMessageListener(listenerAdapter());
			return container;
		}

		/**
		 * @return a non-durable auto-delete exchange.
		 */
		@Bean
		public DirectExchange ex() {
			return new DirectExchange(exchage, false, false);
		}

		@Bean
		public Binding binding() {
			return BindingBuilder.bind(requestQueue()).to(ex()).with(routing);
		}

		/**
		 * @return an anonymous (auto-delete) queue.
		 */
		@Bean
		public Queue requestQueue() {
			return new Queue(requestQueue,false, false,false);
		}

		/**
		 * @return an anonymous (auto-delete) queue.
		 */
		@Bean
		public Queue replyQueue() {
			return new Queue(responseQueue,false, false,false);
		}
		
		@Bean
		public Binding bindingReply() {
			return BindingBuilder.bind(replyQueue()).to(ex()).with(replyRouting);
		}

		/**
		 * @return an admin to handle the declarations.
		 */
		@Bean
		public RabbitAdmin admin() {
			return new RabbitAdmin(rabbitConnectionFactory());
		}

		@Bean
		MessageListenerAdapter listenerAdapter() {
			PojoListener pojoListener = new PojoListener();
			MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(
					pojoListener, "handleMessage");
			listenerAdapter.setMessageConverter(messageConverter());
			return listenerAdapter;
		}
		/**
		 * Listener that upcases the request.
		 */
		public static class PojoListener {

			public AmqpRespParam handleMessage(AmqpReqParam param)
					throws InterruptedException {
				// Thread.sleep(100000);
				AmqpRespParam resp = new AmqpRespParam();
				resp.setCmdid("1111");
				resp.setMsg("测试");
				resp.setRet(0);
				return resp;
			}
		}

		@Bean
		public MessageConverter messageConverter() {
			// return new JsonMessageConverter();
			return new Jackson2JsonMessageConverter();

		}
	}

}
