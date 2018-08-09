package com.whoiszxl.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.whoiszxl.entity.PageResult;
import com.whoiszxl.mapper.TbBrandMapper;
import com.whoiszxl.pojo.TbBrand;
import com.whoiszxl.pojo.TbBrandExample;
import com.whoiszxl.pojo.TbBrandExample.Criteria;
import com.whoiszxl.sellergoods.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {
		return brandMapper.selectByExample(null);
		
	}

	@Override
	public PageResult findAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void add(TbBrand brand) {
		brandMapper.insert(brand);
	}

	@Override
	public void update(TbBrand brand) {
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public TbBrand findOne(Long id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			brandMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findAll(TbBrand tbBrand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TbBrandExample example = new TbBrandExample();
		Criteria criteria = example.createCriteria();
		if(tbBrand != null) {
			if(tbBrand.getName() != null && tbBrand.getName().length() > 0) {
				criteria.andNameLike("%" + tbBrand.getName() + "%");
			}
			
			if(tbBrand.getFirstChar() != null && tbBrand.getFirstChar().length() > 0) {
				criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
			}
		}
		
		Page<TbBrand> page = (Page<TbBrand>) brandMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

}
