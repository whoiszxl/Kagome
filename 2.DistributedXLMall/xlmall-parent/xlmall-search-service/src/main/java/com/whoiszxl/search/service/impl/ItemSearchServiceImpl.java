package com.whoiszxl.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.whoiszxl.pojo.TbItem;
import com.whoiszxl.search.service.ItemSearchService;

@Service(timeout = 3000)
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SolrTemplate solrTemplate;

	@Override
	public Map<String, Object> search(Map searchMap) {
		Map<String, Object> map = new HashMap<>();
		// 1.查询列表
		map.putAll(searchList(searchMap));
		// 2.根据关键字查询商品分类
		List categoryList = searchCategoryList(searchMap);
		map.put("categoryList", categoryList);
		// 3.查询品牌和规格列表
		if (categoryList.size() > 0) {
			map.putAll(searchBrandAndSpecList((String) categoryList.get(0)));
		}
		return map;
	}

	/**
	 * 根据关键字搜索列表
	 * 
	 * @param keywords
	 * @return
	 */
	private Map searchList(Map searchMap) {
		Map map = new HashMap();
		// 高亮选项初始化
		HighlightQuery query = new SimpleHighlightQuery();
		HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");// 高亮域
		highlightOptions.setSimplePrefix("<em style='color:red'>");// 前缀
		highlightOptions.setSimplePostfix("</em>");
		query.setHighlightOptions(highlightOptions);// 为查询对象设置高亮选项

		// 1.1 关键字查询
		Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);

		// 1.2 按商品分类过滤
		if (!"".equals(searchMap.get("category"))) {// 如果用户选择了分类
			FilterQuery filterQuery = new SimpleFilterQuery();
			Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
			filterQuery.addCriteria(filterCriteria);
			query.addFilterQuery(filterQuery);
		}

		// 1.3 按品牌过滤
		if (!"".equals(searchMap.get("brand"))) {// 如果用户选择了品牌
			FilterQuery filterQuery = new SimpleFilterQuery();
			Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
			filterQuery.addCriteria(filterCriteria);
			query.addFilterQuery(filterQuery);
		}
		// 1.4 按规格过滤
		if (searchMap.get("spec") != null) {
			Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
			for (String key : specMap.keySet()) {

				FilterQuery filterQuery = new SimpleFilterQuery();
				Criteria filterCriteria = new Criteria("item_spec_" + key).is(specMap.get(key));
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);

			}

		}

		// 1.5按价格筛选.....
		if (!"".equals(searchMap.get("price"))) {
			String[] price = ((String) searchMap.get("price")).split("-");
			if (!price[0].equals("0")) {// 如果区间起点不等于0
				Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
			if (!price[1].equals("*")) {// 如果区间终点不等于*
				Criteria filterCriteria = new Criteria("item_price").lessThanEqual(price[1]);
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
		}

		// *********** 获取高亮结果集 ***********
		// 高亮页对象
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
		// 高亮入口集合(每条记录的高亮入口)
		List<HighlightEntry<TbItem>> entryList = page.getHighlighted();
		for (HighlightEntry<TbItem> entry : entryList) {
			// 获取高亮列表(高亮域的个数)
			List<Highlight> highlightList = entry.getHighlights();
			/*
			 * for(Highlight h:highlightList){ List<String> sns =
			 * h.getSnipplets();//每个域有可能存储多值 System.out.println(sns); }
			 */
			if (highlightList.size() > 0 && highlightList.get(0).getSnipplets().size() > 0) {
				TbItem item = entry.getEntity();
				item.setTitle(highlightList.get(0).getSnipplets().get(0));
			}
		}
		map.put("rows", page.getContent());
		return map;
	}

	/**
	 * 查询分类列表
	 * 
	 * @param searchMap
	 * @return
	 */
	private List searchCategoryList(Map searchMap) {
		List<String> list = new ArrayList();
		Query query = new SimpleQuery();
		// 按照关键字查询
		Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		// 设置分组选项
		GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
		query.setGroupOptions(groupOptions);
		// 得到分组页
		GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
		// 根据列得到分组结果集
		GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
		// 得到分组结果入口页
		Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
		// 得到分组入口集合
		List<GroupEntry<TbItem>> content = groupEntries.getContent();
		for (GroupEntry<TbItem> entry : content) {
			list.add(entry.getGroupValue());// 将分组结果的名称封装到返回值中
		}
		return list;
	}

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询品牌和规格列表
	 * 
	 * @param category
	 *            分类名称
	 * @return
	 */
	private Map searchBrandAndSpecList(String category) {
		Map map = new HashMap();
		Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);// 获取模板ID
		if (typeId != null) {
			// 根据模板ID查询品牌列表
			List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
			map.put("brandList", brandList);// 返回值添加品牌列表
			// 根据模板ID查询规格列表
			List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
			map.put("specList", specList);
		}
		return map;
	}
}
