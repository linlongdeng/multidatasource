package weChat.web;

import java.util.Map;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.utils.HttpClientUtils;
import weChat.json.PostJsonUtils;
import weChat.parameter.weixin.WReqParam;

public class WeixinInfoControllerTest {

	private String ip = "http://192.168.74.35:3003";

	@Test
	public void testGetAccessToken() throws Exception {
		String actionPath = "/WeixinInfo/getAccessToken";
		WReqParam param = new WReqParam();
		param.setWechatpubinfoid(1);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			long currentTime = System.currentTimeMillis();
			System.out.println("第" + i + "次调用, 间隔" + (currentTime - startTime)
					/ 1000 + "s");
			Map pDto = PostJsonUtils.postObject( actionPath, param);
			System.out.println("第" + i + "次调用" + "，返回参数是：" + pDto);
			//Thread.sleep(10000);
		}

	}
}
