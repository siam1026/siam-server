package com.siam.system.modular.package_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.OrderDetail;
import com.siam.system.modular.package_order.model.example.OrderDetailExample;

import java.util.List;

public interface OrderDetailService {
    int countByExample(OrderDetailExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(OrderDetail record);

    List<OrderDetail> selectByExample(OrderDetailExample example);

    OrderDetail selectByPrimaryKey(Integer id);

    void updateByExampleSelective(OrderDetail record, OrderDetailExample example);

    void updateByPrimaryKeySelective(OrderDetail record);

    Page getListByPage(int pageNo, int pageSize, OrderDetail orderDetail);

    List<OrderDetail> selectByOrderId(Integer orderId);

}