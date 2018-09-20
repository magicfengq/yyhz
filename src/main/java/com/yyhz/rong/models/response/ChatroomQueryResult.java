package com.yyhz.rong.models.response;

import com.yyhz.rong.models.Result;
import com.yyhz.rong.models.chatroom.ChatroomModel;
import com.yyhz.rong.util.GsonUtil;

import java.util.List;

/**
 *  chatroomQuery 返回结果
 */
public class ChatroomQueryResult extends Result {
	List<ChatroomModel> chatRooms;
	public ChatroomQueryResult(Integer code, String errorMessage, List<ChatroomModel> chatRooms) {
		super(code, errorMessage);
		this.chatRooms = chatRooms;
	}
	/**
	 * 设置chatRooms
	 *
	 */
	public void setChatRooms(List<ChatroomModel> chatRooms) {
		this.chatRooms = chatRooms;
	}
	
	/**
	 * 获取chatRooms
	 *
	 * @return chatRooms
	 */
	public List<ChatroomModel> getChatRooms() {
		return chatRooms;
	}

	@Override
	public String toString() {
		return GsonUtil.toJson(this, ChatroomQueryResult.class);
	}
}
