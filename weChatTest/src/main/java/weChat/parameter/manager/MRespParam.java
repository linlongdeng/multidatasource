package weChat.parameter.manager;

import weChat.parameter.IRespParam;
import weChat.utils.RespMsgCode;

/**
 *  管理系统数据同步接口（管理系统发起）处理结束参数
 * @author deng
 * @date 2015年4月23日
 * @version 1.0.0
 */
public class MRespParam implements IRespParam {

	public MRespParam(int ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
	}
	
	public MRespParam(){
		this.ret = RespMsgCode.SUCCESS_CODE;
		this.msg ="";
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

	@Override
	public String toString() {
		return "{ ret :" + ret + ", msg:" + msg +"}";
	}
	
	
}
