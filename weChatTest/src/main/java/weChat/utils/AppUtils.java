package weChat.utils;


import weChat.core.exception.ArgumentEmptyException;
import weChat.core.exception.WeixinException;
import weChat.core.metatype.Dto;
import weChat.core.utils.CommonUtils;
import weChat.domain.primary.Company;
import weChat.domain.primary.Companywechatpub;
import weChat.domain.primary.Interfacecheck;
import weChat.domain.primary.MemberCache;
import weChat.domain.primary.Wechatpubinfo;
import static weChat.utils.AppConstants.*;

/**
 * 一些方法集合
 * 
 * @author deng
 * @date 2015年6月4日
 * @version 1.0.0
 */
public abstract class AppUtils {
	/**
	 * 判断code是否和success_code相等，如果不等，返回false,如果相等，返回true
	 * 
	 * @param code
	 * @return
	 */
	public static boolean checkSuccess(int code) {
		return RespMsgCode.SUCCESS_CODE == code;
	}

	/**
	 * 商家不能为空
	 * 
	 * @param company
	 */
	public static void assertCompanyNotNull(Company company) {
		notNull(company, "COMPANY_NULL", "COMPANY_NULL_INFO");
	}

	/**
	 * 微信公众号是否存在
	 * 
	 * @param wechatpubinfo
	 */
	public static void assertWechatpubinfoNotNull(Wechatpubinfo wechatpubinfo) {
		notNull(wechatpubinfo, "WECHATPUBINFO_NULL", "WECHATPUBINFO_NULL_INFO");
	}

	/**
	 * 根据返回参数，判断微信接口调用是否成功,如果接口调用成功返回true,否则抛出微信异常
	 * 
	 * @param pDto
	 * @return
	 */
	public static boolean checkWeixinApi(Dto pDto) {
		// 查询errcode是否存在，如果不存在，肯定调用成功
		Integer errcode = pDto.getAsInteger(WEIXIN_ERRCODE);
		if (CommonUtils.isEmpty(errcode)) {
			return true;
		} else {
			// 如果errcode，则看一下是否等于0
			if (WEIXIN_SUCCESS == errcode) {
				return true;
			} else {
				String errmsg = pDto.getAsString("errmsg");
				throwWeixinException(errcode, errmsg);
				return false;
			}
		}
	}

	/**
	 * 授权失败
	 * 
	 * @param interfacecheck
	 */
	public static void assertInterfacecheckNotNull(Interfacecheck interfacecheck) {
		notNull(interfacecheck, "ACCESS_TOKEN", "ACCESS_TOKEN_INFO");
	}

	/**
	 * 授权失败
	 * 
	 * @param flag
	 */
	public static void assertTrueAccess(boolean flag) {
		isTrue(flag, "ACCESS_TOKEN", "ACCESS_TOKEN_INFO");
	}
/**
 * 微信公众号不存在
 * @param companywechatpub
 */
	public static void assertWechatNotNull(Companywechatpub companywechatpub) {
		notNull(companywechatpub, "WECHATPUB", "WECHATPUB_INFO");

	}
	/**
	 * 会员建卡分店信息查询失败
	 * @param company
	 */
	public static void assertBuildCardNull(Company company){
		notNull(company, "BUILD_CARD","BUILD_CARD_INFO");
	}
	
	/**
	 * 验证商家会员信息
	 * @param memberCache
	 */
	public static void assertMemberCacheNull(MemberCache memberCache){
		notNull(memberCache, "NO_MEMBER","NO_MEMBER_INFO");
	}
	
	/**更新KM绑卡时，更新会员信息
	 * @param memberCache
	 */
	public static void assertMemberCacheKmNull(MemberCache memberCache){
		notNull(memberCache, "KM_BIND","KM_BIND_INFO");
	}
	
	/**
	 * 查询的参数不存
	 */
	public static void paramerNotExist(){
		 throwArgumentEmptyException("PARAMER", "PARAMEE_INFO", null);
	}
	/**
	 * 会员信息不存在
	 * @param memberCache
	 * @param kmid
	 */
	public static void assertMemberNotExits(MemberCache memberCache, String kmid){
		notNull(memberCache, "MEMBER_NOT_EXIST","MEMBER_NOT_EXIST_INFO",kmid);
	}
	
	/**
	 * 微信通信异常
	 */
	public static void weixinException(){
		 throwArgumentEmptyException("WEIXIN", "WEIXIN_INFO", null);
	}
	
	/**
	 * =商家公众号信息验证
	 * @author deng
	 * @param companywechatpub
	 */
	public static void assertCompanywechatpubNotNull(Companywechatpub companywechatpub) {
		notNull(companywechatpub, "WECHAT_PUB", "WECHAT_PUB_INFO");
	}

	private static void notNull(Object object, String ret, String msg,
			String argu) {
		if (object == null) {
			throwArgumentEmptyException(ret, msg, argu);
		}
	}

	private static void notNull(Object object, String ret, String msg) {
		if (object == null) {
			throwArgumentEmptyException(ret, msg, null);
		}
	}

	/**
	 * 表达是否为真，如果为假抛出异常
	 * 
	 * @param expression
	 * @param ret
	 * @param msg
	 * @param argu
	 */
	private static void isTrue(boolean expression, String ret, String msg,
			String argu) {
		if (!expression) {
			throwArgumentEmptyException(ret, msg, argu);
		}
	}

	private static void isTrue(boolean expression, String ret, String msg) {
		if (!expression) {
			throwArgumentEmptyException(ret, msg, null);
		}
	}

	/**
	 * 抛出ArgumentEmptyException
	 * 
	 * @param ret
	 * @param msg
	 * @param argu
	 */
	private static void throwArgumentEmptyException(String ret, String msg,
			String argu) {
		ArgumentEmptyException ex = new ArgumentEmptyException(ret, msg, argu);
		throw ex;
	}
	
	/**
	 * 抛出微信定义异常
	 * @author deng
	 * @param errcode
	 * @param errmsg
	 */
	public static void throwWeixinException(int errcode, String errmsg){
		throw new WeixinException(errcode, errmsg);
	}
	/**
	 *	功能未开启
	 */
	public static void notActive(){
		throwArgumentEmptyException("PARAMER_NOT_ACTIVE", "PARAMER_NOT_ACTIVE_INFO",null);
	}
}
