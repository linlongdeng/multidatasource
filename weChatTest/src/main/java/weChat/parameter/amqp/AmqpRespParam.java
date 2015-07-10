package weChat.parameter.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weChat.core.metatype.BaseDto;
import weChat.parameter.IRespParam;
/**
 * 远程调用协议(微信服务发起) 返回消息
 * @author deng
 * @date 2015年5月25日
 * @version 1.0.0
 */
public class AmqpRespParam implements IRespParam {
	private static ObjectMapper mapper = new ObjectMapper();
	private int ret;

	private String msg;

	private String cmdid;

	private BaseDto data;

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

	public String getCmdid() {
		return cmdid;
	}

	public void setCmdid(String cmdid) {
		this.cmdid = cmdid;
	}

	public BaseDto getData() {
		return data;
	}

	public void setData(BaseDto data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		try {
			String string = mapper.writeValueAsString(this);
			return string;
		} catch (JsonProcessingException e) {
		}
		return super.toString();
	}

}
