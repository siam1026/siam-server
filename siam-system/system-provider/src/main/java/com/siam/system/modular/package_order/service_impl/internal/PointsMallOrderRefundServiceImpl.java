package com.siam.system.modular.package_order.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderMapper;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderRefundMapper;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderRefundService;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefund;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallOrderRefundServiceImpl implements PointsMallOrderRefundService {

    @Autowired
    private PointsMallOrderRefundMapper orderRefundMapper;

    @Autowired
    private PointsMallOrderMapper orderMapper;

    public int countByExample(PointsMallOrderRefundExample example){
        return orderRefundMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderRefundMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(PointsMallOrderRefund record){
        orderRefundMapper.insertSelective(record);
    }

    public List<PointsMallOrderRefund> selectByExample(PointsMallOrderRefundExample example){
        return orderRefundMapper.selectByExample(example);
    }

    public PointsMallOrderRefund selectByPrimaryKey(Integer id){
        return orderRefundMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(PointsMallOrderRefund record, PointsMallOrderRefundExample example){
        orderRefundMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallOrderRefund record){
        orderRefundMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, PointsMallOrderRefund orderRefund) {
        Page<Map<String, Object>> page = orderRefundMapper.getListByPage(new Page(pageNo, pageSize), orderRefund);
        return page;
    }

    @Override
    public PointsMallOrderRefund selectByPointsMallOrderId(Long orderId) {
        return orderRefundMapper.selectByPointsMallOrderId(orderId);
    }

    @Override
    public BigDecimal selectSumRefundAmount(PointsMallOrderRefund orderRefund, Date startTime, Date endTime) {
        return orderRefundMapper.selectSumRefundAmount(orderRefund, startTime, endTime);
    }

    @Override
    public BigDecimal selectSumRefundAmountByPlatformCoin(PointsMallOrderRefund orderRefund, Date startTime, Date endTime) {
        return orderRefundMapper.selectSumRefundAmountByPlatformCoin(orderRefund, startTime, endTime);
    }
}