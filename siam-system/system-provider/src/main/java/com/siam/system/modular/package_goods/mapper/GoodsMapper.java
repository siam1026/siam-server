package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.model.example.GoodsExample;
import com.siam.system.modular.package_goods.model.dto.GoodsMenuDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface GoodsMapper extends BaseMapper<Goods> {
    int countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    @ResultMap("BaseResultMap")
    @Select("<script>select g.* from tb_goods g" +
            "<where> 1=1 " +
            "<if test=\"goods.id != null\"> AND g.id = #{goods.id} </if>" +
            "<if test=\"goods.shopId != null\"> AND g.shop_id = #{goods.shopId} </if>" +
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
    Page<Goods> getListByPage(@Param("page") Page page, @Param("goods") Goods goods);

    @ResultMap("CustomResultMap")
    @Select("<script>select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_order_detail as od left join tb_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6) as totalSales, " +
            "m.id AS menuId, m.name AS menuName FROM tb_goods g " +
            "left join tb_menu_goods_relation mgr ON g.id = mgr.goods_id " +
            "left join tb_menu m ON m.id = mgr.menu_id " +
            "<where> 1=1 " +
            "<if test=\"goodsMenuDto.id != null\"> AND g.id = #{goodsMenuDto.id} </if>" +
            "<if test=\"goodsMenuDto.shopId != null\"> AND g.shop_id = #{goodsMenuDto.shopId} </if>" +
            "<if test=\"goodsMenuDto.name != null and goodsMenuDto.name !=''\"> AND g.name like '%${goodsMenuDto.name}%' </if>" +
            "<if test=\"goodsMenuDto.mainImage != null and goodsMenuDto.mainImage !=''\"> AND g.main_image like '%${goodsMenuDto.mainImage}%' </if>" +
            "<if test=\"goodsMenuDto.subImages != null and goodsMenuDto.subImages !=''\"> AND g.sub_images like '%${goodsMenuDto.subImages}%' </if>" +
            "<if test=\"goodsMenuDto.detail != null and goodsMenuDto.detail !=''\"> AND g.detail like '%${goodsMenuDto.detail}%' </if>" +
            "<if test=\"goodsMenuDto.detailImages != null and goodsMenuDto.detailImages !=''\"> AND g.detail_images like '%${goodsMenuDto.detailImages}%' </if>" +
            "<if test=\"goodsMenuDto.price != null\"> AND g.price = #{goodsMenuDto.price} </if>" +
            "<if test=\"goodsMenuDto.isSale != null\"> AND g.is_sale = #{goodsMenuDto.isSale} </if>" +
            "<if test=\"goodsMenuDto.salePrice != null\"> AND g.sale_price = #{goodsMenuDto.salePrice} </if>" +
            "<if test=\"goodsMenuDto.monthlySales != null\"> AND g.monthly_sales = #{goodsMenuDto.monthlySales} </if>" +
            "<if test=\"goodsMenuDto.totalSales != null\"> AND g.total_sales = #{goodsMenuDto.totalSales} </if>" +
            "<if test=\"goodsMenuDto.totalComments != null\"> AND g.total_comments = #{goodsMenuDto.totalComments} </if>" +
            "<if test=\"goodsMenuDto.stock != null\"> AND g.stock = #{goodsMenuDto.stock} </if>" +
            "<if test=\"goodsMenuDto.isHot != null\"> AND g.is_hot = #{goodsMenuDto.isHot} </if>" +
            "<if test=\"goodsMenuDto.isNew != null\"> AND g.is_new = #{goodsMenuDto.isNew} </if>" +
            "<if test=\"goodsMenuDto.status != null\"> AND g.status = #{goodsMenuDto.status} </if>" +
            "<if test=\"goodsMenuDto.menuId != null\"> AND m.id = #{goodsMenuDto.menuId} </if>" +
            "<if test=\"goodsMenuDto.menuName != null and goodsMenuDto.menuName !=''\"> AND m.name like '%${goodsMenuDto.menuName}%' </if>" +
            "<if test=\"goodsMenuDto.goodsStatusIn2And4 != null and goodsMenuDto.goodsStatusIn2And4 == true\"> AND g.status in (2, 4) </if>" +
            "<if test=\"goodsMenuDto.preferentialName != null and goodsMenuDto.preferentialName !=''\"> AND g.preferential_name like '%${goodsMenuDto.preferentialName}%' </if>" +
            "<if test=\"goodsMenuDto.packingCharges != null\"> AND g.packing_charges = #{goodsMenuDto.packingCharges} </if>" +
            "</where> order by g.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinMenu(@Param("page") Page page, @Param("goodsMenuDto") GoodsMenuDto goodsMenuDto);

    @ResultMap("CustomResultMap")
    @Select("<script>select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_order_detail as od left join tb_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6 and (o.create_time between #{goodsMenuDto.startTime} and #{goodsMenuDto.endTime})) as latelyMonthlySales, " +
            "m.id AS menuId, m.name AS menuName FROM tb_goods g " +
            "left join tb_menu_goods_relation mgr ON g.id = mgr.goods_id " +
            "left join tb_menu m ON m.id = mgr.menu_id " +
            "<where> 1=1 " +
            "<if test=\"goodsMenuDto.id != null\"> AND g.id = #{goodsMenuDto.id} </if>" +
            "<if test=\"goodsMenuDto.shopId != null\"> AND g.shop_id = #{goodsMenuDto.shopId} </if>" +
            "<if test=\"goodsMenuDto.name != null and goodsMenuDto.name !=''\"> AND g.name like '%${goodsMenuDto.name}%' </if>" +
            "<if test=\"goodsMenuDto.mainImage != null and goodsMenuDto.mainImage !=''\"> AND g.main_image like '%${goodsMenuDto.mainImage}%' </if>" +
            "<if test=\"goodsMenuDto.subImages != null and goodsMenuDto.subImages !=''\"> AND g.sub_images like '%${goodsMenuDto.subImages}%' </if>" +
            "<if test=\"goodsMenuDto.detail != null and goodsMenuDto.detail !=''\"> AND g.detail like '%${goodsMenuDto.detail}%' </if>" +
            "<if test=\"goodsMenuDto.detailImages != null and goodsMenuDto.detailImages !=''\"> AND g.detail_images like '%${goodsMenuDto.detailImages}%' </if>" +
            "<if test=\"goodsMenuDto.price != null\"> AND g.price = #{goodsMenuDto.price} </if>" +
            "<if test=\"goodsMenuDto.isSale != null\"> AND g.is_sale = #{goodsMenuDto.isSale} </if>" +
            "<if test=\"goodsMenuDto.salePrice != null\"> AND g.sale_price = #{goodsMenuDto.salePrice} </if>" +
            "<if test=\"goodsMenuDto.monthlySales != null\"> AND g.monthly_sales = #{goodsMenuDto.monthlySales} </if>" +
            "<if test=\"goodsMenuDto.totalSales != null\"> AND g.total_sales = #{goodsMenuDto.totalSales} </if>" +
            "<if test=\"goodsMenuDto.totalComments != null\"> AND g.total_comments = #{goodsMenuDto.totalComments} </if>" +
            "<if test=\"goodsMenuDto.stock != null\"> AND g.stock = #{goodsMenuDto.stock} </if>" +
            "<if test=\"goodsMenuDto.isHot != null\"> AND g.is_hot = #{goodsMenuDto.isHot} </if>" +
            "<if test=\"goodsMenuDto.isNew != null\"> AND g.is_new = #{goodsMenuDto.isNew} </if>" +
            "<if test=\"goodsMenuDto.status != null\"> AND g.status = #{goodsMenuDto.status} </if>" +
            "<if test=\"goodsMenuDto.menuId != null\"> AND m.id = #{goodsMenuDto.menuId} </if>" +
            "<if test=\"goodsMenuDto.menuName != null and goodsMenuDto.menuName !=''\"> AND m.name like '%${goodsMenuDto.menuName}%' </if>" +
            "<if test=\"goodsMenuDto.goodsStatusIn2And4 != null and goodsMenuDto.goodsStatusIn2And4 == true\"> AND g.status in (2, 4) </if>" +
            "<if test=\"goodsMenuDto.preferentialName != null and goodsMenuDto.preferentialName !=''\"> AND g.preferential_name like '%${goodsMenuDto.preferentialName}%' </if>" +
            "<if test=\"goodsMenuDto.packingCharges != null\"> AND g.packing_charges = #{goodsMenuDto.packingCharges} </if>" +
            "</where> order by latelyMonthlySales desc" +
            "</script>")
    Page<Map<String, Object>> getListByPageJoinMenuOrderByLatelyMonthlySales(@Param("page") Page page, @Param("goodsMenuDto") GoodsMenuDto goodsMenuDto);

    @ResultMap("BaseResultMap")
    @Select("select g.* from tb_goods as g,tb_coupons_goods_relation as cgr where g.id=cgr.goods_id and cgr.coupons_id=#{couponsId}")
    List<Goods> getListByCouponsId(Integer couponsId);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_goods where status = 2 order by total_sales desc, create_time desc limit 3")
    List<Goods> getListByTotalSalesTop3();

    @ResultMap("CustomResultMap")
    @Select("<script>select g.*, " +
            "(select ifnull(sum(od.number), 0) from tb_order_detail as od left join tb_order as o on od.order_id = o.id where od.goods_id = g.id and o.status = 6 and (o.create_time between #{startTime} and #{endTime})) as latelyMonthlySales " +
            "from tb_goods as g " +
            "left join tb_shop as s on s.id = g.shop_id " +
            "<where> 1=1 and s.status = 2 and g.status = 2 " +
            "<if test=\"shopId != null\"> AND s.id = #{shopId} </if>" +
            "</where> order by latelyMonthlySales desc, g.create_time asc limit #{topNumber}" +
            "</script>")
    List<Map<String, Object>> getListByLatelyMonthlySalesTopNumber(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("topNumber") Integer topNumber, @Param("shopId") Integer shopId);

    @Update("update tb_goods set monthly_sales=monthly_sales+#{num} where id=#{goodsId}")
    int updateMonthlySales(@Param("goodsId") Integer goodsId,@Param("num") Integer num);

    @Update("update tb_goods set total_sales=total_sales+#{num} where id=#{goodsId}")
    int updateTotalSales(@Param("goodsId") Integer goodsId,@Param("num") Integer num);

    @Update("update tb_goods set monthly_sales=0 ")
    int monthlySalesReset();

    @ResultMap("BaseResultMap")
    @Select("select * from tb_goods where status = 2 order by create_time desc limit 1")
    List<Goods> getListByLastestShelvesTop1();
}