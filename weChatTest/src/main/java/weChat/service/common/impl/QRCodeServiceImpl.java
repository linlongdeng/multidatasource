package weChat.service.common.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Sceneqrcode;
import weChat.domain.primary.Wechatpubinfo;
import weChat.parameter.IRespParam;
import weChat.parameter.common.DynamicRespParam;
import weChat.repository.primary.SceneqrcodeRepository;
import weChat.repository.primary.WechatpubinfoRepository;
import weChat.service.common.QRCodeService;
import weChat.service.weixin.WeixinInfoService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;

@Service
public class QRCodeServiceImpl implements QRCodeService {
	@Autowired
	WechatpubinfoRepository wechatpubinfoRepository;
	@Autowired
	WeixinInfoService weixinInfoService;
	@Autowired
	SceneqrcodeRepository sceneqrcodeRepository;
	
	
	@Override
	public IRespParam getWechatPubInfoQrCode(int wechatpubinfoid) {
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findTopByWechatPubInfoID(wechatpubinfoid);
		DynamicRespParam resp = new DynamicRespParam();
		resp.set("wechaturl", wechatpubinfo.getWechatUrl());
		return resp;
	}


	@Override
	public IRespParam getParameQrCode(int wechatpubinfoid, String companycode,
			String cardNum){
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findTopByWechatPubInfoID(wechatpubinfoid);
		
		DynamicRespParam resp = new DynamicRespParam();
		BaseDto baseDto = new BaseDto();
		baseDto.put("action_name", "QR_SCENE");
		baseDto.put("expire_seconds", AppConstants.EXPIRE_SECONDS);
		BaseDto actionInfo = new BaseDto();
		BaseDto scenInfo = new BaseDto();
		scenInfo.put("scene_id", getSceneID(companycode, cardNum));
		actionInfo.put("scene", scenInfo);	
		baseDto.put("action_info", actionInfo);
		try {
			Dto resDto = weixinInfoService.getParameQrCode(baseDto, wechatpubinfo);
			if (CommonUtils.isEmpty(resDto.get("errcode"))) {
				resp.set("ticket", resDto.get("ticket"));
				resp.set("expire_seconds", resDto.get("expire_seconds"));
				resp.set("url", resDto.get("url"));
			}else {
				if (CommonUtils.isNotEmpty(resDto.get("errcode"))||CommonUtils.isNotEmpty(resDto.get("errmsg"))){
					resp.setRet((int) resDto.get("errcode"));
					resp.setMsg((String) resDto.get("errmsg"));
				}else {
					AppUtils.weixinException();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			AppUtils.weixinException();
		}
		resp.set("cardnum", cardNum);
		return resp;
	}
	
	@Transactional
	private int getSceneID(String companyID,String cardNum){
		Sceneqrcode sceneqrcode = new Sceneqrcode();
		sceneqrcode.setSceneStr(AppConstants.QRCODE_CONSTANS+"|"+cardNum+"|"+companyID);
		sceneqrcode.setUpdateDatetime(CommonUtils.currentTimestamp());
		sceneqrcodeRepository.save(sceneqrcode);
		return sceneqrcode.getSceneID();
	}
	
}
