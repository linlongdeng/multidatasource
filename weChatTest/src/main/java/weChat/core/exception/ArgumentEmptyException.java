package weChat.core.exception;

/**参数校验异常类
 * @author deng
 * @date 2015年6月19日
 * @version 1.0.0
 */
public class ArgumentEmptyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ret;
	
	private String msg;
	
	private String argument;

	public ArgumentEmptyException(String ret, String msg, String argument) {
		super();
		this.ret = ret;
		this.msg = msg;
		this.argument = argument;
	}

	public ArgumentEmptyException(String ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
		this.argument = null;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

}
