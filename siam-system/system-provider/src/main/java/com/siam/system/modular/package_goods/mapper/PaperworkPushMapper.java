package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.PaperworkPush;
import com.siam.system.modular.package_goods.model.example.PaperworkPushExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

public interface PaperworkPushMapper extends BaseMapper<PaperworkPush> {
    int countByExample(PaperworkPushExample example);

    int deleteByExample(PaperworkPushExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PaperworkPush record);

    List<PaperworkPush> selectByExample(PaperworkPushExample example);

    PaperworkPush selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PaperworkPush record, @Param("example") PaperworkPushExample example);

    int updateByExample(@Param("record") PaperworkPush record, @Param("example") PaperworkPushExample example);

    int updateByPrimaryKeySelective(PaperworkPush record);

    int updateByPrimaryKey(PaperworkPush record);

    @ResultMap("BaseResultMap")
    @Select("<script>select pp.* from tb_paperwork_push pp" +
            "<where> 1=1 " +
            "<if test=\"paperworkPush.id != null\"> AND pp.id = #{paperworkPush.id} </if>" +
            "<if test=\"paperworkPush.name != null and paperworkPush.name !=''\"> AND pp.name like '%${paperworkPush.name}%' </if>" +
            "<if test=\"paperworkPush.startCreateTime != null\"> AND DATE_FORMAT(pp.create_time, '%Y/%m/%d') &gt;= #{paperworkPush.startCreateTime} </if>" +
            "<if test=\"paperworkPush.endCreateTime != null\"> AND DATE_FORMAT(pp.create_time, '%Y/%m/%d') &lt;= #{paperworkPush.endCreateTime} </if>" +
            "</where> order by pp.id desc" +
            "</script>")
    Page<Map<String, Object>> getListByPage(@Param("page") Page page, @Param("paperworkPush") PaperworkPush paperworkPush);
}