package com.siam.system.modular.package_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.OrderRefundGoods;
import com.siam.system.modular.package_order.model.example.OrderRefundGoodsExample;

import java.util.List;

public interface OrderRefundGoodsService {
    int countByExample(OrderRefundGoodsExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(OrderRefundGoods record);

    List<OrderRefundGoods> selectByExample(OrderRefundGoodsExample example);

    OrderRefundGoods selectByPrimaryKey(Integer id);

    void updateByExampleSelective(OrderRefundGoods record, OrderRefundGoodsExample example);

    void updateByPrimaryKeySelective(OrderRefundGoods record);

    Page getListByPage(int pageNo, int pageSize, OrderRefundGoods orderRefundGoods);

    List<OrderRefundGoods> selectByOrderRefundId(Integer orderRefundId);
}