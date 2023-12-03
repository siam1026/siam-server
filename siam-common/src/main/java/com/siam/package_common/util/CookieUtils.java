package com.siam.package_common.util;

import javax.servlet.http.Cookie;

/**
 * Cookie工具类
 **/
public class CookieUtils {

    /**
     * 判断cookie中是否存在某个键
     *
     * @param cookies
     * @param name
     * @return
     **/
    public static boolean existCookie(Cookie[] cookies, String name){
       if(cookies != null && cookies.length>0){
           for(Cookie cookie : cookies){
               if(cookie.getName().equals(name)){
                   return true;
               }
           }
       }
       return false;
    }

    /**
     * 获取cookie中键对应的值
     *
     * @param cookies
     * @param name
     * @return
     **/
    public static String getValue(Cookie[] cookies, String name){
        if(cookies != null && cookies.length>0){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    return  cookie.getValue();
                }
            }
        }
        return  null;
    }
}