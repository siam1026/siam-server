package com.siam.system.modular.package_goods.service_impl;

import com.siam.system.modular.package_goods.mapper.WxPublicPlatformSubscribeUserMapper;
import com.siam.system.modular.package_goods.model.example.WxPublicPlatformSubscribeUserExample;
import com.siam.system.modular.package_goods.service.WxPublicPlatformSubscribeUserService;
import com.siam.package_weixin_basic.config.WxPublicPlatformSubscribeUser;
import com.siam.system.modular.package_goods.model.example.WxPublicPlatformSubscribeUserExample;
import com.siam.system.modular.package_goods.mapper.WxPublicPlatformSubscribeUserMapper;
import com.siam.system.modular.package_goods.service.WxPublicPlatformSubscribeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxPublicPlatformSubscribeUserServiceImpl implements WxPublicPlatformSubscribeUserService {

    @Autowired
    private WxPublicPlatformSubscribeUserMapper wxPublicPlatformSubscribeUserMapper;

    @Override
    public int countByExample(WxPublicPlatformSubscribeUserExample example) {
        return wxPublicPlatformSubscribeUserMapper.countByExample(example);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        wxPublicPlatformSubscribeUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(WxPublicPlatformSubscribeUser wxPublicPlatformSubscribeUser) {
        wxPublicPlatformSubscribeUserMapper.insertSelective(wxPublicPlatformSubscribeUser);
    }

    @Override
    public WxPublicPlatformSubscribeUser selectByPrimaryKey(Integer id) {
        return wxPublicPlatformSubscribeUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(WxPublicPlatformSubscribeUser wxPublicPlatformSubscribeUser) {
        wxPublicPlatformSubscribeUserMapper.updateByPrimaryKeySelective(wxPublicPlatformSubscribeUser);
    }

    @Override
    public List<WxPublicPlatformSubscribeUser> selectByExample(WxPublicPlatformSubscribeUserExample example) {
        return wxPublicPlatformSubscribeUserMapper.selectByExample(example);
    }
}