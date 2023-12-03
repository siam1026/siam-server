package com.siam.system.modular.package_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_user.entity.MerchantToken;
import com.siam.system.modular.package_user.model.example.MerchantTokenExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantTokenMapper extends BaseMapper<MerchantToken> {
    int countByExample(MerchantTokenExample example);

    int deleteByExample(MerchantTokenExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MerchantToken record);

    List<MerchantToken> selectByExample(MerchantTokenExample example);

    MerchantToken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantToken record, @Param("example") MerchantTokenExample example);

    int updateByExample(@Param("record") MerchantToken record, @Param("example") MerchantTokenExample example);

    int updateByPrimaryKeySelective(MerchantToken record);

    int updateByPrimaryKey(MerchantToken record);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_merchant_token where token = #{token} order by id desc limit 1 ")
    MerchantToken getLoginMerchantInfo(@Param("token") String token);
}