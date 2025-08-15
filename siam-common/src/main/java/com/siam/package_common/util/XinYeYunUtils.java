package com.siam.package_common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 芯烨云打印机开放 API
 */
@Data
@Slf4j
@Component
public class XinYeYunUtils {

    @Value("${xinyeyun.user:siam1026@163.com}")
    private String user;
    @Value("${xinyeyun.key:siam1026@163.com}")
    private String key;
    @Value("${xinyeyun.api_url.print:siam1026@163.com}")
    private String print;
    @Value("${xinyeyun.api_url.setVoiceType:siam1026@163.com}")
    private String setVoiceType;

    public static JSONObject commonParams(JSONObject params) {
        JSONObject signParams = new JSONObject();

        signParams.put("user", "210005441@qq.com");
        signParams.put("key", "1130259797874a119035b5665b016dca");
        signParams.put("timestamp", System.currentTimeMillis());

        JSONObject commonParams = new JSONObject();
        commonParams.putAll(signParams);
        //signParams.putAll(params);

        String signKey = getNotNullSignStr(signParams, "");
        commonParams.put("sign", signKey);

//        commonParams.put("debug", "1");
        commonParams.putAll(params);
        return commonParams;
    }

    public static String getNotNullSignStr(Map<String, Object> paramMap, String secret) {
        StringBuilder builder = new StringBuilder();
        Set<String> paramsSet = paramMap.keySet();
        for (String param : paramsSet) {
            Object value = paramMap.get(param);
            builder.append(value);
        }

        String encryptStr = builder + "";
        log.info("芯烨云开放API请求参数签名前拼接字符串：" + encryptStr);
        // 步骤三，MD5进行加密，转化为大写
        String sign = Sha1Utils.sha1(encryptStr);
        return sign;
    }

    /**
     * 按照ASCII码进行由小到大排序
     *
     * @param paramList 传入的参数
     * @return
     */
    public static List<String> sortParams(List<String> paramList) {
        Collections.sort(paramList);
        return paramList;
    }

    public static JSONObject jsonDataAnalysis(JSONObject params, JSONObject result) {
        log.info("芯烨云开放API接口请求参数={},芯烨云开放API接口返回参数={}", params, result);
        JSONObject result_data = new JSONObject();
        if (result.getBoolean("success")) {
            JSONObject data = result.getJSONObject("data");
            if (data.getInteger("code") == 0) {
                result_data.put("success", data.getInteger("code") == 0 ? true : false);
                result_data.put("code", data.getInteger("code"));
                result_data.put("message", data.getString("msg"));
                result_data.put("data", data);
                return result_data;
            }
            result_data.put("success", false);
            result_data.put("code", data.getInteger("resCode"));
            result_data.put("message", data.getString("resMsg"));
            return result_data;
        }
        return result;
    }

    /**
     * 添加打印机到开发者账户（可批量） 【必接】
     *
     * @param items 数组元素为 json 对象：{"name":"打印机名称","sn":"打印机编号"}
     * @return
     */
    public JSONObject addPrinters(String items) {
        JSONObject paramsObj = new JSONObject();
        paramsObj.put("items", items);
        JSONObject params = this.commonParams(paramsObj);
        JSONObject result_data = CloseableHttpClientUtil.sendPost("https://open.xpyun.net/api/openapi/xprinter/addPrinters", params.toJSONString());
        return jsonDataAnalysis(params, result_data);
    }

    /**
     * 设置打印机语音类型
     *
     * @param voiceType   声音类型：
     *                    打印机固件版本为V10.xx的机器取值： 0 真人语音（大） 1 真
     *                    人语音（中） 2 真人语音（小） 3 嘀嘀声 4 静音
     *                    其它固件版本的机器取值：0 真人语音 3 嘀嘀声 4 静音
     * @param volumeLevel 声音大小：0 大 1 中 2 小 3 关闭
     *                    说明：打印机固件版本为非 V10.xx 的机器支持此参数
     * @return
     */
    public JSONObject setVoiceType(String voiceType, String volumeLevel) {
        JSONObject paramsObj = new JSONObject();
        paramsObj.put("voiceType", voiceType);
        paramsObj.put("volumeLevel", volumeLevel);
        JSONObject params = this.commonParams(paramsObj);
        JSONObject result_data = CloseableHttpClientUtil.sendPost("https://open.xpyun.net/api/openapi/xprinter/setVoiceType", params.toJSONString());
        return jsonDataAnalysis(params, result_data);
    }

