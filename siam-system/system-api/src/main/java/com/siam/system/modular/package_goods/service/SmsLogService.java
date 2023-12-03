package com.siam.system.modular.package_goods.service;

import com.siam.system.modular.package_goods.entity.SmsLog;
import com.siam.system.modular.package_goods.model.example.SmsLogExample;

import java.util.List;

public interface SmsLogService {
    int countByExample(SmsLogExample example);

    void insertSelective(SmsLog record);

    List<SmsLog> selectByExample(SmsLogExample example);

    SmsLog selectByPrimaryKey(Long id);

    void updateByExampleSelective(SmsLog record, SmsLogExample example);

    void updateByPrimaryKeySelective(SmsLog record);

    /**
     * 获取最新的短信验证码记录
     **/
    SmsLog getLastLog(String mobile, String type, int minutes);
}
