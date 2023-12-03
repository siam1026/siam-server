package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.Rawmaterial;
import com.siam.system.modular.package_goods.model.example.RawmaterialExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface RawmaterialMapper extends BaseMapper<Rawmaterial> {
    int countByExample(RawmaterialExample example);

    int deleteByExample(RawmaterialExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Rawmaterial record);

    List<Rawmaterial> selectByExample(RawmaterialExample example);

    Rawmaterial selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Rawmaterial record, @Param("example") RawmaterialExample example);

    int updateByExample(@Param("record") Rawmaterial record, @Param("example") RawmaterialExample example);

    int updateByPrimaryKeySelective(Rawmaterial record);

    int updateByPrimaryKey(Rawmaterial record);

    @ResultMap("BaseResultMap")
    @Select("<script>select r.* from tb_rawmaterial r" +
            "<where> 1=1 " +
            "<if test=\"rawmaterial.id != null\"> AND r.id = #{rawmaterial.id} </if>" +
            "<if test=\"rawmaterial.name != null and rawmaterial.name !=''\"> AND r.name like '%${rawmaterial.name}%' </if>" +
            "<if test=\"rawmaterial.mainImage != null and rawmaterial.mainImage != ''\"> AND r.main_image like '%${rawmaterial.mainImage}' </if>" +
            "<if test=\"rawmaterial.description != null and rawmaterial.description != ''\"> AND r.description like '%${rawmaterial.description}' </if>" +
            "<if test=\"rawmaterial.unit != null and rawmaterial.unit != ''\"> AND r.unit like '%${rawmaterial.unit}' </if>" +
            "<if test=\"rawmaterial.price != null\"> AND r.price = #{rawmaterial.price} </if>" +
            "<if test=\"rawmaterial.stock != null\"> AND r.stock = #{rawmaterial.stock} </if>" +
            "<if test=\"rawmaterial.stockLowerLimit != null\"> AND r.stock_lower_limit = #{rawmaterial.stockLowerLimit} </if>" +
            "<if test=\"rawmaterial.stockUpperLimit != null\"> AND r.stock_upper_limit = #{rawmaterial.stockUpperLimit} </if>" +
            "</where> order by r.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("rawmaterial") Rawmaterial rawmaterial);

    @Update("update tb_rawmaterial set stock=stock-#{consumeAmount} where id=#{id}")
    void consumeRawmaterial(@Param("consumeAmount") BigDecimal consumeAmount,@Param("id") Integer id);

    @ResultMap("CustomResultMap")
    @Select("SELECT FLOOR(MIN((tr.stock/tgr.consumed_quantity)))as goodsStock FROM tb_rawmaterial tr LEFT JOIN tb_goods_rawmaterial_relation tgr on tr.id=tgr.rawmaterial_id WHERE tgr.goods_id=#{id}")
    List<Map<String, Object>>  getGoodsStockById(@Param("id") Integer id);
}