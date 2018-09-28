package com.whoiszxl.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.seckill.result.CodeMsg;
import com.whoiszxl.seckill.result.Result;

@Controller
@RequestMapping("/demo")
public class DemoController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!";
	}

	@GetMapping("/hello")
	@ResponseBody
	public Result<String> hello() {
		return Result.success("hello,world");
	}

	@GetMapping("/helloError")
	@ResponseBody
	public Result<String> helloError() {
		return Result.error(CodeMsg.SERVER_ERROR);
	}

	@GetMapping("/thymeleaf")
	public String thymeleaf(Model model) {
		model.addAttribute("name", "王菲");
		return "hello";
	}

}
