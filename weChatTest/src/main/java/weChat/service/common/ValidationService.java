package weChat.service.common;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import weChat.core.exception.ValidationErrorException;

public interface ValidationService {

	/**
	 * JSR303校验对象，并返回校验的错误对象
	 * @param target 目录对象
	 * @param objectName 对象名
	 * @param isThrow 如果校验不通过，是否要抛出异常
	 * @return
	 */
	public Errors validate(Object target, String objectName, boolean isThrow) throws ValidationErrorException;
	/**
	 * JSR303校验对象，如果出错，抛出异常
	 * @param target
	 * @param objectName
	 * @return
	 * @throws ValidationErrorException
	 */
	public void validate(Object target, String objectName)throws ValidationErrorException;
	/**
	 * 使用外部Validator验证对象
	 * @param target
	 * @param validator
	 */
	public void validate(Object target,String objectName,Validator validator);
}
