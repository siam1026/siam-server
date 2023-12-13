package com.siam.system.modular.package_order.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderLogistics;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderLogisticsExample;

import java.util.List;

public interface PointsMallOrderLogisticsService {
    int countByExample(PointsMallOrderLogisticsExample example);

    void deleteByPrimaryKey(Integer id);

    void deleteByExample(PointsMallOrderLogisticsExample example);

    void insertSelective(PointsMallOrderLogistics record);

    List<PointsMallOrderLogistics> selectByExample(PointsMallOrderLogisticsExample example);

    PointsMallOrderLogistics selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallOrderLogistics record, PointsMallOrderLogisticsExample example);

    void updateByPrimaryKeySelective(PointsMallOrderLogistics record);

    Page getListByPage(int pageNo, int pageSize, PointsMallOrderLogistics orderLogistics);

    Boolean updateOrderLogisticsInfo(Long orderId, String logisticsNo);
}