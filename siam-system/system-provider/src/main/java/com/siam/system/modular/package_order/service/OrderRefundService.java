package com.siam.system.modular.package_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.OrderRefund;
import com.siam.system.modular.package_order.model.example.OrderRefundExample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderRefundService {
    int countByExample(OrderRefundExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(OrderRefund record);

    List<OrderRefund> selectByExample(OrderRefundExample example);

    OrderRefund selectByPrimaryKey(Integer id);

    void updateByExampleSelective(OrderRefund record, OrderRefundExample example);

    void updateByPrimaryKeySelective(OrderRefund record);

    Page getListByPage(int pageNo, int pageSize, OrderRefund orderRefund);

    OrderRefund selectByOrderId(Integer orderId);

    Map<String, Object> selectSumField(OrderRefund orderRefund);

    BigDecimal selectSumRefundAmount(OrderRefund orderRefund, Date startTime, Date endTime);

    BigDecimal selectSumRefundAmountByPlatformCoin(OrderRefund orderRefund, Date startTime, Date endTime);
}