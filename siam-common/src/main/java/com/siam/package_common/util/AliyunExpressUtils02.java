package com.siam.package_common.util;

import com.siam.package_common.entity.AliyunExpress;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * 全球快递物流查询 - 未知公司【已废弃】
 */
@Data
@Slf4j
@Component
public class AliyunExpressUtils02 {

    @Value(value = "${aliyun.express.appCode:siam1026@163.com}")
    private String appCode;

    /**
     * 查询物流信息
     * @param no 快递单号
     */
    public AliyunExpress query(String no) {
        String host = "https://wuliu.market.alicloudapi.com";// 【1】请求地址 支持http 和 https 及 WEBSOCKET
        String path = "/kdi";  // 【2】后缀
        String type = ""; //  【4】请求参数，不知道可不填 95%能自动识别
        String urlSend = host + path + "?no=" + no +"&type="+type;  // 【5】拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon .setRequestProperty("Authorization", "APPCODE " + appCode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                return this.parseJson(json);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    log.error("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    log.error("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    log.error("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    log.error("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    log.error("套餐包次数用完 ");
                } else {
                    log.error("参数名错误 或 其他错误");
                    log.error(error);
                }
            }
        } catch (MalformedURLException e) {
            log.error("URL格式错误");
        } catch (UnknownHostException e) {
            log.error("URL地址错误");
        } catch (Exception e) {
            // 打开注释查看详细报错异常信息
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    /*
     * 解析返回报文
     */
    private AliyunExpress parseJson(String json) throws IOException {
        Map<String, Object> map = GsonUtils.toMap(json);
        int status = Integer.valueOf((String) map.get("status"));
        switch (status){
            case 0 :
                //正常查询
                //第一次转的时候已经把result转成map对象了，现在需要将map对象再转成json字符串，然后才能将json字符串转成目标实体类
                String resultJson = GsonUtils.toJson(map.get("result"));
                AliyunExpress aliyunExpress = GsonUtils.toBean(resultJson, AliyunExpress.class);
                return aliyunExpress;
            case 201 :
                log.error("快递单号错误");
                break;
            case 203 :
                log.error("快递公司不存在");
                break;
            case 204 :
                log.error("快递公司识别失败");
                break;
            case 205 :
                log.error("没有信息");
                break;
            case 206 :
                log.error("快递单号错误");
                break;
            case 207 :
                log.error("该单号被限制，错误单号 | IP被限制，IP黑名单");
                break;
        }
        return null;
    }
}