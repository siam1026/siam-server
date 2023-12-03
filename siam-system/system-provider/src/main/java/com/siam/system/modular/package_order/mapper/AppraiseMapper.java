package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.Appraise;
import com.siam.system.modular.package_order.model.example.AppraiseExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface AppraiseMapper extends BaseMapper<Appraise> {
    int countByExample(AppraiseExample example);

    int deleteByExample(AppraiseExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Appraise record);

    List<Appraise> selectByExample(AppraiseExample example);

    Appraise selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Appraise record, @Param("example") AppraiseExample example);

    int updateByExample(@Param("record") Appraise record, @Param("example") AppraiseExample example);

    int updateByPrimaryKeySelective(Appraise record);

    int updateByPrimaryKey(Appraise record);

    @ResultMap("BaseResultMap")
    @Select("<script>select a.* from tb_appraise a" +
            "<where> 1=1 " +
            "<if test=\"appraise.id != null\"> AND a.id = #{appraise.id} </if>" +
            "<if test=\"appraise.memberId != null\"> AND a.member_id = #{appraise.memberId} </if>" +
            "<if test=\"appraise.orderId != null\"> AND a.order_id = #{appraise.orderId} </if>" +
            "<if test=\"appraise.shopId != null\"> AND a.shop_id = #{appraise.shopId} </if>" +
            "<if test=\"appraise.appraiseType != null\"> AND a.appraise_type = #{appraise.appraiseType} </if>" +
            "<if test=\"appraise.level != null\"> AND a.level = #{appraise.level} </if>" +
            "<if test=\"appraise.startCreateTime != null\"> AND DATE_FORMAT(a.create_time, '%Y/%m/%d') &gt;= #{appraise.startCreateTime} </if>" +
            "<if test=\"appraise.endCreateTime != null\"> AND DATE_FORMAT(a.create_time, '%Y/%m/%d') &lt;= #{appraise.endCreateTime} </if>" +
            "</where> order by a.id desc" +
            "</script>")
    Page<Appraise> getListByPage(@Param("page") Page page, @Param("appraise") Appraise appraise);

    @ResultMap("CustomResultMap")
    @Select("<script>select a.*, s.name as shopName, m.username, m.head_img as headImg, o.order_no as orderNo, " +
            "(SELECT if(COUNT(*)>0, true, false) FROM tb_give_like WHERE TYPE = 1 AND member_id = #{appraise.memberId} AND appraise_id = a.id) as isGiveLike " +
            "from tb_appraise a " +
            "left join tb_shop s on s.id = a.shop_id " +
            "left join tb_member m on m.id = a.member_id " +
            "left join tb_order o on o.id = a.order_id " +
            "<where> 1=1 " +
            "<if test=\"appraise.id != null\"> AND a.id = #{appraise.id} </if>" +
            /*"<if test=\"appraise.memberId != null\"> AND a.member_id = #{appraise.memberId} </if>" +*/
            "<if test=\"appraise.orderId != null\"> AND a.order_id = #{appraise.orderId} </if>" +
            "<if test=\"appraise.shopId != null\"> AND a.shop_id = #{appraise.shopId} </if>" +
            "<if test=\"appraise.appraiseType != null\"> AND a.appraise_type = #{appraise.appraiseType} </if>" +
            "<if test=\"appraise.level != null\"> AND a.level = #{appraise.level} </if>" +
            "<if test=\"appraise.startCreateTime != null\"> AND DATE_FORMAT(a.create_time, '%Y/%m/%d') &gt;= #{appraise.startCreateTime} </if>" +
            "<if test=\"appraise.endCreateTime != null\"> AND DATE_FORMAT(a.create_time, '%Y/%m/%d') &lt;= #{appraise.endCreateTime} </if>" +
            "</where> order by a.id desc" +
            "</script>")
    Page<Map<String, Object>> getMapListByPage(@Param("page") Page page, @Param("appraise") Appraise appraise);

    @Select("SELECT if(COUNT(*)>0, false, true) FROM tb_appraise WHERE order_id = #{appraise.orderId} AND member_id = #{appraise.memberId}")
    boolean getIsAllowAppraise(@Param("appraise") Appraise appraise);
}