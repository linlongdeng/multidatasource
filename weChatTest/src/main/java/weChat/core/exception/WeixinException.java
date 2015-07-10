package weChat.core.exception;

/**
 * 微信返回的异常
 * 
 * @author deng
 * @date 2015年7月9日
 * @version 1.0.0
 */
public class WeixinException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errcode;

	private String errmsg;

	public WeixinException(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
