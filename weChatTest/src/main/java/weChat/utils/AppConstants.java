package weChat.utils;

/**
 * 存在一些常量
 * 
 * @author deng
 * @date 2015年5月21日
 * @version 1.0.0
 */
/**
 * @author Lote
 *
 */
public class AppConstants {
	/**斜杠**/
	public static final String SLASH="/";
	/** 商家 **/
	public static final String COMPANY = "company";
	/** 公众号ID **/
	public static final String WECHATPUBINFOID = "wechatpubinfoid";
	/**数据**/
	public static final String DATA= "data";
	/**
	 * 后缀
	 */
	public static final String WJMQ_SUFFIX="wj_service";
	/**
	 * wj mq服务cmdid
	 */
	public static final String WJMQ_CMDID ="cmdid";
	/**wj mq服务的具体参数名称 **/
	public static final String WJMQ_PARAM = "param";
	/**参数错误**/
	public static final String ARGUMENT_NOT_VALID="ARGUMENT_NOT_VALID_INFO";
	/**参数不能为空**/
	public static final String ARGUMENT_NOT_EMPTY_INFO ="ARGUMENT_NOT_EMPTY_INFO";
	/**会员状态：启用**/
	public static final String MEMBER_STATUS_USER="启用";
	/**微信用户绑定绑卡状态**/
	public static final byte WEIXIN_USER_BIND_STATUS_BIND=1;
	/**K米APP绑卡关系表, 状态 1绑卡 2取消绑卡**/
	public static final byte  KM_BIND_CARD_STATUS_BIND=1;
	/**取消绑卡**/
	public static final byte KM_BIND_CARD_STATUS_UNBIND=2;
	/**K米APP绑卡关系表的绑卡来源，微信绑卡**/
	public static final byte KM_BIND_CARD_SOURCE_WEIXIN =1;
	/**K米APP绑卡关系表的绑卡来源，K米绑卡**/
	public static final byte KM_BIND_CARD_SOURCE_KM=2;
	/**微信绑卡进程休息时间**/
	public static final long SLEEP_MILLIS= 3000;
	/**微信接口调用成功标识**/
	public static final int WEIXIN_SUCCESS=0;
	/**微信接口错误码名称**/
	public static final String WEIXIN_ERRCODE="errcode";
	/**微信接口错误消息名称**/
	public static final String WEXIN_ERRMSG="errmsg";
	/**默认cache**/
	public static final String CACHE_DEFAULT="APP_CACHE";
	/**微信cache**/
	public static final String CACHE_WEIXIN ="WEIXIN_CACHE";
	/**微+与K米的token 专用 cache**/
	public static final String CACHE_WJ_TOKEN="WJ_TOKEN_CACHE";
	/**微+与K米其他的cache**/
	public static final String CACHE_WJ_KM="WJ_KM_CACHE";
	/**其他参数**/
	public static final String OTHER_PARAM ="OTHER_PARAM";
	/**K米的授权类型**/
	public static final String GRANT_TYPE_KM="kmapp";
	/**管理系统的授权类型**/
	public static final String GRANT_TYPE_MANAGE="manageclient";
	/**K米APP用户ID**/
	public static final String CUSTOMERID="CUSTOMERID";
	/**电子会员卡号**/
	public static final String KMID="KMID";
	/**文件下载服务器参数名称**/
	public static final String FILE_DWONLOAD_PATH ="FileServerDownloadPath";
	/**线上会员申请默认等级标志**/
	public static final int onlineApp = 1;
	/**模板标志
	 * 会员消费数据	1
	 * 会员充值数据	2
	 * 会员积分赠送数据	3
	 * 会员积分扣除数据	4
	 * 会员积分兑换物品	5
	 * 会员寄存数据	6
	 * 会员支取数据	7
	 * **/
	public static final int MEMBER_CONSUME =1;
	public static final int MEMBER_RECHARGE =2;
	public static final int MEMBER_GIVING_POINTS =3;
	public static final int MEMBER_POINTS_DEDUCTION=4;
	public static final int MEMBER_POINTS_TO_GIFT=5;
	public static final int MEMBER_CHECKED=6;
	public static final int MEMBER_DRAW=7;
	/**商家发送数据状态标志**/
	public static final byte SEND = 1;
	public static final byte NOT_SEND = 2;
	/**已绑卡状态**/
	public static final byte isBind = 1;
	/**模板启用状态**/
	public static final byte MODEL_MESSAGE_ACTIVE = 1;
	/**临时二维码有效时间**/
	public static final int EXPIRE_SECONDS = 604800;
	/**二维码数据拼接常量**/
	public static final String QRCODE_CONSTANS = "bind";
	/**
	 * 参数相关
	 */
	/**启用**/
	public static final String PARAMER_ACTIVE = "1";
	/**是否开启电子会员卡服务 参数名称**/
	public static final String ISCUSTOMER = "iscustomer";
	/**微信公众号是否开启在线申请会员卡 参数名称**/
	public static final String WECHAT_ONLINE_CUSTOMER = "wechatonlinecustomer";
	/**K米APP是否开启在线申请会员卡 参数名称**/
	public static final String KM_ONLINE_CUSTOMER = "kmonlinecustomer";
	/**是否开启是否开启微信模板功能 参数名称**/
	public static final String WECHAT_TEMPLATE = "wechattemplate";
	
}
