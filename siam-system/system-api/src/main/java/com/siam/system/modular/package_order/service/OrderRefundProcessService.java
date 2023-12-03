package com.siam.system.modular.package_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.OrderRefundProcess;
import com.siam.system.modular.package_order.model.example.OrderRefundProcessExample;

import java.util.List;

public interface OrderRefundProcessService {
    int countByExample(OrderRefundProcessExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(OrderRefundProcess record);

    List<OrderRefundProcess> selectByExample(OrderRefundProcessExample example);

    OrderRefundProcess selectByPrimaryKey(Integer id);

    void updateByExampleSelective(OrderRefundProcess record, OrderRefundProcessExample example);

    void updateByPrimaryKeySelective(OrderRefundProcess record);

    Page getListByPage(int pageNo, int pageSize, OrderRefundProcess orderRefundProcess);

    List<OrderRefundProcess> selectByOrderRefundId(Integer orderRefundId);
}