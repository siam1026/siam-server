package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.internal.PointsMallShoppingCart;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallShoppingCartExample;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallShoppingCartMapper extends BaseMapper<PointsMallShoppingCart> {
    int countByExample(PointsMallShoppingCartExample example);

    int deleteByExample(PointsMallShoppingCartExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PointsMallShoppingCart record);

    List<PointsMallShoppingCart> selectByExample(PointsMallShoppingCartExample example);

    PointsMallShoppingCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PointsMallShoppingCart record, @Param("example") PointsMallShoppingCartExample example);

    int updateByExample(@Param("record") PointsMallShoppingCart record, @Param("example") PointsMallShoppingCartExample example);

    int updateByPrimaryKeySelective(PointsMallShoppingCart record);

    int updateByPrimaryKey(PointsMallShoppingCart record);

    @ResultMap("BaseResultMap")
    @Select("<script>select sc.* from tb_points_mall_shopping_cart sc" +
            "<where> 1=1 " +
            "<if test=\"shoppingCart.id != null\"> AND sc.id = #{shoppingCart.id} </if>" +
            "<if test=\"shoppingCart.memberId != null\"> AND sc.member_id = #{shoppingCart.memberId} </if>" +
            "<if test=\"shoppingCart.goodsId != null\"> AND sc.goods_id = #{shoppingCart.goodsId} </if>" +
            "<if test=\"shoppingCart.specList != null and shoppingCart.specList !=''\"> AND sc.spec_list like '%${shoppingCart.specList}%' </if>" +
            "<if test=\"shoppingCart.number != null\"> AND sc.number = #{shoppingCart.number} </if>" +
            "<if test=\"shoppingCart.isGoodsExists != null\"> AND sc.is_goods_exists = #{shoppingCart.isGoodsExists} </if>" +
            "</where> order by sc.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("shoppingCart") PointsMallShoppingCart shoppingCart);

    @ResultMap("CustomResultMap")
    @Select("<script>select sc.*, g.name as goodsName, g.main_image as mainImage, g.price as goodsPrice, g.sale_price as salePrice, g.stock, g.status as goodsStatus, g.is_sale as isSale, g.packing_charges as packingCharges from tb_points_mall_shopping_cart sc" +
            " left join tb_points_mall_goods g on g.id = sc.goods_id" +
            "<where> 1=1 " +
            "<if test=\"shoppingCart.id != null\"> AND sc.id = #{shoppingCart.id} </if>" +
            "<if test=\"shoppingCart.memberId != null\"> AND sc.member_id = #{shoppingCart.memberId} </if>" +
            "<if test=\"shoppingCart.goodsId != null\"> AND sc.goods_id = #{shoppingCart.goodsId} </if>" +
            "<if test=\"shoppingCart.specList != null and shoppingCart.specList !=''\"> AND sc.spec_list like '%${shoppingCart.specList}%' </if>" +
            "<if test=\"shoppingCart.number != null\"> AND sc.number = #{shoppingCart.number} </if>" +
            "<if test=\"shoppingCart.isGoodsExists != null\"> AND sc.is_goods_exists = #{shoppingCart.isGoodsExists} </if>" +
            "</where> order by sc.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinPointsMallGoods(@Param("page") Page page, @Param("shoppingCart") PointsMallShoppingCart shoppingCart);

    @Select("<script>select count(*) from tb_points_mall_shopping_cart sc" +
            " where sc.id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            " and sc.member_id = #{memberId}" +
            "</script>")
    int countByIdListAndMemberId(@Param("idList") List<Integer> idList, @Param("memberId") Integer memberId);

    @Delete("<script>delete from tb_points_mall_shopping_cart" +
            " where id in <foreach collection=\"idList\" item=\"item\" index=\"index\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach>" +
            "</script>")
    int batchDeleteByIdList(@Param("idList") List<Integer> idList);

    @Update("update tb_points_mall_shopping_cart set is_goods_exists = 0 where goods_id = #{goodsId}")
    void updateIsGoodsExistsTo0ByPointsMallGoodsId(@Param("goodsId") int goodsId);

    @Select("select IFNULL(sum(sc.number), 0) from tb_points_mall_shopping_cart as sc "+
            "where (sc.create_time between #{startTime} and #{endTime})")
    int selectCountGoodsNumber(@Param("shopId") Integer shopId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}