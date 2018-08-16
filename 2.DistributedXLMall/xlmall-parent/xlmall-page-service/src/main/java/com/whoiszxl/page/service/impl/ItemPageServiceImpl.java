package com.whoiszxl.page.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Service;
import com.whoiszxl.mapper.TbGoodsDescMapper;
import com.whoiszxl.mapper.TbGoodsMapper;
import com.whoiszxl.mapper.TbItemCatMapper;
import com.whoiszxl.mapper.TbItemMapper;
import com.whoiszxl.page.service.ItemPageService;
import com.whoiszxl.pojo.TbGoods;
import com.whoiszxl.pojo.TbGoodsDesc;
import com.whoiszxl.pojo.TbItem;
import com.whoiszxl.pojo.TbItemExample;
import com.whoiszxl.pojo.TbItemExample.Criteria;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class ItemPageServiceImpl implements ItemPageService {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Value("${pagedir}")
	private String pagedir;
	
	
	@Autowired
	private TbGoodsMapper goodsMapper;
	
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public boolean genItemHtml(Long goodsId) {
		
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		configuration.setDefaultEncoding("UTF-8");
		try {
			Template template = configuration.getTemplate("item.ftl");
			template.setEncoding("UTF-8");
			//创建数据模型
			Map dataModel=new HashMap<>();
			//1.商品主表数据
			TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goods", goods);
			//2.商品扩展表数据
			TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
			dataModel.put("goodsDesc", goodsDesc);
			//3.读取商品分类
			
			String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
			String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
			String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
			dataModel.put("itemCat1", itemCat1);
			dataModel.put("itemCat2", itemCat2);
			dataModel.put("itemCat3", itemCat3);
			
			//4.读取SKU列表
			TbItemExample example=new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andGoodsIdEqualTo(goodsId);//SPU ID
			criteria.andStatusEqualTo("1");//状态有效			
			example.setOrderByClause("is_default desc");//按是否默认字段进行降序排序，目的是返回的结果第一条为默认SKU
			
			List<TbItem> itemList = itemMapper.selectByExample(example);
			dataModel.put("itemList", itemList);
			
			/*Writer out=new FileWriter(pagedir+goodsId+".html");
			template.setEncoding("UTF-8");
			template.process(dataModel, out);//输出
			out.close();*/
			
			// 创建输出对象
			File tagFile = new File(pagedir + goodsId+".html");
			//以UTF-8的形式输出文件
			Writer out = new PrintWriter(tagFile,"UTF-8");
			template.process(dataModel, out);//输出
			return true;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
	}

}