package com.siam.system.modular.package_order.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderMapper;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderRefundProcessMapper;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderRefundProcessService;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefundProcess;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundProcessExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PointsMallOrderRefundProcessServiceImpl implements PointsMallOrderRefundProcessService {

    @Autowired
    private PointsMallOrderRefundProcessMapper orderRefundProcessMapper;

    @Autowired
    private PointsMallOrderMapper orderMapper;

    public int countByExample(PointsMallOrderRefundProcessExample example){
        return orderRefundProcessMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderRefundProcessMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(PointsMallOrderRefundProcess record){
        orderRefundProcessMapper.insertSelective(record);
    }

    public List<PointsMallOrderRefundProcess> selectByExample(PointsMallOrderRefundProcessExample example){
        return orderRefundProcessMapper.selectByExample(example);
    }

    public PointsMallOrderRefundProcess selectByPrimaryKey(Integer id){
        return orderRefundProcessMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(PointsMallOrderRefundProcess record, PointsMallOrderRefundProcessExample example){
        orderRefundProcessMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallOrderRefundProcess record){
        orderRefundProcessMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, PointsMallOrderRefundProcess orderRefundProcess) {
        Page<Map<String, Object>> page = orderRefundProcessMapper.getListByPage(new Page(pageNo, pageSize), orderRefundProcess);
        return page;
    }

    @Override
    public List<PointsMallOrderRefundProcess> selectByPointsMallOrderRefundId(Integer orderRefundId) {
        return orderRefundProcessMapper.selectByPointsMallOrderRefundId(orderRefundId);
    }
}