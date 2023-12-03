package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsSpecificationOptionExample;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallGoodsSpecificationOptionMapper extends BaseMapper<PointsMallGoodsSpecificationOption> {
    int countByExample(PointsMallGoodsSpecificationOptionExample example);

    int deleteByExample(PointsMallGoodsSpecificationOptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallGoodsSpecificationOption record);

    List<PointsMallGoodsSpecificationOption> selectByExample(PointsMallGoodsSpecificationOptionExample example);

    PointsMallGoodsSpecificationOption selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallGoodsSpecificationOption record, @Param("example") PointsMallGoodsSpecificationOptionExample example);

    int updateByExample(@Param("record") PointsMallGoodsSpecificationOption record, @Param("example") PointsMallGoodsSpecificationOptionExample example);

    int updateByPrimaryKeySelective(PointsMallGoodsSpecificationOption record);

    int updateByPrimaryKey(PointsMallGoodsSpecificationOption record);

    @ResultMap("BaseResultMap")
    @Select("<script>select so.* from tb_points_mall_goods_specification_option so" +
            "<where> 1=1 " +
            "<if test=\"specificationOption.id != null\"> AND so.id = #{specificationOption.id} </if>" +
            "<if test=\"specificationOption.goodsId != null\"> AND so.goods_id = #{specificationOption.goodsId} </if>" +
            "<if test=\"specificationOption.goodsSpecificationId != null\"> AND so.goods_specification_id = #{specificationOption.goodsSpecificationId} </if>" +
            "<if test=\"specificationOption.name != null and so.name !=''\"> AND so.name like '%${specificationOption.name}%' </if>" +
            "<if test=\"specificationOption.price != null\"> AND so.price = #{specificationOption.price} </if>" +
            "<if test=\"specificationOption.stock != null\"> AND so.stock = #{specificationOption.stock} </if>" +
            "<if test=\"specificationOption.sortNumber != null\"> AND so.sort_number = #{specificationOption.sortNumber} </if>" +
            "</where> order by so.id asc" +
            "</script>")
    Page<PointsMallGoodsSpecificationOption> getListByPage(@Param("page") Page page, @Param("specificationOption") PointsMallGoodsSpecificationOption goodsSpecificationOption);

    @ResultMap("CustomResultMap")
    @Select("<script>select so.*, s.name AS specificationName, g.name AS goodsName, g.main_image AS goodsMainImage from tb_points_mall_goods_specification_option so " +
            "LEFT JOIN tb_points_mall_goods_specification s ON s.id = so.goods_specification_id " +
            "LEFT JOIN tb_points_mall_goods g ON g.id = s.goods_id " +
            "<where> 1=1 " +
            "<if test=\"specificationOptionPointsMallGoodsDto.id != null\"> AND so.id = #{specificationOptionPointsMallGoodsDto.id} </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.goodsId != null\"> AND so.goods_id = #{specificationOptionPointsMallGoodsDto.goodsId} </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.goodsSpecificationId != null\"> AND so.goods_specification_id = #{specificationOptionPointsMallGoodsDto.goodsSpecificationId} </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.name != null and specificationOptionPointsMallGoodsDto.name !=''\"> AND so.name like '%${specificationOptionPointsMallGoodsDto.name}%' </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.price != null\"> AND so.price = #{specificationOptionPointsMallGoodsDto.price} </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.stock != null\"> AND so.stock = #{specificationOptionPointsMallGoodsDto.stock} </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.sortNumber != null\"> AND so.sort_number = #{specificationOptionPointsMallGoodsDto.sortNumber} </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.specificationName != null and specificationOptionPointsMallGoodsDto.specificationName !=''\"> AND s.name like '%${specificationOptionPointsMallGoodsDto.specificationName}%' </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.goodsName != null and specificationOptionPointsMallGoodsDto.goodsName !=''\"> AND g.name like '%${specificationOptionPointsMallGoodsDto.goodsName}%' </if>" +
            "<if test=\"specificationOptionPointsMallGoodsDto.goodsMainImage != null and specificationOptionPointsMallGoodsDto.goodsMainImage !=''\"> AND g.main_image like '%${specificationOptionPointsMallGoodsDto.goodsMainImage}%' </if>" +
            "</where> order by g.id desc, s.sort_number asc, so.sort_number asc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinPointsMallGoods(@Param("page") Page page, @Param("specificationOptionPointsMallGoodsDto") PointsMallGoodsSpecificationOptionDto goodsSpecificationOptionDto);

    @Select("select IFNULL(max(so.sort_number), 0) from tb_points_mall_goods_specification_option so where so.goods_specification_id = #{goodsSpecificationId}")
    int selectMaxSortNumberByPointsMallGoodsSpecificationId(@Param("goodsSpecificationId") Integer goodsSpecificationId);

    @Select("<script>select IFNULL(sum(so.price), 0) from tb_points_mall_goods_specification_option so" +
            " where so.goods_id = #{goodsId} and so.name in <foreach collection=\"nameList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            "</script>")
    BigDecimal selectSumPriceByGoodsIdAndName(@Param("goodsId") Integer goodsId, @Param("nameList") List<String> nameList);

//    @Update("update tb_points_mall_goods_specification_option set price = #{goodsAccessories.price}, stock = #{goodsAccessories.stock}, update_time = now() where name = #{goodsAccessories.name}")
//    void updateByPointsMallGoodsAccessories(@Param("goodsAccessories") PointsMallGoodsAccessories goodsAccessories);

    @Delete("delete from tb_points_mall_goods_specification_option where goods_id = #{goodsId}")
    void deleteByPointsMallGoodsId(@Param("goodsId") int goodsId);
}