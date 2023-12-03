package com.siam.system.modular.package_goods.service_impl;

import com.siam.system.modular.package_goods.entity.ScheduledTask;
import com.siam.system.modular.package_goods.mapper.ScheduledTaskMapper;
import com.siam.system.modular.package_goods.model.example.ScheduledTaskExample;
import com.siam.system.modular.package_goods.service.ScheduledTaskService;
import com.siam.system.modular.package_goods.entity.ScheduledTask;
import com.siam.system.modular.package_goods.model.example.ScheduledTaskExample;
import com.siam.system.modular.package_goods.mapper.ScheduledTaskMapper;
import com.siam.system.modular.package_goods.service.ScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    @Autowired
    private ScheduledTaskMapper scheduledTaskMapper;

    public int countByExample(ScheduledTaskExample example){
        return scheduledTaskMapper.countByExample(example);
    }

    public void deleteByExample(ScheduledTaskExample example){
        scheduledTaskMapper.deleteByExample(example);
    }

    public void insertSelective(ScheduledTask record){
        scheduledTaskMapper.insertSelective(record);
    }

    public List<ScheduledTask> selectByExample(ScheduledTaskExample example){
        return scheduledTaskMapper.selectByExample(example);
    }

    public ScheduledTask selectByPrimaryKey(Integer id){
        return scheduledTaskMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(ScheduledTask record, ScheduledTaskExample example){
        scheduledTaskMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(ScheduledTask record){
        scheduledTaskMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByStartScheduledTask(String code) {
        return scheduledTaskMapper.updateByStartScheduledTask(code);
    }

    @Override
    public int updateByEndScheduledTask(String code) {
        return scheduledTaskMapper.updateByEndScheduledTask(code);
    }

    @Override
    public ScheduledTask selectByCode(String code) {
        return scheduledTaskMapper.selectByCode(code);
    }
}