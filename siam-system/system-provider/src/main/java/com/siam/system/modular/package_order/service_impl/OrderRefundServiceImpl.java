package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.mapper.OrderRefundMapper;
import com.siam.system.modular.package_order.mapper.OrderMapper;
import com.siam.system.modular.package_order.service.OrderRefundService;
import com.siam.system.modular.package_order.entity.OrderRefund;
import com.siam.system.modular.package_order.model.example.OrderRefundExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderRefundServiceImpl implements OrderRefundService {
    @Autowired
    private OrderRefundMapper orderRefundMapper;
    @Autowired
    private OrderMapper orderMapper;

    public int countByExample(OrderRefundExample example){
        return orderRefundMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderRefundMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(OrderRefund record){
        orderRefundMapper.insertSelective(record);
    }

    public List<OrderRefund> selectByExample(OrderRefundExample example){
        return orderRefundMapper.selectByExample(example);
    }

    public OrderRefund selectByPrimaryKey(Integer id){
        return orderRefundMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(OrderRefund record, OrderRefundExample example){
        orderRefundMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(OrderRefund record){
        orderRefundMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, OrderRefund orderRefund) {
        Page<Map<String, Object>> page = orderRefundMapper.getListByPage(new Page(pageNo, pageSize), orderRefund);
        return page;
    }

    @Override
    public OrderRefund selectByOrderId(Integer orderId) {
        return orderRefundMapper.selectByOrderId(orderId);
    }

    @Override
    public Map<String, Object> selectSumField(OrderRefund orderRefund) {
        return this.orderRefundMapper.selectSumField(orderRefund);
    }

    @Override
    public BigDecimal selectSumRefundAmount(OrderRefund orderRefund, Date startTime, Date endTime) {
        return this.orderRefundMapper.selectSumRefundAmount(orderRefund, startTime, endTime);
    }

    @Override
    public BigDecimal selectSumRefundAmountByPlatformCoin(OrderRefund orderRefund, Date startTime, Date endTime) {
        return orderRefundMapper.selectSumRefundAmountByPlatformCoin(orderRefund, startTime, endTime);
    }
}