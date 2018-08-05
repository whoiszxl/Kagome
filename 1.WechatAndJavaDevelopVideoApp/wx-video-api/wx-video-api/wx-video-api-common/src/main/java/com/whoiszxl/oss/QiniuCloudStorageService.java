package com.whoiszxl.oss;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.whoiszxl.config.SecretConfig;

/**
 * 七牛云存储
 * 文档: http://developer.qiniu.com/code/v7/sdk/java.html
 * @author whoiszxl
 *
 */
@Component
public class QiniuCloudStorageService implements CloudStorageService {

	@Autowired
	private SecretConfig secretConfig;
	
	@Override
	public void upload(byte[] data, String path) throws Exception {
		System.out.println(secretConfig.getQiniuAccessKey());
		System.out.println(secretConfig.getQiniuSecretKey());
		System.out.println(secretConfig.getQiniuBucket());
		Auth auth = Auth.create(secretConfig.getQiniuAccessKey(), secretConfig.getQiniuSecretKey());
		Configuration conf = new Configuration(Zone.huanan());
		UploadManager uploadManager = new UploadManager(conf);
		Response res = uploadManager.put(data, path, auth.uploadToken(secretConfig.getQiniuBucket()));
		if(!res.isOK()) {
			throw new RuntimeException("上传七牛出错：" + res.toString());
		}
	}

	@Override
	public void upload(InputStream inputStream, String path) throws Exception {
		upload(IOUtils.toByteArray(inputStream), path);
		
	}

	@Override
	public void upload(File file, String path) throws Exception {
		upload(FileUtils.readFileToByteArray(file), path);
	}

	@Override
	public String getBaseUrl() {
		return secretConfig.getQiniuHttpBase();
	}

}
