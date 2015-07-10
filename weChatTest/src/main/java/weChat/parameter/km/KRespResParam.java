package weChat.parameter.km;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weChat.core.metatype.BaseDto;
import weChat.parameter.IRespParam;

public class KRespResParam implements IRespParam {
	
	private static ObjectMapper mapper = new ObjectMapper();
	private int ret;

	private String msg;
	
	private List<BaseDto> res;

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

	public List<BaseDto> getRes() {
		return res;
	}

	public void setRes(List<BaseDto> res) {
		this.res = res;
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
