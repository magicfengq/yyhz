package com.yyhz.sc.services;

import com.yyhz.sc.data.model.Advert;
import com.yyhz.utils.stream.util.StreamVO;

import java.util.List;

import com.yyhz.sc.base.service.BaseService;

public interface AdvertService extends BaseService<Advert>{

	public int insertWithImage(Advert info, StreamVO streamVO);

	public int updateWithImage(Advert info, StreamVO streamVO);
	
	public List<Advert> selectEntityByIds(List<String> idList);

}
