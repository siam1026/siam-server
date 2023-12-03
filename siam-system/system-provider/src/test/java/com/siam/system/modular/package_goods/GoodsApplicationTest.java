package com.siam.system.modular.package_goods;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.internal.LinkedTreeMap;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.*;
import com.siam.system.modular.package_goods.service.*;
import com.siam.package_weixin_basic.service.WxNotifyService;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.system.modular.package_order.service.OrderDetailService;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_user.service.AdminService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MerchantService;
import com.siam.system.SiamApplication;
import com.siam.system.modular.package_goods.entity.Menu;
import com.siam.system.modular.package_goods.entity.SysMessage;
import com.siam.system.modular.package_order.entity.OrderDetail;
import com.siam.package_weixin_basic.util.WxQrCodeUtils;
import com.siam.package_weixin_pay.util.*;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.model.example.MemberExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiamApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class GoodsApplicationTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private GoodsRawmaterialRelationService rawmaterialRelationService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private WxNotifyService wxNotifyService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WxdecodeUtils wxdecodeUtils;

    @Autowired
    private AliyunExpressUtils aliyunExpressUtils;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private WxQrCodeUtils wxQrCodeUtils;

    @Autowired
    private ImageComposeUtils imageComposeUtils;

    @Autowired
    private MenuService menuService;

    @Test
    public void generatePassword(){
        // 对密码进行盐值加密
//        String passwordSalt = CommonUtils.genSalt();
        String passwordSalt = "honyar";
        String password = "123456";
        String newPassword = CommonUtils.genMd5Password(password, passwordSalt);

        System.out.println("password: " + password);
        System.out.println("passwordSalt: " + passwordSalt);
        System.out.println("newPassword: " + newPassword);

        System.out.println(new Date("1970/1/1 00:00:00"));
    }

    @Test
    public void testCouponsMemberRelationList(){
        /*Page page = couponsMemberRelationService.getListByPage(new Page(pageNo, pageSize), 1, 50, new CouponsMemberRelation());
        System.out.println(page.getPages()+ " " +page.getPageNum()+ " " +page.getPageSize());
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());*/

        int counts = couponsMemberRelationService.getCountsByMemberId(23);
        System.out.println("---------" + counts);
    }


    private static String genMobileCode(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        int number = 0;
        for(int i = 0; i < 6; i++) {
            do {
                number = r.nextInt(10);
            }while (i==0 && number==0);
            code.append(number);
        }
        return code.toString();
    }

    @Test
    public void testMobileCode(){
        int i = 0;
        while(i < 100){
            System.out.println("------- "+genMobileCode());
            i++;
        }
    }

    @Test
    public void testConsume(){
        List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(151);
        rawmaterialRelationService.updateRawmaterialConsumedQuantityByOrderDetailList(orderDetailList);
    }

    @Test
    public void testMonthlySalesReset(){
        goodsService.monthlySalesReset();
    }

    @Test
    public void testStream(){
        List<Member> memberList = new ArrayList<>();
        Member member_001 = new Member();
        member_001.setId(1);
        member_001.setNickname("a");
        Member member_002 = new Member();
        member_002.setId(2);
        member_002.setNickname("b");
        Member member_003 = new Member();
        member_003.setId(3);
        member_003.setNickname("a");
        memberList.add(member_001);
        memberList.add(member_002);
        memberList.add(member_003);

        memberList = memberList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Member:: getNickname))), ArrayList::new));

        System.out.println(GsonUtils.toJson(memberList));
    }

    @Test
    public void testDeliveryFee(){
        //TODO-按照商品件数 / 商品总金额来算都有道理
        //先算出某段距离内 配送费多少钱
        //1、需要考虑商品总金额多少内，无附加费用
        System.out.println("\n\n计算规则：");
        System.out.println("1、起送价1.5元(0~1.5KM)，每增加1KM加1元\n");
        /*System.out.println("距离(KM)\t\t用户支付配送费(元)\t\t用户支付商品费用(元)\t\t平台抽成比例-金额(元)\t\t商家承租费用(元)\t\t骑手到手配送费(元)\t\t商家到手收入(元)");*/
        System.out.println("距离\t\t用户支付配送费\t\t用户支付商品费用\t\t平台抽成比例-金额\t\t商家承租费用\t\t骑手到手配送费\t\t商家到手收入");
        String[] distanceArray = {"0.0 ~ 1.5", "1.5 ~ 2.5", "2.5 ~ 3.5", "3.5 ~ 4.5", "4.5 ~ 5.5", "5.5 ~ 6.5", "6.5 ~ 7.5", "7.5 ~ 8.5"};
        BigDecimal[] actualPriceArray = {BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(50), BigDecimal.valueOf(60)};
        for (String distanceStr : distanceArray) {
            for (BigDecimal actualPrice : actualPriceArray) {

                BigDecimal platformRatio = BigDecimal.valueOf(20);
                BigDecimal basicActualPrice = BigDecimal.valueOf(10);
                BigDecimal stageActualPrice = BigDecimal.valueOf(10);
                BigDecimal decreasedRatio = BigDecimal.ONE;
                if(actualPrice.compareTo(BigDecimal.ZERO)>=0 && actualPrice.compareTo(basicActualPrice)<=0){
                }else{
                    //增加的价格要按照天花板取整
                    BigDecimal sumIncreasedPrice = actualPrice.subtract(basicActualPrice).divide(stageActualPrice, 0, BigDecimal.ROUND_CEILING);
                    platformRatio = platformRatio.subtract(sumIncreasedPrice.multiply(decreasedRatio));
                }

                BigDecimal distanceValue = BigDecimal.valueOf(Double.valueOf(distanceStr.split(" ~ ")[1]));
                //配送费计算规则：起送价1.5元(0~1KM)，每增加1KM加1元
                //得按照行车距离-骑行来计算，不能按照直线距离
                BigDecimal basicPrice = BigDecimal.valueOf(1.5);
                BigDecimal increasedPrice = BigDecimal.ONE;
                BigDecimal deliveryFee = BigDecimal.ZERO;
                BigDecimal basicDistance = BigDecimal.valueOf(1.5);
                if(distanceValue.compareTo(BigDecimal.ZERO)>=0 && distanceValue.compareTo(basicDistance)<=0){
                    deliveryFee = basicPrice;
                }else{
                    //增加的价格要按照天花板取整
                    BigDecimal sumIncreasedPrice = distanceValue.subtract(basicDistance).multiply(increasedPrice).setScale(0, BigDecimal.ROUND_CEILING);
                    deliveryFee = basicPrice.add(sumIncreasedPrice);
                }
                /*BigDecimal actualPrice = BigDecimal.ZERO;*/

                BigDecimal platformRatioPercent = platformRatio.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal platformFee = actualPrice.multiply(platformRatioPercent).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal merchantFee = BigDecimal.ZERO;
                BigDecimal courierFee = deliveryFee.add(platformFee).add(merchantFee);
                BigDecimal merchantIncome = actualPrice.subtract(courierFee);
                /*String str = "{0}\t\t\t{1}\t\t\t\t\t\t{2}\t\t\t\t\t\t{3}\t\t\t\t\t{4}\t\t\t\t\t{5}";*/
                String str = "{0}\t\t{1}\t\t\t{2}\t\t{3}\t\t{4}\t\t{5}\t\t\t{6}";
                str = MessageFormat.format(str, distanceStr, deliveryFee, actualPrice, String.valueOf(platformRatio + "% -> " + platformFee), merchantFee, courierFee, merchantIncome);
                System.out.println(str);
            }
            System.out.println("\n");
        }
        System.out.println("\n\n");
    }

    /**
     * 测试微信小程序订阅消息
     */
    @Test
    public void testSubscribeMessage() throws IOException {
        String appkey = "wx2e1a8193d3ed12fe";
        String secret = "2774e3a86dc30fbf1ac63d81b56f2291";
        //获取access_token
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appkey + "&secret=" + secret);
        HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("access_token") == null){
            throw new StoneCustomerException(500, "服务通知-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
        String access_token = (String) map.get("access_token");

        Map data = new HashMap();

        Map subMap = new HashMap();
        subMap.put("value", "asdf1234");
        data.put("character_string5", subMap);

        subMap = new HashMap();
        subMap.put("value", "锅包肉");
        data.put("thing4", subMap);

        subMap = new HashMap();
        subMap.put("value", "1.00");
        data.put("amount1", subMap);

        subMap = new HashMap();
        subMap.put("value", "2019-12-26 09:12:47");
        data.put("date2", subMap);

        subMap = new HashMap();
        subMap.put("value", "现金");
        data.put("thing3", subMap);

        /*List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", access_token));
        params.add(new BasicNameValuePair("touser", "o8Dqn5C5YhXn5IrMhIZPcyx7BVLA"));
        params.add(new BasicNameValuePair("template_id", "N63hQksq6Rp3XlrBySkligk-pSvvpJ5fCovwUkwHVH4"));
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("miniprogram_state", "developer"));*/

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", "o8Dqn5C5YhXn5IrMhIZPcyx7BVLA");
        paramsMap.put("template_id", "N63hQksq6Rp3XlrBySkligk-pSvvpJ5fCovwUkwHVH4");
        paramsMap.put("data", data);
        paramsMap.put("miniprogram_state", "developer");

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + access_token);
        httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
        map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
        if((double) map.get("errcode") != 0){
            throw new StoneCustomerException(500, "服务通知-发送消息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
    }

    /**
     * 测试微信公众号发送消息
     */
    @Test
    public void testPublicPlatformMessage() throws IOException {
        String appkey = "wxd28950054b3c01ff";
        String secret = "d0054fc133b5dffae28050e33a5e1873";
        //获取access_token
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appkey + "&secret=" + secret);
        HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("access_token") == null){
            throw new StoneCustomerException(500, "微信公众号-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
        String access_token = (String) map.get("access_token");

        Map data = new HashMap();
        data.put("first", "您好，已为您备齐您的餐单，请您取餐。");
        data.put("storeName", "示例餐厅");
        data.put("orderId", "1002");
        data.put("orderType", "堂食订单金额：100.00元");
        data.put("remark", "为了保证出品新鲜美味，请您及时取餐。");

        /*List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", access_token));
        params.add(new BasicNameValuePair("touser", "o8Dqn5C5YhXn5IrMhIZPcyx7BVLA"));
        params.add(new BasicNameValuePair("template_id", "N63hQksq6Rp3XlrBySkligk-pSvvpJ5fCovwUkwHVH4"));
        params.add(new BasicNameValuePair("data", data));
        params.add(new BasicNameValuePair("miniprogram_state", "developer"));*/

        Map paramsMap = new HashMap();
        paramsMap.put("access_token", access_token);
        paramsMap.put("touser", "ocYTLtxbT_Hlidv4MjZRSVhgqa8I");
        paramsMap.put("template_id", "znwy2IeZtXgsBOIlqeMJPCychMfIAeCqeltXQX23v78");
        paramsMap.put("appid", "wx2e1a8193d3ed12fe");
        paramsMap.put("data", data);

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
        httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
        map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
        if((double) map.get("errcode") != 0){
            throw new StoneCustomerException(500, "微信公众号-发送消息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
    }

    /**
     * 测试获取微信公众号中用户的openId
     */
    @Test
    public void testPublicPlatformOpenId() throws IOException {
        String appkey = "wxd28950054b3c01ff";
        String secret = "d0054fc133b5dffae28050e33a5e1873";
        //获取access_token
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appkey + "&secret=" + secret);
        HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("access_token") == null){
            throw new StoneCustomerException(500, "微信公众号-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
        String access_token = (String) map.get("access_token");

        //获取用户列表
        httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token);
        httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("data") == null){
            throw new StoneCustomerException(500, "微信公众号-获取用户列表失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
        List<String> openidList = (List<String>) ((LinkedTreeMap) map.get("data")).get("openid");

        List<Map<String, Object>> userInfoList = new ArrayList<>();

        //批量获取用户基本信息，最多支持一次拉取100条
        List batchOpenidList = new ArrayList();
        for (int i = 0; i < openidList.size(); i++) {
            if((i+1)%100==0 || i==openidList.size()-1){
                //已经汇集100个openid 或 到达列表末尾，则进行用户基本信息批量查询操作
                Map paramsMap = new HashMap();
                paramsMap.put("user_list", batchOpenidList);
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + access_token);
                httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
                if(map.get("user_info_list") == null){
                    throw new StoneCustomerException(500, "微信公众号-批量获取用户基本信息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
                }
                List<Map<String, Object>> batchUserInfoList = (List<Map<String, Object>>) map.get("user_info_list");
                //将数据组装进完整的用户基本信息集合
                userInfoList.addAll(batchUserInfoList);
                //清空集合数据
                batchOpenidList.clear();
            }
            Map subMap = new HashMap();
            subMap.put("openid", openidList.get(i));
            batchOpenidList.add(subMap);
        }

        int repeatNumber = 0;
        String repeatText = "";
        Map<String, Map<String, Object>> filterMap = new HashMap<>();
        for (Map<String, Object> user : userInfoList) {
            /*String key = user.get("nickname") + " lu-> " + user.get("city") + " lu-> " + user.get("province") + " lu-> " + user.get("country") + " lu-> " + user.get("subscribe_time");*/
            String key = user.get("nickname") + " lu-> ";
            if(filterMap.containsKey(key)){
                repeatNumber++;
                repeatText += key + "\n";
            }
            filterMap.put(key, user);
        }

        System.out.println("\nuserInfoList=" + userInfoList);
        System.out.println("\nrepeatNumber=" + repeatNumber);
        System.out.println("\nrepeatText=" + repeatText);

        int repeatNumber_member = 0;
        String repeatText_member = "";
        for (String key : filterMap.keySet()){
            if(repeatText.contains(key)){
                continue;
            }

            String nickname = key.replace(" lu-> ", "");

            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria().andUsernameEqualTo(nickname);
            List<Member> memberList = memberService.selectByExample(memberExample);
            if(memberList == null){
                repeatNumber_member++;
                repeatText_member += nickname + "-为空\n";
            }else if(memberList.size() != 1){
                repeatNumber_member++;
                repeatText_member += nickname + "\n";
            }else{
                //赋值微信公众号openId
                Member updateMember = new Member();
                updateMember.setId(memberList.get(0).getId());
                updateMember.setWxPublicPlatformOpenId(filterMap.get(key).get("openid").toString());
                memberService.updateByPrimaryKeySelective(updateMember);
            }
        }
        System.out.println("\nrepeatNumber_member=" + repeatNumber_member);
        System.out.println("\nrepeatText_member=" + repeatText_member);
    }

    /**
     * 测试oss工具类
     */
    @Test
    public void testOssUtils() throws Exception {
        /*Boolean exist = ossUtils.doesObjectExist("data/images/admin/1/siam_1574048067619.jpg");
        System.out.println("exist : " + exist);*/

        Integer memberId = 111;
        String path = "data/images/invite_suncode/test";
        String fileName = "suncode_" + memberId + ".png";
        String savePath = path + "/" + fileName;
        wxQrCodeUtils.generateSunCode("pages/login/choose/choose", "inviterId=111", savePath);

        String mainImagePath = "https://deerspot.oss-cn-hangzhou.aliyuncs.com/test/siamCompose.png";
        String qrCodePath = "https://deerspot.oss-cn-hangzhou.aliyuncs.com/" + savePath;

        path = "data/images/invite_poster_compose/" + memberId;
        fileName = fileName + "#" +"siamCompose.png";
        savePath = path + "/" + fileName;

        imageComposeUtils.compoundImage(mainImagePath, qrCodePath, savePath, "8888711246079366");
    }

    @Test
    public void test(){
        SysMessage sysMessage = new SysMessage();
        sysMessage.setUserId(BusinessType.ADMIN_ID);
        sysMessage.setUserType(SysMessage.USER_TYPE_ADMIN);
        sysMessage.setTitle("test");
        sysMessage.setContent("test");
        messageService.save(sysMessage);
    }

    @Test
    public void testGetListByRedis() throws InterruptedException {
        Menu menu = new Menu();
        menu.setShopId(13);
        menuService.getListByRedis(menu);
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(null));
    }
}