package com.siam.system.modular.package_order.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.mapper.OrderMapper;
import com.siam.system.modular.package_order.entity.OrderDetail;
import com.siam.system.modular.package_order.model.example.OrderDetailExample;
import com.siam.system.modular.package_order.mapper.OrderDetailMapper;
import com.siam.system.modular.package_order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;

    public int countByExample(OrderDetailExample example){
        return orderDetailMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderDetailMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(OrderDetail record){
        orderDetailMapper.insertSelective(record);
    }

    public List<OrderDetail> selectByExample(OrderDetailExample example){
        return orderDetailMapper.selectByExample(example);
    }

    public OrderDetail selectByPrimaryKey(Integer id){
        return orderDetailMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(OrderDetail record, OrderDetailExample example){
        orderDetailMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(OrderDetail record){
        orderDetailMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, OrderDetail orderDetail) {
        Page<Map<String, Object>> page = orderDetailMapper.getListByPage(new Page(pageNo, pageSize), orderDetail);
        return page;
    }

    @Override
    public List<OrderDetail> selectByOrderId(Integer orderId) {
        return orderDetailMapper.selectByOrderId(orderId);
    }


}