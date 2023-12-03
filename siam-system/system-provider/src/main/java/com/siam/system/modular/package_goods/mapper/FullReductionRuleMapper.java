package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.model.example.FullReductionRuleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

;import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface FullReductionRuleMapper extends BaseMapper<FullReductionRule> {
    int countByExample(FullReductionRuleExample example);

    int deleteByExample(FullReductionRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(FullReductionRule record);

    List<FullReductionRule> selectByExample(FullReductionRuleExample example);

    FullReductionRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FullReductionRule record, @Param("example") FullReductionRuleExample example);

    int updateByExample(@Param("record") FullReductionRule record, @Param("example") FullReductionRuleExample example);

    int updateByPrimaryKeySelective(FullReductionRule record);

    int updateByPrimaryKey(FullReductionRule record);

    @ResultMap("BaseResultMap")
    @Select("<script>select f.* from tb_full_reduction_rule f" +
            "<where> 1=1 " +
            "<if test=\"fullReductionRule.id != null\"> AND f.id = #{fullReductionRule.id} </if>" +
            "<if test=\"fullReductionRule.shopId != null\"> AND f.shop_id = #{fullReductionRule.shopId} </if>" +
            "<if test=\"fullReductionRule.name != null and fullReductionRule.name !=''\"> AND f.name like '%${fullReductionRule.name}%' </if>" +
            "<if test=\"fullReductionRule.status != null\"> AND f.status = #{fullReductionRule.status} </if>" +
            "<if test=\"fullReductionRule.limitedPrice != null\"> AND f.limited_price = #{fullReductionRule.limitedPrice} </if>" +
            "<if test=\"fullReductionRule.reducedPrice != null\"> AND f.reduced_price = #{fullReductionRule.reducedPrice} </if>" +
            "</where> order by f.limited_price asc" +
            "</script>")
    Page<FullReductionRule> getListByPage(@Param("page") Page page, @Param("fullReductionRule") FullReductionRule fullReductionRule);
}