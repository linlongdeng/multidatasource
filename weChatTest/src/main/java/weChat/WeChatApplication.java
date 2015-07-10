package weChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:jdbc.properites",
		"classpath:rabbitmq.properties", "classpath:km.properties",
		"classpath:weixin.properties" })
public class WeChatApplication {


	public static void main(String[] args) {
		SpringApplication.run(WeChatApplication.class, args);
	}
}
