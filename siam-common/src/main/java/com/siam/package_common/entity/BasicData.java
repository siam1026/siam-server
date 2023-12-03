package com.siam.package_common.entity;

/**
 * API接口调用返回结果基础封装类
 * 针对于查询操作，有data属性
 **/
public class BasicData extends BasicResult{
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}