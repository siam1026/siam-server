package com.siam.system.modular.package_goods.service;

import com.siam.system.modular.package_goods.model.example.WxPublicPlatformSubscribeUserExample;
import com.siam.package_weixin_basic.config.WxPublicPlatformSubscribeUser;

import java.util.List;

/**
 *  暹罗
 */
public interface WxPublicPlatformSubscribeUserService {

    int countByExample(WxPublicPlatformSubscribeUserExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(WxPublicPlatformSubscribeUser wxPublicPlatformSubscribeUser);

    WxPublicPlatformSubscribeUser selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(WxPublicPlatformSubscribeUser wxPublicPlatformSubscribeUser);

    List<WxPublicPlatformSubscribeUser> selectByExample(WxPublicPlatformSubscribeUserExample example);

}
