package com.siam.system.modular.package_order.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefund;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundExample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface PointsMallOrderRefundService {
    int countByExample(PointsMallOrderRefundExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallOrderRefund record);

    List<PointsMallOrderRefund> selectByExample(PointsMallOrderRefundExample example);

    PointsMallOrderRefund selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallOrderRefund record, PointsMallOrderRefundExample example);

    void updateByPrimaryKeySelective(PointsMallOrderRefund record);

    Page getListByPage(int pageNo, int pageSize, PointsMallOrderRefund orderRefund);

    PointsMallOrderRefund selectByPointsMallOrderId(Long orderId);

    BigDecimal selectSumRefundAmount(PointsMallOrderRefund orderRefund, Date startTime, Date endTime);

    BigDecimal selectSumRefundAmountByPlatformCoin(PointsMallOrderRefund orderRefund, Date startTime, Date endTime);
}