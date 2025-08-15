//package com.siam.online_work;
//
//import com.siam.system.modular.package_goods.mapper.*;
//import com.siam.system.modular.package_goods.service.*;
//import com.siam.system.modular.package_goods.entity.*;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@Deprecated
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableConfigurationProperties
//public class SystemOnlineWork_V3_0 {
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.GoodsService goodsService;
//
//    @Autowired
//    private com.siam.package_user.service.MemberService memberService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.OrderService orderService;
//
//    @Autowired
//    private com.siam.system.modular.package_goods.service.ShopService shopService;
//
//    @Autowired
//    private WxQrCodeUtils wxQrCodeUtils;
//
//    @Autowired
//    private ImageComposeUtils imageComposeUtils;
//
//    @Test
//    public void work() {
//        //外卖系统未上正式可不操作
//        //1、外卖系统&积分商城--商品主图 全部 改成商品轮播图的第一张
//        List<com.siam.system.modular.package_goods.entity.Goods> goodsList = goodsService.selectByExample(new com.siam.system.modular.package_goods.model.example.GoodsExample());
//        goodsList.forEach(goods -> {
//            com.siam.system.modular.package_goods.entity.Goods updateGoods = new com.siam.system.modular.package_goods.entity.Goods();
//            updateGoods.setId(goods.getId());
//            //商品的主图 等于 商品轮播图的第一张图
//            if(StringUtils.isNotBlank(goods.getSubImages())){
//                updateGoods.setMainImage(goods.getSubImages().split(",")[0]);
//                goodsService.updateById(updateGoods);
//            }
//        });
//
//        //2、会员编号 全部 重新获取
//        List<com.siam.package_user.entity.Member> memberList = memberService.selectByExample(new com.siam.package_user.model.example.MemberExample());
//        memberList.forEach(member -> {
//            //获取会员卡号
//            String vipNo = memberService.generateVipNo();
//            com.siam.package_user.entity.Member updateMember = new com.siam.package_user.entity.Member();
//            updateMember.setId(member.getId());
//            updateMember.setVipNo(vipNo);
//            memberService.updateByPrimaryKeySelective(updateMember);
//        });
//
//        //3、外卖系统&积分商城--订单表的 接单门店logo、第一件商品的主图 需要维护
//        List<com.siam.system.modular.package_goods.entity.Order> orderList = orderService.selectByExample(new com.siam.system.modular.package_goods.model.example.OrderExample());
//        orderList.forEach(order -> {
//            com.siam.system.modular.package_goods.entity.Shop shop = shopService.getById(order.getShopId());
//            com.siam.system.modular.package_goods.entity.Order updateOrder = new com.siam.system.modular.package_goods.entity.Order();
//            updateOrder.setId(order.getId());
//            updateOrder.setShopLogoImg(shop.getShopLogoImg());
//            orderService.updateById(updateOrder);
//        });
//
//        //4、生成个人邀请分享太阳码
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
//}