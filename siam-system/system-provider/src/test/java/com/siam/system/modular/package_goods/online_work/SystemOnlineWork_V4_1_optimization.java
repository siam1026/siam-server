//package com.siam.online_work;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.siam.system.modular.package_goods.service.*;
//import com.siam.package_common.constant.BusinessType;
//import com.siam.package_common.constant.Quantity;
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
//import java.io.IOException;
//import java.util.List;
//
//@Deprecated
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableConfigurationProperties
//public class SystemOnlineWork_V4_1_optimization {
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
//    @Test
//    public void work() {
//        //1、分享邀请好友-活动规则 初始化
//    }
//
//    /**
//     * 初始化生成个人邀请分享太阳码
//     * 【已执行】
//     * @return
//     */
//    @Test
//    public void initSunCode() {
//        List<com.siam.package_user.entity.Member> memberList = memberService.selectByExample(new com.siam.package_user.model.example.MemberExample());
//        memberList = memberService.selectByExample(new com.siam.package_user.model.example.MemberExample());
//        memberList.forEach(member -> {
//            String path = "data/images/invite_suncode/v1.0";
//            String fileName = "suncode_" + member.getId() + ".png";
//            String savePath = path + "/" + fileName;
//            wxQrCodeUtils.generateSunCode("pages/login/choose/choose", "inviterId="+member.getId(), savePath);
//
//            com.siam.package_user.entity.Member updateMember = new com.siam.package_user.entity.Member();
//            updateMember.setId(member.getId());
//            updateMember.setInviteSuncode(savePath);
//            memberService.updateByPrimaryKeySelective(updateMember);
//        });
//    }
//
//    /**
//     * 初始化邀请分享注册-生成美图的海报
//     * 【已执行】
//     * @return
//     */
//    @Test
//    public void initAdvertisement() {
//        com.siam.system.modular.package_goods.entity.Advertisement advertisement = new com.siam.system.modular.package_goods.entity.Advertisement();
//        advertisement.setType(Quantity.INT_4);
//        Page<com.siam.system.modular.package_goods.entity.Advertisement> page = advertisementService.getListByPage(new Page(pageNo, pageSize), -1, 10, advertisement);
//
//        List<com.siam.package_user.entity.Member> memberList = memberService.selectByExample(new com.siam.package_user.model.example.MemberExample());
//        memberList.forEach(dbMember -> {
//            //截取个人邀请分享太阳码的文件名
//            int suncode_index = dbMember.getInviteSuncode().lastIndexOf("/");
//            String suncode_fileName = dbMember.getInviteSuncode().substring(suncode_index+1);
//
//            //如果类型为分享页面生成美图，则需要合成个人邀请二维码
//            page.getRecords().forEach(dbAdvertisement -> {
//                int index = dbAdvertisement.getImagePath().lastIndexOf("/");
//                String poster_fileName = dbAdvertisement.getImagePath().substring(index+1);
//                String fileName = suncode_fileName + "--" + poster_fileName;
//                String filePath = "data/images/invite_poster_compose/" + dbMember.getId() + "/" + fileName;
//                Boolean isExist = ossUtils.doesObjectExist(filePath);
//                if (isExist){
//                    /*dbAdvertisement.setImagePath(filePath);*/
//                } else {
//                    //合成并上传图片
//                    String compose_path = "data/images/invite_poster_compose/" + dbMember.getId();
//                    String compose_fileName = suncode_fileName + "--" + poster_fileName;
//                    String savePath = compose_path + "/" + compose_fileName;
//                    try {
//                        imageComposeUtils.compoundImage(BusinessType.OSS_PREFIX + dbAdvertisement.getImagePath(), BusinessType.OSS_PREFIX + dbMember.getInviteSuncode(), savePath, dbMember.getVipNo());
//                        /*dbAdvertisement.setImagePath(savePath);*/
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        });
//    }
//}