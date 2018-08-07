package com.whoiszxl.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whoiszxl.config.ResourceConfig;
import com.whoiszxl.enums.VideoStatusEnum;
import com.whoiszxl.pojo.Bgm;
import com.whoiszxl.pojo.Users;
import com.whoiszxl.pojo.Videos;
import com.whoiszxl.service.BgmService;
import com.whoiszxl.service.VideoService;
import com.whoiszxl.utils.FetchVideoCover;
import com.whoiszxl.utils.FileUploadUtils;
import com.whoiszxl.utils.JSONResult;
import com.whoiszxl.utils.MergeVideoMp3;
import com.whoiszxl.utils.PagedResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "短视频业务接口", tags = "短视频相关业务Controller")
@RestController
@RequestMapping("/video")
public class VideoController extends BasicController {

	@Autowired
	private FileUploadUtils fileUploadUtils;

	@Autowired
	private BgmService bgmService;

	@Autowired
	private ResourceConfig resourceConfig;

	@Autowired
	private VideoService videoService;

	@ApiOperation(value = "用户上传视频接口", notes = "用户上传视频接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "desc", value = "视频描述", required = false, dataType = "String", paramType = "form") })
	@PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
	public JSONResult upload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight,
			String desc, @ApiParam(value = "短视频文件", required = true) MultipartFile file) throws Exception {

		if (StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户ID不正确");
		}
		String uploadPath = "video/" + userId + "/";
		if (file != null) {
			// TODO 开始上传操作，上传到服务器本地
			String videoPathOfDB = fileUploadUtils.uploadToQiniu(file, uploadPath);
			if (StringUtils.isNotBlank(videoPathOfDB)) {
				// 判断bgmid是否为空，不为空就查询bgm信息并合并视频产生新的视频
				if (StringUtils.isNotBlank(bgmId)) {
					Bgm bgm = bgmService.queryBgmById(bgmId);
					String mp3InputPath = resourceConfig.getQiniuHttpBase() + bgm.getPath();
					MergeVideoMp3 tool = new MergeVideoMp3(resourceConfig.getFfmpegExe());
					String videoInputPath = resourceConfig.getQiniuHttpBase() + videoPathOfDB;

					String outputPathFile = resourceConfig.getTmpFilePath() + videoPathOfDB;
					System.out.println("输出的视频路径：" + outputPathFile);
					File outFile = new File(outputPathFile);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						// 创建父文件夹
						System.out.println("开始创建文件夹");
						outFile.getParentFile().mkdirs();
					}

					// 对视频音频进行合并
					tool.convertor(videoInputPath, mp3InputPath, videoSeconds, outputPathFile);
					// 将合并后的视频上传到oss
					String finalVideoPathOfDB = fileUploadUtils.uploadToQiniu(outFile, uploadPath);

					// 生成一发cover封面
					// 1. 先获取到视频文件的前缀，也就是不带后缀的文件名
					String outFileName = outFile.getName();
					String arrayFilenameItem[] = outFileName.split("\\.");
					String fileNamePrefix = "";
					for (int i = 0; i < arrayFilenameItem.length - 1; i++) {
						fileNamePrefix += arrayFilenameItem[i];
					}

					// 拼接出需要入库的封面路径
					String coverFilePathDB = uploadPath + fileNamePrefix + ".jpg";
					// 拼接出本地保存的封面图路径
					String outputPathCoverFile = resourceConfig.getTmpFilePath() + coverFilePathDB;
					System.out.println("输出的封面路径：" + outputPathCoverFile);
					// 然后进行截图一发
					FetchVideoCover coverTool = new FetchVideoCover(resourceConfig.getFfmpegExe());
					coverTool.getCover(videoInputPath, outputPathCoverFile);
					// 拿到文件后直接给存到oss上面去
					File coverFile = new File(resourceConfig.getTmpFilePath() + coverFilePathDB);
					String finalCoverPathDB = fileUploadUtils.uploadToQiniu(coverFile, uploadPath);

					// 然后保存视频信息到数据库
					Videos video = new Videos();
					video.setAudioId(bgmId);
					video.setUserId(userId);
					video.setVideoSeconds((float) videoSeconds);
					video.setVideoHeight(videoHeight);
					video.setVideoWidth(videoWidth);
					video.setVideoDesc(desc);
					video.setVideoPath(finalVideoPathOfDB);
					video.setCoverPath(finalCoverPathDB);
					video.setStatus(VideoStatusEnum.SUCCESS.value);
					video.setCreateTime(new Date());

					String videoId = videoService.saveVideo(video);
					return JSONResult.ok(videoId);
				}

			}
		}
		return JSONResult.errorMsg("上传短视频出错");
	}

	@ApiOperation(value = "用户上传视频封面接口", notes = "用户上传视频封面接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
			@ApiImplicitParam(name = "videoId", value = "视频id", required = true, dataType = "String", paramType = "form") })
	@PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
	public JSONResult uploadCover(String userId, String videoId,
			@ApiParam(value = "短视频文件封面", required = true) MultipartFile file) throws Exception {

		if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
			return JSONResult.errorMsg("短视频or用户ID不正确");
		}
		String uploadPath = "cover/" + userId + "/";
		if (file != null) {
			String fileName = file.getOriginalFilename();
			if (StringUtils.isNotBlank(fileName)) {
				// 开始上传操作
				String coverPathOfDB = fileUploadUtils.uploadToQiniu(file, uploadPath);
				if (StringUtils.isNotBlank(coverPathOfDB)) {
					// 更新cover到数据库
					videoService.updateVideoCover(videoId, coverPathOfDB);
					return JSONResult.ok();
				}
			}
		}
		return JSONResult.errorMsg("上传短视频封面出错");
	}
	
	
	@ApiOperation(value = "获取视频列表接口", notes = "获取视频列表接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "Integer", paramType = "form"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = false, dataType = "Integer", paramType = "form") })
	@PostMapping(value = "/showAll")
	public JSONResult showAll(Integer page, Integer pageSize) throws Exception {
		if(page == null) {
			page = 1;
		}
		if(pageSize == null) {
			pageSize = resourceConfig.getVideoPageSize();
		}
		PagedResult pagedResult = videoService.getAllVideos(page, pageSize);
		return JSONResult.ok(pagedResult);
	}
}
