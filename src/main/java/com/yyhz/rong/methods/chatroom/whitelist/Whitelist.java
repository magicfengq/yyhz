package com.yyhz.rong.methods.chatroom.whitelist;

import com.yyhz.rong.RongCloud;
import com.yyhz.rong.exception.ParamException;
import com.yyhz.rong.models.CheckMethod;
import com.yyhz.rong.models.response.ResponseResult;
import com.yyhz.rong.models.CommonConstrants;
import com.yyhz.rong.util.CommonUtil;
import com.yyhz.rong.util.GsonUtil;
import com.yyhz.rong.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
/**
 *
 * 聊天室用户白名单服务
 * docs: "http://www.rongcloud.cn/docs/server.html#chatroom_user_whitelist"
 *
 * @author RongCloud
 * */
public class Whitelist {
    private static final String UTF8 = "UTF-8";
    private static final String PATH = "chatroom/whitelist";
    private String appKey;
    private String appSecret;
    private RongCloud rongCloud;
    public User user;
    public Messages message;

    public RongCloud getRongCloud() {
        return rongCloud;
    }
    public void setRongCloud(RongCloud rongCloud) {
        this.rongCloud = rongCloud;
        message.setRongCloud(rongCloud);
        user.setRongCloud(rongCloud);
    }
    public Whitelist(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.message = new Messages(appKey,appSecret);
        this.user = new User(appKey,appSecret);
    }
}
