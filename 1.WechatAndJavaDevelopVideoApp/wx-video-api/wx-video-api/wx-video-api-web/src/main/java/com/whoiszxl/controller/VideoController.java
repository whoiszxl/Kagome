package com.whoiszxl.controller;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whoiszxl.config.ResourceConfig;
import com.whoiszxl.pojo.Bgm;
import com.whoiszxl.pojo.Users;
import com.whoiszxl.service.BgmService;
import com.whoiszxl.utils.FileUploadUtils;
import com.whoiszxl.utils.JSONResult;
import com.whoiszxl.utils.MergeVideoMp3;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "短视频业务接口", tags = "短视频相关业务Controller")
@RestController
@RequestMapping("/video")
public class VideoController {

	@Autowired
	private FileUploadUtils fileUploadUtils;
	
	@Autowired
	private BgmService bgmService;
	
	@Autowired
	private ResourceConfig resourceConfig;
	
	
	@ApiOperation(value = "用户上传视频接口", notes = "用户上传视频接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="bgmId", value="背景音乐id", required=false, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="videoHeight", value="视频高度", required=true, 
				dataType="String", paramType="form"),
		@ApiImplicitParam(name="desc", value="视频描述", required=false, 
				dataType="String", paramType="form")
	})
	@PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
	public JSONResult upload(
			String userId,
			String bgmId,
			double videoSeconds,
			int videoWidth,
			int videoHeight,
			String desc,
			@ApiParam(value = "短视频文件", required = true)MultipartFile file) {
		
		if(StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户ID不正确");
		}
		String separator = System.getProperty("file.separator");
		String uploadPath = "video/" + userId + "/";
		if(file != null) {
			
			String fileName = file.getOriginalFilename();
			if(StringUtils.isNotBlank(fileName)) {				
				//开始上传操作
				String videoPathOfDB = fileUploadUtils.uploadToQiniu(file, uploadPath);
				if(StringUtils.isNotBlank(videoPathOfDB)) {
					//判断bgmid是否为空，不为空就查询bgm信息并合并视频产生新的视频
					if(StringUtils.isNotBlank(bgmId)) {
						Bgm bgm = bgmService.queryBgmById(bgmId);
						String mp3InputPath = resourceConfig.getQiniuHttpBase() + bgm.getPath();
						MergeVideoMp3 tool = new MergeVideoMp3(resourceConfig.getFfmpegExe());
						String videoInputPath = resourceConfig.getQiniuHttpBase() + videoPathOfDB;
						
						String outputPathFile = resourceConfig.getTmpFilePath() + videoPathOfDB;
						System.out.println("输出的视频路径："+outputPathFile);
						File outFile = new File(outputPathFile);
						if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
							// 创建父文件夹
							System.out.println("开始创建文件夹");
							outFile.getParentFile().mkdirs();
						}
						
						try {
							tool.convertor(videoInputPath, mp3InputPath, videoSeconds, outputPathFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					return JSONResult.ok();
				}
			}
		}
		return JSONResult.errorMsg("上传短视频出错");
	}
}
