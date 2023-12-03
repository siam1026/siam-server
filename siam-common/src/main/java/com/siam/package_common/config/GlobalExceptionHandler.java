package com.siam.package_common.config;

import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.PermissionException;
import com.siam.package_common.exception.StoneCustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public BasicResult handle(Exception e){
        BasicResult basicResult = new BasicResult();
        if(e instanceof StoneCustomerException){
            e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setCode(((StoneCustomerException) e).getCode());
            basicResult.setMessage(e.getMessage());
        }else if(e instanceof BindException){
            //TODO-需要把default message抽离出来
            e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage(e.getMessage());
        }else if(e instanceof PermissionException){
            e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setCode(((PermissionException) e).getCode());
            basicResult.setMessage(e.getMessage());
        }else{
            log.error("\n系统异常", e);
            e.printStackTrace();
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("系统异常，请稍后重试");
        }
        return basicResult;
    }
}