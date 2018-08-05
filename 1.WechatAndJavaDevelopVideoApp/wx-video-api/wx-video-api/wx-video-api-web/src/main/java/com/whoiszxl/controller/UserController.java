package com.whoiszxl.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whoiszxl.pojo.Users;
import com.whoiszxl.service.UserService;
import com.whoiszxl.utils.FileUploadUtils;
import com.whoiszxl.utils.JSONResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户控制器
 * @author whoiszxl
 *
 */
@Api(value = "用户相关业务接口", tags = "用户相关业务Controller")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUploadUtils fileUploadUtils;
	
	
	
	@ApiOperation(value = "用户上传头像", notes = "用户上传接口")
	@PostMapping("/uploadFace")
	public JSONResult uploadFace(String userId,@RequestParam("file") MultipartFile[] files) {
		
		if(StringUtils.isBlank(userId)) {
			return JSONResult.errorMsg("用户ID不正确");
		}
		
		String uploadPath = "/face/" + userId + "/";
		if(files != null && files.length > 0) {
			
			String fileName = files[0].getOriginalFilename();
			if(StringUtils.isNotBlank(fileName)) {				
				//开始上传操作
				String facePathOfDB = fileUploadUtils.uploadToQiniu(files[0], uploadPath);
				if(StringUtils.isNotBlank(facePathOfDB)) {
					Users user = new Users();
					user.setId(userId);
					user.setFaceImage(facePathOfDB);
					userService.updateUserInfo(user);
				}
			}
		}else {
			return JSONResult.errorMsg("上传头像出错");
		}
		return JSONResult.ok("上传成功");
	}
}
