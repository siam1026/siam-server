package com.siam.package_common.util;

import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@ConfigurationProperties(value = "aliyun.oss")
public class AliyunOss {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String accessKeyId;

    private String secret;

    private String endpoint;

    private String bucketName;

    private OSSClient ossClient;

    /**
     * 上传文件
     *
     * @param inputStream 文件输入流
     * @param objectName 文件全路径
     * @return
     **/
    public void uploadFile(InputStream inputStream, String objectName) throws IOException {
        // 创建OSSClient实例
        OSSClient ossClient = getOssClient();

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）
        ossClient.putObject(bucketName, objectName, inputStream);

        // 关闭OSSClient
        ossClient.shutdown();

        // 关闭IO流
        inputStream.close();
    }

    /**
     * 创建模拟文件夹
     * PS: OSS上传文件时如果某一级目录不存在，系统会自动创建的
     *
     * @param remotePath 文件夹全路径
     * @return
     **/
    public void createFolder(String remotePath){
        // 创建OSSClient实例
        OSSClient ossClient = getOssClient();

        // OSS 控制台中的文件夹本质上来说是创建了一个大小为 0 并以“/”结尾的对象，用于同类文件的归类操作和批处理
        byte[] bytes = new byte[0];
        ossClient.putObject(bucketName, remotePath, new ByteArrayInputStream(bytes));

        // 关闭OSSClient
        ossClient.shutdown();
    }

    /**
     * 关闭OSSClient
     * @return
     **/
    public void closeOssClient(){
        if(ossClient != null){
            ossClient.shutdown();
        }
    }

    /**
     * 判断文件是否存在
     * @param remoteFileName
     * @return
     */
    public Boolean doesObjectExist(String remoteFileName) {
        // 创建OSSClient实例
        OSSClient ossClient = getOssClient();

        // 判断文件是否存在
        Boolean result=ossClient.doesObjectExist(bucketName, remoteFileName);

        // 关闭OSSClient
        ossClient.shutdown();

        return result;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public OSSClient getOssClient() {
        /*if(ossClient == null){
            ossClient = new OSSClient(endpoint, accessKeyId, secret);
        }*/
        ossClient = new OSSClient(endpoint, accessKeyId, secret);
        return ossClient;
    }

    public void setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
    }

    public static void main(String[] args) {
        /*// 检查二级目录是否存在
        boolean found = ossClient.doesObjectExist(bucketName, remotePath);
        if(found == false){
            // 不存在则创建文件夹后再上传
            createFolder(remotePath);
        }*/
    }
}