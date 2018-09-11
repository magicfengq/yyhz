package com.yyhz.constant;

public class CfgConstant {

	/**
	 * 第三方用户唯一凭证
	 */
	public static String APPID = "";
	/**
	 * 第三方用户唯一凭证密钥
	 */
	public static String APPSECRET = "";
	/**
	 * 静默授权并自动跳转到回调页snsapi_base
	 */
	public static String _SCOPE = "snsapi_base";
	/**
	 * 获取GET_AUTHORIZE_URL接口
	 */
	public static String GET_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=_APPID&redirect_uri=_REDIRECT_URI&response_type=code&scope=_SCOPE&state=STATE#wechat_redirect";
	/**
	 * 获取GET_CALLBACK_URL接口
	 */
	public static String GET_CALLBACK_URL = "http://mywechatpublhl.tunnel.2bdata.com/tywy/callback";
	/**
	 * 获取ACCESS_TOKEN接口
	 */
	public static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=_APPID&secret=_APPSECRET";
	/**
	 * 微信接入token ，用于验证微信接口
	 */
	public static String TOKEN = "";
	/**
	 * ACCESS_TOKEN有效时间(单位：ms)
	 */
	public static int EFFECTIVE_TIME = 700000;
	/**
	 * 获取用户信息接口
	 */
	public static String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=_ACCESS_TOKEN&openid=_OPENID&lang=zh_CN";
	/**
	 * 客服接口-发消息
	 */
	public static String GET_CUSTOM_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=_ACCESS_TOKEN";
	/**
	 * 公众号拉黑用户
	 */
	public static String BATCHBLACKLIST = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=_ACCESS_TOKEN";
	/**
	 * 公众号取消拉黑用户
	 */
	public static String BATCHUNBLACKLIST = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=_ACCESS_TOKEN";
	/**
	 * 获取服务器访问路径
	 */
	public static String GET_SERVER_URL = "";
	/**
	 * 创建个性化菜单
	 */
	public static String ADD_CONDITIONAL_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=_ACCESS_TOKEN";
	/**
	 * 新增临时素材
	 */
	public static String ADD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=_ACCESS_TOKEN&type=_TYPE";
	
	public static String DEFAULT_HQ_NAME = "";
	
	public static String DEFAULT_HQ_POINT_LNG = "";
	
	public static String DEFAULT_HQ_POINT_LAT = "";

}
