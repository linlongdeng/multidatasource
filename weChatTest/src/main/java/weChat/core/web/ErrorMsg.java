package weChat.core.web;
/**
 * 错误信息消息
 * @author deng
 * @date 2015年5月13日
 * @version 1.0.0
 */
public class ErrorMsg {

	public ErrorMsg(int ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
	}

	private int ret;
	
	private String msg;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
