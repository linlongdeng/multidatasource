package weChat.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import weChat.domain.primary.MemberCache;

public class ValidationTests {

	@Test
	public void testValidation(){
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
		MemberCache memberCache = new MemberCache();
		Set<ConstraintViolation<MemberCache>> result = validator.validate(memberCache);
		for(ConstraintViolation<MemberCache> cv : result){
			System.out.println("path:" + cv.getPropertyPath());
			System.out.println("message:" +cv.getMessage());
		}
	}
}
