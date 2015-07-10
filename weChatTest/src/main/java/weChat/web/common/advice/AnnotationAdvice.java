package weChat.web.common.advice;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import weChat.core.exception.ArgumentEmptyException;
import weChat.core.exception.ValidationErrorException;
import weChat.core.exception.WeixinException;
import weChat.core.web.ErrorMsg;
import weChat.parameter.IRespParam;
import weChat.parameter.manager.MRespParam;
import weChat.service.common.RespService;
import weChat.utils.RespMsgCode;

//Target all Controllers annotated with @RestController
@ControllerAdvice(annotations = RestController.class)
public class AnnotationAdvice {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RespService respService;

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handlerRuntimeException(Exception ex,
			WebRequest request) {
		logger.error("系统出现错误", ex);
		String message = ex.getMessage();
		IRespParam error = respService.serverError(message);
		return handleExceptionInternal(error);
	}

	/**
	 * 参数校验出错
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<Object> handleValidException(
			MethodArgumentNotValidException ex, WebRequest request) {
		BindingResult bindingResult = ex.getBindingResult();
		List<ObjectError> errorList = bindingResult.getAllErrors();
		String msg = "";
		for (ObjectError objectError : errorList) {
			if (objectError instanceof FieldError) {
				FieldError fieldError = (FieldError) objectError;
				msg = fieldError.getField() + ":"
						+ fieldError.getDefaultMessage();
			} else {
				msg = objectError.getObjectName() + ":"
						+ objectError.getDefaultMessage();
			}
		}
		IRespParam parameterError = respService.parameterError(msg);
		return handleExceptionInternal(parameterError);

	}

	/**
	 * 参数校验不通过
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { ValidationErrorException.class })
	public ResponseEntity<Object> handlerValidationErrorException(
			ValidationErrorException ex, WebRequest request) {
		IRespParam parameterError = respService.parameterError(ex);
		return handleExceptionInternal(parameterError);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(
			IllegalArgumentException ex, WebRequest request) {
		String message = ex.getMessage();
		IRespParam illegalArgument = respService.IllegalArgument(message);
		return handleExceptionInternal(illegalArgument);

	}

	/**
	 * 参数校验出错处理
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { ArgumentEmptyException.class })
	public ResponseEntity<Object> handleArgumentErrorException(
			ArgumentEmptyException ex, WebRequest request) {
		IRespParam resp = null;
		if(ex.getArgument() != null){
			resp = respService.newRespParam(ex.getRet(),
					ex.getMsg(), ex.getArgument());			
		}else{
			resp =respService.newRespParam(ex.getRet(),
					ex.getMsg());
		}
		return handleExceptionInternal(resp);

	}
	@ExceptionHandler(value = { WeixinException.class })
	public ResponseEntity<Object> handleWeixinException(WeixinException ex, WebRequest request){
		int errcode = ex.getErrcode();
		String errmsg = ex.getErrmsg();
		logger.error("调用微信服务出错,错误码: "  + errcode + "错误消息:" + errmsg,ex);
		MRespParam resp = new MRespParam(errcode, errmsg);
		return handleExceptionInternal(resp);
	}

	protected ResponseEntity<Object> handleExceptionInternal(Object body) {
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Object>(body, headers, HttpStatus.OK);
	}
}
