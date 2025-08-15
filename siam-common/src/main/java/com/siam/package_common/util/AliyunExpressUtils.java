package com.siam.package_common.util;

import cn.hutool.core.convert.Convert;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全球快递物流查询 - 四川涪擎大数据技术有限公司
 */
@Data
@Slf4j
@Component
public class AliyunExpressUtils {

    @Value(value = "${aliyun.express.appCode:siam1026@163.com}")
    private String appCode;

    /**
     * 查询物流信息
     * @param no 快递单号
     */
    public AliyunExpress query(String no) {
        String host = "https://wdexpress.market.alicloudapi.com"; // 【1】请求地址 支持http 和 https 及 WEBSOCKET
        String path = "/gxali"; // 【2】后缀
//        String appcode = ""; // 【3】开通服务后 买家中心-查看AppCode
        String n = no; // 【4】请求参数，详见文档描述
        String t = "";//  【4】请求参数，不知道可不填 95%能自动识别
        String urlSend = host + path + "?n=" + n + "&t="+ t;  // 【5】拼接请求链接
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appCode);// 格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                System.out.println("正常请求计费(其他均不计费)");
                System.out.println("获取返回的json:");
                System.out.print(json);
                return this.parseJson(json);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("套餐包次数用完 ");
                } else if (httpCode == 403 && error.equals("Api Market Subscription quota exhausted")) {
                    System.out.println("套餐包次数用完，请续购套餐");
                } else {
                    System.out.println("参数名错误 或 其他错误");
                    System.out.println(error);
                }
            }

        } catch (MalformedURLException e) {
            System.out.println("URL格式错误");
        } catch (UnknownHostException e) {
            System.out.println("URL地址错误");
        } catch (Exception e) {
            // 打开注释查看详细报错异常信息
            // e.printStackTrace();
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
        int state = Integer.valueOf((String) map.get("State"));
        switch (state){
            case -1:
                log.info("单号或快递公司代码错误,state={0}", state);
                break;
            case 0:
                log.info("暂无轨迹,state={0}", state);
                break;
            default:
                //正常查询
                AliyunExpress aliyunExpress = new AliyunExpress();
                aliyunExpress.setNumber(Convert.toStr(map.get("LogisticCode")));
                aliyunExpress.setType(Convert.toStr(map.get("ShipperCode")));
                aliyunExpress.setList(tracesToList(Convert.toList(Map.class, map.get("Traces"))));
                aliyunExpress.setDeliverystatus(stateToDeliverystatus(Convert.toInt(map.get("State"))));
                aliyunExpress.setIssign(aliyunExpress.getDeliverystatus() == 3 ? 1 : 0);
                aliyunExpress.setExpName(Convert.toStr(map.get("Name")));
                aliyunExpress.setExpSite(Convert.toStr(map.get("Site")));
                aliyunExpress.setExpPhone(Convert.toStr(map.get("Phone")));
                aliyunExpress.setCourier(Convert.toStr(map.get("Courier")));
                aliyunExpress.setCourierPhone(Convert.toStr(map.get("CourierPhone")));
                aliyunExpress.setUpdateTime(Convert.toDate(map.get("updateTime")));
                aliyunExpress.setTakeTime(Convert.toStr(map.get("takeTime")));
                aliyunExpress.setLogo(Convert.toStr(map.get("Logo")));
                return aliyunExpress;
        }
        return null;
    }

    private Integer stateToDeliverystatus(Integer state){
        /*state  -1：单号或快递公司代码错误, 0：暂无轨迹，1:快递收件，2：在途中,3：签收,4：问题件 5.疑难件 6.退件签收*/
        //deliverystatus  快递状态 0=快递收件(揽件) 1=在途中 2=正在派件 3=已签收 4=派送失败 5=疑难件 6=退件签收
        switch (state){
            case 1:
                return 0;
            case 2:
                return 2;
            case 3:

                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return null;
        }
    }

    private List<Map<String, Object>> tracesToList(List<Map> traces){
        List<Map<String, Object>> list = new ArrayList<>();
        traces.forEach(map -> {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("status", map.get("AcceptStation"));
            resultMap.put("time", map.get("AcceptTime"));
            list.add(resultMap);
        });
        return list;
    }

    public static void main(String[] args) {
        AliyunExpress yt7502172804744 = new AliyunExpressUtils().query("0000000000");
    }
}