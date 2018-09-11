package com.yyhz.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yyhz.easemob.httpclient.apidemo.EasemobIMUsers;
import com.yyhz.easemob.httpclient.apidemo.EasemobMessages;

public class EasemobUtil {
		
	/**
	 * 注册
	 * 
	 * @param username
	 * @param password
	 */
	public static ObjectNode register(String username, String password) {
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put("username", username);
		datanode.put("password", password);
		ObjectNode createNewIMUserSingleNode = EasemobIMUsers
				.createNewIMUserSingle(datanode);
		if (null != createNewIMUserSingleNode) {
			System.out.println("注册IM用户[单个]: "
					+ createNewIMUserSingleNode.toString());
		}
		return createNewIMUserSingleNode;
	}

	/**
	 * 添加好友[单个]
	 * 
	 * @param ownerUserName
	 * @param friendUserName
	 */
	public static ObjectNode addFriendSingle(String ownerUserName,
			String friendUserName) {
		ObjectNode addFriendSingleNode = EasemobIMUsers.addFriendSingle(
				ownerUserName, friendUserName);
		if (null != addFriendSingleNode) {
			System.out.println("添加好友[单个]: " + addFriendSingleNode.toString());
		}
		return addFriendSingleNode;
	}

	/**
	 * 解除好友关系
	 * 
	 * @param ownerUserName
	 * @param friendUserName
	 */
	public static ObjectNode deleteFriendSingle(String ownerUserName,
			String friendUserName) {
		ObjectNode deleteFriendSingleNode = EasemobIMUsers.deleteFriendSingle(
				ownerUserName, friendUserName);
		if (null != deleteFriendSingleNode) {
			System.out.println("解除好友关系: " + deleteFriendSingleNode.toString());
		}
		return deleteFriendSingleNode;
	}

	public static String getEasemobId() {
		Date date = new Date();
		return date.getTime() + "_" + UUIDUtil.getRandomLowStr(4);
	}

	public static void main(String[] args) {
//		ObjectNode objectNode = EasemobUtil.register("aaaaaaaaaaaa",
//				"123456");
//		System.out.println(objectNode.toString());
		List<String> targets = new ArrayList<String>();
		targets.add("960dedcc58264b87ae4890ade5a38ae1");
		targets.add("e7f9b4fdab1b401ea8bf88137d4bb7e2");
		targets.add("582b04d378754397a20c7494d79c1583");
		ObjectNode objectNode = EasemobUtil.sendAnnounceMessage(targets, "testSystemMessage");
		System.out.println("--------------------------------------------");
		System.out.println(objectNode.toString());
	}

	/**
	 * 重置IM用户密码 提供管理员token
	 */
	public ObjectNode modifyPassWord(String username, String password) {

		ObjectNode json2 = JsonNodeFactory.instance.objectNode();
		json2.put("newpassword", password);
		ObjectNode modifyIMUserPasswordWithAdminTokenNode = EasemobIMUsers
				.modifyIMUserPasswordWithAdminToken(username, json2);
		if (null != modifyIMUserPasswordWithAdminTokenNode) {
			System.out.println("重置IM用户密码 提供管理员token: "
					+ modifyIMUserPasswordWithAdminTokenNode.toString());
		}
		return modifyIMUserPasswordWithAdminTokenNode;
	}

	/**
	 * 发透传消息
	 * 
	 * @param users
	 * @param ext
	 * @return
	 */
	public static ObjectNode sendNotify(List<String> users,
			Map<String, Object> ext) {
		return EasemobIMUsers.sendNotify(users, ext);
	}
	
	/**
	 * 发送通告消息
	 */
	public static ObjectNode sendAnnounceMessage(String target, String message) {
		return sendTextMessage(target, "announce", message, null); // 通告消息
	}
	
	/**
	 * 发送通告消息
	 */
	public static ObjectNode sendAnnounceMessage(String target, String message, Map<String, Object> ext) {
		return sendTextMessage(target, "announce", message, ext); // 通告消息
	}
	
	/**
	 * 发送通告消息
	 */
	public static ObjectNode sendAnnounceMessage(List<String> targets, String message) {
		return sendTextMessage(targets, "announce", message, null); // 通告消息
	}

	/**
	 * 发送通告消息
	 */
	public static ObjectNode sendAnnounceMessage(List<String> targets, String message, Map<String, Object> ext) {
		return sendTextMessage(targets, "announce", message, ext); // 通告消息
	}
	
	/**
	 * 发送秀消息
	 */
	public static ObjectNode sendShowMessage(String target, String message, Map<String, Object> ext) {
		return sendTextMessage(target, "show", message, ext); // 通告消息
	}

	/**
	 * 发送秀消息
	 */
	public static ObjectNode sendShowMessage(List<String> targets, String message, Map<String, Object> ext) {
		return sendTextMessage(targets, "show", message, ext); // 通告消息
	}

	/**
	 * 发送系统消息
	 */
	public static ObjectNode sendSystemMessage(String target, String message) {
		return sendTextMessage(target, "admin", message, null);
	}
	
	/**
	 * 发送系统消息
	 */
	public static ObjectNode sendSystemMessage(List<String> targets, String message) {
		return sendTextMessage(targets, "admin", message, null); // 系统消息
	}
	
	/**
	 * 发送文本消息
	 */
	public static ObjectNode sendTextMessage(String target, String from, String message, Map<String, Object> ext) {
		if(target == null || from == null) {
			return null;
		}
		
		List<String> targets = new ArrayList<String>();
		targets.add(target);
		
		return sendTextMessage(targets, from, message, ext);
	}

	/**
	 * 发送文本消息
	 */
	public static ObjectNode sendTextMessage(List<String> targets, String from, String message, Map<String, Object> ext) {
		if(targets == null || targets.size() <= 0 || from == null) {
			return null;
		}
		
		JsonNodeFactory factory = JsonNodeFactory.instance;

        // 给用户发一条文本消息
         ArrayNode targetusers = factory.arrayNode();
        
        for(String usr : targets) {
        	targetusers.add(usr);
        }

        ObjectNode txtmsg = factory.objectNode();
        txtmsg.put("msg", message);
        txtmsg.put("type","txt");
        
        ObjectNode extObj = factory.objectNode();
        if(ext != null) {
        	for (Map.Entry<String, Object> entry : ext.entrySet()) {  	  
        	    extObj.put(entry.getKey(), entry.getValue().toString());
        	}  
        }
        return EasemobMessages.sendMessages("users", targetusers, txtmsg, from, extObj);
	}
}