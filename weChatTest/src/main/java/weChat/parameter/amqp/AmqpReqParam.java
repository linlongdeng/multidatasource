package weChat.parameter.amqp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import weChat.core.metatype.BaseDto;
import weChat.parameter.IReqParam;
/**
 * 远程调用协议(微信服务发起)，请求参数
 * @author deng
 * @date 2015年5月25日
 * @version 1.0.0
 */
public class AmqpReqParam implements IReqParam {
	@NotNull
	private String cmdid;
	@NotNull
	private String companycode;
	@NotNull
	private int wechatpubinfoid;
	@NotEmpty
	private BaseDto params;

	public String getCmdid() {
		return cmdid;
	}

	public void setCmdid(String cmdid) {
		this.cmdid = cmdid;
	}

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

	public BaseDto getParams() {
		return params;
	}

	public void setParams(BaseDto params) {
		this.params = params;
	}

}
