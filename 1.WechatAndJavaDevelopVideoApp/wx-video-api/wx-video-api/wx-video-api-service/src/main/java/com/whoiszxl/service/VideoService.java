package com.whoiszxl.service;

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
	 * @param page 页码
	 * @param pageSize 每页显示数量
	 * @return
	 */
	public PagedResult getAllVideos(Integer page, Integer pageSize);
}
