package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.internal.VipRechargeRecord;

import java.util.Map;

/**
 *  jianyang
 */
public interface VipRechargeRecordService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(VipRechargeRecord vipRechargeRecord);

    VipRechargeRecord selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(VipRechargeRecord vipRechargeRecord);

    /**
     * 条件查询集合
     * @param vipRechargeRecord 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page getListByPage(int pageNo, int pageSize, VipRechargeRecord vipRechargeRecord);

    /**
     * 条件查询集合
     * @param vipRechargeRecord 查询条件对象
     * @param pageNo 分页所在页
     * @param pageSize 单页数据量大小
     * @return
     */
    Page getListByPageJoinMember(int pageNo, int pageSize, VipRechargeRecord vipRechargeRecord);

    Map<String, Object> statisticalAmount(VipRechargeRecord vipRechargeRecord);

    /**
     * 普通订单回调
     * 支付成功回调时修改订单状态，并且触发一系列关联操作
     * @param outTradeNo 商户单号
     */
    void updateByPayNotify(String outTradeNo);

    VipRechargeRecord selectLastestPaid(Integer memberId);
}
