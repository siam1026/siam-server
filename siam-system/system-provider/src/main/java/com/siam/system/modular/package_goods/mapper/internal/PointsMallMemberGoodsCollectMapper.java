package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallMemberGoodsCollectDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMemberGoodsCollectExample;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallMemberGoodsCollectMapper extends BaseMapper<PointsMallMemberGoodsCollect> {
    int countByExample(PointsMallMemberGoodsCollectExample example);

    int deleteByExample(PointsMallMemberGoodsCollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallMemberGoodsCollect record);

    List<PointsMallMemberGoodsCollect> selectByExample(PointsMallMemberGoodsCollectExample example);

    PointsMallMemberGoodsCollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallMemberGoodsCollect record, @Param("example") PointsMallMemberGoodsCollectExample example);

    int updateByExample(@Param("record") PointsMallMemberGoodsCollect record, @Param("example") PointsMallMemberGoodsCollectExample example);

    int updateByPrimaryKeySelective(PointsMallMemberGoodsCollect record);

    int updateByPrimaryKey(PointsMallMemberGoodsCollect record);

    @ResultMap("BaseResultMap")
    @Select("<script>select sc.* from tb_points_mall_member_goods_collect sc" +
            "<where> 1=1 " +
            "<if test=\"PointsMallMemberGoodsCollect.id != null\"> AND sc.id = #{PointsMallMemberGoodsCollect.id} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.memberId != null\"> AND sc.member_id = #{PointsMallMemberGoodsCollect.memberId} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.goodsId != null\"> AND sc.goods_id = #{PointsMallMemberGoodsCollect.goodsId} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.isGoodsExists != null\"> AND sc.is_goods_exists = #{PointsMallMemberGoodsCollect.isGoodsExists} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.isBuy != null\"> AND sc.is_buy = #{PointsMallMemberGoodsCollect.isBuy} </if>" +
            "</where> order by sc.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("PointsMallMemberGoodsCollect") PointsMallMemberGoodsCollect PointsMallMemberGoodsCollect);

    @ResultMap("CustomResultMap")
    @Select("<script>select sc.*, g.name as goodsName, g.main_image as mainImage, g.price as goodsPrice, g.sale_price as salePrice, g.stock, g.status as goodsStatus, g.is_sale as isSale, g.packing_charges as packingCharges from tb_points_mall_member_goods_collect sc" +
            " left join tb_points_mall_goods g on g.id = sc.goods_id" +
            "<where> 1=1 " +
            "<if test=\"PointsMallMemberGoodsCollect.id != null\"> AND sc.id = #{PointsMallMemberGoodsCollect.id} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.memberId != null\"> AND sc.member_id = #{PointsMallMemberGoodsCollect.memberId} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.goodsId != null\"> AND sc.goods_id = #{PointsMallMemberGoodsCollect.goodsId} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.isGoodsExists != null\"> AND sc.is_goods_exists = #{PointsMallMemberGoodsCollect.isGoodsExists} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.isBuy != null\"> AND sc.is_buy = #{PointsMallMemberGoodsCollect.isBuy} </if>" +
            "<if test=\"PointsMallMemberGoodsCollect.goodsName != null and PointsMallMemberGoodsCollect.goodsName !=''\"> AND g.name like '%${PointsMallMemberGoodsCollect.goodsName}%' </if>" +
            "</where> order by sc.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinPointsMallGoods(@Param("page") Page page, @Param("PointsMallMemberGoodsCollect") PointsMallMemberGoodsCollectDto pointsMallMemberGoodsCollectDto);

    @Select("<script>select count(*) from tb_points_mall_member_goods_collect sc" +
            " where sc.id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            " and sc.member_id = #{memberId}" +
            "</script>")
    int countByIdListAndMemberId(@Param("idList") List<Integer> idList, @Param("memberId") Integer memberId);

    @Delete("<script>delete from tb_points_mall_member_goods_collect" +
            " where id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            "</script>")
    int batchDeleteByIdList(@Param("idList") List<Integer> idList);

    @Update("update tb_points_mall_member_goods_collect set is_goods_exists = 0 where goods_id = #{goodsId}")
    void updateIsGoodsExistsTo0ByPointsMallGoodsId(@Param("goodsId") int goodsId);

    @Select("select IFNULL(sum(sc.number), 0) from tb_points_mall_member_goods_collect as sc "+
            "where (sc.create_time between #{startTime} and #{endTime})")
    int selectCountPointsMallGoodsNumber(@Param("shopId") Integer shopId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Update("update tb_points_mall_member_goods_collect mgc set mgc.is_buy = (select if(count(0) > 0, 1, 0) from tb_points_mall_order_detail od where od.goods_id = mgc.goods_id) where mgc.is_buy = 0")
    void updateIsBuy();
}