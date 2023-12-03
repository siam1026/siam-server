package com.siam.package_common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * OSS服务器工具类
 **/
@Slf4j
@Component
public class OSSUtils {

    @Autowired
    private AliyunOss aliyunOSS;

    /**
     * 上传图片--前端文件上传交互
     *
     * @param file 文件对象
     * @param path 模块名称
     * @param id 表唯一标识主键id，如：用户id、商品id；类型应该定义成Object，因为mongodb数据表主键id为字符串类型
     * @return
     **/
    public String uploadImage(MultipartFile file, String path, Object id){
        log.debug("name: " + file.getName());
        log.debug("originalFilename: " + file.getOriginalFilename());
        String objectName = null;
        try {
            InputStream inputStream = file.getInputStream();

            // 文件名处理
            String fileName = file.getOriginalFilename();
            fileName = "siam_" + new Date().getTime() + fileName.substring(fileName.lastIndexOf("."));

            // 根据模块名称、用户id作为文件夹命名
            objectName = "data/images/" + path + "/" + id + "/" + fileName;

            aliyunOSS.uploadFile(inputStream, objectName);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException("图片上传失败");
        }
        return objectName;
    }

    /**
     * 上传图片--逻辑处理时上传文件
     *
     * @return
     **/
    public void uploadImage(InputStream inputStream, String savePath){
        try {
            aliyunOSS.uploadFile(inputStream, savePath);
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException("图片上传失败");
        }
    }

    /**
     * 判断文件是否存在
     * @param remoteFileName
     * @return
     */
    public Boolean doesObjectExist(String remoteFileName) {
        return aliyunOSS.doesObjectExist(remoteFileName);
    }
}