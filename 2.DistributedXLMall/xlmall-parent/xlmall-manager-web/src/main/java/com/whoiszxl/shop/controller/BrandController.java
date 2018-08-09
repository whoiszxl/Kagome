package com.whoiszxl.shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.whoiszxl.entity.PageResult;
import com.whoiszxl.entity.Result;
import com.whoiszxl.pojo.TbBrand;
import com.whoiszxl.sellergoods.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	@GetMapping("/findAll")
	public PageResult findsAll(int page, int size) {
		return brandService.findAll(page, size);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody TbBrand brand) {
		try {
			brandService.add(brand);
			return new Result(true, "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
}
