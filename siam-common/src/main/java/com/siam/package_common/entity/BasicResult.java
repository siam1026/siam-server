package com.siam.package_common.entity;

import lombok.Data;

/**
 * API接口调用返回结果基础封装类
 * 针对于新增、修改、删除操作，无data属性
 * 前端判断"接口调用成功与否"取决于success属性，success为true-则成功，success为false-则失败
 **/
@Data
public class BasicResult {

    public static final Integer CODE_DEFAULT_SUCCESS = 200;
    public static final Integer CODE_DEFAULT_ERROR = 500;

    public static final String MESSAGE_DEFAULT_SUCCESS = "请求成功";
    public static final String MESSAGE_DEFAULT_ERROR = "网络异常";

    private Boolean success;

    private Integer code;

    private String message;

    private Object data;

    public BasicResult() {
    }

    public BasicResult(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BasicResult success() {
        return new BasicResult(true, CODE_DEFAULT_SUCCESS, MESSAGE_DEFAULT_SUCCESS, null);
    }

    public static BasicResult success(Object data) {
        return new BasicResult(true, CODE_DEFAULT_SUCCESS, MESSAGE_DEFAULT_SUCCESS, data);
    }

    public static BasicResult error(String message) {
        return new BasicResult(false, CODE_DEFAULT_ERROR, message, null);
    }
}