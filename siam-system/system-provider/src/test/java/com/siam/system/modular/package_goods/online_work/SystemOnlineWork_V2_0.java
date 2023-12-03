//package com.siam.online_work;
//
//import com.siam.system.modular.package_goods.mapper.*;
//import com.siam.package_common.constant.Quantity;
//import com.siam.package_common.util.BeanUtils;
//import com.siam.package_common.util.GsonUtils;
//import com.siam.package_common.util.JsonUtils;
//import com.siam.system.modular.package_goods.entity.*;
//import com.siam.system.modular.package_goods.model.example.*;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.nio.charset.StandardCharsets;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Deprecated
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@EnableConfigurationProperties
//public class SystemOnlineWork_V2_0 {
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.GoodsService goodsService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.CouponsGoodsRelationService goodsRelationService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.CouponsShopRelationMapper couponsShopRelationMapper;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.CouponsMapper couponsMapper;
//
//    //tb_coupons
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.CouponsGoodsRelationMapper couponsGoodsRelationMapper;
//    //tb_coupons_goods_relation
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.CouponsMemberRelationMapper couponsMemberRelationMapper;
//    //tb_coupons_member_relation
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.DeliveryAddressMapper deliveryAddressMapper;
//
//    //tb_delivery_address
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.FullReductionRuleMapper fullReductionRuleMapper;
//
//    //tb_full_reduction_rule
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.GoodsMapper goodsMapper;
//
//    //tb_goods
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.GoodsAccessoriesMapper goodsAccessoriesMapper;
//
//    //tb_goods_accessories
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.GoodsRawmaterialRelationMapper goodsRawmaterialRelationMapper;
//
//    //tb_goods_rawmaterial_relation
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.GoodsSpecificationMapper goodsSpecificationMapper;
//
//    //tb_goods_specification
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.GoodsSpecificationOptionMapper goodsSpecificationOptionMapper;
//
//    //tb_goods_specification_option
//    @Autowired
//    private com.siam.package_user.mapper.MemberMapper memberMapper;
//
//    //tb_member
//    @Autowired
//    private com.siam.package_user.mapper.MemberBillingRecordMapper memberBillingRecordMapper;
//
//    //tb_member_billing_record
//    @Autowired
//    private com.siam.package_user.mapper.MemberInviteRelationMapper memberInviteRelationMapper;
//
//    //tb_member_invite_relation
//    @Autowired
//    private com.siam.package_user.mapper.MemberTokenMapper memberTokenMapper;
//
//    //tb_member_token
//    @Autowired
//    private com.siam.package_user.mapper.MemberTradeRecordMapper memberTradeRecordMapper;
//
//    //tb_member_trade_record
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.MenuMapper menuMapper;
//
//    //tb_menu
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.MenuGoodsRelationMapper menuGoodsRelationMapper;
//
//    //tb_menu_goods_relation
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.OrderMapper orderMapper;
//
//    //tb_order
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.OrderDetailMapper orderDetailMapper;
//
//    //tb_order_detail
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.RawmaterialMapper rawmaterialMapper;
//
//    //tb_rawmaterial
//    @Autowired
//    private com.siam.system.modular.package_goods.mapper.ShoppingCartMapper shoppingCartMapper;
//
//    //tb_shopping_cart
//
//    /**
//     *
//     */
//    /*@Test*/
//    public void test() throws Exception {
//        //空表
//        /*tb_admin_token
//        tb_appraise
//        tb_give_like
//        tb_order_refund
//        tb_order_refund_goods
//        tb_order_refund_process
//        tb_promotion_activity
//        tb_promotion_activity_goods_relation
//        tb_reply
//        tb_scheduled_task_log
//        tb_sms_log
//        tb_system_usage_record
//        tb_vip_recharge_record
//        tb_shop_change_record
//        tb_merchant_billing_record
//        tb_merchant_recommend_goods
//        tb_merchant_token
//        tb_merchant_trade_record
//        tb_merchant_withdraw_record*/
//
//        /*TRUNCATE TABLE tb_admin_token;
//        TRUNCATE TABLE tb_appraise;
//        TRUNCATE TABLE tb_give_like;
//        TRUNCATE TABLE tb_order_refund;
//        TRUNCATE TABLE tb_order_refund_goods;
//        TRUNCATE TABLE tb_order_refund_process;
//        TRUNCATE TABLE tb_promotion_activity;
//        TRUNCATE TABLE tb_promotion_activity_goods_relation;
//        TRUNCATE TABLE tb_reply;
//        TRUNCATE TABLE tb_scheduled_task_log;
//        TRUNCATE TABLE tb_sms_log;
//        TRUNCATE TABLE tb_system_usage_record;
//        TRUNCATE TABLE tb_vip_recharge_record;
//        TRUNCATE TABLE tb_shop_change_record;
//        TRUNCATE TABLE tb_merchant_billing_record;
//        TRUNCATE TABLE tb_merchant_recommend_goods;
//        TRUNCATE TABLE tb_merchant_token;
//        TRUNCATE TABLE tb_merchant_trade_record;
//        TRUNCATE TABLE tb_merchant_withdraw_record;*/
//
//        //需要初始化
//        /*tb_admin --
//        tb_advertisement --
//        tb_merchant --
//        tb_scheduled_task --
//        tb_setting --
//        tb_shop --*/
//
//        //需要迁移数据
//        /*tb_coupons
//        tb_coupons_goods_relation
//        tb_coupons_member_relation
//        tb_coupons_shop_relation
//        tb_delivery_address
//        tb_full_reduction_rule
//        tb_goods
//        tb_goods_accessories
//        tb_goods_rawmaterial_relation
//        tb_goods_specification
//        tb_goods_specification_option
//        tb_member
//        tb_member_billing_record
//        tb_member_invite_relation
//        tb_member_token
//        tb_member_trade_record
//        tb_menu
//        tb_menu_goods_relation
//        tb_order
//        tb_order_detail
//        tb_rawmaterial
//        tb_shopping_cart*/
//
//        HttpPost httpPost = new HttpPost("http://localhost:8080/deerspot-milktea-server/rest/common/selectAllMigrateData");
//        httpPost.setEntity(new StringEntity(GsonUtils.toJson(new HashMap()), HTTP.UTF_8));
//        HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
//
//        String jsonStr = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
//
//        Map<String, Object> map = JsonUtils.toMap(jsonStr);
//
//        Map<String, Object> resultMap = (Map<String, Object>) map.get("data");
//
//        final Integer defaultMerchantId = 18;
//        final Integer defaultShopId = 13;
//
//        //tb_coupons
//
//        List<Map<String, Object>> couponsLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("couponsList");
//        filterBigDecimal(couponsLinkedTreeMapList);
//        List<com.siam.system.modular.package_goods.entity.Coupons> couponsList = BeanUtils.mapsToObjects(couponsLinkedTreeMapList, com.siam.system.modular.package_goods.entity.Coupons.class);
//
//        System.out.println(couponsList.size());
//
//        /*List<Coupons> couponsList = (List<Coupons>) resultMap.get("couponsList");*/
////        couponsList.forEach(coupons -> {
////            if(coupons.getId()==1 || coupons.getId()==2){
////                return;
////            }
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            coupons.setSource(Quantity.INT_1);
////            couponsMapper.insertSelective(coupons);
////
////            //添加优惠券与店铺的关系
////            CouponsShopRelation couponsShopRelation = new CouponsShopRelation();
////            couponsShopRelation.setCouponsId(coupons.getId());
////            couponsShopRelation.setShopId(defaultShopId);
////            couponsShopRelation.setCreateTime(new Date());
////            couponsShopRelationMapper.insertSelective(couponsShopRelation);
////        });
//
//        //tb_coupons_goods_relation
////        List<Map<String, Object>> couponsGoodsRelationLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("couponsGoodsRelationList");
////        filterBigDecimal(couponsGoodsRelationLinkedTreeMapList);
////        List<CouponsGoodsRelation> couponsGoodsRelationList = BeanUtils.mapsToObjects(couponsGoodsRelationLinkedTreeMapList, CouponsGoodsRelation.class);
////
////        /*List<CouponsGoodsRelation> couponsGoodsRelationList = (List<CouponsGoodsRelation>) resultMap.get("couponsGoodsRelationList");*/
////        couponsGoodsRelationList.forEach(couponsGoodsRelation -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            couponsGoodsRelationMapper.insertSelective(couponsGoodsRelation);
////        });
//
//        //tb_coupons_member_relation
////        List<Map<String, Object>> couponsMemberRelationLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("couponsMemberRelationList");
////        filterBigDecimal(couponsMemberRelationLinkedTreeMapList);
////        List<CouponsMemberRelation> couponsMemberRelationList = BeanUtils.mapsToObjects(couponsMemberRelationLinkedTreeMapList, CouponsMemberRelation.class);
////
////        /*List<CouponsMemberRelation> couponsMemberRelationList = (List<CouponsMemberRelation>) resultMap.get("couponsMemberRelationList");*/
////        couponsMemberRelationList.forEach(couponsMemberRelation -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            couponsMemberRelationMapper.insertSelective(couponsMemberRelation);
////        });
//
//        //tb_delivery_address
////        List<Map<String, Object>> deliveryAddressLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("deliveryAddressList");
////        filterBigDecimal(deliveryAddressLinkedTreeMapList);
////        List<DeliveryAddress> deliveryAddressList = BeanUtils.mapsToObjects(deliveryAddressLinkedTreeMapList, DeliveryAddress.class);
////
////        /*List<DeliveryAddress> deliveryAddressList = (List<DeliveryAddress>) resultMap.get("deliveryAddressList");*/
////        deliveryAddressList.forEach(deliveryAddress -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            deliveryAddressMapper.insertSelective(deliveryAddress);
////        });
//
//        //tb_full_reduction_rule
////        List<Map<String, Object>> fullReductionRuleLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("fullReductionRuleList");
////        filterBigDecimal(fullReductionRuleLinkedTreeMapList);
////        List<FullReductionRule> fullReductionRuleList = BeanUtils.mapsToObjects(fullReductionRuleLinkedTreeMapList, FullReductionRule.class);
////
////        /*List<FullReductionRule> fullReductionRuleList = (List<FullReductionRule>) resultMap.get("fullReductionRuleList");*/
////        fullReductionRuleList.forEach(fullReductionRule -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            fullReductionRule.setShopId(defaultShopId);
////            fullReductionRuleMapper.insertSelective(fullReductionRule);
////        });
//
//        //tb_goods
////        List<Map<String, Object>> goodsLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("goodsList");
////        filterBigDecimal(goodsLinkedTreeMapList);
////        List<Goods> goodsList = BeanUtils.mapsToObjects(goodsLinkedTreeMapList, Goods.class);
////
////        /*List<Goods> goodsList = (List<Goods>) resultMap.get("goodsList");*/
////        goodsList.forEach(goods -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            goods.setShopId(defaultShopId);
////            goodsMapper.insertSelective(goods);
////        });
//
//        //tb_goods_accessories
////        List<Map<String, Object>> goodsAccessoriesLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("goodsAccessoriesList");
////        filterBigDecimal(goodsAccessoriesLinkedTreeMapList);
////        List<GoodsAccessories> goodsAccessoriesList = BeanUtils.mapsToObjects(goodsAccessoriesLinkedTreeMapList, GoodsAccessories.class);
////
////        /*List<GoodsAccessories> goodsAccessoriesList = (List<GoodsAccessories>) resultMap.get("goodsAccessoriesList");*/
////        goodsAccessoriesList.forEach(goodsAccessories -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            goodsAccessoriesMapper.insertSelective(goodsAccessories);
////        });
//
//        //tb_goods_rawmaterial_relation
////        List<Map<String, Object>> goodsRawmaterialRelationLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("goodsRawmaterialRelationList");
////        filterBigDecimal(goodsRawmaterialRelationLinkedTreeMapList);
////        List<GoodsRawmaterialRelation> goodsRawmaterialRelationList = BeanUtils.mapsToObjects(goodsRawmaterialRelationLinkedTreeMapList, GoodsRawmaterialRelation.class);
////
////        /*List<GoodsRawmaterialRelation> goodsRawmaterialRelationList = (List<GoodsRawmaterialRelation>) resultMap.get("goodsRawmaterialRelationList");*/
////        goodsRawmaterialRelationList.forEach(goodsRawmaterialRelation -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            goodsRawmaterialRelationMapper.insertSelective(goodsRawmaterialRelation);
////        });
//
//        //tb_goods_specification
////        List<Map<String, Object>> goodsSpecificationLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("goodsSpecificationList");
////        filterBigDecimal(goodsSpecificationLinkedTreeMapList);
////        List<GoodsSpecification> goodsSpecificationList = BeanUtils.mapsToObjects(goodsSpecificationLinkedTreeMapList, GoodsSpecification.class);
////
////        /*List<GoodsSpecification> goodsSpecificationList = (List<GoodsSpecification>) resultMap.get("goodsSpecificationList");*/
////        goodsSpecificationList.forEach(goodsSpecification -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            goodsSpecificationMapper.insertSelective(goodsSpecification);
////        });
//
//        //tb_goods_specification_option
////        List<Map<String, Object>> goodsSpecificationOptionLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("goodsSpecificationOptionList");
////        filterBigDecimal(goodsSpecificationOptionLinkedTreeMapList);
////        List<GoodsSpecificationOption> goodsSpecificationOptionList = BeanUtils.mapsToObjects(goodsSpecificationOptionLinkedTreeMapList, GoodsSpecificationOption.class);
////
////        /*List<GoodsSpecificationOption> goodsSpecificationOptionList = (List<GoodsSpecificationOption>) resultMap.get("goodsSpecificationOptionList");*/
////        goodsSpecificationOptionList.forEach(goodsSpecificationOption -> {
////            //添加优惠券 这些优惠券都是属于辰泰科技园区店
////            goodsSpecificationOptionMapper.insertSelective(goodsSpecificationOption);
////        });
//
//        //tb_member
//        List<Map<String, Object>> memberLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("memberList");
//        filterBigDecimal(memberLinkedTreeMapList);
//        List<com.siam.package_user.entity.Member> memberList = BeanUtils.mapsToObjects(memberLinkedTreeMapList, com.siam.package_user.entity.Member.class);
//
//        /*List<Member> memberList = (List<Member>) resultMap.get("memberList");*/
//        memberList.forEach(member -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            member.setRegisterWay(Quantity.INT_1);
//            member.setWxPublicPlatformOpenId(null);
//            member.setIsRequestWxNotify(true);
//            member.setLastRequestWxNotifyTime(new Date());
//            memberMapper.insertSelective(member);
//        });
//
//        //tb_member_billing_record
//        List<Map<String, Object>> memberBillingRecordLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("memberBillingRecordList");
//        filterBigDecimal(memberBillingRecordLinkedTreeMapList);
//        List<com.siam.package_user.entity.MemberBillingRecord> memberBillingRecordList = BeanUtils.mapsToObjects(memberBillingRecordLinkedTreeMapList, com.siam.package_user.entity.MemberBillingRecord.class);
//
//        /*List<MemberBillingRecord> memberBillingRecordList = (List<MemberBillingRecord>) resultMap.get("memberBillingRecordList");*/
//        memberBillingRecordList.forEach(memberBillingRecord -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            memberBillingRecordMapper.insertSelective(memberBillingRecord);
//        });
//
//        //tb_member_invite_relation
//        List<Map<String, Object>> memberInviteRelationLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("memberInviteRelationList");
//        filterBigDecimal(memberInviteRelationLinkedTreeMapList);
//        List<com.siam.package_user.entity.MemberInviteRelation> memberInviteRelationList = BeanUtils.mapsToObjects(memberInviteRelationLinkedTreeMapList, com.siam.package_user.entity.MemberInviteRelation.class);
//
//        /*List<MemberInviteRelation> memberInviteRelationList = (List<MemberInviteRelation>) resultMap.get("memberInviteRelationList");*/
//        memberInviteRelationList.forEach(memberInviteRelation -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            memberInviteRelationMapper.insertSelective(memberInviteRelation);
//        });
//
//        //tb_member_token
//        List<Map<String, Object>> memberTokenLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("memberTokenList");
//        filterBigDecimal(memberTokenLinkedTreeMapList);
//        List<com.siam.package_user.entity.MemberToken> memberTokenList = BeanUtils.mapsToObjects(memberTokenLinkedTreeMapList, com.siam.package_user.entity.MemberToken.class);
//
//        /*List<MemberToken> memberTokenList = (List<MemberToken>) resultMap.get("memberTokenList");*/
//        memberTokenList.forEach(memberToken -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            memberTokenMapper.insertSelective(memberToken);
//        });
//
//        //tb_member_trade_record
//        List<Map<String, Object>> memberTradeRecordLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("memberTradeRecordList");
//        filterBigDecimal(memberTradeRecordLinkedTreeMapList);
//        List<com.siam.package_user.entity.MemberTradeRecord> memberTradeRecordList = BeanUtils.mapsToObjects(memberTradeRecordLinkedTreeMapList, com.siam.package_user.entity.MemberTradeRecord.class);
//
//        /*List<MemberTradeRecord> memberTradeRecordList = (List<MemberTradeRecord>) resultMap.get("memberTradeRecordList");*/
//        memberTradeRecordList.forEach(memberTradeRecord -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            memberTradeRecord.setMerchantId(null);
//            memberTradeRecordMapper.insertSelective(memberTradeRecord);
//        });
//
//        //tb_menu
//        List<Map<String, Object>> menuLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("menuList");
//        filterBigDecimal(menuLinkedTreeMapList);
//        List<com.siam.system.modular.package_goods.entity.Menu> menuList = BeanUtils.mapsToObjects(menuLinkedTreeMapList, com.siam.system.modular.package_goods.entity.Menu.class);
//
//        /*List<Menu> menuList = (List<Menu>) resultMap.get("menuList");*/
//        menuList.forEach(menu -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            menu.setShopId(defaultShopId);
//            menuMapper.insertSelective(menu);
//        });
//
//        //tb_menu_goods_relation
//        List<Map<String, Object>> menuGoodsRelationLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("menuGoodsRelationList");
//        filterBigDecimal(menuGoodsRelationLinkedTreeMapList);
//        List<com.siam.system.modular.package_goods.entity.MenuGoodsRelation> menuGoodsRelationList = BeanUtils.mapsToObjects(menuGoodsRelationLinkedTreeMapList, com.siam.system.modular.package_goods.entity.MenuGoodsRelation.class);
//
//        /*List<MenuGoodsRelation> menuGoodsRelationList = (List<MenuGoodsRelation>) resultMap.get("menuGoodsRelationList");*/
//        menuGoodsRelationList.forEach(menuGoodsRelation -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            menuGoodsRelationMapper.insertSelective(menuGoodsRelation);
//        });
//
//        //tb_order
//        List<Map<String, Object>> orderLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("orderList");
//        filterBigDecimal(orderLinkedTreeMapList);
//        List<com.siam.system.modular.package_goods.entity.Order> orderList = BeanUtils.mapsToObjects(orderLinkedTreeMapList, com.siam.system.modular.package_goods.entity.Order.class);
//
//        /*List<Order> orderList = (List<Order>) resultMap.get("orderList");*/
//        orderList.forEach(order -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            order.setShopId(defaultShopId);
//            order.setShopAddress("湖南长沙岳麓区欣盛路辰泰科技园3栋");
//            order.setPlatformExtractRatio(BigDecimal.ZERO);
//            order.setPlatformExtractPrice(BigDecimal.ZERO);
//            order.setPlatformDeliveryFee(BigDecimal.ZERO);
//            order.setPlatformIncome(BigDecimal.ZERO);
//            order.setMerchantDeliveryFee(BigDecimal.ZERO);
//            order.setCourierIncome(BigDecimal.ZERO);
//            order.setMerchantIncome(order.getActualPrice());
//            order.setPaymentSuccessTime(null);
//            order.setOrderCompletionTime(null);
//            order.setPaidOrderCancelReason(null);
//            order.setLimitedPrice(null); //sql修改
//            order.setReducedPrice(null); //sql修改
//            order.setCouponsDiscountPrice(null); //sql修改
//            order.setDeliveryWay(Quantity.INT_1);
//            order.setIsPayToMerchant(true);
//            order.setBeforeReducedDeliveryFee(order.getDeliveryFee());
//            orderMapper.insertSelective(order);
//        });
//
//        //tb_order_detail
//        List<Map<String, Object>> orderDetailLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("orderDetailList");
//        filterBigDecimal(orderDetailLinkedTreeMapList);
//        List<com.siam.system.modular.package_order.entity.OrderDetail> orderDetailList = BeanUtils.mapsToObjects(orderDetailLinkedTreeMapList, com.siam.system.modular.package_order.entity.OrderDetail.class);
//
//        /*List<OrderDetail> orderDetailList = (List<OrderDetail>) resultMap.get("orderDetailList");*/
//        orderDetailList.forEach(orderDetail -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            orderDetail.setPackingCharges(null); //未知
//            orderDetail.setIsUsedCoupons(null); //未知
//            orderDetail.setCouponsDiscountPrice(null); //未知
//            orderDetail.setAfterCouponsDiscountPrice(null); //未知
//            orderDetailMapper.insertSelective(orderDetail);
//        });
//
//        //tb_rawmaterial
//        List<Map<String, Object>> rawmaterialLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("rawmaterialList");
//        filterBigDecimal(rawmaterialLinkedTreeMapList);
//        List<com.siam.system.modular.package_goods.entity.Rawmaterial> rawmaterialList = BeanUtils.mapsToObjects(rawmaterialLinkedTreeMapList, com.siam.system.modular.package_goods.entity.Rawmaterial.class);
//
//        /*List<Rawmaterial> rawmaterialList = (List<Rawmaterial>) resultMap.get("rawmaterialList");*/
//        rawmaterialList.forEach(rawmaterial -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            rawmaterialMapper.insertSelective(rawmaterial);
//        });
//
//        //tb_shopping_cart
//        List<Map<String, Object>> shoppingCartLinkedTreeMapList = (List<Map<String, Object>>) resultMap.get("shoppingCartList");
//        filterBigDecimal(shoppingCartLinkedTreeMapList);
//        List<com.siam.system.modular.package_goods.entity.ShoppingCart> shoppingCartList = BeanUtils.mapsToObjects(shoppingCartLinkedTreeMapList, com.siam.system.modular.package_goods.entity.ShoppingCart.class);
//
//        /*List<ShoppingCart> shoppingCartList = (List<ShoppingCart>) resultMap.get("shoppingCartList");*/
//        shoppingCartList.forEach(shoppingCart -> {
//            //添加优惠券 这些优惠券都是属于辰泰科技园区店
//            shoppingCart.setShopId(defaultShopId);
//            shoppingCartMapper.insertSelective(shoppingCart);
//        });
//    }
//
//    public void filterBigDecimal(List<Map<String, Object>> couponsLinkedTreeMapList){
//        for (Map<String, Object> stringObjectMap : couponsLinkedTreeMapList) {
//            for(String key : stringObjectMap.keySet()){
//                if(stringObjectMap.get(key) instanceof Double){
//                    stringObjectMap.put(key, BigDecimal.valueOf((double) stringObjectMap.get(key)));
//                }else if(stringObjectMap.get(key) instanceof String && stringObjectMap.get(key).equals("null")){
//                    stringObjectMap.put(key, null);
//                }else if(stringObjectMap.get(key) instanceof String && (((String) stringObjectMap.get(key)).contains("2019/") || ((String) stringObjectMap.get(key)).contains("2020/") || ((String) stringObjectMap.get(key)).contains("2021/") || ((String) stringObjectMap.get(key)).contains("2022/"))){
//                    stringObjectMap.put(key, new Date((String) stringObjectMap.get(key)));
//                }else{
//                    System.out.println(key + " - " +stringObjectMap.get(key));
//                }
//            }
//        }
//    }
//
//    public static String dealDateFormat(String oldDate) {
//        Date date1 = null;
//        DateFormat df2 = null;
//        try {
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            Date date = df.parse(oldDate);
//            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
//            date1 = df1.parse(date.toString());
//            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return df2.format(date1);
//    }
//
//    public static void main(String[] args) {
//        /*Map map = new HashMap();
//        map.put("discountAmount", 100.0);
//        Coupons coupons = new Coupons();
//        BeanUtils.mapToBean(map, coupons);
//        System.out.println(coupons);*/
//
//        /*String str = dealDateFormat("2020-04-27T09:39:40.000+0000");
//        System.out.println(str);*/
//
//        System.out.println(new Date("2020/04/27 17:39:40"));
//    }
//}