package com.siam.system.modular.package_goods.service_impl;

import com.siam.system.modular.package_goods.entity.ScheduledTaskLog;
import com.siam.system.modular.package_goods.mapper.ScheduledTaskLogMapper;
import com.siam.system.modular.package_goods.model.example.ScheduledTaskLogExample;
import com.siam.system.modular.package_goods.service.ScheduledTaskLogService;
import com.siam.system.modular.package_goods.entity.ScheduledTaskLog;
import com.siam.system.modular.package_goods.model.example.ScheduledTaskLogExample;
import com.siam.system.modular.package_goods.mapper.ScheduledTaskLogMapper;
import com.siam.system.modular.package_goods.service.ScheduledTaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTaskLogServiceImpl implements ScheduledTaskLogService {

    @Autowired
    private ScheduledTaskLogMapper scheduledTaskLogMapper;

    public int countByExample(ScheduledTaskLogExample example){
        return scheduledTaskLogMapper.countByExample(example);
    }

    public void deleteByExample(ScheduledTaskLogExample example){
        scheduledTaskLogMapper.deleteByExample(example);
    }

    public void insertSelective(ScheduledTaskLog record){
        scheduledTaskLogMapper.insertSelective(record);
    }

    public List<ScheduledTaskLog> selectByExample(ScheduledTaskLogExample example){
        return scheduledTaskLogMapper.selectByExample(example);
    }

    public ScheduledTaskLog selectByPrimaryKey(Integer id){
        return scheduledTaskLogMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(ScheduledTaskLog record, ScheduledTaskLogExample example){
        scheduledTaskLogMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(ScheduledTaskLog record){
        scheduledTaskLogMapper.updateByPrimaryKeySelective(record);
    }
}