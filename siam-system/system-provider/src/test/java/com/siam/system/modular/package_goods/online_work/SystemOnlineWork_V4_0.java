//package com.siam.online_work;
//
//import com.siam.system.modular.package_goods.service.*;
//import com.siam.package_common.constant.Quantity;
//import com.siam.package_common.exception.StoneCustomerException;
//import com.siam.package_common.util.DateUtils;
//import com.siam.system.modular.package_goods.entity.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Deprecated
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableConfigurationProperties
//public class SystemOnlineWork_V4_0 {
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
//    @Test
//    public void work() {
//        //纠正错误的积分商城退款订单
//        List<Integer> idList = new ArrayList<>();
//        idList.add(2);
//        idList.add(3);
//        idList.add(9);
//        idList.add(10);
//
//        for (Integer id : idList) {
//            com.siam.system.modular.package_goods.entity.PointsMallOrder dbPointsMallOrder = orderService.selectByPrimaryKey(id);
//            if (dbPointsMallOrder == null) throw new StoneCustomerException("该订单不存在");
//
//            com.siam.system.modular.package_goods.entity.PointsMallOrderRefund dbPointsMallOrderRefund = orderRefundService.selectByPointsMallOrderId(id);
//            if (dbPointsMallOrderRefund == null) throw new StoneCustomerException("该订单无退款记录");
//            if (dbPointsMallOrderRefund.getStatus() != Quantity.INT_4) {
//                throw new StoneCustomerException("该售后订单已被处理过，不允许操作");
//            }
//
//            //获取该订单的对应用户
//            com.siam.package_user.entity.Member orderMember = memberService.selectByPrimaryKey(dbPointsMallOrder.getMemberId());
//
//            //审核通过
//
//            //修改订单记录状态-售后处理完成
//            com.siam.system.modular.package_goods.entity.PointsMallOrder updatePointsMallOrder = new com.siam.system.modular.package_goods.entity.PointsMallOrder();
//            updatePointsMallOrder.setId(id);
//            updatePointsMallOrder.setStatus(Quantity.INT_9);
//            orderService.updateByPrimaryKeySelective(updatePointsMallOrder);
//
//            //进行订单自动退款操作
//            boolean isRefundSuccess = false;
//            if(dbPointsMallOrder.getPaymentMode().equals(Quantity.INT_1)){
//                //微信支付
//                isRefundSuccess = true;
//
//            }else if(dbPointsMallOrder.getPaymentMode().equals(Quantity.INT_3)){
//                //余额支付
//
//                isRefundSuccess = true;
//            }
//
//            if(!isRefundSuccess){
//                throw new StoneCustomerException("退款失败，请联系管理员");
//            }
//
//            //5）只有在退了使用优惠券的商品时，优惠券才会被退回
//            if(dbPointsMallOrderRefund.getIsUsedCoupons()){
//                //退回优惠卷
//                Integer couponsMemberRelationId = dbPointsMallOrder.getCouponsMemberRelationId();
//                if (couponsMemberRelationId != null) {
//                    couponsMemberRelationService.updateCouponsUsed(couponsMemberRelationId,false);
//                }
//            }
//
//            //修改退款状态 -- 退款成功
//            com.siam.system.modular.package_goods.entity.PointsMallOrderRefund updatePointsMallOrderRefund = new com.siam.system.modular.package_goods.entity.PointsMallOrderRefund();
//            updatePointsMallOrderRefund.setId(dbPointsMallOrderRefund.getId());
//            updatePointsMallOrderRefund.setStatus(Quantity.INT_7);
//            orderRefundService.updateByPrimaryKeySelective(updatePointsMallOrderRefund);
//
//            //添加退款流程 -- 等待平台处理
//            com.siam.system.modular.package_goods.entity.PointsMallOrderRefundProcess orderRefundProcess = new com.siam.system.modular.package_goods.entity.PointsMallOrderRefundProcess();
//            orderRefundProcess.setOrderRefundId(dbPointsMallOrderRefund.getId());
//            orderRefundProcess.setName("等待平台处理");
//            orderRefundProcess.setCreateTime(new Date());
//            orderRefundProcessService.insertSelective(orderRefundProcess);
//
//            //添加退款流程 -- 退款成功
//            com.siam.system.modular.package_goods.entity.PointsMallOrderRefundProcess orderRefundProcess_second = new com.siam.system.modular.package_goods.entity.PointsMallOrderRefundProcess();
//            orderRefundProcess_second.setOrderRefundId(dbPointsMallOrderRefund.getId());
//            orderRefundProcess_second.setName("退款成功");
//            orderRefundProcess_second.setCreateTime(DateUtils.addSeconds(new Date(), 5));
//            orderRefundProcessService.insertSelective(orderRefundProcess_second);
//
//            //发送服务通知
//            wxNotifyService.sendOrderRefundSuccessMessage(orderMember.getOpenId(), dbPointsMallOrder.getShopName(), dbPointsMallOrder.getOrderNo(), dbPointsMallOrder.getDescription(), dbPointsMallOrderRefund.getRefundAmount(), new Date());
//
//            //退回下单佣金奖励
//            List typeList = new ArrayList();
//            typeList.add(com.siam.package_user.entity.MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION);
//            typeList.add(com.siam.package_user.entity.MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION);
//            typeList.add(com.siam.package_user.entity.MemberBillingRecord.TYPE_OWN_COMMISSION);
//            com.siam.system.modular.package_goods.model.example.MemberBillingRecordExample example = new com.siam.system.modular.package_goods.model.example.MemberBillingRecordExample();
//            example.createCriteria().andTypeIn(typeList)
//                    .andOperateTypeEqualTo(com.siam.package_user.entity.MemberBillingRecord.OPERATE_TYPE_ADD)
//                    .andCoinTypeEqualTo(com.siam.package_user.entity.MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT)
//                    .andPointsMallOrderIdEqualTo(dbPointsMallOrder.getId());
//            List<com.siam.package_user.entity.MemberBillingRecord> list = memberBillingRecordService.selectByExample(example);
//            if(!list.isEmpty()){
//                for (com.siam.package_user.entity.MemberBillingRecord dbMemberBillingRecord : list) {
//                    Integer type = null;
//                    String message = "";
//                    if(dbMemberBillingRecord.getType().equals(com.siam.package_user.entity.MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION)){
//                        type = com.siam.package_user.entity.MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION_RETURN;
//                        message = "订单退款-一级邀请人佣金奖励退回";
//                    }else if(dbMemberBillingRecord.getType().equals(com.siam.package_user.entity.MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION)){
//                        type = com.siam.package_user.entity.MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION_RETURN;
//                        message = "订单退款-二级邀请人佣金奖励退回";
//                    }else if(dbMemberBillingRecord.getType().equals(com.siam.package_user.entity.MemberBillingRecord.TYPE_OWN_COMMISSION)){
//                        type = com.siam.package_user.entity.MemberBillingRecord.TYPE_OWN_COMMISSION_RETURN;
//                        message = "订单退款-下单用户佣金奖励退回";
//                    }
//
//                    com.siam.package_user.entity.Member dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
//                    //增加用户的邀请新用户注册奖励金额
//                    BigDecimal updateUnreceivedInviteRewardAmount = dbMember.getUnreceivedInviteRewardAmount().subtract(dbMemberBillingRecord.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
//
//                    com.siam.package_user.entity.Member updateMember = new com.siam.package_user.entity.Member();
//                    updateMember.setId(dbMember.getId());
//                    updateMember.setUnreceivedInviteRewardAmount(updateUnreceivedInviteRewardAmount);
//                    updateMember.setUpdateTime(new Date());
//                    memberService.updateByPrimaryKeySelective(updateMember);
//                    dbMember = memberService.selectByPrimaryKey(dbMemberBillingRecord.getMemberId());
//
//                    //增加用户账单记录
//                    com.siam.package_user.entity.MemberBillingRecord memberBillingRecord = new com.siam.package_user.entity.MemberBillingRecord();
//                    memberBillingRecord.setMemberId(dbMember.getId());
//                    memberBillingRecord.setType(type);
//                    memberBillingRecord.setOperateType(com.siam.package_user.entity.MemberBillingRecord.OPERATE_TYPE_SUB);
//                    memberBillingRecord.setCoinType(com.siam.package_user.entity.MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT);
//                    memberBillingRecord.setNumber(dbMemberBillingRecord.getNumber());
//                    memberBillingRecord.setMessage(message);
//                    memberBillingRecord.setPointsMallOrderId(dbPointsMallOrder.getId());
//                    memberBillingRecord.setCreateTime(new Date());
//                    memberBillingRecordService.insertSelective(memberBillingRecord);
//
//                    //之前的账单记录标注已退回
//                    com.siam.package_user.entity.MemberBillingRecord updateMemberBillingRecord = new com.siam.package_user.entity.MemberBillingRecord();
//                    updateMemberBillingRecord.setId(dbMemberBillingRecord.getId());
//                    updateMemberBillingRecord.setIsReturn(true);
//                    memberBillingRecordService.updateByPrimaryKeySelective(updateMemberBillingRecord);
//                }
//            }
//        }
//    }
//}