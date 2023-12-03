package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.PointsMallFullReductionRule;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallFullReductionRuleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

;

public interface PointsMallFullReductionRuleMapper extends BaseMapper<PointsMallFullReductionRule> {
    int countByExample(PointsMallFullReductionRuleExample example);

    int deleteByExample(PointsMallFullReductionRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallFullReductionRule record);

    List<PointsMallFullReductionRule> selectByExample(PointsMallFullReductionRuleExample example);

    PointsMallFullReductionRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallFullReductionRule record, @Param("example") PointsMallFullReductionRuleExample example);

    int updateByExample(@Param("record") PointsMallFullReductionRule record, @Param("example") PointsMallFullReductionRuleExample example);

    int updateByPrimaryKeySelective(PointsMallFullReductionRule record);

    int updateByPrimaryKey(PointsMallFullReductionRule record);

    @ResultMap("BaseResultMap")
    @Select("<script>select f.* from tb_points_mall_full_reduction_rule f" +
            "<where> 1=1 " +
            "<if test=\"fullReductionRule.id != null\"> AND f.id = #{fullReductionRule.id} </if>" +
            "<if test=\"fullReductionRule.name != null and fullReductionRule.name !=''\"> AND f.name like '%${fullReductionRule.name}%' </if>" +
            "<if test=\"fullReductionRule.status != null\"> AND f.status = #{fullReductionRule.status} </if>" +
            "<if test=\"fullReductionRule.limitedPrice != null\"> AND f.limited_price = #{fullReductionRule.limitedPrice} </if>" +
            "<if test=\"fullReductionRule.reducedPrice != null\"> AND f.reduced_price = #{fullReductionRule.reducedPrice} </if>" +
            "</where> order by f.limited_price asc" +
            "</script>")
    Page<PointsMallFullReductionRule> getListByPage(@Param("page") Page page, @Param("fullReductionRule") PointsMallFullReductionRule fullReductionRule);
}