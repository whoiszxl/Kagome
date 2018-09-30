package com.whoiszxl.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.product.dataobject.ProductCategory;
import com.whoiszxl.product.dataobject.ProductInfo;
import com.whoiszxl.product.dto.CartDTO;
import com.whoiszxl.product.service.CategoryService;
import com.whoiszxl.product.service.ProductService;
import com.whoiszxl.product.utils.ResultVOUtil;
import com.whoiszxl.product.vo.ProductInfoVO;
import com.whoiszxl.product.vo.ProductVO;
import com.whoiszxl.product.vo.ResultVO;

/**
 * 
 * @author whoiszxl
 *
 */
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 1. 查询所有在架商品
	 * 2. 获取类目type列表
	 * 3. 查询类目
	 * 4. 构造数据
	 */
	@GetMapping("/list")
	public ResultVO list() {
		//1. 查询所有在架商品
		List<ProductInfo> productInfoList = productService.findUpAll();
		
		//2. 获取类目type列表
		List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
		
		//3. 查询类目
		List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
		
		//4.构造数据
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategory productCategory : categoryList) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryName(productCategory.getCategoryName());
			productVO.setCategoryType(productCategory.getCategoryType());
			
			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if(productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);
				}
			}
			productVO.setProductInfoVOList(productInfoVOList);
			productVOList.add(productVO);
		}
		
		return ResultVOUtil.success(productVOList);
	}
	
	
	/**
	 * 获取商品列表(for orderService)
	 * 
	 * @tips 使用@RequestBody后，必须使用PostMapping，单参数才能用@GetMapping
	 * 
	 * @param productIdList 商品id list
	 * @return 商品集合
	 */
	@PostMapping("/listForOrder")
	public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList) {
		return productService.findListByProductIdIn(productIdList);
	}
	
	
	/**
	 * 通过CartDTO传入商品数量和商品id进行库存扣减
	 * @param cartDTOList
	 * @return
	 */
	@PostMapping("/decreaseStock")
	public ResultVO decreaseStock(@RequestBody List<CartDTO> cartDTOList) {
		System.out.println("调用decreaseStockdecreaseStockdecreaseStock");
		productService.decreaseStock(cartDTOList);
		return ResultVOUtil.success("库存扣减成功");
	}
}
