package com.siam.system.modular.package_order.service_impl.internal;

import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.util.DateUtilsExtend;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.system.modular.package_goods.service.internal.VipRechargeRecordService;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MemberInviteRelationService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;
import com.siam.system.modular.package_order.mapper.OrderMapper;
import com.siam.system.modular.package_order.model.example.OrderExample;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderExample;
import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.model.param.internal.PointsMallOrderParam;
import com.siam.system.modular.package_order.service.RewardService;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SettingService settingService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberInviteRelationService memberInviteRelationService;

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService pointsMallOrderService;

    @Autowired
    private VipRechargeRecordService vipRechargeRecordService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    /**
     * 给邀请人发放佣金
     *
     * @return
     */
    public void giveInviterReward(Integer inviterId, BigDecimal commissionAmount, Integer type, String message, Integer orderId){
        //规则：如果邀请人不是会员，则不给与佣金奖励；如果邀请人在近30天内无消费，则不给予佣金奖励，不对其它邀请人造成影响；
        //1、开通会员后的第一笔订单无需判断近30天是否有消费这个条件，直接给与佣金奖励
        //2、近30天是否有消费这个条件 是指用户下过单(无论什么状态)--只要支付成功过的都算
        //3、近30天是否有消费这个条件 是指外卖系统、积分商城、(会员充值) 任意有一笔消费即可
        //4、近30天是否有消费这个条件 需要排除当前支付的订单
        Member dbMember = memberService.selectByPrimaryKey(inviterId);
        if(!dbMember.getType().equals(Quantity.INT_2)){
            log.info("\n\nid为" + inviterId + "的邀请人不是会员，不给予佣金奖励");
            return;
        }

        //判断当前订单是否为开通会员后的第一笔订单，如果是，则直接给与佣金奖励
        //查询当前用户最近一笔支付成功的会员充值记录
        VipRechargeRecord vipRechargeRecord = vipRechargeRecordService.selectLastestPaid(dbMember.getId());
        List excludeStatusList = new ArrayList();
        excludeStatusList.add(1);
        excludeStatusList.add(10);
        //查询外卖系统的符合订单数
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIdNotEqualTo(orderId).andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
        Integer count = orderMapper.countByExample(orderExample);
        //查询积分商城的符合订单数
        PointsMallOrderExample pointsMallOrderExample = new PointsMallOrderExample();
        pointsMallOrderExample.createCriteria().andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
        int count_pointsMall = pointsMallOrderService.countByExample(pointsMallOrderExample);
        int total_count = count + count_pointsMall;
        if(total_count > 0){
            //不是第一笔订单，进行近30天是否有消费的条件判断
            //近30天外卖系统支付成功的订单数
            OrderParam param = new OrderParam();
            param.setMemberId(inviterId);
            param.setExcludeOrderId(orderId);
            param.setStartTime(DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30));
            param.setEndTime(DateUtilsExtend.getDayEnd());
            count = this.orderMapper.selectCountPaid(param);
            count = count!=null ? count : 0;

            //近30天积分商城支付成功的订单数
            PointsMallOrderParam pointsMallOrder = new PointsMallOrderParam();
            pointsMallOrder.setMemberId(inviterId);
            count_pointsMall = this.pointsMallOrderService.selectCountPaid(pointsMallOrder, DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30), DateUtilsExtend.getDayEnd());

            total_count = count + count_pointsMall;
            if(total_count == 0){
                //TODO-可以建立一张表把这些信息存下来的
                log.info("\n\nid为" + inviterId + "的邀请人在近30天内无消费，不给予佣金奖励");
                return;
            }
        }

        //增加用户的邀请新用户注册奖励金额
        BigDecimal updateUnreceivedInviteRewardAmount = dbMember.getUnreceivedInviteRewardAmount().add(commissionAmount).setScale(2, BigDecimal.ROUND_HALF_UP);

        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setUnreceivedInviteRewardAmount(updateUnreceivedInviteRewardAmount);
        updateMember.setUpdateTime(new Date());
        memberService.updateByPrimaryKeySelective(updateMember);
        dbMember = memberService.selectByPrimaryKey(inviterId);

        //增加用户账单记录
        MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
        memberBillingRecord.setMemberId(inviterId);
        memberBillingRecord.setType(type);
        memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_UNRECEIVED_INVITE_REWARD_AMOUNT);
        memberBillingRecord.setNumber(commissionAmount);
        memberBillingRecord.setMessage(message);
        memberBillingRecord.setOrderId(orderId);
        memberBillingRecord.setCreateTime(new Date());
        memberBillingRecordService.insertSelective(memberBillingRecord);
    }

    @Override
    public String selectCommissionReward(OrderParam param) {
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        String text = null;
        BigDecimal commissionAmount = BigDecimal.ZERO;

        Setting setting=settingService.selectCurrent();
        //平台邀请佣金
        BigDecimal inviteeConsumeCommissionPercent = setting.getInviteeConsumeCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal totalCommissionAmount = param.getActualPrice().multiply(inviteeConsumeCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        BigDecimal inviterCommissionPercent;
        String message;
        //给下单用户的邀请人发放佣金
        Map<String, Integer> inviterMap = memberInviteRelationService.selectInviter(loginMember.getId());
        if(inviterMap.containsKey("secondLevelInviter")){
            //有2个上级邀请人
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getCasethreeOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);

            //发放一级邀请人佣金奖励
            inviterCommissionPercent = setting.getCasethreeFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "一级邀请人佣金奖励，来自" + loginMember.getMobile()+"-"+loginMember.getUsername();
            giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message, param.getId());

            //发放二级邀请人佣金奖励
            inviterCommissionPercent = setting.getCasethreeSecondLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "二级邀请人佣金，来自" + loginMember.getMobile()+"-"+loginMember.getUsername();
            giveInviterReward(inviterMap.get("secondLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_SECOND_LEVEL_INVITER_COMMISSION, message, param.getId());

        }else if(inviterMap.containsKey("firstLevelInviter")){
            //有1个上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getCasetwoOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);

            //发放一级邀请人佣金奖励
            inviterCommissionPercent = setting.getCasetwoFirstLevelInviterCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
            message = "一级邀请人佣金奖励，来自" + loginMember.getMobile()+"-"+loginMember.getUsername();
            giveInviterReward(inviterMap.get("firstLevelInviter"), commissionAmount, MemberBillingRecord.TYPE_FIRST_LEVEL_INVITER_COMMISSION, message, param.getId());

        }else if(inviterMap.isEmpty()){
            //无上级邀请人时
            //发放下单用户佣金奖励
            inviterCommissionPercent = setting.getCaseoneOwnCommission().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
            commissionAmount = totalCommissionAmount.multiply(inviterCommissionPercent).setScale(Quantity.INT_2, BigDecimal.ROUND_HALF_UP);
        }

        //规则：如果邀请人不是会员，则不给与佣金奖励；如果邀请人在近30天内无消费，则不给予佣金奖励，不对其它邀请人造成影响；
        //1、开通会员后的第一笔订单无需判断近30天是否有消费这个条件，直接给与佣金奖励
        //2、近30天是否有消费这个条件 是指用户下过单(无论什么状态)--只要支付成功过的都算
        //3、近30天是否有消费这个条件 是指外卖系统、积分商城、(会员充值) 任意有一笔消费即可
        //4、近30天是否有消费这个条件 需要排除当前支付的订单
        Member dbMember = memberService.selectByPrimaryKey(loginMember.getId());
        if(!dbMember.getType().equals(Quantity.INT_2)){
            text = "您现在不是会员，充值会员后，您可得到"+ commissionAmount +"元返利";
        }else{
            //判断当前订单是否为开通会员后的第一笔订单，如果是，则直接给与佣金奖励
            //查询当前用户最近一笔支付成功的会员充值记录
            VipRechargeRecord vipRechargeRecord = vipRechargeRecordService.selectLastestPaid(dbMember.getId());
            List excludeStatusList = new ArrayList();
            excludeStatusList.add(1);
            excludeStatusList.add(10);
            //查询外卖系统的符合订单数
            OrderExample orderExample = new OrderExample();
            orderExample.createCriteria().andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
            int count = orderMapper.countByExample(orderExample);
            //查询积分商城的符合订单数
            PointsMallOrderExample pointsMallOrderExample = new PointsMallOrderExample();
            pointsMallOrderExample.createCriteria().andCreateTimeGreaterThan(vipRechargeRecord.getCreateTime()).andStatusNotIn(excludeStatusList);
            int count_pointsMall = pointsMallOrderService.countByExample(pointsMallOrderExample);
            int total_count = count + count_pointsMall;
            if(total_count > 0){
                //不是第一笔订单，进行近30天是否有消费的条件判断
                //近30天外卖系统支付成功的订单数
                OrderParam orderParam = new OrderParam();
                orderParam.setMemberId(dbMember.getId());
                orderParam.setStartTime(DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30));
                orderParam.setEndTime(DateUtilsExtend.getDayEnd());
                count = this.orderMapper.selectCountPaid(orderParam);
                //近30天积分商城支付成功的订单数
                PointsMallOrderParam pointsMallOrder = new PointsMallOrderParam();
                pointsMallOrder.setMemberId(dbMember.getId());
                count_pointsMall = this.pointsMallOrderService.selectCountPaid(pointsMallOrder, DateUtilsExtend.getFrontDay(DateUtilsExtend.getDayEnd(), 30), DateUtilsExtend.getDayEnd());

                total_count = count + count_pointsMall;
                if(total_count == 0){
                    //TODO-可以建立一张表把这些信息存下来的
                    /*text = "您在近30天内无消费，该笔订单完成后再次消费，您可得到"+ commissionAmount +"元返利";*/
                }else{
                    text = "该笔订单完成后，您可得到" + commissionAmount + "元返利";
                }
            }else{
                text = "该笔订单完成后，您可得到" + commissionAmount + "元返利";
            }
        }
        return text;
    }
}