package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.GiveLike;
import com.siam.system.modular.package_order.model.example.GiveLikeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GiveLikeMapper extends BaseMapper<GiveLike> {
    int countByExample(GiveLikeExample example);

    int deleteByExample(GiveLikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(GiveLike record);

    List<GiveLike> selectByExample(GiveLikeExample example);

    GiveLike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GiveLike record, @Param("example") GiveLikeExample example);

    int updateByExample(@Param("record") GiveLike record, @Param("example") GiveLikeExample example);

    int updateByPrimaryKeySelective(GiveLike record);

    int updateByPrimaryKey(GiveLike record);
}