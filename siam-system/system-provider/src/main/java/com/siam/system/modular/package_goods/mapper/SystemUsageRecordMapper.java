package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.SystemUsageRecord;
import com.siam.system.modular.package_goods.model.example.SystemUsageRecordExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface SystemUsageRecordMapper extends BaseMapper<SystemUsageRecord> {
    int countByExample(SystemUsageRecordExample example);

    int deleteByExample(SystemUsageRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SystemUsageRecord record);

    List<SystemUsageRecord> selectByExample(SystemUsageRecordExample example);

    SystemUsageRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemUsageRecord record, @Param("example") SystemUsageRecordExample example);

    int updateByExample(@Param("record") SystemUsageRecord record, @Param("example") SystemUsageRecordExample example);

    int updateByPrimaryKeySelective(SystemUsageRecord record);

    int updateByPrimaryKey(SystemUsageRecord record);

    @Select("<script>select count(DISTINCT(sur.member_id)) from tb_system_usage_record as sur "+
            "<where> sur.type = 'intoShop' and (sur.create_time between #{startTime} and #{endTime})" +
            "<if test=\"shopId != null\"> AND sur.shop_id = #{shopId} </if>" +
            "</where>"+
            "</script>")
    int selectCountIntoShop(@Param("shopId") Integer shopId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("<script>select count(DISTINCT(sur.member_id)) from tb_system_usage_record as sur "+
            "<where> sur.type = 'intoPointsMall' and (sur.create_time between #{startTime} and #{endTime})" +
            "</where>"+
            "</script>")
    int selectCountIntoPointsMall(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}