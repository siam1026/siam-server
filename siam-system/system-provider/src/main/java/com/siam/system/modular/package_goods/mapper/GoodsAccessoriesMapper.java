package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.GoodsAccessories;
import com.siam.system.modular.package_goods.model.example.GoodsAccessoriesExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface GoodsAccessoriesMapper extends BaseMapper<GoodsAccessories> {
    int countByExample(GoodsAccessoriesExample example);

    int deleteByExample(GoodsAccessoriesExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(GoodsAccessories record);

    List<GoodsAccessories> selectByExample(GoodsAccessoriesExample example);

    GoodsAccessories selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsAccessories record, @Param("example") GoodsAccessoriesExample example);

    int updateByExample(@Param("record") GoodsAccessories record, @Param("example") GoodsAccessoriesExample example);

    int updateByPrimaryKeySelective(GoodsAccessories record);

    int updateByPrimaryKey(GoodsAccessories record);

    @ResultMap("BaseResultMap")
    @Select("<script>select ga.* from tb_goods_accessories ga" +
            "<where> 1=1 " +
            "<if test=\"goodsAccessories.id != null\"> AND ga.id = #{goodsAccessories.id} </if>" +
            "<if test=\"goodsAccessories.name != null and goodsAccessories.name !=''\"> AND ga.name like '%${goodsAccessories.name}%' </if>" +
            "<if test=\"goodsAccessories.mainImage != null and goodsAccessories.mainImage != ''\"> AND ga.main_image like '%${goodsAccessories.mainImage}' </if>" +
            "<if test=\"goodsAccessories.description != null and goodsAccessories.description != ''\"> AND ga.description like '%${goodsAccessories.description}' </if>" +
            "<if test=\"goodsAccessories.price != null\"> AND ga.price = #{goodsAccessories.price} </if>" +
            "<if test=\"goodsAccessories.stock != null\"> AND ga.stock = #{goodsAccessories.stock} </if>" +
            "</where> order by ga.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("goodsAccessories") GoodsAccessories goodsAccessories);

    @ResultMap("BaseResultMap")
    @Select("select ga.* from tb_goods_accessories ga where ga.name = #{name} limit 1")
    GoodsAccessories selectByName(@Param("name") String name);
}