    /**
     * 扩展语音播报
     *
     * @param content       语音播报内容，如#62，将播报序号为 62 对应的语音内容。
     *                      该内容需加入芯烨云技术支持 QQ 群（856926694）咨询
     * @param voiceTime     语音播报次数，默认为 1 次
     * @param voiceInterval 语音播报多次，当前次播报与下一次播报的时间间隔，只有
     *                      当播报次数大于 1 时才有效。
     * @return
     */
    public JSONObject playVoiceExt(String content, String voiceTime, String voiceInterval) {
        JSONObject paramsObj = new JSONObject();
        paramsObj.put("content", content);
        paramsObj.put("voiceTime", voiceTime);
        paramsObj.put("voiceInterval", voiceInterval);
        JSONObject params = this.commonParams(paramsObj);
        JSONObject result_data = CloseableHttpClientUtil.sendPost("https://open.xpyun.net/api/openapi/xprinter/playVoiceExt", params.toJSONString());
        return jsonDataAnalysis(params, result_data);
    }

    /**
     * 自定义语音播报
     *
     * @param content       语音播报内容可以自由指定内容，具体可以参考下发样例，
     *                      目前仅支持汉语播报
     * @param voiceTime     语音播报次数，默认为 1 次
     * @param voiceInterval 语音播报多次，当前次播报与下一次播报的时间间隔，只有
     *                      当播报次数大于 1 时才有效。
     * @return
     */
    public JSONObject playCustomVoice(String content, String voiceTime, String voiceInterval) {
        JSONObject paramsObj = new JSONObject();
        paramsObj.put("content", content);
        paramsObj.put("voiceTime", voiceTime);
        paramsObj.put("voiceInterval", voiceInterval);
        JSONObject params = this.commonParams(paramsObj);
        JSONObject result_data = CloseableHttpClientUtil.sendPost("https://open.xpyun.net/api/openapi/xprinter/playCustomVoice", params.toJSONString());
        return jsonDataAnalysis(params, result_data);
    }

    /**
     * 打印小票订单
     *
     * @param content 打印内容,打印内容使用 GBK 编码判断,不能超过12K
     * @param copies  打印份数，默认为 1，最大值为 5，超出范围将视为无效参数
     * @param voice   声音播放模式，0 为取消订单模式，1 为静音模式，2 为来单播放模式，3 为有用户申请退单了，默认为2 来单播放模式
     * @return
     */
    public static JSONObject print(String sn, String content, String copies, String voice, String mode) {
        JSONObject paramsObj = new JSONObject();
        paramsObj.put("content", content);
        paramsObj.put("copies", copies);
        paramsObj.put("voice", voice);
        paramsObj.put("mode", mode);
        paramsObj.put("sn", sn);
        JSONObject params = commonParams(paramsObj);

        JSONObject result_data = CloseableHttpClientUtil.sendPost("https://open.xpyun.net/api/openapi/xprinter/print", params.toJSONString());
        return jsonDataAnalysis(params, result_data);
    }

