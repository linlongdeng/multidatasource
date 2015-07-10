package weChat.parameter.manager;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weChat.parameter.IReqParam;

public class MInfoReqParam  implements IReqParam{
	private static ObjectMapper mapper = new ObjectMapper();
	/**场所编号**/
	  @NotNull
	  @Size(max=5)
	private String companycode;
	  
	  @NotNull
	private String access_token;
	  
	  @NotNull
	private int wechatpubinfoid;

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}



	public int getWechatpubinfoid() {
		return wechatpubinfoid;
	}

	public void setWechatpubinfoid(int wechatpubinfoid) {
		this.wechatpubinfoid = wechatpubinfoid;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
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
