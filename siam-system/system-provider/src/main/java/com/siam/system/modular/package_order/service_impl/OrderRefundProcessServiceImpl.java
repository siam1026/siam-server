package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.mapper.OrderRefundProcessMapper;
import com.siam.system.modular.package_order.mapper.OrderMapper;
import com.siam.system.modular.package_order.service.OrderRefundProcessService;
import com.siam.system.modular.package_order.entity.OrderRefundProcess;
import com.siam.system.modular.package_order.model.example.OrderRefundProcessExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderRefundProcessServiceImpl implements OrderRefundProcessService {
    @Autowired
    private OrderRefundProcessMapper orderRefundProcessMapper;
    @Autowired
    private OrderMapper orderMapper;

    public int countByExample(OrderRefundProcessExample example){
        return orderRefundProcessMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderRefundProcessMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(OrderRefundProcess record){
        orderRefundProcessMapper.insertSelective(record);
    }

    public List<OrderRefundProcess> selectByExample(OrderRefundProcessExample example){
        return orderRefundProcessMapper.selectByExample(example);
    }

    public OrderRefundProcess selectByPrimaryKey(Integer id){
        return orderRefundProcessMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(OrderRefundProcess record, OrderRefundProcessExample example){
        orderRefundProcessMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(OrderRefundProcess record){
        orderRefundProcessMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, OrderRefundProcess orderRefundProcess) {
        Page<Map<String, Object>> page = orderRefundProcessMapper.getListByPage(new Page(pageNo, pageSize), orderRefundProcess);
        return page;
    }

    @Override
    public List<OrderRefundProcess> selectByOrderRefundId(Integer orderRefundId) {
        return orderRefundProcessMapper.selectByOrderRefundId(orderRefundId);
    }
}