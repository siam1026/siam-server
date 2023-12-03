package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.mapper.OrderRefundGoodsMapper;
import com.siam.system.modular.package_order.mapper.OrderMapper;
import com.siam.system.modular.package_order.service.OrderRefundGoodsService;
import com.siam.system.modular.package_order.entity.OrderRefundGoods;
import com.siam.system.modular.package_order.model.example.OrderRefundGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderRefundGoodsServiceImpl implements OrderRefundGoodsService {
    @Autowired
    private OrderRefundGoodsMapper orderRefundGoodsMapper;
    @Autowired
    private OrderMapper orderMapper;

    public int countByExample(OrderRefundGoodsExample example){
        return orderRefundGoodsMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderRefundGoodsMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(OrderRefundGoods record){
        orderRefundGoodsMapper.insertSelective(record);
    }

    public List<OrderRefundGoods> selectByExample(OrderRefundGoodsExample example){
        return orderRefundGoodsMapper.selectByExample(example);
    }

    public OrderRefundGoods selectByPrimaryKey(Integer id){
        return orderRefundGoodsMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(OrderRefundGoods record, OrderRefundGoodsExample example){
        orderRefundGoodsMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(OrderRefundGoods record){
        orderRefundGoodsMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, OrderRefundGoods orderRefundGoods) {
        Page<Map<String, Object>> page = orderRefundGoodsMapper.getListByPage(new Page(pageNo, pageSize), orderRefundGoods);
        return page;
    }

    @Override
    public List<OrderRefundGoods> selectByOrderRefundId(Integer orderRefundId) {
        return orderRefundGoodsMapper.selectByOrderRefundId(orderRefundId);
    }
}