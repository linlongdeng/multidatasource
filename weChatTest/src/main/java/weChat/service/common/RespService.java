package weChat.service.common;

import static weChat.utils.RespMsgCode.ARGUMENT_NOT_VALID;
import static weChat.utils.RespMsgCode.NO_EXIST;
import static weChat.utils.RespMsgCode.SUCCESS_CODE;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import weChat.core.exception.ValidationErrorException;
import weChat.parameter.IRespParam;
import weChat.parameter.manager.MRespParam;
import static weChat.core.utils.TypeCaseHelper.*;

/**
 * 返回消息服务类
 * 
 * @author deng
 * @date 2015年5月22日
 * @version 1.0.0
 */
@Service
public class RespService {
	@Autowired
	private MessageSource messageSource;
	/** 默认local **/
	private static final Locale defaultLocal = Locale.CHINA;

	/**
	 * 根据code从资源文件中获取消，并转换成int
	 * 
	 * @param code
	 * @return
	 */
	private int getAsInt(String code) {
		int result = convert2Integer(messageSource.getMessage(code, null,
				defaultLocal));
		return result;
	}

	/**
	 * 根据code和参数获取消息,先支持一个接口的
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	private String getMessage(String code, String arg) {
		return messageSource.getMessage(code, new Object[] { arg },
				defaultLocal);
	}

	/**
	 * 根据code获取消息
	 * 
	 * @param code
	 * @return
	 */
	private String getMessage(String code) {
		return messageSource.getMessage(code, null, defaultLocal);
	}

	/**
	 * 根据消息参数和消息编码，生成一个返回消息对象
	 * 
	 * @param code
	 * @param descCode
	 * @return
	 */
	public IRespParam newRespParam(String code, String descCode) {
		return new MRespParam(getAsInt(code), getMessage(descCode));
	}

	/**
	 * 根据消息参数和消息编码，生成一个返回消息对象
	 * 
	 * @param code
	 * @param descCode
	 * @param arg
	 * @return
	 */
	public IRespParam newRespParam(String code, String descCode, String arg) {
		return new MRespParam(getAsInt(code), getMessage(descCode, arg));
	}

	/**
	 * 成功返回参数，返回的消息对象只有ret和msg两个属性。
	 * 
	 * @return
	 */
	public IRespParam success() {
		return newRespParam("SUCCESS", "SUCCESS_INFO");
	}

	/**
	 * 参数错误
	 * 
	 * @param msg
	 * @return
	 */
	public IRespParam parameterError(String arg) {
		return newRespParam("ARGUMENT_NOT_VALID", "ARGUMENT_NOT_VALID_INFO",
				arg);
	}
	/**
	 * 处理参数异常错误
	 * @param ex
	 * @return
	 */
	public IRespParam parameterError(ValidationErrorException ex){
		Errors errors = ex.getErrors();
		List<ObjectError> allErrors = errors.getAllErrors();
		ObjectError objectError = allErrors.get(0);
		String message = messageSource.getMessage(objectError, defaultLocal);
		MRespParam resp = new MRespParam(getAsInt("ARGUMENT_NOT_VALID"), message);
		return resp;
	}

	/**
	 * 查询结果为空
	 * 
	 * @return
	 */
	public IRespParam notExist() {
		return newRespParam("NO_EXIST", "NO_EXIST_INFO");
	}
	
	/**
	 * 服务器错误
	 * @param arg
	 * @return
	 */
	public IRespParam serverError(String arg){
		return newRespParam("SERVER", "SERVER_INFO", arg);
	}
	/**
	 * 参数错误
	 * @param msg
	 * @return
	 */
	public IRespParam IllegalArgument(String msg){
		return newRespParam("ARGUMENT_NOT_VALID","ARGUMENT_NOT_VALID_INFO",msg);
	}
	/**
	 * 参数为空
	 * @param argu
	 * @return
	 */
	public IRespParam argumentEmpty(String argu){
		return newRespParam("ARGUMENT_NOT_EMPTY","ARGUMENT_NOT_EMPTY_INFO",argu);
	}

}
