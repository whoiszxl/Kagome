package com.whoiszxl.mapper;

import java.util.List;

import com.whoiszxl.pojo.Videos;
import com.whoiszxl.pojo.vo.VideosVO;
import com.whoiszxl.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	public List<VideosVO> queryAllVideos();
	
}