//package com.siam.system.modular.package_goods;
//
//import com.siam.system.modular.package_goods.service.*;
//import com.siam.package_common.util.ImageComposeUtils;
//import com.siam.package_common.util.OSSUtils;
//import com.siam.package_common.util.WxQrCodeUtils;
//import com.siam.system.modular.package_goods.entity.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableConfigurationProperties
//public class SystemBackstage {
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallOrderService orderService;
//
//    @Autowired
//    private com.siam.package_common.service.AliyunSms aliyunSms;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallOrderRefundService orderRefundService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallOrderRefundGoodsService orderRefundGoodsService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallOrderRefundProcessService orderRefundProcessService;
//
//    @Autowired
//    private com.siam.business.extend.WxNotifyService wxNotifyService;
//
//    @Autowired
//    private com.siam.business.extend.WxPublicPlatformNotifyService wxPublicPlatformNotifyService;
//
//    @Autowired
//    private com.siam.package_user.service.MemberService memberService;
//
//    @Autowired
//    private com.siam.controller.mod_wx_pay.WxPayService wxPayService;
//
//    @Autowired
//    private com.siam.package_user.service.MemberBillingRecordService memberBillingRecordService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallOrderLogisticsService orderLogisticsService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallOrderDetailService orderDetailService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.PointsMallCouponsMemberRelationService couponsMemberRelationService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.AdvertisementService advertisementService;
//
//    @Autowired
//    private OSSUtils ossUtils;
//
//    @Autowired
//    private ImageComposeUtils imageComposeUtils;
//
//    @Autowired
//    private WxQrCodeUtils wxQrCodeUtils;
//
//    /**
//     * 微信退款
//     *
//     * @return
//     */
//    @Test
//    public void work() {
//        String orderNo = "183157560481818044";
//        BigDecimal actualPrice = BigDecimal.valueOf(9.90);
//        BigDecimal refundAmount = BigDecimal.valueOf(9.90);
//        //微信支付
//        Boolean isRefundSuccess = wxPayService.refund(orderNo, actualPrice, refundAmount, "vip_recharge");
//        if (isRefundSuccess){
//            System.out.println("退款成功");
//        }else{
//            System.out.println("退款失败");
//        }
//    }
//}