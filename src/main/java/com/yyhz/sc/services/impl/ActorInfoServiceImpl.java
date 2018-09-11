package com.yyhz.sc.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.ActorInfoDao;
import com.yyhz.sc.data.dao.ActorRoleDao;
import com.yyhz.sc.data.model.ActorInfo;
import com.yyhz.sc.data.model.ActorRole;
import com.yyhz.sc.services.ActorInfoService;
import com.yyhz.sc.services.aop.picture.PictureEntity;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;

@Service
public class ActorInfoServiceImpl extends BaseServiceImpl<ActorInfo> implements ActorInfoService{
	
	@Autowired
	private ActorInfoDao actorInfoDao;
	
	@Autowired
	private ActorRoleDao actorRoleDao;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(actorInfoDao);
	}
	
	@Override
	public PageInfo<ActorInfo> selectActorInfoForPage(ActorInfo info, PageInfo<ActorInfo> pageInfo, String key,boolean b) {
		if(b){
			String roleName = info.getRoleName();
			List<String> actorIdList = new ArrayList<String>();
			if(StringUtils.isNotBlank(roleName)){
				List<ActorRole> actorRoleList = actorRoleDao.selectActorRoleInfo(roleName);
				if(actorRoleList == null || actorRoleList.isEmpty()){
					return pageInfo;
				}
				for(ActorRole actorRole : actorRoleList){
					actorIdList.add(actorRole.getActorId());
				}
				List<ActorRole> filterList = actorRoleDao.selectActorRoleInfoByActorIds(actorIdList);
				if(filterList == null || filterList.isEmpty()){
					return pageInfo;
				}
				Map<String,String> actorIdRoleNameRel = new HashMap<String,String>();
				for(ActorRole actorRole : filterList){
					if(!actorIdRoleNameRel.containsKey(actorRole.getActorId())){
						actorIdRoleNameRel.put(actorRole.getActorId(), actorRole.getRoleName());
					}else{
						String roleNames = actorIdRoleNameRel.get(actorRole.getActorId());						
						actorIdRoleNameRel.put(actorRole.getActorId(), roleNames + "," + actorRole.getRoleName());
					}
				}
				info.setActorIdList(actorIdList);
				super.selectAll(info, pageInfo, key);
				List<ActorInfo> actorInfoList = pageInfo.getRows();
				if(actorInfoList == null || actorInfoList.isEmpty()){
					return pageInfo;
				}
				for(ActorInfo actorInfo : actorInfoList){
					actorInfo.setRoleName(actorIdRoleNameRel.get(actorInfo.getId()));
				}								
			}else{
				super.selectAll(info, pageInfo, key);
				List<ActorInfo> actorInfoList = pageInfo.getRows();
				if(actorInfoList == null || actorInfoList.isEmpty()){
					return pageInfo;
				}
				for(ActorInfo actorInfo : actorInfoList){
					actorIdList.add(actorInfo.getId());
				}
				
				List<ActorRole> filterList = actorRoleDao.selectActorRoleInfoByActorIds(actorIdList);
				if(filterList == null || filterList.isEmpty()){
					return pageInfo;
				}
				Map<String,String> actorIdRoleNameRel = new HashMap<String,String>();
				for(ActorRole actorRole : filterList){
					if(!actorIdRoleNameRel.containsKey(actorRole.getActorId())){
						actorIdRoleNameRel.put(actorRole.getActorId(), actorRole.getRoleName());
					}else{
						String roleNames = actorIdRoleNameRel.get(actorRole.getActorId());						
						actorIdRoleNameRel.put(actorRole.getActorId(), roleNames + "," + actorRole.getRoleName());
					}
				}
				for(ActorInfo actorInfo : actorInfoList){
					actorInfo.setRoleName(actorIdRoleNameRel.get(actorInfo.getId()));
				}
			}
		}else{
			super.selectAll(info, pageInfo, key);
		}
		return pageInfo;
	}

	@Override
	@Pictureable
	public List<ActorInfo> selectByIds(@PictureList List<String> idList) {
		return super.selectByIds(idList);
	}

	@Override
	@Pictureable
	public ActorInfo selectById(@PictureEntity String id) {
		return super.selectById(id);
	}
	
	public static void main(String[] args) {
		/*float a=1, b=2, c=3;
		b = (a + c) / 2;
		a = (b + c) / 5;
		System.out.println(b);
		System.out.println(a);
		System.out.println(a / b);*/
		
		//float a = 1 , b = 9, c = 5;
		float b = 9, c = 5;
		
		float x = 144 - (b + c);
		//float d = (c / b);
		float y = x * c; 
		System.out.println(y);
		/*if(a + b + c == 144){
			float d = (c / b);
			float e = a * c;
			System.out.println(e);
		}*/
		
		
		
		
		
		
	}
	
}