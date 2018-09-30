package com.yyhz.utils;

import org.apache.commons.lang3.StringUtils;

import com.yyhz.rong.RongCloud;
import com.yyhz.rong.messages.TxtMessage;
import com.yyhz.rong.messages.VoiceMessage;
import com.yyhz.rong.methods.message._private.Private;
import com.yyhz.rong.methods.message.system.MsgSystem;
import com.yyhz.rong.methods.user.User;
import com.yyhz.rong.models.Result;
import com.yyhz.rong.models.message.PrivateMessage;
import com.yyhz.rong.models.message.SystemMessage;
import com.yyhz.rong.models.response.ResponseResult;
import com.yyhz.rong.models.response.TokenResult;
import com.yyhz.rong.models.user.UserModel;

public class RongCloudMethodUtil {
	private final static String appkey = "25wehl3u29fsw";// 申请的融云key
	private final static String appSecret = "bfb0X3V2hl1rK5";// 申请的的云secret

	/**
	 * 注册用户，生成用户在融云的唯一身份标识 Token
	 * 
	 * @param userId
	 * @param name
	 * @param portraitUri
	 */
	public static String AddUserInfo(String userId, String name, String portraitUri) {
		RongCloud rongCloud = RongCloud.getInstance(appkey, appSecret);
		// 自定义 api 地址方式
		// RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);
		User User = rongCloud.user;

		/**
		 * API 文档:
		 * http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
		 *
		 * 注册用户，生成用户在融云的唯一身份标识 Token
		 */
		if(StringUtils.isEmpty(portraitUri)){
			portraitUri="http://www.rongcloud.cn/images/logo.png";
		}
		UserModel user = new UserModel().setId(userId).setName(name).setPortrait(portraitUri);
		
		TokenResult result = null;
		try {
			result = User.register(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("获取token出错了");
		}
		System.out.println("getToken:  " + result.toString());
		return result.getToken();
	}
	/**
	 * 刷新用户信息方法
	 * 
	 * @param userId
	 * @param name
	 * @param portraitUri
	 */
	public static void updateUserInfo(String userId, String name, String portraitUri) {
		RongCloud rongCloud = RongCloud.getInstance(appkey, appSecret);
		// 自定义 api 地址方式
		// RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);
		User User = rongCloud.user;

		/**
		 * API 文档:
		 * http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
		 *
		 * 注册用户，生成用户在融云的唯一身份标识 Token
		 */
		if(StringUtils.isEmpty(portraitUri)){
			portraitUri="http://www.rongcloud.cn/images/logo.png";
		}
		UserModel user = new UserModel().setId(userId).setName(name).setPortrait(portraitUri);
		
		Result result = null;
		try {
			result = User.update(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("获取token出错了");
		}
		System.out.println("getToken:  " + result.toString());
	}
	/**
	 * 发送单聊消息
	 * 
	 * @param content
	 *            消息内容
	 * @param fromUserId
	 *            1
	 * @param targetIds
	 *            userId
	 * @param objectName
	 *            RC:TxtMsg
	 * @param pushContent
	 *            消息标题
	 * @param pushData
	 *            空-安卓 非空：苹果
	 */
	public static ResponseResult privateMessage(String senderId,String content, String[] targetIds, String pushData) {
		TxtMessage txtMessage = new TxtMessage(content, "");
		RongCloud rongCloud = RongCloud.getInstance(appkey, appSecret);
		Private Private = rongCloud.message.msgPrivate;
		/**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/message/private.html#send
         *
         * 发送单聊消息
         * */
        PrivateMessage privateMessage = new PrivateMessage()
                .setSenderId(senderId)
                .setTargetId(targetIds)
                .setObjectName(txtMessage.getType())
                .setContent(txtMessage)
                .setPushContent("")
                .setPushData(pushData)
                .setCount("4")
                .setVerifyBlacklist(0)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setIsIncludeSender(0);
        ResponseResult privateResult = null;
		try {
			privateResult = Private.send(privateMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("send private message:  " + privateResult.toString());
        return privateResult;
	}

	public static void main(String args[]) {

	}
}