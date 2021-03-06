package com.whoiszxl.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.whoiszxl.mapper.TbSpecificationMapper;
import com.whoiszxl.mapper.TbSpecificationOptionMapper;
import com.whoiszxl.pojo.TbSpecification;
import com.whoiszxl.pojo.TbSpecificationExample;
import com.whoiszxl.pojo.TbSpecificationExample.Criteria;
import com.whoiszxl.pojo.TbSpecificationOption;
import com.whoiszxl.pojo.TbSpecificationOptionExample;
import com.whoiszxl.pojogroup.Specification;
import com.whoiszxl.sellergoods.service.SpecificationService;

import com.whoiszxl.entity.PageResult;

/**
 * 服务实现层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		// 插入规格名称排序的信息
		specificationMapper.insert(specification.getSpecification());

		// 插入规则选项的信息
		for (TbSpecificationOption option : specification.getSpecificationOptionList()) {
			option.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(option);
		}

	}

	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification) {
		// 保存修改的规格
		specificationMapper.updateByPrimaryKey(specification.getSpecification());// 保存规格
		// 删除原有的规格选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.whoiszxl.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(specification.getSpecification().getId());// 指定规格ID为条件
		specificationOptionMapper.deleteByExample(example);// 删除
		// 循环插入规格选项
		for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
			specificationOption.setSpecId(specification.getSpecification().getId());
			specificationOptionMapper.insert(specificationOption);
		}
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id) {
		// 通过id查询出规格
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);

		// 查询规格选项列表
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.whoiszxl.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);// 根据规格ID查询
		List<TbSpecificationOption> optionList = specificationOptionMapper.selectByExample(example);
		// 构建组合实体类返回结果
		Specification spec = new Specification();
		spec.setSpecification(tbSpecification);
		spec.setSpecificationOptionList(optionList);
		return spec;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			specificationMapper.deleteByPrimaryKey(id);			
			//删除原有的规格选项		
			TbSpecificationOptionExample example=new TbSpecificationOptionExample();
			com.whoiszxl.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);//指定规格ID为条件
			specificationOptionMapper.deleteByExample(example);//删除
		}
	}

	@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbSpecificationExample example = new TbSpecificationExample();
		Criteria criteria = example.createCriteria();

		if (specification != null) {
			if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
				criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
			}

		}

		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}

}
