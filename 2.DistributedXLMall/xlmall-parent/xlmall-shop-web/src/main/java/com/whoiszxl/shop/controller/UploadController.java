package com.whoiszxl.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whoiszxl.entity.Result;
import com.whoiszxl.oss.QiniuConfig;
import com.whoiszxl.utils.FileUploadUtils;

@RestController
public class UploadController {

	/**
	 * 七牛云http地址
	 */
	@Value("${com.whoiszxl.qiniuHttpBase}")
	private String qiniuHttpBase;
	
	/**
	 * 七牛云accessKey
	 */
	@Value("${com.whoiszxl.qiniuAccessKey}")
	private String qiniuAccessKey;
	
	/**
	 * 七牛云secretKey
	 */
	@Value("${com.whoiszxl.qiniuSecretKey}")
	private String qiniuSecretKey;
	
	/**
	 * 七牛云bucket空间
	 */
	@Value("${com.whoiszxl.qiniuBucket}")
	private String qiniuBucket;
	
	
	@PostMapping("/upload")
	public Result upload(MultipartFile file) {
		System.out.println("qiniuHttpBase:" + qiniuHttpBase);
		try {
			QiniuConfig qiniuConfig = new QiniuConfig(qiniuHttpBase, qiniuAccessKey, qiniuSecretKey, qiniuBucket);
			String uploadPath = FileUploadUtils.uploadToQiniu(file, "goods/", qiniuConfig);
			return new Result(true, qiniuHttpBase + uploadPath);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
	}
	
}
