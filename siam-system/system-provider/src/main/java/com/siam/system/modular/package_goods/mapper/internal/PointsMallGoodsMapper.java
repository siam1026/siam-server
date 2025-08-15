package com.siam.system.modular.package_goods.mapper.internal;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsMenuDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PointsMallGoodsMapper extends BaseMapper<PointsMallGoods> {
    int countByExample(PointsMallGoodsExample example);

    int deleteByExample(PointsMallGoodsExample example);

    List<PointsMallGoods> selectByExample(PointsMallGoodsExample example);

    int updateByExampleSelective(@Param("record") PointsMallGoods record, @Param("example") PointsMallGoodsExample example);

    int updateByExample(@Param("record") PointsMallGoods record, @Param("example") PointsMallGoodsExample example);

    int updateByPrimaryKeySelective(PointsMallGoods record);

    int updateByPrimaryKey(PointsMallGoods record);

    @ResultMap("BaseResultMap")
    @Select("<script>select g.* from tb_points_mall_goods g" +
            "<where> 1=1 " +
            "<if test=\"goods.id != null\"> AND g.id = #{goods.id} </if>" +
            "<if test=\"goods.name != null and goods.name !=''\"> AND g.name like '%${goods.name}%' </if>" +
            "<if test=\"goods.mainImage != null and goods.mainImage !=''\"> AND g.main_image like '%${goods.mainImage}%' </if>" +
            "<if test=\"goods.subImages != null and goods.subImages !=''\"> AND g.sub_images like '%${goods.subImages}%' </if>" +
            "<if test=\"goods.detail != null and goods.detail !=''\"> AND g.detail like '%${goods.detail}%' </if>" +
            "<if test=\"goods.detailImages != null and goods.detailImages !=''\"> AND g.detail_images like '%${goods.detailImages}%' </if>" +
            "<if test=\"goods.price != null\"> AND g.price = #{goods.price} </if>" +
            "<if test=\"goods.stock != null\"> AND g.stock = #{goods.stock} </if>" +
            "<if test=\"goods.isNew != null\"> AND g.is_new = #{goods.isNew} </if>" +
            "<if test=\"goods.status != null\"> AND g.status = #{goods.status} </if>" +
            "<if test=\"goods.isSale != null\"> AND g.is_sale = #{goods.isSale} </if>" +
            "<if test=\"goods.salePrice != null\"> AND g.sale_price = #{goods.salePrice} </if>" +
            "<if test=\"goods.monthlySales != null\"> AND g.monthly_sales = #{goods.monthlySales} </if>" +
            "<if test=\"goods.totalSales != null\"> AND g.total_sales = #{goods.totalSales} </if>" +
            "<if test=\"goods.totalComments != null\"> AND g.total_comments = #{goods.totalComments} </if>" +
            "<if test=\"goods.isHot != null\"> AND g.is_hot = #{goods.isHot} </if>" +
            "<if test=\"goods.preferentialName != null and goods.preferentialName !=''\"> AND g.preferential_name like '%${goods.preferentialName}%' </if>" +
            "<if test=\"goods.packingCharges != null\"> AND g.packing_charges = #{goods.packingCharges} </if>" +
            "</where> order by g.id asc" +
            "</script>")
    Page<PointsMallGoods> getListByPage(@Param("page") Page page, @Param("goods") PointsMallGoods goods);

    @ResultMap("CustomResultMap")
    @Select("<script>select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_points_mall_order_detail as od left join tb_points_mall_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6) as totalSales, " +
            "m.id AS menuId, m.name AS menuName FROM tb_points_mall_goods g " +
            "left join tb_points_mall_menu_goods_relation mgr ON g.id = mgr.goods_id " +
            "left join tb_points_mall_menu m ON m.id = mgr.menu_id " +
            "<where> 1=1 " +
            "<if test=\"goodsPointsMallMenuDto.id != null\"> AND g.id = #{goodsPointsMallMenuDto.id} </if>" +
            "<if test=\"goodsPointsMallMenuDto.name != null and goodsPointsMallMenuDto.name !=''\"> AND g.name like '%${goodsPointsMallMenuDto.name}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.mainImage != null and goodsPointsMallMenuDto.mainImage !=''\"> AND g.main_image like '%${goodsPointsMallMenuDto.mainImage}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.subImages != null and goodsPointsMallMenuDto.subImages !=''\"> AND g.sub_images like '%${goodsPointsMallMenuDto.subImages}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.detail != null and goodsPointsMallMenuDto.detail !=''\"> AND g.detail like '%${goodsPointsMallMenuDto.detail}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.detailImages != null and goodsPointsMallMenuDto.detailImages !=''\"> AND g.detail_images like '%${goodsPointsMallMenuDto.detailImages}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.price != null\"> AND g.price = #{goodsPointsMallMenuDto.price} </if>" +
            "<if test=\"goodsPointsMallMenuDto.isSale != null\"> AND g.is_sale = #{goodsPointsMallMenuDto.isSale} </if>" +
            "<if test=\"goodsPointsMallMenuDto.salePrice != null\"> AND g.sale_price = #{goodsPointsMallMenuDto.salePrice} </if>" +
            "<if test=\"goodsPointsMallMenuDto.monthlySales != null\"> AND g.monthly_sales = #{goodsPointsMallMenuDto.monthlySales} </if>" +
            "<if test=\"goodsPointsMallMenuDto.totalSales != null\"> AND g.total_sales = #{goodsPointsMallMenuDto.totalSales} </if>" +
            "<if test=\"goodsPointsMallMenuDto.totalComments != null\"> AND g.total_comments = #{goodsPointsMallMenuDto.totalComments} </if>" +
            "<if test=\"goodsPointsMallMenuDto.stock != null\"> AND g.stock = #{goodsPointsMallMenuDto.stock} </if>" +
            "<if test=\"goodsPointsMallMenuDto.isHot != null\"> AND g.is_hot = #{goodsPointsMallMenuDto.isHot} </if>" +
            "<if test=\"goodsPointsMallMenuDto.isNew != null\"> AND g.is_new = #{goodsPointsMallMenuDto.isNew} </if>" +
            "<if test=\"goodsPointsMallMenuDto.status != null\"> AND g.status = #{goodsPointsMallMenuDto.status} </if>" +
            "<if test=\"goodsPointsMallMenuDto.menuId != null\"> AND m.id = #{goodsPointsMallMenuDto.menuId} </if>" +
            "<if test=\"goodsPointsMallMenuDto.menuName != null and goodsPointsMallMenuDto.menuName !=''\"> AND m.name like '%${goodsPointsMallMenuDto.menuName}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.goodsStatusIn2And4 != null and goodsPointsMallMenuDto.goodsStatusIn2And4 == true\"> AND g.status in (2, 4) </if>" +
            "<if test=\"goodsPointsMallMenuDto.preferentialName != null and goodsPointsMallMenuDto.preferentialName !=''\"> AND g.preferential_name like '%${goodsPointsMallMenuDto.preferentialName}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.packingCharges != null\"> AND g.packing_charges = #{goodsPointsMallMenuDto.packingCharges} </if>" +
            "</where> order by g.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinPointsMallMenu(@Param("page") Page page, @Param("goodsPointsMallMenuDto") PointsMallGoodsMenuDto goodsPointsMallMenuDto);

    @ResultMap("CustomResultMap")
    @Select("<script>select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_points_mall_order_detail as od left join tb_points_mall_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6 and (o.create_time between #{goodsPointsMallMenuDto.startTime} and #{goodsPointsMallMenuDto.endTime})) as latelyMonthlySales, " +
            "m.id AS menuId, m.name AS menuName FROM tb_points_mall_goods g " +
            "left join tb_points_mall_menu_goods_relation mgr ON g.id = mgr.goods_id " +
            "left join tb_points_mall_menu m ON m.id = mgr.menu_id " +
            "<where> 1=1 " +
            "<if test=\"goodsPointsMallMenuDto.id != null\"> AND g.id = #{goodsPointsMallMenuDto.id} </if>" +
            "<if test=\"goodsPointsMallMenuDto.name != null and goodsPointsMallMenuDto.name !=''\"> AND g.name like '%${goodsPointsMallMenuDto.name}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.mainImage != null and goodsPointsMallMenuDto.mainImage !=''\"> AND g.main_image like '%${goodsPointsMallMenuDto.mainImage}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.subImages != null and goodsPointsMallMenuDto.subImages !=''\"> AND g.sub_images like '%${goodsPointsMallMenuDto.subImages}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.detail != null and goodsPointsMallMenuDto.detail !=''\"> AND g.detail like '%${goodsPointsMallMenuDto.detail}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.detailImages != null and goodsPointsMallMenuDto.detailImages !=''\"> AND g.detail_images like '%${goodsPointsMallMenuDto.detailImages}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.price != null\"> AND g.price = #{goodsPointsMallMenuDto.price} </if>" +
            "<if test=\"goodsPointsMallMenuDto.isSale != null\"> AND g.is_sale = #{goodsPointsMallMenuDto.isSale} </if>" +
            "<if test=\"goodsPointsMallMenuDto.salePrice != null\"> AND g.sale_price = #{goodsPointsMallMenuDto.salePrice} </if>" +
            "<if test=\"goodsPointsMallMenuDto.monthlySales != null\"> AND g.monthly_sales = #{goodsPointsMallMenuDto.monthlySales} </if>" +
            "<if test=\"goodsPointsMallMenuDto.totalSales != null\"> AND g.total_sales = #{goodsPointsMallMenuDto.totalSales} </if>" +
            "<if test=\"goodsPointsMallMenuDto.totalComments != null\"> AND g.total_comments = #{goodsPointsMallMenuDto.totalComments} </if>" +
            "<if test=\"goodsPointsMallMenuDto.stock != null\"> AND g.stock = #{goodsPointsMallMenuDto.stock} </if>" +
            "<if test=\"goodsPointsMallMenuDto.isHot != null\"> AND g.is_hot = #{goodsPointsMallMenuDto.isHot} </if>" +
            "<if test=\"goodsPointsMallMenuDto.isNew != null\"> AND g.is_new = #{goodsPointsMallMenuDto.isNew} </if>" +
            "<if test=\"goodsPointsMallMenuDto.status != null\"> AND g.status = #{goodsPointsMallMenuDto.status} </if>" +
            "<if test=\"goodsPointsMallMenuDto.menuId != null\"> AND m.id = #{goodsPointsMallMenuDto.menuId} </if>" +
            "<if test=\"goodsPointsMallMenuDto.menuName != null and goodsPointsMallMenuDto.menuName !=''\"> AND m.name like '%${goodsPointsMallMenuDto.menuName}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.goodsStatusIn2And4 != null and goodsPointsMallMenuDto.goodsStatusIn2And4 == true\"> AND g.status in (2, 4) </if>" +
            "<if test=\"goodsPointsMallMenuDto.preferentialName != null and goodsPointsMallMenuDto.preferentialName !=''\"> AND g.preferential_name like '%${goodsPointsMallMenuDto.preferentialName}%' </if>" +
            "<if test=\"goodsPointsMallMenuDto.packingCharges != null\"> AND g.packing_charges = #{goodsPointsMallMenuDto.packingCharges} </if>" +
            "</where> order by latelyMonthlySales desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinPointsMallMenuPointsMallOrderByLatelyMonthlySales(@Param("page") Page page, @Param("goodsPointsMallMenuDto") PointsMallGoodsMenuDto goodsPointsMallMenuDto);

    @ResultMap("BaseResultMap")
    @Select("select g.* from tb_points_mall_goods as g,tb_points_mall_coupons_goods_relation as cgr where g.id=cgr.goods_id and cgr.coupons_id=#{couponsId}")
    List<PointsMallGoods> getListByPointsMallCouponsId(Integer couponsId);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_points_mall_goods where status = 2 order by total_sales desc, create_time desc limit 3")
    List<PointsMallGoods> getListByTotalSalesTop3();

    @ResultMap("CustomResultMap")
    @Select("select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_points_mall_order_detail as od left join tb_points_mall_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6 and (o.create_time between #{startTime} and #{endTime})) as latelyMonthlySales " +
            "from tb_points_mall_goods as g " +
            "where g.status = 2 " +
            "order by latelyMonthlySales desc " +
            "limit #{topNumber}")
    List<Map<String, Object>> getListByLatelyMonthlySalesTopNumber(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("topNumber") Integer topNumber);

    @Update("update tb_points_mall_goods set monthly_sales=monthly_sales+#{num} where id=#{goodsId}")
    int updateMonthlySales(@Param("goodsId") Integer goodsId, @Param("num") Integer num);

    @Update("update tb_points_mall_goods set total_sales=total_sales+#{num} where id=#{goodsId}")
    int updateTotalSales(@Param("goodsId") Integer goodsId, @Param("num") Integer num);

    @Update("update tb_points_mall_goods set monthly_sales=0 ")
    int monthlySalesReset();

    @ResultMap("BaseResultMap")
    @Select("select * from tb_points_mall_goods where status = 2 order by create_time desc limit 1")
    List<PointsMallGoods> getListByLastestShelvesTop1();

    @ResultMap("CustomResultMap")
    @Select("<script>select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_points_mall_order_detail as od left join tb_points_mall_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6 and (o.create_time between #{startTime} and #{endTime})) as latelyMonthlySales " +
            "from tb_points_mall_goods g" +
            "<where> 1=1 and g.status in (2, 4) " +
            "<if test=\"goods.id != null\"> AND g.id = #{goods.id} </if>" +
            "<if test=\"goods.name != null and goods.name !=''\"> AND g.name like '%${goods.name}%' </if>" +
            "<if test=\"goods.mainImage != null and goods.mainImage !=''\"> AND g.main_image like '%${goods.mainImage}%' </if>" +
            "<if test=\"goods.subImages != null and goods.subImages !=''\"> AND g.sub_images like '%${goods.subImages}%' </if>" +
            "<if test=\"goods.detail != null and goods.detail !=''\"> AND g.detail like '%${goods.detail}%' </if>" +
            "<if test=\"goods.detailImages != null and goods.detailImages !=''\"> AND g.detail_images like '%${goods.detailImages}%' </if>" +
            "<if test=\"goods.price != null\"> AND g.price = #{goods.price} </if>" +
            "<if test=\"goods.stock != null\"> AND g.stock = #{goods.stock} </if>" +
            "<if test=\"goods.isNew != null\"> AND g.is_new = #{goods.isNew} </if>" +
            "<if test=\"goods.status != null\"> AND g.status = #{goods.status} </if>" +
            "<if test=\"goods.isSale != null\"> AND g.is_sale = #{goods.isSale} </if>" +
            "<if test=\"goods.salePrice != null\"> AND g.sale_price = #{goods.salePrice} </if>" +
            "<if test=\"goods.monthlySales != null\"> AND g.monthly_sales = #{goods.monthlySales} </if>" +
            "<if test=\"goods.totalSales != null\"> AND g.total_sales = #{goods.totalSales} </if>" +
            "<if test=\"goods.totalComments != null\"> AND g.total_comments = #{goods.totalComments} </if>" +
            "<if test=\"goods.isHot != null\"> AND g.is_hot = #{goods.isHot} </if>" +
            "<if test=\"goods.preferentialName != null and goods.preferentialName !=''\"> AND g.preferential_name like '%${goods.preferentialName}%' </if>" +
            "<if test=\"goods.packingCharges != null\"> AND g.packing_charges = #{goods.packingCharges} </if>" +
            "</where> order by g.id asc" +
            "</script>")
    Page<Map<String, Object>> recommendGoodsList(@Param("page") Page page, @Param("goods") PointsMallGoods goods, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("select " +
            "(select ifnull(sum(od.number), 0) from tb_points_mall_order_detail as od left join tb_points_mall_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6 and (o.create_time between #{startTime} and #{endTime})) as latelyMonthlySales " +
            "FROM tb_points_mall_goods g " +
            "where g.id = #{id}")
    long selectLatelyMonthlySalesById(@Param("id") Integer id, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}