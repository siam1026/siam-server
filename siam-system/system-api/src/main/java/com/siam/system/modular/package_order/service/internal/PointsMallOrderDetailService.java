package com.siam.system.modular.package_order.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderDetail;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderDetailExample;

import java.util.List;

public interface PointsMallOrderDetailService {
    int countByExample(PointsMallOrderDetailExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallOrderDetail record);

    List<PointsMallOrderDetail> selectByExample(PointsMallOrderDetailExample example);

    PointsMallOrderDetail selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallOrderDetail record, PointsMallOrderDetailExample example);

    void updateByPrimaryKeySelective(PointsMallOrderDetail record);

    Page getListByPage(int pageNo, int pageSize, PointsMallOrderDetail orderDetail);

    List<PointsMallOrderDetail> selectByPointsMallOrderId(Long orderId);

}