package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whoiszxl.config.ResourceConfig;
import com.whoiszxl.mapper.BgmMapper;
import com.whoiszxl.mapper.SearchRecordsMapper;
import com.whoiszxl.mapper.VideosMapper;
import com.whoiszxl.mapper.VideosMapperCustom;
import com.whoiszxl.pojo.SearchRecords;
import com.whoiszxl.pojo.Videos;
import com.whoiszxl.pojo.vo.VideosVO;
import com.whoiszxl.service.VideoService;
import com.whoiszxl.utils.PagedResult;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideosMapper videosMapper;
	
	@Autowired
	private VideosMapperCustom videosMapperCustom;
	
	@Autowired
	private ResourceConfig resourceConfig;
	
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	
	@Autowired
	private Sid sid;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String saveVideo(Videos video) {
		String id = sid.nextShort();
		video.setId(id);
		
		videosMapper.insertSelective(video);
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateVideoCover(String videoId, String coverPath) {
		Videos videos = new Videos();
		videos.setId(videoId);
		videos.setCoverPath(coverPath);
		int result = videosMapper.updateByPrimaryKeySelective(videos);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
		
		//保存热搜词
		String desc = video.getVideoDesc();
		if(isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			record.setId(sid.nextShort());
			record.setContent(desc);
			searchRecordsMapper.insert(record);
		}
		
		PageHelper.startPage(page, pageSize);
		List<VideosVO> list = videosMapperCustom.queryAllVideos(desc);
		PageInfo<VideosVO> pageList = new PageInfo<>(list);
		
		List<VideosVO> newlist = new ArrayList<>();
		for (VideosVO videosVO : list) {
			videosVO.setPrefixHost(resourceConfig.getQiniuHttpBase());
			newlist.add(videosVO);
		}
		
		PagedResult pagedResult = new PagedResult();
		pagedResult.setPage(page);
		pagedResult.setTotal(pageList.getPages());
		pagedResult.setRows(newlist);
		pagedResult.setRecords(pageList.getTotal());
		return pagedResult;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<String> getHotWords() {
		return searchRecordsMapper.getHotWords();
	}

	
}
