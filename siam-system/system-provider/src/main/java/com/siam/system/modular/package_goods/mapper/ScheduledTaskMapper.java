package com.siam.system.modular.package_goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_goods.entity.ScheduledTask;
import com.siam.system.modular.package_goods.model.example.ScheduledTaskExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ScheduledTaskMapper extends BaseMapper<ScheduledTask> {
    int countByExample(ScheduledTaskExample example);

    int deleteByExample(ScheduledTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(ScheduledTask record);

    List<ScheduledTask> selectByExample(ScheduledTaskExample example);

    ScheduledTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScheduledTask record, @Param("example") ScheduledTaskExample example);

    int updateByExample(@Param("record") ScheduledTask record, @Param("example") ScheduledTaskExample example);

    int updateByPrimaryKeySelective(ScheduledTask record);

    int updateByPrimaryKey(ScheduledTask record);

    @Update("update tb_scheduled_task set state = 2, last_start_time = now() where state = 1 and code = #{code}")
    int updateByStartScheduledTask(@Param("code") String code);

    @Update("update tb_scheduled_task set state = 1, last_end_time = now() where state = 2 and code = #{code}")
    int updateByEndScheduledTask(@Param("code") String code);

    @ResultMap("BaseResultMap")
    @Select("select * from tb_scheduled_task where code = #{code} limit 1")
    ScheduledTask selectByCode(@Param("code") String code);
}