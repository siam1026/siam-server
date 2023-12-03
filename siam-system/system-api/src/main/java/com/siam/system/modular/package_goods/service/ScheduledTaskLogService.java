package com.siam.system.modular.package_goods.service;

import com.siam.system.modular.package_goods.entity.ScheduledTaskLog;
import com.siam.system.modular.package_goods.model.example.ScheduledTaskLogExample;

import java.util.List;

public interface ScheduledTaskLogService {
    int countByExample(ScheduledTaskLogExample example);

    void insertSelective(ScheduledTaskLog record);

    List<ScheduledTaskLog> selectByExample(ScheduledTaskLogExample example);

    ScheduledTaskLog selectByPrimaryKey(Integer id);

    void updateByExampleSelective(ScheduledTaskLog record, ScheduledTaskLogExample example);

    void updateByPrimaryKeySelective(ScheduledTaskLog record);
}
