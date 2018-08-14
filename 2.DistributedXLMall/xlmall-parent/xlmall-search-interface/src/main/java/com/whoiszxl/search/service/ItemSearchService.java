package com.whoiszxl.search.service;

import java.util.List;
import java.util.Map;

/**
 * 搜索服务接口
 * @author Administrator
 *
 */
public interface ItemSearchService {

	public Map<String,Object> search(Map searchMap);
	
	
	/**
	 * 导入数据
	 * @param list
	 */
	public void importList(List list);
}
