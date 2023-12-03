package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.entity.GoodsSpecificationOption;
import com.siam.system.modular.package_goods.model.example.GoodsSpecificationOptionExample;

import java.math.BigDecimal;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import org.apache.ibatis.annotations.*;

public interface GoodsSpecificationOptionMapper extends BaseMapper<GoodsSpecificationOption> {
    int countByExample(GoodsSpecificationOptionExample example);

    int deleteByExample(GoodsSpecificationOptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(GoodsSpecificationOption record);

    List<GoodsSpecificationOption> selectByExample(GoodsSpecificationOptionExample example);

    GoodsSpecificationOption selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsSpecificationOption record, @Param("example") GoodsSpecificationOptionExample example);

    int updateByExample(@Param("record") GoodsSpecificationOption record, @Param("example") GoodsSpecificationOptionExample example);

    int updateByPrimaryKeySelective(GoodsSpecificationOption record);

    int updateByPrimaryKey(GoodsSpecificationOption record);

    @ResultMap("BaseResultMap")
    @Select("<script>select so.* from tb_goods_specification_option so" +
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
    Page<GoodsSpecificationOption> getListByPage(@Param("page") Page page, @Param("specificationOption") GoodsSpecificationOption goodsSpecificationOption);

    @ResultMap("CustomResultMap")
    @Select("<script>select so.*, s.name AS specificationName, g.name AS goodsName, g.main_image AS goodsMainImage from tb_goods_specification_option so " +
            "LEFT JOIN tb_goods_specification s ON s.id = so.goods_specification_id " +
            "LEFT JOIN tb_goods g ON g.id = s.goods_id " +
            "<where> 1=1 " +
            "<if test=\"specificationOptionGoodsDto.id != null\"> AND so.id = #{specificationOptionGoodsDto.id} </if>" +
            "<if test=\"specificationOptionGoodsDto.goodsId != null\"> AND so.goods_id = #{specificationOptionGoodsDto.goodsId} </if>" +
            "<if test=\"specificationOptionGoodsDto.goodsSpecificationId != null\"> AND so.goods_specification_id = #{specificationOptionGoodsDto.goodsSpecificationId} </if>" +
            "<if test=\"specificationOptionGoodsDto.name != null and specificationOptionGoodsDto.name !=''\"> AND so.name like '%${specificationOptionGoodsDto.name}%' </if>" +
            "<if test=\"specificationOptionGoodsDto.price != null\"> AND so.price = #{specificationOptionGoodsDto.price} </if>" +
            "<if test=\"specificationOptionGoodsDto.stock != null\"> AND so.stock = #{specificationOptionGoodsDto.stock} </if>" +
            "<if test=\"specificationOptionGoodsDto.sortNumber != null\"> AND so.sort_number = #{specificationOptionGoodsDto.sortNumber} </if>" +
            "<if test=\"specificationOptionGoodsDto.specificationName != null and specificationOptionGoodsDto.specificationName !=''\"> AND s.name like '%${specificationOptionGoodsDto.specificationName}%' </if>" +
            "<if test=\"specificationOptionGoodsDto.goodsName != null and specificationOptionGoodsDto.goodsName !=''\"> AND g.name like '%${specificationOptionGoodsDto.goodsName}%' </if>" +
            "<if test=\"specificationOptionGoodsDto.goodsMainImage != null and specificationOptionGoodsDto.goodsMainImage !=''\"> AND g.main_image like '%${specificationOptionGoodsDto.goodsMainImage}%' </if>" +
            "</where> order by g.id desc, s.sort_number asc, so.sort_number asc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinGoods(@Param("page") Page page, @Param("specificationOptionGoodsDto") GoodsSpecificationOptionDto goodsSpecificationOptionDto);

    @Select("select IFNULL(max(so.sort_number), 0) from tb_goods_specification_option so where so.goods_specification_id = #{goodsSpecificationId}")
    int selectMaxSortNumberByGoodsSpecificationId(@Param("goodsSpecificationId") Integer goodsSpecificationId);

    @Select("<script>select IFNULL(sum(so.price), 0) from tb_goods_specification_option so" +
            " where so.goods_id = #{goodsId} and so.name in <foreach collection=\"nameList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            "</script>")
    BigDecimal selectSumPriceByGoodsIdAndName(@Param("goodsId") Integer goodsId, @Param("nameList") List<String> nameList);

    @Update("update tb_goods_specification_option set price = #{goodsAccessories.price}, stock = #{goodsAccessories.stock}, update_time = now() where name = #{goodsAccessories.name}")
    void updateByGoodsAccessories(@Param("goodsAccessories") GoodsAccessories goodsAccessories);

    @Delete("delete from tb_goods_specification_option where goods_id = #{goodsId}")
    void deleteByGoodsId(@Param("goodsId") int goodsId);
}