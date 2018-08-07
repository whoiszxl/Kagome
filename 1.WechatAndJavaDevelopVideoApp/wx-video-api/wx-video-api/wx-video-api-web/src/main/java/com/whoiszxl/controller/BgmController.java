package com.whoiszxl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.pojo.Bgm;
import com.whoiszxl.service.BgmService;
import com.whoiszxl.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "背景音乐业务接口", tags = "背景音乐相关业务Controller")
@RestController
@RequestMapping("/bgm")
public class BgmController extends BasicController{

	@Autowired
	private BgmService bgmService;
	
	@ApiOperation(value = "背景音乐列表", notes = "背景音乐列表查询接口")
	@PostMapping("/list")
	public JSONResult<List<Bgm>> list() {
		List<Bgm> bgmList = bgmService.queryBgmList();
		return JSONResult.ok(bgmList);
	}
}
