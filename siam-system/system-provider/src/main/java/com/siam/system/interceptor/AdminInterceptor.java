package com.siam.system.interceptor;

import com.siam.package_common.constant.BaseCode;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.JsonUtils;
import com.siam.system.modular.package_user.auth.cache.AdminSessionManager;
import com.siam.system.modular.package_user.entity.Admin;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员拦截器
 **/
@Slf4j
@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AdminSessionManager adminSessionManager;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从header或param里面获取token(暂时这样写，兼容之前前端的写法)
        String token = TokenUtil.getToken();
        log.debug("\ntoken = " + token);

        if(token == null){
            BasicResult basicResult = new BasicResult();
            basicResult.setSuccess(false);
            basicResult.setCode(BaseCode.TOKEN_ERR);
            basicResult.setMessage("您暂未登录，请登陆后查看");
            log.debug("\n" + JsonUtils.toJson(basicResult));
            response.getWriter().print(JsonUtils.toJson(basicResult));
            return false;
        }

        //TODO - 目前如果此处调用出错，该方法是不会继续执行，但是目标接口依旧是返回了数据
        Admin loginAdmin = adminSessionManager.getSession(token);
        if(loginAdmin != null){
            return true;
        }

        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        BasicResult basicResult = new BasicResult();
        basicResult.setSuccess(false);
        basicResult.setCode(BaseCode.TOKEN_ERR);
        basicResult.setMessage("您暂未登录，请登陆后查看");
        log.debug("\n" + JsonUtils.toJson(basicResult));
        response.getWriter().print(JsonUtils.toJson(basicResult));
        return false;
    }
}