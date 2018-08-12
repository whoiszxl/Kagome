package com.whoiszxl.oss;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云存储
 * 文档: http://developer.qiniu.com/code/v7/sdk/java.html
 * @author whoiszxl
 *
 */
public class QiniuCloudStorageService {

	/**
	 * 七牛云http地址
	 */
	private String qiniuHttpBase;
	
	/**
	 * 七牛云accessKey
	 */
	private String qiniuAccessKey;
	
	/**
	 * 七牛云secretKey
	 */
	private String qiniuSecretKey;
	
	/**
	 * 七牛云bucket空间
	 */
	private String qiniuBucket;
	

	public void upload(byte[] data, String path) throws Exception {
		System.out.println(qiniuAccessKey);
		System.out.println(qiniuSecretKey);
		System.out.println(qiniuBucket);
		Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
		Configuration conf = new Configuration(Zone.huanan());
		UploadManager uploadManager = new UploadManager(conf);
		Response res = uploadManager.put(data, path, auth.uploadToken(qiniuBucket));
		if(!res.isOK()) {
			throw new RuntimeException("上传七牛出错：" + res.toString());
		}
	}

	public void upload(InputStream inputStream, String path) throws Exception {
		upload(IOUtils.toByteArray(inputStream), path);
		
	}
	
	public void upload(File file, String path) throws Exception {
		upload(FileUtils.readFileToByteArray(file), path);
	}

	public String getBaseUrl() {
		return qiniuHttpBase;
	}
	
	
	private QiniuCloudStorageService() {
		if(Holder.instance != null) {
			throw new IllegalStateException();
		}
	}

	public static class Holder {
		private static QiniuCloudStorageService instance = new QiniuCloudStorageService();
	}
	
	public static QiniuCloudStorageService getInstance() {
		return Holder.instance;
	}

	public void setConfig(QiniuConfig qiniuConfig) {
		this.qiniuAccessKey = qiniuConfig.getQiniuAccessKey();
		this.qiniuSecretKey = qiniuConfig.getQiniuSecretKey();
		this.qiniuBucket = qiniuConfig.getQiniuBucket();
		this.qiniuHttpBase = qiniuConfig.getQiniuHttpBase();
	}
}
