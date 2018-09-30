package com.yyhz.sc.services;

import com.yyhz.sc.base.service.BaseService;
import com.yyhz.sc.data.model.AudioInfo;
import com.yyhz.utils.stream.util.StreamVO;

public interface AudioInfoService extends BaseService<AudioInfo>{

	int insertWithFile(AudioInfo info, StreamVO streamVO);

}
