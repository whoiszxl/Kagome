package com.whoiszxl.oss;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * UUID唯一值上傳文件工具
 * @author whoiszxl
 *
 */
public class UniqueFileUploader {
	
	public static String getFormattedBaseUrl() {
        String httpBase = QiniuCloudStorageService.getInstance().getBaseUrl();
        if(!httpBase.endsWith("/")) {
            httpBase += "/";
        }
        return httpBase;
    }

    /**
     * 上传字节数组
     * @param data 字节数组
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     * @throws Exception
     */
    public static String upload(byte[] data, String format) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        QiniuCloudStorageService.getInstance().upload(data, target);
        return httpBase + target;
    }

    /**
     * 上传字节流
     * @param inputStream 字节流
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     * @throws Exception
     */
    public static String upload(InputStream inputStream, String format) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String target = uuid + "." + format;
        String httpBase = getFormattedBaseUrl();
        QiniuCloudStorageService.getInstance().upload(inputStream, target);
        return httpBase + target;
    }

    /**
     * 上传本地文件
     * @param file 待上传的本地文件
     * @param format 文件扩展名
     * @return 云存储HTTP地址
     * @throws Exception
     */
    public static String upload(File file, String format, String path, boolean keepName, QiniuConfig qiniuConfig) throws Exception {
    	String target = null;
    	if(keepName) {
    		target = file.getName();
    	}else {
    		String uuid = UUID.randomUUID().toString();
            target = path + uuid + "." + format;
    	}
    	QiniuCloudStorageService.getInstance().setConfig(qiniuConfig);
    	QiniuCloudStorageService.getInstance().upload(file, target);
        return target;
    }
}