    /**
     * 后厨单打印
     *
     * @param printingOrderInfo 订单信息
     * @param printingMenuList  订单商品信息
     */
    public static JSONObject backKitchenDataPrint(JSONObject printingOrderInfo, List<JSONObject> printingMenuList) {
        //58mm的机器，一行打印 16 个汉字，32 个字母
        //80mm的机器，一行打印 24 个汉字，48 个字母
        List<JSONObject> mmList = new ArrayList<>();
        JSONObject mm = new JSONObject();
        mm.put("mm", 80);//打印机大小类型
        mm.put("mmFontSizeLen", 24);//打印机大小类型
        mm.put("mmLetterSizeLen", 48);//打印机大小类型
        mm.put("titleCheckoutSpaceNum", 14);//打印机空格长度
        mm.put("titleBackKitchenSpaceNum", 14);//打印机空格长度
        mmList.add(mm);
        int mmLetterSizeLen = mm.getInteger("mmLetterSizeLen");
        String character = "";
        for (int i = 0; i < mmLetterSizeLen; i++) {
            character = character + "-";
        }
        String tableNumber = printingOrderInfo.getString("tableNumber");
        String orderTime = printingOrderInfo.getString("orderTime");
        String contactRealname = printingOrderInfo.getString("contactRealname");
        String remark = printingOrderInfo.getString("remark");
        String backKitchentPrinterSn = printingOrderInfo.getString("backKitchentPrinterSn");
        //门店后厨单打印
        String backKitchenMenuContent = "<C>" + character + "<BR></C>" +
                //"<RH n=\"2\"><TABLE col=\"24,24\" w=2 h=2><tr>桌号：" + tableNumber + "<td>下单人：" + contactRealname + "</tr></TABLE><BR>" +
                "<RH n=\"2\"><L><B>下单人：" + contactRealname + "</B><BR>" +
                "<BOLD></BOLD><BR>" +
                "<BOLD>时间：" + orderTime + "</BOLD><BR></L>" +
                "<C>" + character + "<BR>" +
                "<B>        </B><BR></C>" +
                "<L><B>备注：" + remark + "</B><BR></L>" +
                "<BR><BR>" +
                "<C>" + character + "<BR></C></RH>" +
                "<L><TABLE col=\"22,18,8\" w=2 h=2><tr>商品名称<td>规格<td>数量</tr></TABLE>";
        String backKitchenFoodListContent = "";
        for (JSONObject food : printingMenuList) {
            String foodName = food.getString("foodName");
            String foodUnit = food.getString("foodUnit");
            String goodsPrinterNames = food.getString("goodsPrinterNames");
            int foodNum = food.getInteger("foodNum");
            String foodSpecs = food.getString("foodSpecs");
            String goodsPrinterSn = food.getString("goodsPrinterSn");
            int printNum = food.getInteger("printNum");

            String titleContent = "<C><B>" + goodsPrinterNames + "</B><BR></C>";
            String foodContent = "<TABLE col=\"22,18,8\" w=2 h=2><tr>" + foodName + "<td>" + foodSpecs + "<td>" + foodNum + "</tr></TABLE><BR>";
            String content = titleContent + backKitchenMenuContent + foodContent + "<BR></L>";

            List<String> backKitchentPrinterSnList = Arrays.asList(goodsPrinterSn.split(","));
            for (String backKitchentPrinterSnStr : backKitchentPrinterSnList) {
                for (int i = 0; i < printNum; i++) {
                    print(backKitchentPrinterSnStr, content, "1", "2", "1");
                }
            }
            backKitchenFoodListContent = backKitchenFoodListContent + foodContent;
        }
        String titleContent = "<C><B>总单</B><BR></C>";
        //String bottomContent = "<BR></L>" + character + "<TABLE col=\"24,24\" w=2 h=2><tr>" + tableNumber + "<td>" + tableNumber + "</tr></TABLE>";
        String bottomContent = "<BR></L>" + character;
        backKitchenMenuContent = titleContent + backKitchenMenuContent + backKitchenFoodListContent + bottomContent;
        //门店总氮打印
        return print(backKitchentPrinterSn, backKitchenMenuContent, "1", "2", "1");
    }

