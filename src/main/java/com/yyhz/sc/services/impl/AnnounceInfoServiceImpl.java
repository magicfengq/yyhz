package com.yyhz.sc.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.AnnounceInfoDao;
import com.yyhz.sc.data.model.AnnounceInfo;
import com.yyhz.sc.data.model.AnnouncePicture;
import com.yyhz.sc.data.model.CardAnnouncePublicType;
import com.yyhz.sc.services.AnnounceInfoService;
import com.yyhz.sc.services.AnnouncePictureService;
import com.yyhz.sc.services.CardAnnouncePublicTypeService;
import com.yyhz.utils.UUIDUtil;

@Service
public class AnnounceInfoServiceImpl extends BaseServiceImpl<AnnounceInfo> implements AnnounceInfoService{


	@Autowired
	private AnnounceInfoDao announceInfoDao;
	
	@Resource
	private CardAnnouncePublicTypeService cardAnnouncePublicTypeService;
	
	@Resource
	private AnnouncePictureService announcePictureService;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(announceInfoDao);
	}

	@Override
	public int addAnnouncement(AnnounceInfo announceInfo, String[] publicTypeIds, String[] imageIds) {
		
		int ret = AppRetCode.ERROR;
		
		announceInfo.setId(UUIDUtil.getUUID());
		announceInfo.setCreateTime(new Date());
		announceInfo.setStatus(0);
		announceInfo.setEnrollStatus(0);
		ret = this.insert(announceInfo);
		if(ret > 0) {
			if(publicTypeIds != null && publicTypeIds.length > 0) {
				List<CardAnnouncePublicType> publicTypeList = new ArrayList<CardAnnouncePublicType>();
				
				for(String typeId : publicTypeIds)
				{
					CardAnnouncePublicType publicType = new CardAnnouncePublicType();
					publicType.setId(UUIDUtil.getUUID());
					publicType.setCardAnnounceId(announceInfo.getId());
					publicType.setPublicTypeId(typeId);
					
					publicTypeList.add(publicType);
				}
				
				ret = cardAnnouncePublicTypeService.insert(publicTypeList, "batchInsert");
			}
			
			if(imageIds != null && imageIds.length > 0) {
				List<AnnouncePicture> imgIdList = new ArrayList<AnnouncePicture>();
				
				for(String uuid : imageIds)
				{
					AnnouncePicture announcePicture = new AnnouncePicture();
					announcePicture.setId(UUIDUtil.getUUID());
					announcePicture.setAnnounceId(announceInfo.getId());
					announcePicture.setImgUuid(uuid);
					
					imgIdList.add(announcePicture);
				}
				
				ret = announcePictureService.insert(imgIdList, "batchInsert");
			}
		}
		
		return ret;
	}
	
	@Override
	public int deleteAnnouncement(AnnounceInfo announceInfo) {
		
		int ret = AppRetCode.NORMAL;
		
		CardAnnouncePublicType type = new CardAnnouncePublicType();
		type.setCardAnnounceId(announceInfo.getId());
		
		cardAnnouncePublicTypeService.delete(type, "deleteByCardAnnounceId");
		
		this.delete(announceInfo);
		
		return ret;
	}
	
	@Override
	public AnnounceInfo getAnnouncementDetails(Map<String, Object> condition) {
		List<AnnounceInfo> infos = this.selectAll(condition, "selectAllDetails");
		
		if(infos == null || infos.size() <= 0) {
			return null;
		}else {
			return infos.get(0);
		}
	}
}