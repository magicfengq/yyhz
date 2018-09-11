package com.yyhz.constant;

public class MessageConstanct {

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";
	
	/**
	 * 事件类型：CLICK(搜索图片)
	 */
	public static final String MENU_CLICK_SEARCH = "searchmenu";
	
	/**
	 * 事件类型：CLICK(联系我们)
	 */
	public static final String MENU_CLICK_CONTACT = "contactmenu";
	
	/**
	 * 订阅欢迎语
	 */
	public static final String WELCOME_WORDS = "谢谢您的关注！";
	
	/**
	 * 搜索图片欢迎语
	 */
	public static final String SEARCH_WELCOME_WORDS = "在公众号下，输入样图编号，可搜索图片！";
	
	/**
	 * 查询结果多张提示语
	 */
	public static final String SEARCH_IMG_MANY_WORDS = "您请求的编号不正确哦，请输入正确的信息！";
	
	/**
	 * 未查到结果提示语
	 */
	public static final String SEARCH_IMG_NONE_WORDS = "抱歉哦！没有您查询的信息呢~！";
	
	/**
	 * 联系我们欢迎语
	 */
	public static final String CONTACT_WELCOME_WORDS = "官方电话：1019010919联系在线客服请回复1！";

}
