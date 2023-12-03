package com.siam.system.modular.package_goods.service_impl;

import com.siam.system.modular.package_goods.entity.SmsLog;
import com.siam.system.modular.package_goods.mapper.SmsLogMapper;
import com.siam.system.modular.package_goods.model.example.SmsLogExample;
import com.siam.system.modular.package_goods.service.SmsLogService;
import com.siam.system.modular.package_goods.model.example.SmsLogExample;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_goods.entity.SmsLog;
import com.siam.system.modular.package_goods.mapper.SmsLogMapper;
import com.siam.system.modular.package_goods.service.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    public int countByExample(SmsLogExample example){
        return smsLogMapper.countByExample(example);
    }

    public void insertSelective(SmsLog record){
        smsLogMapper.insertSelective(record);
    }

    public List<SmsLog> selectByExample(SmsLogExample example){
        return smsLogMapper.selectByExample(example);
    }

    public SmsLog selectByPrimaryKey(Long id){
        return smsLogMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(SmsLog record, SmsLogExample example){
        smsLogMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(SmsLog record){
        smsLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SmsLog getLastLog(String mobile, String type, int minutes) {
        SmsLog smsLog = smsLogMapper.getLastLog(mobile, type);
        if(smsLog != null){
            long seconds = DateUtilsPlus.diffSeconds(new Date(), smsLog.getCreateTime());
            if(seconds > minutes * 60){
                return null;
            }
        }
        return smsLog;
    }
}
