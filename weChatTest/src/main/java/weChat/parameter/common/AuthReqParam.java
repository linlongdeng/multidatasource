package weChat.parameter.common;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * K米APP获取授权码请求参数
 * 
 * @author deng
 * @date 2015年6月11日
 * @version 1.0.0
 */
public class AuthReqParam {
	private static ObjectMapper mapper = new ObjectMapper();
	@NotNull
	@Pattern(regexp = "^(kmapp)|(manageclient)$", message = "granttype参数值非法")
	private String granttype;
	
	private Integer appid;
	@NotNull
	private String appkey;
	
	private String companycode;

	public String getGranttype() {
		return granttype;
	}

	public void setGranttype(String granttype) {
		this.granttype = granttype;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public Integer getAppid() {
		return appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
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
