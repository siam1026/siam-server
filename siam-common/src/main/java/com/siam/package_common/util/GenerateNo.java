package com.siam.package_common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 编号生成工具类
 **/
public class GenerateNo {

    //商品编号
    private static String productNo;

    //订单编号
    private static String orderNo;

    private static final String format= "yyyyMMddHHmmss";

    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" ;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

    /**
     * 商品编号
     *
     * @return
     */
    public static String getProductNo(){
        productNo = simpleDateFormat.format(new Date());
        productNo = "P"+productNo+(int)((Math.random()*9+1)*1000)+getUpperLetter();
        return productNo;
    }

    /**
     * 订单编号 18位全数字
     *
     * @return
     */
    public static String getOrderNo(){
        //orderNo = simpleDateFormat.format(new Date());
        //orderNo = "D"+orderNo+(int)((Math.random()*9+1)*1000)+getUpperLetter();
        orderNo = "" + (int)((Math.random()*9+1)*1000000000);
        orderNo = orderNo+(int)((Math.random()*9+1)*10000000);
        return orderNo;
    }


    /**
     * 获取随机大写字母
     *
     * @return
     */
    private static char getUpperLetter() {
        return (chars.charAt((int) (Math.random() * 26)));
    }

    /**
     * 32位uuid
     *
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 16位uuid
     *
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return machineId + String.format("%017d", hashCodeV);
    }

    public static void main(String[] args) {
        String no = GenerateNo.getOrderNo();
        System.out.println(no + " " + no.length());
        System.out.println(GenerateNo.getOrderIdByUUId() + " " + GenerateNo.getOrderIdByUUId().length());
    }
}