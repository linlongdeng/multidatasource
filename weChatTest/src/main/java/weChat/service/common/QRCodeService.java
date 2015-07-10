package weChat.service.common;

import weChat.parameter.IRespParam;

public interface QRCodeService {
	
	public IRespParam getWechatPubInfoQrCode(int wechatpubinfoid);
	
	public IRespParam getParameQrCode(int wechatpubinfoid,String companycode,String cardNum);

}
