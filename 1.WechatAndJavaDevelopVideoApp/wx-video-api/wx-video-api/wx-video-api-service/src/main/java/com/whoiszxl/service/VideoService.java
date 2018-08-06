package com.whoiszxl.service;

import com.whoiszxl.pojo.Videos;

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
}
