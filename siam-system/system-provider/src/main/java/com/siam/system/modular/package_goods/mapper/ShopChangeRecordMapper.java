package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.ShopChangeRecord;
import com.siam.system.modular.package_goods.model.example.ShopChangeRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface ShopChangeRecordMapper extends BaseMapper<ShopChangeRecord> {
    int countByExample(ShopChangeRecordExample example);

    int deleteByExample(ShopChangeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ShopChangeRecord record);

    List<ShopChangeRecord> selectByExample(ShopChangeRecordExample example);

    ShopChangeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShopChangeRecord record, @Param("example") ShopChangeRecordExample example);

    int updateByExample(@Param("record") ShopChangeRecord record, @Param("example") ShopChangeRecordExample example);

    int updateByPrimaryKeySelective(ShopChangeRecord record);

    int updateByPrimaryKey(ShopChangeRecord record);

    @ResultMap("BaseResultMap")
    @Select("<script>select scr.* from tb_shop_change_record scr" +
            "<where> 1=1 " +
            "<if test=\"shopChangeRecord.id != null\"> AND scr.id = #{shopChangeRecord.id} </if>" +
            "<if test=\"shopChangeRecord.shopId != null\"> AND scr.shop_id = #{shopChangeRecord.shopId} </if>" +
            "<if test=\"shopChangeRecord.startCreateTime != null\"> AND DATE_FORMAT(scr.create_time, '%Y/%m/%d') &gt;= #{shopChangeRecord.startCreateTime} </if>" +
            "<if test=\"shopChangeRecord.endCreateTime != null\"> AND DATE_FORMAT(scr.create_time, '%Y/%m/%d') &lt;= #{shopChangeRecord.endCreateTime} </if>" +
            "</where> order by scr.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("shopChangeRecord") ShopChangeRecord shopChangeRecord);

    @ResultMap("CustomResultMap")
    @Select("<script>select scr.*, s.name as rawShopName from tb_shop_change_record scr " +
            "left join tb_shop s on s.id = scr.shop_id " +
            "<where> 1=1 " +
            "<if test=\"shopChangeRecord.id != null\"> AND scr.id = #{shopChangeRecord.id} </if>" +
            "<if test=\"shopChangeRecord.shopId != null\"> AND scr.shop_id = #{shopChangeRecord.shopId} </if>" +
            "<if test=\"shopChangeRecord.startCreateTime != null\"> AND DATE_FORMAT(scr.create_time, '%Y/%m/%d') &gt;= #{shopChangeRecord.startCreateTime} </if>" +
            "<if test=\"shopChangeRecord.endCreateTime != null\"> AND DATE_FORMAT(scr.create_time, '%Y/%m/%d') &lt;= #{shopChangeRecord.endCreateTime} </if>" +
            "</where> order by scr.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinShop(@Param("page") Page page, @Param("shopChangeRecord") ShopChangeRecord shopChangeRecord);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_shop_change_record where shop_id = #{shopId} order by create_time desc limit 1 ")
    ShopChangeRecord selectLastestByShopId(Integer shopId);
}