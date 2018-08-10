package com.whoiszxl.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.whoiszxl.pojo.TbSpecification;
import com.whoiszxl.pojo.TbSpecificationOption;

/**
 * 规则组合实体类，接收参数用
 * @author Administrator
 *
 */
public class Specification implements Serializable {	
	private TbSpecification specification;
	private List<TbSpecificationOption> specificationOptionList;
	
	public TbSpecification getSpecification() {
		return specification;
	}
	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}
	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}
	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}	
}