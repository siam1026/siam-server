package com.siam.package_common.exception;

import com.siam.package_common.constant.BasicResultCode;

/**
 * 自定义业务异常类
 **/
public class StoneCustomerException extends RuntimeException{
    // 结果码
    private Integer code = BasicResultCode.ERR;

    // 结果码描述
    private String message;

    public StoneCustomerException() {
    }

    public StoneCustomerException(String message) {
        this.message = message;
    }

    public StoneCustomerException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}