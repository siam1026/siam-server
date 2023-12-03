package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.modular.package_user.entity.MerchantBillingRecord;
import com.siam.system.modular.package_user.mapper.MerchantBillingRecordMapper;
import com.siam.system.modular.package_user.model.param.MerchantBillingRecordParam;
import com.siam.system.modular.package_user.service.MerchantBillingRecordService;
import com.siam.system.modular.package_user.service.MerchantService;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class MerchantBillingRecordServiceImpl implements MerchantBillingRecordService {

    @Autowired
    private MerchantBillingRecordMapper merchantBillingRecordMapper;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        merchantBillingRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(MerchantBillingRecord merchantBillingRecord) {
        merchantBillingRecordMapper.insertSelective(merchantBillingRecord);
    }

    @Override
    public MerchantBillingRecord selectByPrimaryKey(Integer id) {
        return merchantBillingRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(MerchantBillingRecord merchantBillingRecord) {
        merchantBillingRecordMapper.updateByPrimaryKeySelective(merchantBillingRecord);
    }

    @Override
    public Page getListByPage(MerchantBillingRecordParam param) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        param.setMerchantId(loginMerchant.getId());
        Page<Map<String, Object>> page = merchantBillingRecordMapper.getListByPage(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Page getListByPageJoinShop(MerchantBillingRecordParam param) {
        Page<Map<String, Object>> page = merchantBillingRecordMapper.getListByPageJoinShop(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Map<String, Object> statisticalAmount(MerchantBillingRecordParam param) {
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        if(loginMerchant != null){
            param.setMerchantId(loginMerchant.getId());
        }

        BigDecimal incomeAmount = merchantBillingRecordMapper.statisticalAmountByIncome(param);
        BigDecimal expendAmount = merchantBillingRecordMapper.statisticalAmountByExpend(param);

        Map<String, Object> map = new HashMap<>();
        map.put("incomeAmount", incomeAmount);
        map.put("expendAmount", expendAmount);
        return map;
    }
}