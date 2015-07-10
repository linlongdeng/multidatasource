package weChat.parameter.km;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import weChat.parameter.IReqParam;

/**
 * 短信请求参数对象
 * 
 * @author deng
 * @date 2015年6月11日
 * @version 1.0.0
 */
public class KSmsReqParam implements IReqParam {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull
	@Size(min = 11, max = 11)
	private String phoneno;
	@NotNull
	private String validcode;
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getValidcode() {
		return validcode;
	}
	public void setValidcode(String validcode) {
		this.validcode = validcode;
	}

	
	

}
