package com.siam.system.modular.package_goods;

import com.google.gson.internal.LinkedTreeMap;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.util.BaiduMapUtils;
import com.siam.system.modular.package_user.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableConfigurationProperties
public class BaiduMapTest {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BaiduMapUtils baiduMapUtils;

    @Test
    public void generatePassword(){
        //getLatAndLngByAddress("湖南省株洲市攸县");
        //Map<String, Object> map = baiduMapUtils.getLongitudeAndLatitude("湖南省株洲市攸县");
        //System.out.println(((HashMap)((HashMap) map.get("result")).get("location")).get("lng"));
        //System.out.println(((LinkedTreeMap)((LinkedTreeMap) map.get("result")).get("location")).get("lng"));

        double distance = baiduMapUtils.getDistanceFromAddress("萧山博地中心", "浙江省杭州市下城区文晖路139号打铁关");
        System.out.println("直线距离：" + distance + "米");

        LinkedTreeMap travelingDistance1 = baiduMapUtils.getTravelingDistanceFromAddress("浙江省杭州市萧山区博地中心(盈丰路地铁站南)", "浙江省杭州市下城区文晖路139号打铁关", BusinessType.BAIDU_TRAVELING_PLAN_DRIVING);
        LinkedTreeMap travelingDistance2 = baiduMapUtils.getTravelingDistanceFromAddress("浙江省杭州市萧山区博地中心(盈丰路地铁站南)", "浙江省杭州市下城区文晖路139号打铁关", BusinessType.BAIDU_TRAVELING_PLAN_RIDING);
        LinkedTreeMap travelingDistance3 = baiduMapUtils.getTravelingDistanceFromAddress("浙江省杭州市萧山区博地中心(盈丰路地铁站南)", "浙江省杭州市下城区文晖路139号打铁关", BusinessType.BAIDU_TRAVELING_PLAN_WALKING);
        System.out.println("行车距离-驾车：" + travelingDistance1);
        System.out.println("行车距离-骑行：" + travelingDistance2);
        System.out.println("行车距离-步行：" + travelingDistance3);
    }

    @Test
    public void gaoDeToBaidu(){
        double gg_lng = 120.034693;
        double gg_lat = 30.288803;
        double X_PI = Math.PI * 3000.0 / 180.0;
        double x = gg_lng, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bd_lng = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        System.out.println(BigDecimal.valueOf(bd_lng).setScale(6, BigDecimal.ROUND_HALF_UP) + " -- " + BigDecimal.valueOf(bd_lat).setScale(6, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 根据地址获得经纬度
     */
    /*public static LatitudeAndLongitude getLngAndLat(String address) {
        LatitudeAndLongitude latAndLng = new LatitudeAndLongitude();
        String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=自己注册的ak值";
        String json = loadJSON(url);
        if (StringUtils.isEmpty(json)) {
            return latAndLng;
        }
        int len = json.length();
        // 如果不是合法的json格式
        if (json.indexOf("{") != 0 || json.lastIndexOf("}") != len - 1) {
            return latAndLng;
        }
        JSONObject obj = JSONObject.fromObject(json);
        if (obj.get("status").toString().equals("0")) {
            double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            latAndLng.setLatitude(lat);
            latAndLng.setLongitude(lng);
        }
        return latAndLng;
    }*/

    private static final Double PI = Math.PI;

    private static final Double PK = 180 / PI;

    private static final String MAP_URL= "http://api.map.baidu.com/geocoding/v3/?address=湖南省株洲市攸县&output=json&ak=GWT3y80ejryNBd0ed64opKGQe16UGOOx&callback=showLocation";

    /**
     * 根据地址获取经纬度
     * @param address
     * @return
     */
    /*private Map<String,Double> getLatAndLngByAddress(String address){
        Map<String,Double> poiMap = null;
        String result = null;
        try {
            result = Request.Get(MAP_URL+ address)
                    .connectTimeout(1000).socketTimeout(1000)
                    .execute().returnContent().asString();
        } catch (IOException e) {
            log.error("调用百度地图API获取{}的经纬度，抛错{}",address,e);
        }
        if (StringUtils.isNotBlank(result) && "0".equals(JSON.parseObject(result).get("status") + "")){
            String lat = result.substring(result.indexOf("\"lat\":")
                    + ("\"lat\":").length(), result.indexOf("},\"precise\""));
            String lng = result.substring(result.indexOf("\"lng\":")
                    + ("\"lng\":").length(), result.indexOf(",\"lat\""));
            poiMap = ImmutableMap.of("lat",Double.parseDouble(lat),"lng",Double.parseDouble(lng));
        }
        return poiMap;
    }*/

    public Map<String, BigDecimal> getLatAndLngByAddress(String addr){
        String address = "";
        String lat = "";
        String lng = "";
        try {
            address = java.net.URLEncoder.encode(addr,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        /*String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
                +"ak=GWT3y80ejryNBd0ed64opKGQe16UGOOx&output=json&address=%s",address);*/

        String url = MAP_URL;

        URL myURL = null;
        URLConnection httpsConn = null;
        //进行转码
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {

        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(
                        httpsConn.getInputStream(), "UTF-8");

                BufferedReader br = new BufferedReader(insr);

                String data = null;
                if ((data = br.readLine()) != null) {
                    lat = data.substring(data.indexOf("\"lat\":")
                            + ("\"lat\":").length(), data.indexOf("},\"precise\""));
                    lng = data.substring(data.indexOf("\"lng\":")
                            + ("\"lng\":").length(), data.indexOf(",\"lat\""));
                }
                insr.close();
            }
        } catch (IOException e) {

        }
        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
        map.put("lat", new BigDecimal(lat));
        map.put("lng", new BigDecimal(lng));
        return map;
    }

    /**
     * 计算两个地址的距离（米）
     * @param address
     * @param otherAddress
     * @return
     */
    public Double getDistanceFromTwoPlaces(String address,String otherAddress){
        Double distance = null;
        if (StringUtils.isNotBlank(address) && StringUtils.isNotBlank(otherAddress)){
            address = address.trim();
            otherAddress = otherAddress.trim();
            if (address.equals(otherAddress)){
                return 0.0d;
            }
            Map pointA = getLatAndLngByAddress(address);
            Map pointB = getLatAndLngByAddress(otherAddress);
            distance = getDistanceFromTwoPoints(pointA,pointB);
        }
        return distance;
    }

    /**
     * 获取两个经纬度之间的距离（单位：米）
     * @param pointA
     * @param pointB
     * @return
     */
    private Double getDistanceFromTwoPoints(Map pointA, Map pointB) {
        Double distance = null;
        if (pointA != null && !pointA.isEmpty() && pointB != null && !pointB.isEmpty()){
            double lat_a = (double) pointA.get("lat");
            double lng_a = (double) pointA.get("lng");
            double lat_b = (double) pointB.get("lat");
            double lng_b = (double) pointB.get("lng");

            if (lat_a == lat_b && lng_a == lng_b){
                return 0.0d;
            }

            double t1 = Math.cos(lat_a / PK) * Math.cos(lng_a / PK) * Math.cos(lat_b / PK) * Math.cos(lng_b / PK);
            double t2 = Math.cos(lat_a / PK) * Math.sin(lng_a / PK) * Math.cos(lat_b / PK) * Math.sin(lng_b / PK);
            double t3 = Math.sin(lat_a / PK) * Math.sin(lat_b / PK);

            double tt = Math.acos(t1 + t2 + t3);
            distance = 6366000 * tt;
        }
        return distance;
    }
}