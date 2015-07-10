package weChat.parameter.km;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;

public class KDynamicReqParam {

	@NotNull
	private String access_token;
	
	private Integer companyid;
	
	private Dto otherProperties = new BaseDto();

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	@JsonAnyGetter
	public Dto any() {
		return otherProperties;
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		otherProperties.put(name, value);
	}
	
	public Object get(String pStr){
		return otherProperties.get(pStr);
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

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	
}
