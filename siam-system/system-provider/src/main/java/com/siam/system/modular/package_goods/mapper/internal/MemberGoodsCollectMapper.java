package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.model.dto.internal.MemberGoodsCollectDto;
import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import com.siam.system.modular.package_goods.model.example.internal.MemberGoodsCollectExample;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberGoodsCollectMapper extends BaseMapper<MemberGoodsCollect> {
    int countByExample(MemberGoodsCollectExample example);

    int deleteByExample(MemberGoodsCollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberGoodsCollect record);

    List<MemberGoodsCollect> selectByExample(MemberGoodsCollectExample example);

    MemberGoodsCollect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MemberGoodsCollect record, @Param("example") MemberGoodsCollectExample example);

    int updateByExample(@Param("record") MemberGoodsCollect record, @Param("example") MemberGoodsCollectExample example);

    int updateByPrimaryKeySelective(MemberGoodsCollect record);

    int updateByPrimaryKey(MemberGoodsCollect record);

    @ResultMap("BaseResultMap")
    @Select("<script>select sc.* from tb_member_goods_collect sc" +
            "<where> 1=1 " +
            "<if test=\"MemberGoodsCollect.id != null\"> AND sc.id = #{MemberGoodsCollect.id} </if>" +
            "<if test=\"MemberGoodsCollect.memberId != null\"> AND sc.member_id = #{MemberGoodsCollect.memberId} </if>" +
            "<if test=\"MemberGoodsCollect.goodsId != null\"> AND sc.goods_id = #{MemberGoodsCollect.goodsId} </if>" +
            "<if test=\"MemberGoodsCollect.shopId != null\"> AND sc.shop_id = #{MemberGoodsCollect.shopId} </if>" +
            "<if test=\"MemberGoodsCollect.isGoodsExists != null\"> AND sc.is_goods_exists = #{MemberGoodsCollect.isGoodsExists} </if>" +
            "<if test=\"MemberGoodsCollect.isBuy != null\"> AND sc.is_buy = #{MemberGoodsCollect.isBuy} </if>" +
            "</where> order by sc.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("MemberGoodsCollect") MemberGoodsCollect MemberGoodsCollect);

    @ResultMap("CustomResultMap")
    @Select("<script>select sc.*, g.name as goodsName, g.main_image as mainImage, g.price as goodsPrice, g.sale_price as salePrice, g.stock, g.status as goodsStatus, g.is_sale as isSale, g.packing_charges as packingCharges from tb_member_goods_collect sc" +
            " left join tb_goods g on g.id = sc.goods_id" +
            "<where> 1=1 " +
            "<if test=\"MemberGoodsCollect.id != null\"> AND sc.id = #{MemberGoodsCollect.id} </if>" +
            "<if test=\"MemberGoodsCollect.shopId != null\"> AND sc.shop_id = #{MemberGoodsCollect.shopId} </if>" +
            "<if test=\"MemberGoodsCollect.memberId != null\"> AND sc.member_id = #{MemberGoodsCollect.memberId} </if>" +
            "<if test=\"MemberGoodsCollect.goodsId != null\"> AND sc.goods_id = #{MemberGoodsCollect.goodsId} </if>" +
            "<if test=\"MemberGoodsCollect.isGoodsExists != null\"> AND sc.is_goods_exists = #{MemberGoodsCollect.isGoodsExists} </if>" +
            "<if test=\"MemberGoodsCollect.isBuy != null\"> AND sc.is_buy = #{MemberGoodsCollect.isBuy} </if>" +
            "<if test=\"MemberGoodsCollect.goodsName != null and MemberGoodsCollect.goodsName !=''\"> AND g.name like '%${MemberGoodsCollect.goodsName}%' </if>" +
            "</where> order by sc.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinGoods(@Param("page") Page page, @Param("MemberGoodsCollect") MemberGoodsCollectDto memberGoodsCollectDto);

    @Select("<script>select count(*) from tb_member_goods_collect sc" +
            " where sc.id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            " and sc.member_id = #{memberId}" +
            "</script>")
    int countByIdListAndMemberId(@Param("idList") List<Integer> idList, @Param("memberId") Integer memberId);

    @Delete("<script>delete from tb_member_goods_collect" +
            " where id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            "</script>")
    int batchDeleteByIdList(@Param("idList") List<Integer> idList);

    @Update("update tb_member_goods_collect set is_goods_exists = 0 where goods_id = #{goodsId}")
    void updateIsGoodsExistsTo0ByGoodsId(@Param("goodsId") int goodsId);

    @Select("select IFNULL(sum(sc.number), 0) from tb_member_goods_collect as sc "+
            "where sc.shop_id = #{shopId} and (sc.create_time between #{startTime} and #{endTime})")
    int selectCountGoodsNumber(@Param("shopId") Integer shopId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Update("update tb_member_goods_collect mgc set mgc.is_buy = (select if(count(0) > 0, 1, 0) from tb_order_detail od where od.goods_id = mgc.goods_id) where mgc.is_buy = 0")
    void updateIsBuy();
}