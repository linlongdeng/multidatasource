package weChat.core.exception;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ValidationErrorException extends RuntimeException {

	public static final String validationMsg = "数据校验不通过";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Errors errors;

	public ValidationErrorException(Errors errors) {
		super(validationMsg);
		this.errors = errors;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(validationMsg + ":");
		if (errors.hasErrors()) {
			List<ObjectError> objectList = errors.getAllErrors();
			for (ObjectError error : objectList) {
				if (error instanceof FieldError) {
					FieldError fieldError = (FieldError) error;
					sb.append(errors.getObjectName() + "."
							+ fieldError.getField() 
							+ fieldError.getDefaultMessage());
				} else {
					sb.append(error.getObjectName() + ":"
							+ error.getDefaultMessage());
				}
			}
		}
		return sb.toString();
	}

	public Errors getErrors() {
		return errors;
	}

}
