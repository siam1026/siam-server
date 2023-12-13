package com.siam.system.modular.package_order.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefundProcess;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundProcessExample;

import java.util.List;

public interface PointsMallOrderRefundProcessService {
    int countByExample(PointsMallOrderRefundProcessExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallOrderRefundProcess record);

    List<PointsMallOrderRefundProcess> selectByExample(PointsMallOrderRefundProcessExample example);

    PointsMallOrderRefundProcess selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallOrderRefundProcess record, PointsMallOrderRefundProcessExample example);

    void updateByPrimaryKeySelective(PointsMallOrderRefundProcess record);

    Page getListByPage(int pageNo, int pageSize, PointsMallOrderRefundProcess orderRefundProcess);

    List<PointsMallOrderRefundProcess> selectByPointsMallOrderRefundId(Integer orderRefundId);
}