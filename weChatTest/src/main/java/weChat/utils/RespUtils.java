package weChat.utils;

import static weChat.utils.RespMsgCode.*;
import weChat.parameter.IRespParam;
import weChat.parameter.manager.MRespParam;

/**
 * 返回消息工具类
 * 
 * @author deng
 * @date 2015年4月23日
 * @version 1.0.0
 */
public class RespUtils {
	/**
	 * 管理系统数据同步接口（管理系统发起）的成功返回参数
	 * 
	 * @return
	 */
	public static IRespParam successMR() {
		return new MRespParam(SUCCESS_CODE, "");
	}
	
	public static IRespParam errorMR(int ret , String msg){
		return new MRespParam(ret, msg);
	}
	
	

}
