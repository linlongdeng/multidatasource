package weChat.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import weChat.core.exception.ValidationErrorException;
import weChat.service.common.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {
	@Autowired
	private SpringValidatorAdapter validator;
	@Override
	public Errors validate(Object target, String objectName, boolean isThrow)
			throws ValidationErrorException {
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(
				target, objectName);
		validator.validate(target, errors);
		if(errors.hasErrors() && isThrow){
				throw new ValidationErrorException(errors);
		}
		return errors;
	}
	@Override
	public void validate(Object target, String objectName)
			throws ValidationErrorException {
		validate(target, objectName, true);
		
	}
	@Override
	public void validate(Object target, String objectName,Validator validator) {
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(
				target,objectName);
		validator.validate(target, errors);
		if(errors.hasErrors()){
			throw new ValidationErrorException(errors);
	}
	}

}
