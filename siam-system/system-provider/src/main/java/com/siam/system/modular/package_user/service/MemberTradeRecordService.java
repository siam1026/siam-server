package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.model.example.MemberTradeRecordExample;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MemberTradeRecordService {
    int countByExample(MemberTradeRecordExample example);

    void deleteByPrimaryKey(Integer id);

    void insertSelective(MemberTradeRecord record);

    List<MemberTradeRecord> selectByExample(MemberTradeRecordExample example);

    MemberTradeRecord selectByPrimaryKey(Integer id);

    void updateByExampleSelective(MemberTradeRecord record, MemberTradeRecordExample example);

    void updateByPrimaryKeySelective(MemberTradeRecord record);

    Page getListByPage(int pageNo, int pageSize, MemberTradeRecord memberTradeRecord);

    MemberTradeRecord selectByOutTradeNo(String outTradeNo);

    BigDecimal selectSumIncome(MemberTradeRecord memberTradeRecord, Date startTime, Date endTime);

    BigDecimal selectSumExpense(MemberTradeRecord memberTradeRecord, Date startTime, Date endTime);
}