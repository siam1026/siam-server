package com.siam.system.modular.package_order.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefundGoods;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderRefundGoodsExample;

import java.util.List;

public interface PointsMallOrderRefundGoodsService {
    int countByExample(PointsMallOrderRefundGoodsExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(PointsMallOrderRefundGoods record);

    List<PointsMallOrderRefundGoods> selectByExample(PointsMallOrderRefundGoodsExample example);

    PointsMallOrderRefundGoods selectByPrimaryKey(Integer id);

    void updateByExampleSelective(PointsMallOrderRefundGoods record, PointsMallOrderRefundGoodsExample example);

    void updateByPrimaryKeySelective(PointsMallOrderRefundGoods record);

    Page getListByPage(int pageNo, int pageSize, PointsMallOrderRefundGoods orderRefundGoods);

    List<PointsMallOrderRefundGoods> selectByPointsMallOrderRefundId(Integer orderRefundId);
}