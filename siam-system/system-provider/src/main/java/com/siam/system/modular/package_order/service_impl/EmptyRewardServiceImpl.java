package com.siam.system.modular.package_order.service_impl;

import com.siam.system.modular.package_order.model.param.OrderParam;
import com.siam.system.modular.package_order.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class EmptyRewardServiceImpl implements RewardService {

    /**
     * 给邀请人发放佣金
     *
     * @return
     */
    public void giveInviterReward(Integer inviterId, BigDecimal commissionAmount, Integer type, String message, Integer orderId){
        // TODO - 开源版本暂不支持此功能，点击(http://xxxxxx)获取社区学习版
    }

    @Override
    public String selectCommissionReward(OrderParam param) {
        return "开源版本暂不支持此功能，点击(http://xxxxxx)获取社区学习版";
    }
}