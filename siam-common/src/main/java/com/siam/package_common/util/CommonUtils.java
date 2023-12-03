package com.siam.package_common.util;

import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 **/
public class CommonUtils {

    private static final  String MOBILE_REG = "(1)(\\d{10})";

    /**
     * 检测字符串是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        if(!StringUtils.hasText(mobile)){
            return false;
        }
        Pattern pattern = Pattern.compile(MOBILE_REG);
        Matcher m = pattern.matcher(mobile);
        return m.find();
    }

    /**
     * 生成一个n位数的随机数字验证码
     *
     * @param n
     * @return
     */
    public static String genVerificationCode(int n){
        return String.valueOf((int)((Math.random()*9+1) * Math.pow(10,n)));
    }

    /**
     * 生成密码，统一使用此加密方式
     *
     * @param pwd
     * @param salt
     * @return
     */
    public static String genMd5Password(String pwd, String salt){
        return MD5Tools.MD5(MD5Tools.MD5(pwd) + salt);
    }

    /**
     * 生成加密盐
     *
     * @return
     */
    public static String genSalt(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase().substring(0,20);
    }

    /**
     * 判断json数据是否合格
     *
     * @param json
     * @return
     **/
    public static boolean isGoodJson(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * BigDecimal值为null时赋值为default-0
     *
     * @param num
     * @return
     **/
    public static BigDecimal nullToDefault(BigDecimal num){
        return num == null ? new BigDecimal(0): num;
    }

    /**
     * Integer值为null时赋值为default-0
     *
     * @param num
     * @return
     **/
    public  static  Integer nullToDefault(Integer num){
        return  num == null ? 0 : num;
    }

    /**
     * 获取request的所有参数值
     *
     * @param request
     * @return
     **/
    public static String getRequestParamStr(HttpServletRequest request){
        Enumeration e = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        while(e.hasMoreElements()) {
            String param = (String)e.nextElement();
            sb.append(param).append(":").append(request.getParameter(param)).append("\n");
        }
        return sb.toString();
    }

    /**
     * 获取本机计算机名称
     *
     * @return
     **/
    public static String getServerHost(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostName=addr.getHostName().toString();
            return hostName;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本机计算机ip地址
     *
     * @return
     **/
    public static String getServerIP(){
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip=addr.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前功能位置/方法位置/函数位置
     *
     * @return
     **/
    public static String getFunctionLocation(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length > 1) {
            return stackTraceElements[1].getClassName() + " -> " + stackTraceElements[1].getMethodName() + " -> " + stackTraceElements[1].getLineNumber();
        }
        return "";
    }

    public static String getHourBySecond(Integer date) {
        //秒数
		Integer dt = date;
		if (dt < 3600) return Math.round(dt / 60) + "分钟";
		int hour = Math.round(dt / 3600);
		int minute = Math.round((dt - (hour * 3600)) / 60);
		return hour + "小时" + (minute == 0 ? "" : minute + "分钟");
    }

    public static String getHourByMinute(Integer date) {
        //分钟
        Integer dt = date;
        if (dt < 60) return dt + "分钟";
        int hour = Math.round(dt / 60);
        int minute = Math.round(dt - (hour * 60));
        return hour + "小时" + (minute == 0 ? "" : minute + "分钟");
    }
}