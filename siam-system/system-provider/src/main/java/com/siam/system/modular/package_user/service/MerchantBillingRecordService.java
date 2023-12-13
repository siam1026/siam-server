package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MerchantBillingRecord;
import com.siam.system.modular.package_user.model.param.MerchantBillingRecordParam;

import java.util.Map;

/**
 *  暹罗
 */
public interface MerchantBillingRecordService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(MerchantBillingRecord merchantBillingRecord);

    MerchantBillingRecord selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(MerchantBillingRecord merchantBillingRecord);

    Page getListByPage(MerchantBillingRecordParam param);

    Page getListByPageJoinShop(MerchantBillingRecordParam param);

    Map<String, Object> statisticalAmount(MerchantBillingRecordParam param);
}