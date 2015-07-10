package weChat.core.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
/**
 * 校验配置
 * @author deng
 * @date 2015年5月19日
 * @version 1.0.0
 */
@Configuration
public class ValidationConfig {
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean(){
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		return validatorFactoryBean;
	}

}
