package weChat.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;



public class TestAMQP {
public static void main(String[] args) {
	ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");

	AmqpAdmin admin = new RabbitAdmin(connectionFactory);
	admin.declareQueue(new Queue("myqueue"));

	AmqpTemplate template = new RabbitTemplate(connectionFactory);
	template.convertAndSend("myqueue", "foo");

	String foo = (String) template.receiveAndConvert("myqueue");
	System.out.println("接收结束");
	
	Message message = MessageBuilder.withBody("foo".getBytes())
			.setContentType(MessageProperties.CONTENT_TYPE_JSON)
			.setMessageId("123")
			.setHeader("bar", "baz")
			.build();

	
}
}