    /**
     * 结账单打印
     *
     * @param printingOrderInfo 订单信息
     * @param printingMenuList  订单商品信息
     */
    public static JSONObject checkoutDataPrint(JSONObject printingOrderInfo, List<JSONObject> printingMenuList) {
        //58mm的机器，一行打印 16 个汉字，32 个字母
        //80mm的机器，一行打印 24 个汉字，48 个字母
        List<JSONObject> mmList = new ArrayList<>();
        JSONObject mm = new JSONObject();
        mm.put("mm", 80);//打印机大小类型
        mm.put("mmFontSizeLen", 24);//打印机大小类型
        mm.put("mmLetterSizeLen", 48);//打印机大小类型
        mm.put("titleCheckoutSpaceNum", 14);//打印机空格长度
        mm.put("titleBackKitchenSpaceNum", 14);//打印机空格长度
        mmList.add(mm);
        int mmLetterSizeLen = mm.getInteger("mmLetterSizeLen");
        String character = "";
        for (int i = 0; i < mmLetterSizeLen; i++) {
            character = character + "-";
        }
        String checkoutFoodListContent = "";
        String tableNumber = printingOrderInfo.getString("tableNumber");
        String orderNo = printingOrderInfo.getString("orderNo");
        String orderTime = printingOrderInfo.getString("orderTime");
        String goodsTotalQuantity = printingOrderInfo.getString("goodsTotalQuantity");
        String totalPrice = printingOrderInfo.getString("totalPrice");
        String actualPrice = printingOrderInfo.getString("actualPrice");
        String packingCharges = printingOrderInfo.getString("packingCharges");
        String deliveryFee = printingOrderInfo.getString("deliveryFee");
        String queueNo = printingOrderInfo.getString("queueNo");
        boolean ifFullReduction = printingOrderInfo.getBoolean("ifFullReduction");
        boolean ifCoupon = printingOrderInfo.getBoolean("ifCoupon");
        String ifFullReductionName = printingOrderInfo.getString("ifFullReductionName");
        String ifCouponName = printingOrderInfo.getString("ifCouponName");
        String paymentTime = printingOrderInfo.getString("paymentTime");
        String payTypeName = printingOrderInfo.getString("payTypeName");
        String shopName = printingOrderInfo.getString("shopName");
        String shopAddress = printingOrderInfo.getString("shopAddress");
        String shopContactNumber = printingOrderInfo.getString("shopContactNumber");
        String printTime = DateUtilsPlus.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        String remark = printingOrderInfo.getString("remark");
        String checkoutPrinterSn = printingOrderInfo.getString("checkoutPrinterSn");
        //用户结账单打印
        String checkoutMenuContent = "<C><B>" + shopName + "</B><BR></C>" +
                "<C>" + character + "<BR></C>" +
                "<RH n=\"2\"><C><B>结账单<BR>" +
                "#" + queueNo + "</B><BR></C>" +
                "<L><BOLD>订单号：" + orderNo + "<BR>" +
                "商品总数：" + goodsTotalQuantity + "<BR>" +
                "下单时间：" + orderTime + "<BR>" +
                "结账时间：" + paymentTime + "</BOLD><BR></L></RH>" +
                character + "<BR>" +
                "<TABLE col=\"24,14,10\" w=1 h=1><tr>商品名称<td>数量<td>小计</tr></TABLE>" +
                character + "<BR>";
        for (JSONObject food : printingMenuList) {
            String foodName = food.getString("foodName");
            String foodUnit = food.getString("foodUnit");
            String foodPrice = food.getString("foodPrice");
            int foodNum = food.getInteger("foodNum");
            String foodSpecs = food.getString("foodSpecs");
            String foodContent = "<TABLE col=\"24,14,10\" w=2 h=2><tr>" + foodName + "<td>" + "x" + foodNum + "<td>" + foodPrice + "</tr></TABLE>" +
                    "<L><TABLE col=\"46,2\"><tr>" + foodSpecs + "<td> </tr></TABLE></L> <BR>";
            checkoutFoodListContent = checkoutFoodListContent + foodContent;
        }
        String characterContent = "结算清单";
        String characterText = character.substring(0, (mmLetterSizeLen / 2) - characterContent.length());
        String discountText = "";
        if (ifFullReduction) {
            discountText = discountText + "<TABLE col=\"24,24\" w=1 h=1><tr>满减优惠<td>" + ifFullReductionName + "</tr></TABLE>";
        }
        if (ifCoupon) {
            discountText = discountText + "<TABLE col=\"24,24\" w=1 h=1><tr>使用优惠券<td>" + ifCouponName + "</tr></TABLE>";
        }
        if (!"".equals(payTypeName)) {
            payTypeName = "<L><BOLD>支付方式：" + payTypeName + "</BOLD><BR></L>";
        }
        checkoutMenuContent = checkoutMenuContent + checkoutFoodListContent + "<BR></L>" +
                "<TABLE col=\"24,24\" w=1 h=1><tr>配送费<td>" + deliveryFee + "</tr></TABLE>" +
                "<TABLE col=\"24,24\" w=1 h=1><tr>包装费<td>" + packingCharges + "</tr></TABLE>" +
                discountText +
                characterText + characterContent + characterText + "<BR>" +
                "<TABLE col=\"24,24\" w=2 h=2><tr>实收金额<td>￥" + actualPrice + "</tr></TABLE><RH n=\"2\"><BR>" +
                payTypeName +
                character + "<BR>" +
                "<L><BOLD>门店地址：" + shopAddress + "<BR>" +
                "门店电话：" + shopContactNumber + "</BOLD><BR></L>" +
                "<C><BOLD>谢谢惠顾，欢迎下次光临<BR>" +
                printTime + "</BOLD><BR></C></RH>";
        return print(checkoutPrinterSn, checkoutMenuContent, "1", "2", "1");
    }

