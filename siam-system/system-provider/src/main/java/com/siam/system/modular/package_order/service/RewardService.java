package com.siam.system.modular.package_order.service;

import com.siam.system.modular.package_order.model.param.OrderParam;

import java.math.BigDecimal;

public interface RewardService {

    /**
     * 给邀请人发放佣金
     *
     * @return
     */
    void giveInviterReward(Integer inviterId, BigDecimal commissionAmount, Integer type, String message, Integer orderId);

    String selectCommissionReward(OrderParam param);
}