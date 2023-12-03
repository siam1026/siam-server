package com.siam.system.modular.package_user.service_impl;

import com.siam.system.modular.package_user.entity.MerchantToken;
import com.siam.system.modular.package_user.mapper.MerchantTokenMapper;
import com.siam.system.modular.package_user.model.example.MerchantTokenExample;
import com.siam.system.modular.package_user.service.MerchantTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantTokenServiceImpl implements MerchantTokenService {

    @Autowired
    private MerchantTokenMapper merchantTokenMapper;

    public int countByExample(MerchantTokenExample example){
        return merchantTokenMapper.countByExample(example);
    }

    public void deleteByExample(MerchantTokenExample example){
        merchantTokenMapper.deleteByExample(example);
    }

    public void insertSelective(MerchantToken record){
        merchantTokenMapper.insertSelective(record);
    }

    public List<MerchantToken> selectByExample(MerchantTokenExample example){
        return merchantTokenMapper.selectByExample(example);
    }

    public MerchantToken selectByPrimaryKey(Integer id){
        return merchantTokenMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(MerchantToken record, MerchantTokenExample example){
        merchantTokenMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(MerchantToken record){
        merchantTokenMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void deleteByToken(String token) {
        MerchantTokenExample example = new MerchantTokenExample();
        MerchantTokenExample.Criteria criteria = example.createCriteria();
        criteria.andTokenEqualTo(token);
        merchantTokenMapper.deleteByExample(example);
    }

    @Override
    public MerchantToken getLoginMerchantInfo(String token){
        return merchantTokenMapper.getLoginMerchantInfo(token);
    }
}