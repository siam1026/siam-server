package com.siam.package_weixin_basic.util;

import com.alibaba.fastjson.JSONObject;
import com.siam.package_common.util.OSSUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信二维码服务类
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(value = "wxlogin")
public class WxQrCodeUtils {

    private String appId;

    private String secret;

    @Autowired
    private OSSUtils ossUtils;

    /**
     * 生成太阳码
     *
     * @return
     * @author 暹罗
     */
    public void generateSunCode(String page, String scene, String savePath) {
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + getToken());
            System.out.println(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", scene);//参数
            paramJson.put("page", page);//小程序未发布前注释，否则找不到对应的页面，就会生成错误的图片
            paramJson.put("width", 430);
            //paramJson.put("auto_color", true);
            //paramJson.put("auto_color", false);
            JSONObject lineColor = new JSONObject();
            lineColor.put("r", 0);
            lineColor.put("g", 0);
            lineColor.put("b", 0);
            paramJson.put("line_color", lineColor);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();

            //将太阳码上传到oss服务器上
            ossUtils.uploadImage(httpURLConnection.getInputStream(), savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public String getToken() {
        try {
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("grant_type", "client_credential");
            map.put("appid", appId);// 这里我是写在配置类里，需要改成自己的appid
            map.put("secret", secret); //这里我是写在配置类里，需要改成自己的secret
            String rt = sendPost("https://api.weixin.qq.com/cgi-bin/token", map);
            JSONObject json = JSONObject.parseObject(rt);
            if (json.getString("access_token") != null || json.getString("access_token") != "") {
//                System.out.println("token:" + json.getString("access_token"));
                return json.getString("access_token");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}