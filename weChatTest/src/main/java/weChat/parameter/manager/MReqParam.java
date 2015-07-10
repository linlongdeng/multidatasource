package weChat.parameter.manager;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import weChat.core.metatype.BaseDto;
import weChat.parameter.IReqParam;
/**
 * 管理系统数据同步接口（管理系统发起）送上来的参数
 * @author deng
 * @date 2015年4月23日
 * @version 1.0.0
 */
public class MReqParam implements IReqParam{

	/**场所编号**/
	  @NotNull
	  @Size(max=5)
	  //@Pattern(regexp = "^(1234)|(456)$",message="商家编码格式不正确")
	private String companycode;
	/**场所密码**/
	  @NotNull
	private String access_token;
	
	/**场所服务号ID**/
	  @NotNull
	private int wechatpubinfoid;
	/**数据**/
	  @NotEmpty
	private List<BaseDto> data;

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}





	public List<BaseDto> getData() {
		return data;
	}

	public void setData(List<BaseDto> data) {
		this.data = data;
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


}
