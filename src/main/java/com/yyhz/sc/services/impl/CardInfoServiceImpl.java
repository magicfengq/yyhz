package com.yyhz.sc.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyhz.constant.AppRetCode;
import com.yyhz.sc.base.page.PageInfo;
import com.yyhz.sc.base.service.BaseServiceImpl;
import com.yyhz.sc.data.dao.CardInfoDao;
import com.yyhz.sc.data.model.CardAnnouncePublicType;
import com.yyhz.sc.data.model.CardInfo;
import com.yyhz.sc.data.model.CardPicture;
import com.yyhz.sc.services.CardAnnouncePublicTypeService;
import com.yyhz.sc.services.CardInfoService;
import com.yyhz.sc.services.CardPictureService;
import com.yyhz.sc.services.aop.picture.PictureList;
import com.yyhz.sc.services.aop.picture.Pictureable;
import com.yyhz.utils.UUIDUtil;

@Service
public class CardInfoServiceImpl extends BaseServiceImpl<CardInfo> implements CardInfoService{

	@Autowired
	private CardInfoDao cardInfoDao;
	
	@Resource
	private CardAnnouncePublicTypeService cardAnnouncePublicTypeService;
	
	@Resource
	private CardPictureService cardPictureService;
	
	@Autowired
	public void setBaseDao() {
	   super.setBaseDao(cardInfoDao);
	}
	
	@Override
	@Pictureable
	public PageInfo<CardInfo> selectAll(CardInfo info, @PictureList PageInfo<CardInfo> pageInfo) {
		return super.selectAll(info, pageInfo);
	}
	
	@Override
	public int addCard(CardInfo cardInfo, String[] imageIds, String[] publicTypeIds) {
		int ret = AppRetCode.ERROR;
		
		cardInfo.setId(UUIDUtil.getUUID());
		cardInfo.setCreateTime(new Date());
		cardInfo.setStatus(0);

		ret = this.insert(cardInfo);
		if(ret > 0) {
			
			if(publicTypeIds != null && publicTypeIds.length > 0) {
				List<CardAnnouncePublicType> publicTypeList = new ArrayList<CardAnnouncePublicType>();
				
				for(String typeId : publicTypeIds)
				{
					CardAnnouncePublicType publicType = new CardAnnouncePublicType();
					publicType.setId(UUIDUtil.getUUID());
					publicType.setCardAnnounceId(cardInfo.getId());
					publicType.setPublicTypeId(typeId);
					
					publicTypeList.add(publicType);
				}
				
				ret = cardAnnouncePublicTypeService.insert(publicTypeList, "batchInsert");
			}
			
			if(imageIds != null && imageIds.length > 0) {
				List<CardPicture> imgIdList = new ArrayList<CardPicture>();
				
				for(String uuid : imageIds)
				{
					CardPicture cardPicture = new CardPicture();
					cardPicture.setId(UUIDUtil.getUUID());
					cardPicture.setCardId(cardInfo.getId());
					cardPicture.setImgUuid(uuid);
					
					imgIdList.add(cardPicture);
				}
				
				ret = cardPictureService.insert(imgIdList, "batchInsert");
			}
		}
		
		return ret;
	}
}