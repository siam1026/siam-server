package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MemberTradeRecord;
import com.siam.system.modular.package_user.mapper.MemberTradeRecordMapper;
import com.siam.system.modular.package_user.model.example.MemberTradeRecordExample;
import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberTradeRecordServiceImpl implements MemberTradeRecordService {
    @Autowired
    private MemberTradeRecordMapper memberTradeRecordMapper;

    public int countByExample(MemberTradeRecordExample example){
        return memberTradeRecordMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        memberTradeRecordMapper.deleteByPrimaryKey(id);
    }

    public void insertSelective(MemberTradeRecord record){
        memberTradeRecordMapper.insertSelective(record);
    }

    public List<MemberTradeRecord> selectByExample(MemberTradeRecordExample example){
        return memberTradeRecordMapper.selectByExample(example);
    }

    public MemberTradeRecord selectByPrimaryKey(Integer id){
        return memberTradeRecordMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(MemberTradeRecord record, MemberTradeRecordExample example){
        memberTradeRecordMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(MemberTradeRecord record){
        memberTradeRecordMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, MemberTradeRecord memberTradeRecord) {
        Page<Map<String, Object>> page = memberTradeRecordMapper.getListByPage(new Page(pageNo, pageSize), memberTradeRecord);
        return page;
    }

    @Override
    public MemberTradeRecord selectByOutTradeNo(String outTradeNo) {
        return memberTradeRecordMapper.selectByOutTradeNo(outTradeNo);
    }

    @Override
    public BigDecimal selectSumIncome(MemberTradeRecord memberTradeRecord, Date startTime, Date endTime) {
        return memberTradeRecordMapper.selectSumIncome(memberTradeRecord, startTime, endTime);
    }

    @Override
    public BigDecimal selectSumExpense(MemberTradeRecord memberTradeRecord, Date startTime, Date endTime) {
        return memberTradeRecordMapper.selectSumExpense(memberTradeRecord, startTime, endTime);
    }
}