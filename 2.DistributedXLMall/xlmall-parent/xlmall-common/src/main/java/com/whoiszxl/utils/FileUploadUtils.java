package com.whoiszxl.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.whoiszxl.oss.QiniuConfig;
import com.whoiszxl.oss.UniqueFileUploader;

/**
 * 文件上传工具
 * 
 * @author whoiszxl
 *
 */
public class FileUploadUtils {

	public static String uploadToQiniu(MultipartFile file, String path, QiniuConfig qiniuConfig) {

		String fileName = file.getOriginalFilename();// 获取原始文件名

		String fileExtsionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println("开始上传文件到七牛云,上传文件的文件名:{}"+ fileName);
		String qiniuFileName = null;
		File targetFile = null;
		try {
			targetFile = File.createTempFile("tmp", null);
			file.transferTo(targetFile);
			// 開始執行上傳到七牛云操作
			qiniuFileName = UniqueFileUploader.upload(targetFile, fileExtsionName, path, false, qiniuConfig);
			targetFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qiniuFileName;
	}

	public static String uploadToQiniu(File file, String path, QiniuConfig qiniuConfig) {

		String fileName = file.getName();// 获取原始文件名

		String fileExtsionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println("开始上传文件到七牛云,上传文件的文件名:{}"+ fileName);
		String qiniuFileName = null;
		try {
			// 開始執行上傳到七牛云操作
			qiniuFileName = UniqueFileUploader.upload(file, fileExtsionName, path, false, qiniuConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qiniuFileName;
	}

	/**
	 * 判断文件夹是否存在
	 * @param path 文件夹绝对地址
	 */
	public static void dirIsExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("dir exists");
			} else {
				System.out.println("the same name file exists, can not create dir");
			}
		} else {
			System.out.println("dir not exists, create it ...");
			file.mkdirs();
		}

	}

}
