package com.whoiszxl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.mapper.BgmMapper;
import com.whoiszxl.pojo.Bgm;
import com.whoiszxl.service.BgmService;

@Service
public class BgmServiceImpl implements BgmService {

	@Autowired
	private BgmMapper bgmMapper;
	
	@Override
	public List<Bgm> queryBgmList() {
		List<Bgm> bgmList = bgmMapper.selectAll();
		return bgmList;
	}

}
