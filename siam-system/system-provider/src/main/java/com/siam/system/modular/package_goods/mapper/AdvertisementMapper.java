package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.Advertisement;
import com.siam.system.modular.package_goods.model.example.AdvertisementExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface AdvertisementMapper extends BaseMapper<Advertisement> {
    int countByExample(AdvertisementExample example);

    int deleteByExample(AdvertisementExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Advertisement record);

    List<Advertisement> selectByExample(AdvertisementExample example);

    Advertisement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByExample(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    int updateByPrimaryKeySelective(Advertisement record);

    int updateByPrimaryKey(Advertisement record);

    @ResultMap("BaseResultMap")
    @Select("<script>select a.* from tb_advertisement a" +
            "<where> 1=1 " +
            "<if test=\"advertisement.id != null\"> AND a.id = #{advertisement.id} </if>" +
            "<if test=\"advertisement.imageName != null and advertisement.imageName !=''\"> AND a.image_name like '%${advertisement.imageName}%' </if>" +
            "<if test=\"advertisement.imagePath != null and advertisement.imagePath !=''\"> AND a.image_path like '%${advertisement.imagePath}%' </if>" +
            "<if test=\"advertisement.description != null and advertisement.description !=''\"> AND a.description like '%${advertisement.description}%' </if>" +
            "<if test=\"advertisement.type != null\"> AND a.type = #{advertisement.type} </if>" +
            "<if test=\"advertisement.sortNumber != null\"> AND a.sort_number = #{advertisement.sortNumber} </if>" +
            "<if test=\"advertisement.imageLinkUrl != null and advertisement.imageLinkUrl !=''\"> AND a.image_link_url like '%${advertisement.imageLinkUrl}%' </if>" +
            "<if test=\"advertisement.startCreateTime != null\"> AND DATE_FORMAT(a.create_time, '%Y/%m/%d') &gt;= #{advertisement.startCreateTime} </if>" +
            "<if test=\"advertisement.endCreateTime != null\"> AND DATE_FORMAT(a.create_time, '%Y/%m/%d') &lt;= #{advertisement.endCreateTime} </if>" +
            "</where> order by a.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("advertisement") Advertisement advertisement);
}