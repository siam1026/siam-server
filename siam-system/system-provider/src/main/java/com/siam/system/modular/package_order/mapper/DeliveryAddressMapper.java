package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.DeliveryAddress;
import com.siam.system.modular.package_order.model.example.DeliveryAddressExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface DeliveryAddressMapper extends BaseMapper<DeliveryAddress> {
    int countByExample(DeliveryAddressExample example);

    int deleteByExample(DeliveryAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(DeliveryAddress record);

    List<DeliveryAddress> selectByExample(DeliveryAddressExample example);

    DeliveryAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliveryAddress record, @Param("example") DeliveryAddressExample example);

    int updateByExample(@Param("record") DeliveryAddress record, @Param("example") DeliveryAddressExample example);

    int updateByPrimaryKeySelective(DeliveryAddress record);

    int updateByPrimaryKey(DeliveryAddress record);

    @ResultMap("BaseResultMap")
    @Select("<script>select da.* from tb_delivery_address da" +
            "<where> 1=1 " +
            "<if test=\"deliveryAddress.id != null\"> AND da.id = #{deliveryAddress.id} </if>" +
            "<if test=\"deliveryAddress.memberId != null\"> AND da.member_id = #{deliveryAddress.memberId} </if>" +
            "<if test=\"deliveryAddress.realname != null and deliveryAddress.realname !=''\"> AND da.realname like '%${deliveryAddress.realname}%' </if>" +
            "<if test=\"deliveryAddress.sex != null\"> AND da.sex = #{deliveryAddress.sex} </if>" +
            "<if test=\"deliveryAddress.phone != null and deliveryAddress.phone !=''\"> AND da.phone like '%${deliveryAddress.phone}%' </if>" +
            "<if test=\"deliveryAddress.province != null and deliveryAddress.province !=''\"> AND da.province like '%${deliveryAddress.province}%' </if>" +
            "<if test=\"deliveryAddress.city != null and deliveryAddress.city !=''\"> AND da.city like '%${deliveryAddress.city}%' </if>" +
            "<if test=\"deliveryAddress.area != null and deliveryAddress.area !=''\"> AND da.area like '%${deliveryAddress.area}%' </if>" +
            "<if test=\"deliveryAddress.street != null and deliveryAddress.street !=''\"> AND da.street like '%${deliveryAddress.street}%' </if>" +
            "<if test=\"deliveryAddress.isDefault != null\"> AND da.is_default = #{deliveryAddress.isDefault} </if>" +
            "</where> order by da.id asc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("deliveryAddress") DeliveryAddress deliveryAddress);

    @Update("update tb_delivery_address set is_default = 0, update_time = now() where member_id = #{memberId} and id != #{id} ")
    int updateIsDefaultExclusion(@Param("id") Integer id, @Param("memberId") Integer memberId);
}