package com.siam.package_common.entity;

import lombok.Data;

import java.util.Date;

/**
 * 本地消息表
 *
 * @return
 * @author 暹罗
 */
@Data
public class LocalMsg {

    //主键id
    private String id;

    //消息内容
    private String content;

    //消息状态 0=执行中 1=执行失败
    private Integer status;

    //发送时间
    private Date sendTime;

    //重试次数
    private Integer retryCount = 0;
}