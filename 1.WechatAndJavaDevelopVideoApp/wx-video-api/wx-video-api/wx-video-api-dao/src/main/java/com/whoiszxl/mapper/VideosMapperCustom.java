package com.whoiszxl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.whoiszxl.pojo.Videos;
import com.whoiszxl.pojo.vo.VideosVO;
import com.whoiszxl.utils.MyMapper;

public interface VideosMapperCustom extends MyMapper<Videos> {
	
	public List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
	
}