package com.siam.package_common.entity;

import lombok.Data;

/**
 * 基础Param类
 *
 * @author 暹罗
 */
@Data
public class BaseParam {

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;
}