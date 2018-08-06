package com.whoiszxl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.config.ResourceConfig;
import com.whoiszxl.mapper.BgmMapper;
import com.whoiszxl.pojo.Bgm;
import com.whoiszxl.service.BgmService;

@Service
public class BgmServiceImpl implements BgmService {

	@Autowired
	private BgmMapper bgmMapper;
	
	@Autowired
	private ResourceConfig resourceConfig;
	
	@Override
	public List<Bgm> queryBgmList() {
		List<Bgm> bgmList = bgmMapper.selectAll();
		List<Bgm> resultList = new ArrayList<Bgm>();
		for (Bgm bgm : bgmList) {
			bgm.setPath(resourceConfig.getQiniuHttpBase() + bgm.getPath());
			resultList.add(bgm);
		}
		return resultList;
	}

	@Override
	public Bgm queryBgmById(String bgmId) {
		Bgm bgm = bgmMapper.selectByPrimaryKey(bgmId);
		return bgm;
	}

}
