package com.siam.system.interceptor;

import com.siam.package_common.constant.BaseCode;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.JsonUtils;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户拦截器
 **/
@Slf4j
@Component
public class MemberInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MemberSessionManager memberSessionManager;

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
        Member loginMember = memberSessionManager.getSession(token);
        if(loginMember != null){
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