package com.siam.package_common.util;

import com.google.gson.internal.LinkedTreeMap;
import com.siam.package_common.constant.Quantity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度地图API
 */
@Component
public class BaiduMapUtils {
    @Value(value = "${baidu.map.ak:siam1026@163.com}")
    private String ak;

    private static final Double PI = Math.PI;

    private static final Double PK = 180 / PI;

    /**
     * 行车距离-获取两个地址之间的距离(单位:米)
     * 返回参数示例：
     * {
     *     "distance": {
     *         "text": "19公里",
     *         "value": 18994
     *     },
     *     "duration": {
     *         "text": "1.6小时",
     *         "value": 5752
     *     }
     * }
     * distance 路线距离；text 线路距离的文本描述；value 线路距离的数值(单位:米)
     * duration 路线耗时；text 路线耗时的文本描述；value 路线耗时的数值(单位:秒)
     * @param travelingPlan 出行方案（驾车、骑行、步行）
     * @return
     */
    public LinkedTreeMap<String, Object> getTravelingDistanceFromCoordinate(BigDecimal lngA, BigDecimal latA, BigDecimal lngB, BigDecimal latB, String travelingPlan){
        if(lngA==null || latA==null || lngB==null || latB==null){
            throw new RuntimeException("经纬度不能为空");
        }
        if(StringUtils.isEmpty(travelingPlan)){
            throw new RuntimeException("出行方案不能为空");
        }
        return getTravelingDistanceFromPoints(lngA, latA, lngB, latB, travelingPlan);
    }

    /**
     * 行车距离-获取两个地址之间的距离(单位:米)
     * 返回参数示例：
     * {
     *     "distance": {
     *         "text": "19公里",
     *         "value": 18994
     *     },
     *     "duration": {
     *         "text": "1.6小时",
     *         "value": 5752
     *     }
     * }
     * distance 路线距离；text 线路距离的文本描述；value 线路距离的数值(单位:米)
     * duration 路线耗时；text 路线耗时的文本描述；value 路线耗时的数值(单位:秒)
     * @param addressA
     * @param addressB
     * @param travelingPlan 出行方案（驾车、骑行、步行）
     * @return
     */
    public LinkedTreeMap<String, Object> getTravelingDistanceFromAddress(String addressA, String addressB, String travelingPlan){
        if(StringUtils.isEmpty(addressA) || StringUtils.isEmpty(addressB)){
            throw new RuntimeException("地址不能为空");
        }
        if(StringUtils.isEmpty(travelingPlan)){
            throw new RuntimeException("出行方案不能为空");
        }
        //获取两个位置的经纬度
        Map pointA = this.getLongitudeAndLatitude(addressA);
        Map pointB = this.getLongitudeAndLatitude(addressB);
        return getTravelingDistanceFromPoints(BigDecimal.valueOf((double) pointA.get("lng")), BigDecimal.valueOf((double) pointA.get("lat")), BigDecimal.valueOf((double) pointB.get("lng")), BigDecimal.valueOf((double) pointB.get("lat")), travelingPlan);
    }

    /**
     * 直线距离-获取两个地址之间的距离(单位:米)
     * @param addressA
     * @param addressB
     * @return
     */
    public double getDistanceFromAddress(String addressA, String addressB){
        if(StringUtils.isEmpty(addressA) || StringUtils.isEmpty(addressB)){
            throw new RuntimeException("地址不能为空");
        }
        //获取两个位置的经纬度
        Map pointA = this.getLongitudeAndLatitude(addressA);
        Map pointB = this.getLongitudeAndLatitude(addressB);
        return getDistanceFromPoints(pointA, pointB);
    }

    /**
     * 直线距离-获取两个经纬度之间的距离(单位:米)
     * @param pointA
     * @param pointB
     */
    public double getDistanceFromPoints(Map pointA, Map pointB){
        if(pointA==null || pointA.isEmpty() || pointB==null || pointB.isEmpty()){
            throw new RuntimeException("经纬度不能为空");
        }

        double lngA = (double) pointA.get("lng");
        double latA = (double) pointA.get("lat");
        double lngB = (double) pointB.get("lng");
        double latB = (double) pointB.get("lat");

        double t1 = Math.cos(latA / PK) * Math.cos(lngA / PK) * Math.cos(latB / PK) * Math.cos(lngB / PK);
        double t2 = Math.cos(latA / PK) * Math.sin(lngA / PK) * Math.cos(latB / PK) * Math.sin(lngB / PK);
        double t3 = Math.sin(latA / PK) * Math.sin(latB / PK);

        double tt = Math.acos(t1 + t2 + t3);
        return 6366000 * tt;
    }

