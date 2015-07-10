package weChat.parameter.weixin;

import javax.validation.constraints.NotNull;

import weChat.parameter.IReqParam;

public class WReqParam implements IReqParam {

	@NotNull
	private Integer wechatpubinfoid;

	public Integer getWechatpubinfoid() {
		return wechatpubinfoid;
	}

	public void setWechatpubinfoid(Integer wechatpubinfoid) {
		this.wechatpubinfoid = wechatpubinfoid;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(obj instanceof WReqParam){
			WReqParam other = (WReqParam) obj;
			if(wechatpubinfoid != null){
				return wechatpubinfoid.equals(other.getWechatpubinfoid());				
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
	
}
