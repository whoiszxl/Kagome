package com.whoiszxl.service;

import java.util.List;

import com.whoiszxl.pojo.Videos;
import com.whoiszxl.utils.PagedResult;

public interface VideoService {

	/**
	 * 保存视频
	 * @param video
	 */
	public String saveVideo(Videos video);
	
	
	/**
	 * 通过视频id修改视频的封面
	 * @param videoId
	 * @param coverPath
	 * @return
	 */
	public void updateVideoCover(String videoId, String coverPath);
	
	
	
	/**
	 * 查询所有视频 带分页
	 * @param video 
	 * @param page 页码
	 * @param pageSize 每页显示数量
	 * @param integer 
	 * @return
	 */
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

	
	/**
	 * 获取热搜词
	 * @return
	 */
	public List<String> getHotWords();
}
