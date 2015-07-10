package weChat.web.weixin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import weChat.domain.primary.Wechatpubinfo;
import weChat.parameter.IRespParam;
import weChat.parameter.weixin.WReqParam;
import weChat.repository.primary.WechatpubinfoRepository;
import weChat.service.weixin.WeixinInfoService;
import weChat.utils.AppConstants;
import weChat.utils.AppUtils;
import static weChat.utils.AppConstants.*;

@RestController
@RequestMapping("/WeixinInfo")
public class WeixinInfoController {
	@Autowired
	private WeixinInfoService weixinInfoService;
	@Autowired
	private WechatpubinfoRepository wechatpubinfoRepository;

	@RequestMapping("/getAccessToken")
	public IRespParam getAccessToken(@RequestBody @Valid WReqParam param)
			throws Exception {
		Wechatpubinfo wechatpubinfo = wechatpubinfoRepository.findOne(param
				.getWechatpubinfoid());
		// 判断微信公众号是否存在
		AppUtils.assertWechatpubinfoNotNull(wechatpubinfo);
		IRespParam resp = weixinInfoService.getAccessToken(wechatpubinfo);
		return resp;
	}
}
