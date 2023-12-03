package com.siam.system.modular.package_goods.service_impl;

import com.siam.system.modular.package_goods.entity.SystemUsageRecord;
import com.siam.system.modular.package_goods.mapper.SystemUsageRecordMapper;
import com.siam.system.modular.package_goods.model.example.SystemUsageRecordExample;
import com.siam.system.modular.package_goods.service.SystemUsageRecordService;
import com.siam.system.modular.package_goods.mapper.SystemUsageRecordMapper;
import com.siam.system.modular.package_goods.service.SystemUsageRecordService;
import com.siam.system.modular.package_goods.entity.SystemUsageRecord;
import com.siam.system.modular.package_goods.model.example.SystemUsageRecordExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SystemUsageRecordServiceImpl implements SystemUsageRecordService {

    @Autowired
    private SystemUsageRecordMapper systemUsageRecordMapper;

    public int countByExample(SystemUsageRecordExample example){
        return systemUsageRecordMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        systemUsageRecordMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(SystemUsageRecord record){
        systemUsageRecordMapper.insertSelective(record);
    }

    public List<SystemUsageRecord> selectByExample(SystemUsageRecordExample example){
        return systemUsageRecordMapper.selectByExample(example);
    }

    public SystemUsageRecord selectByPrimaryKey(Integer id){
        return systemUsageRecordMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(SystemUsageRecord record, SystemUsageRecordExample example){
        systemUsageRecordMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(SystemUsageRecord record){
        systemUsageRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int selectCountIntoShop(Integer shopId, Date startTime, Date endTime) {
        return systemUsageRecordMapper.selectCountIntoShop(shopId, startTime, endTime);
    }

    @Override
    public int selectCountIntoPointsMall(Date startTime, Date endTime) {
        return systemUsageRecordMapper.selectCountIntoPointsMall(startTime, endTime);
    }
}