package weChat.parameter.common;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.parameter.IRespParam;
import weChat.utils.RespMsgCode;
/**
 * 具有动态属性的参数对象
 * @author deng
 * @date 2015年5月21日
 * @version 1.0.0
 */
public class DynamicRespParam implements IRespParam {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ObjectMapper mapper = new ObjectMapper();
	private int ret;

	private String msg;

	private Dto otherProperties = new BaseDto();
	
	public DynamicRespParam(){
		this.ret = RespMsgCode.SUCCESS_CODE;
		this.msg ="";
	}
	
	public DynamicRespParam(int ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
	}


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


	@JsonAnyGetter
	public Map<String, Object> any() {
		return otherProperties;
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		otherProperties.put(name, value);
	}

	public Integer getAsInteger(String pStr) {
		return otherProperties.getAsInteger(pStr);
	}

	public Long getAsLong(String pStr) {
		return otherProperties.getAsLong(pStr);
	}

	public String getAsString(String pStr) {
		return otherProperties.getAsString(pStr);
	}

	public BigDecimal getAsBigDecimal(String pStr) {
		return otherProperties.getAsBigDecimal(pStr);
	}

	public Date getAsDate(String pStr) {
		return otherProperties.getAsDate(pStr);
	}

	public List getAsList(String key) {
		return otherProperties.getAsList(key);
	}

	public Timestamp getAsTimestamp(String pStr) {
		return otherProperties.getAsTimestamp(pStr);
	}

	public Boolean getAsBoolean(String key) {
		return otherProperties.getAsBoolean(key);
	}
	public Date getAsDate(String pStr, String format) {
		return otherProperties.getAsDate(pStr, format);
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
