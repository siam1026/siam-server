package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.entity.MerchantRecommendGoods;
import com.siam.system.modular.package_goods.model.example.MerchantRecommendGoodsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface MerchantRecommendGoodsMapper extends BaseMapper<MerchantRecommendGoods> {
    int countByExample(MerchantRecommendGoodsExample example);

    int deleteByExample(MerchantRecommendGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(MerchantRecommendGoods record);

    List<MerchantRecommendGoods> selectByExample(MerchantRecommendGoodsExample example);

    MerchantRecommendGoods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantRecommendGoods record, @Param("example") MerchantRecommendGoodsExample example);

    int updateByExample(@Param("record") MerchantRecommendGoods record, @Param("example") MerchantRecommendGoodsExample example);

    int updateByPrimaryKeySelective(MerchantRecommendGoods record);

    int updateByPrimaryKey(MerchantRecommendGoods record);

    @ResultMap("BaseResultMap")
    @Select("<script>select mrg.* from tb_merchant_recommend_goods mgr" +
            "<where> 1=1 " +
            "<if test=\"merchantRecommendGoods.id != null\"> AND mrg.id = #{merchantRecommendGoods.id} </if>" +
            "<if test=\"merchantRecommendGoods.shopId != null\"> AND mrg.shop_id = #{merchantRecommendGoods.shopId} </if>" +
            "<if test=\"merchantRecommendGoods.goodsId != null\"> AND mrg.goods_id = #{merchantRecommendGoods.goodsId} </if>" +
            "<if test=\"merchantRecommendGoods.sortNumber != null\"> AND mrg.sort_number = #{merchantRecommendGoods.sortNumber} </if>" +
            "</where> order by mrg.id desc" +
            "</script>")
    Page<MerchantRecommendGoods> getListByPage(@Param("page") Page page, @Param("merchantRecommendGoods") MerchantRecommendGoods merchantRecommendGoods);

    @ResultMap("CustomResultMap")
    @Select("<script>select mrg.*, " +
            "(select ifnull(sum(od.number), 0) from tb_order_detail as od left join tb_order as o on od.order_id = o.id where od.goods_id = mrg.goods_id and o.status = 6 and (o.create_time BETWEEN '1970-1-1' AND NOW())) as latelyMonthlySales, " +
            "g.name as goodsName, g.main_image as mainImage, g.price as goodsPrice, g.sale_price as salePrice, g.stock, g.status as goodsStatus, g.is_sale as isSale, g.packing_charges as packingCharges " +
            "from tb_merchant_recommend_goods mrg " +
            "left join tb_goods g on g.id = mrg.goods_id" +
            "<where> 1=1 " +
            "<if test=\"merchantRecommendGoods.id != null\"> AND mrg.id = #{merchantRecommendGoods.id} </if>" +
            "<if test=\"merchantRecommendGoods.shopId != null\"> AND mrg.shop_id = #{merchantRecommendGoods.shopId} </if>" +
            "<if test=\"merchantRecommendGoods.goodsId != null\"> AND mrg.goods_id = #{merchantRecommendGoods.goodsId} </if>" +
            "<if test=\"merchantRecommendGoods.sortNumber != null\"> AND mrg.sort_number = #{merchantRecommendGoods.sortNumber} </if>" +
            "</where> order by mrg.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinGoods(@Param("page") Page page, @Param("merchantRecommendGoods") MerchantRecommendGoods merchantRecommendGoods);
}