package com.whoiszxl.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.whoiszxl.mapper.TbBrandMapper;
import com.whoiszxl.pojo.TbBrand;
import com.whoiszxl.sellergoods.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		return brandMapper.selectByExample(null);
		
	}

}