    /**
     * 根据位置获取经纬度(地理编码服务)
     * @param address
     * @return
     */
    public Map<String, Object> getLongitudeAndLatitude(String address){
        //url地址不允许出现空格，所以地址要去除空格
        address = address.replaceAll(" ", "");

        Map<String, Object> map = null;
        String apiUrl = "http://api.map.baidu.com/geocoding/v3/?output=json&ak=" + ak + "&address=" + address;
        try {
            URL url = new URL(apiUrl);
            URLConnection urlConnection = url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), "utf-8");

            String data = null;
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            if ((data = bufferedReader.readLine()) != null){
                map = GsonUtils.toMap(data);
            }

            bufferedReader.close();
            inputStreamReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //status等于0，代表请求成功
        //在转成json的过程中，该字段的数据类型由int变成了double
        if((double) map.get("status") != Quantity.INT_0){
            throw new RuntimeException("请求失败，原因：" + map.get("message"));
        }

        LinkedTreeMap location = ((LinkedTreeMap) ((LinkedTreeMap) map.get("result")).get("location"));
        return location;
    }

    /**
     * 行车距离-获取两个经纬度之间的距离(单位:米)--批量算路服务
     * 返回参数示例：
     * {
     *     "distance": {
     *         "text": "19公里",
     *         "value": 18994
     *     },
     *     "duration": {
     *         "text": "1.6小时",
     *         "value": 5752
     *     }
     * }
     * distance 路线距离；text 线路距离的文本描述；value 线路距离的数值(单位:米)
     * duration 路线耗时；text 路线耗时的文本描述；value 路线耗时的数值(单位:秒)
     * @param travelingPlan 出行方案（驾车、骑行、步行）
     */
    public LinkedTreeMap<String, Object> getTravelingDistanceFromPoints(BigDecimal lngA, BigDecimal latA, BigDecimal lngB, BigDecimal latB, String travelingPlan){
        if(lngA==null || latA==null || lngB==null || latB==null){
            throw new RuntimeException("经纬度不能为空");
        }
        if(StringUtils.isEmpty(travelingPlan)){
            throw new RuntimeException("出行方案不能为空");
        }

        Map<String, Object> map = null;

        //坐标格式为：纬度,经度|纬度,经度 (这是多个地点传参，如果只有一个地点，就不要竖杠)
        //传参一定得是维度在前，经度在后，否则会抛出参数错误
        String origins = latA + "," + lngA;
        String destinations = latB + "," + lngB;
        String apiUrl = "http://api.map.baidu.com/routematrix/v2/" + travelingPlan + "?output=json&origins=" + origins + "&destinations=" + destinations + "&ak=" + ak;
        try {
            URL url = new URL(apiUrl);
            URLConnection urlConnection = url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream(), "utf-8");

            String data = null;
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            if ((data = bufferedReader.readLine()) != null){
                map = GsonUtils.toMap(data);
            }

            bufferedReader.close();
            inputStreamReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //status等于0，代表请求成功
        //在转成json的过程中，该字段的数据类型由int变成了double
        if((double) map.get("status") != Quantity.INT_0){
            throw new RuntimeException("请求失败，原因：" + map.get("message"));
        }

        //API返回的result是一个数组，数组里面只有一个元素
        LinkedTreeMap<String, Object> travelingMap = ((LinkedTreeMap) ((ArrayList) map.get("result")).get(0));
        return travelingMap;
    }

    /**
     * 高德坐标转百度
     *
     * @return
     */
    public Map<String, BigDecimal> gaoDeToBaidu(double gd_lng, double gd_lat){
        double X_PI = Math.PI * 3000.0 / 180.0;
        double x = gd_lng, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;

        //处理返回结构
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("lng", BigDecimal.valueOf(bd_lng).setScale(6, BigDecimal.ROUND_HALF_UP));
        map.put("lat", BigDecimal.valueOf(bd_lat).setScale(6, BigDecimal.ROUND_HALF_UP));
        return map;
    }

    /**
     * 百度坐标转高德
     *
     * @return
     */
    public Map<String, BigDecimal> BaiduToGaoDe(double bd_lng, double bd_lat){
        double X_PI = Math.PI * 3000.0 / 180.0;
        double x = bd_lng - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gd_lng = z * Math.cos(theta);
        double gd_lat = z * Math.sin(theta);

        //处理返回结构
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("lng", BigDecimal.valueOf(gd_lng).setScale(6, BigDecimal.ROUND_HALF_UP));
        map.put("lat", BigDecimal.valueOf(gd_lat).setScale(6, BigDecimal.ROUND_HALF_UP));
        return map;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }
}