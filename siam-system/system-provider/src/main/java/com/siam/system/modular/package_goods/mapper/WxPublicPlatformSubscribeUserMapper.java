package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.package_weixin_basic.config.WxPublicPlatformSubscribeUser;
import com.siam.system.modular.package_goods.model.example.WxPublicPlatformSubscribeUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxPublicPlatformSubscribeUserMapper extends BaseMapper<WxPublicPlatformSubscribeUser> {
    int countByExample(WxPublicPlatformSubscribeUserExample example);

    int deleteByExample(WxPublicPlatformSubscribeUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(WxPublicPlatformSubscribeUser record);

    List<WxPublicPlatformSubscribeUser> selectByExample(WxPublicPlatformSubscribeUserExample example);

    WxPublicPlatformSubscribeUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WxPublicPlatformSubscribeUser record, @Param("example") WxPublicPlatformSubscribeUserExample example);

    int updateByExample(@Param("record") WxPublicPlatformSubscribeUser record, @Param("example") WxPublicPlatformSubscribeUserExample example);

    int updateByPrimaryKeySelective(WxPublicPlatformSubscribeUser record);

    int updateByPrimaryKey(WxPublicPlatformSubscribeUser record);
}