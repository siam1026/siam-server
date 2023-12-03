package com.siam.system.modular.package_user.model.dto;

import com.siam.system.modular.package_user.entity.Member;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberDto extends Member {

    //今日获得余额
    private BigDecimal totayGainBalance;

    //今日获得积分
    private BigDecimal totayGainPoints;

    //昨日收益-邀请新用户注册奖励金额/佣金
    private BigDecimal yesterdayGainInviteRewardAmount;

    //优惠券数量
    private Integer couponsNumber;

}