    @Test
    public void playCustomVoice_test() {
        playCustomVoice("来订单了", "1", "1");
    }

    @Test
    public void setVoiceType_test() {
        setVoiceType("0", "0");
    }

    @Test
    public void addPrinters_test() {
        List<JSONObject> items = new ArrayList<>();
        JSONObject item = new JSONObject();
        item.put("sn", "14JQK87GKXD5649");
        item.put("name", "kkrgjouyoo");
        items.add(item);
        addPrinters(JSONObject.toJSONString(items));
    }

    @Test
    public void print_test() {
        //58mm的机器，一行打印 16 个汉字，32 个字母
        //80mm的机器，一行打印 24 个汉字，48 个字母
        List<JSONObject> mmList = new ArrayList<>();
        JSONObject mm = new JSONObject();
        mm.put("mm", 80);//打印机大小类型
        mm.put("mmFontSizeLen", 24);//打印机大小类型
        mm.put("mmLetterSizeLen", 48);//打印机大小类型
        mm.put("titleCheckoutSpaceNum", 14);//打印机空格长度
        mm.put("titleBackKitchenSpaceNum", 14);//打印机空格长度
        mmList.add(mm);
        List<JSONObject> menuList = new ArrayList<>();
        JSONObject menu = new JSONObject();
        menu.put("foodName", "金钱班");
        menu.put("foodNum", 1);
        menu.put("foodUnit", "条");
        menu.put("foodWeight", "22两");
        menu.put("foodSpecs", "头/白灼");
        menu.put("foodPrice", "33.3");
        menu.put("title", "海鲜");
        menu.put("tableNumber", "13");
        menuList.add(menu);
        JSONObject menu1 = new JSONObject();
        menu1.put("foodName", "剁椒鱼头");
        menu1.put("foodNum", 1);
        menu1.put("foodUnit", "个");
        menu1.put("foodSpecs", "大/加配菜");
        menu1.put("foodPrice", "34.3");
        menu1.put("title", "燕翅鲍");
        menu1.put("tableNumber", "13");
        menuList.add(menu1);
        JSONObject menu2 = new JSONObject();
        menu2.put("foodName", "酸辣土豆丝炒牛肉");
        menu2.put("foodNum", 1);
        menu2.put("foodUnit", "份");
        menu2.put("foodSpecs", "不酸/不加牛肉");
        menu2.put("foodPrice", "44.3");
        menu2.put("title", "爆炒");
        menu2.put("tableNumber", "13");
        menuList.add(menu2);

        int titleBackKitchenSpaceNum = mm.getInteger("titleBackKitchenSpaceNum");
        int titleCheckoutSpaceNum = mm.getInteger("titleCheckoutSpaceNum");
        int mmLetterSizeLen = mm.getInteger("mmLetterSizeLen");
        String character = "";
        for (int i = 0; i < mmLetterSizeLen; i++) {
            character = character + "-";
        }
        //门店后厨单打印
        String backKitchenMenuContent = "<C>" + character + "<BR></C>" +
                "<RH n=\"2\"><L><B>桌号：32<HT>下单人：***</B><BR>" +
                "<BOLD>时间：2024-06-24 15:24:00</BOLD><BR></L>" +
                "<C><BOLD>" + character + "<BR></C>" +
                "<C><B>     测    试   </B><BR></C>" +
                "<L><B>备注：免辣~</B><BR></L>" +
                "<BR><BR>" +
                "<C>" + character + "<BR></C></RH>" +
                "<L><POS>1B3302</POS><TABLE col=\"22,18,8\" w=2 h=2><tr>商品名称<td>规格<td>数量</tr>";
        String backKitchenFoodListContent = "", checkoutFoodListContent = "";
        for (JSONObject food : menuList) {
            String foodName = food.getString("foodName");
            String foodUnit = food.getString("foodUnit");
            String title = food.getString("title");
            int foodNum = food.getInteger("foodNum");
            String foodSpecs = food.getString("foodSpecs");
            String titleContent = "<C><B>" + title + "</B><BR></C>";
            String foodContent = "<tr>" + foodName + "<td>" + foodSpecs + "<td>" + foodNum + "</tr>";
            print("14JQK87GKXD5649", titleContent + backKitchenMenuContent + foodContent + "</TABLE><BR></L>", "1", "2", "1");
            backKitchenFoodListContent = backKitchenFoodListContent + foodContent;
        }
        String titleContent = "<C><B>总单</B><BR></C>";
        String bottomContent = "</TABLE><POS>1B32</POS><BR></L>" + character + "<TABLE col=\"24,24\" w=2 h=2><tr>32<td>32</tr></TABLE>";
        backKitchenMenuContent = titleContent + backKitchenMenuContent + backKitchenFoodListContent + bottomContent;
        //门店总氮打印
        print("14JQK87GKXD5649", backKitchenMenuContent, "1", "2", "1");

        //用户结账单打印
        String checkoutMenuContent = "<C><B>店铺名称</B><BR></C>" +
                "<C>" + character + "<BR></C>" +
                "<RH n=\"2\"><C><BOLD>结账单</BOLD><BR></C>" +
                "<C><BOLD>#12</BOLD><BR></C>" +
                "<L><BOLD>订单号：20240602174200</BOLD><BR>" +
                "<BOLD>商品总数：1</BOLD><BR>" +
                "<BOLD>下单时间：2024-06-02 17:37</BOLD><BR>" +
                "<BOLD>结账时间：2024-06-02 17:45</BOLD><BR></RH>" +
                character + "<BR></L>" +
                "<L><TABLE col=\"22,18,8\" w=2 h=2><tr>商品名称<td>数量<td>小计</tr>" +
                character + "<BR>";
        for (JSONObject food : menuList) {
            String foodName = food.getString("foodName");
            String foodUnit = food.getString("foodUnit");
            String foodPrice = food.getString("foodPrice");
            int foodNum = food.getInteger("foodNum");
            String foodSpecs = food.getString("foodSpecs");
            String foodContent = "<tr>" + foodName + "<td>" + foodNum + "<td>" + foodPrice + "</tr>" +
                    "<BOLD>" + foodSpecs + "</BOLD><BR>";
            checkoutFoodListContent = checkoutFoodListContent + foodContent;
        }
        String characterText = character.substring(0, (mmLetterSizeLen / 2) - 4);
        checkoutMenuContent = checkoutMenuContent + checkoutFoodListContent + "</TABLE><BR></L>" +
                characterText + "结算清单" + characterText + "<BR>" +
                "<RH n=\"2\"><L><BOLD>实收金额：￥1</BOLD><BR></L>" +
                "<L><BOLD>支付方式：现金支付</BOLD><BR></L>" +
                character + "<BR>" +
                "<L><BOLD>门店地址：************************************</BOLD><BR></C>" +
                "<L><BOLD>门店电话：1557*******</BOLD><BR></C></RH>" +
                "<C><BOLD>谢谢回顾，欢迎下次光临</BOLD><BR></C>";
        print("14JQK87GKXD5649", checkoutMenuContent, "1", "2", "1");
    }
}