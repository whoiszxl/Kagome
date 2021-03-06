package com.whoiszxl.service;

import java.util.List;

import com.whoiszxl.pojo.Bgm;

public interface BgmService {

	/**
	 * 查询所有的bgm
	 * @return 数据库中所有的bgm
	 */
	public List<Bgm> queryBgmList();
	
	/**
	 * 通过bgmId查询这个bgm
	 * @param bgmId
	 * @return
	 */
	public Bgm queryBgmById(String bgmId);
}
