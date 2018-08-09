package com.whoiszxl.sellergoods.service;

import java.util.List;

import com.whoiszxl.entity.PageResult;
import com.whoiszxl.pojo.TbBrand;

/**
 * 品牌服务接口
 * @author whoiszxl
 *
 */
public interface BrandService {

	/**
	 * 查询所有品牌列表
	 * @return
	 */
	public List<TbBrand> findAll();
	
	/**
	 * 分页查询品牌列表
	 * @param pageNum 页码
	 * @param pageSize 页面数量
	 * @return
	 */
	public PageResult findAll(int pageNum, int pageSize);
}
