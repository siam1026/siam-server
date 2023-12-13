package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.model.dto.MemberBillingRecordDto;
import com.siam.system.modular.package_user.model.example.MemberBillingRecordExample;
import com.siam.system.modular.package_user.model.param.MemberBillingRecordParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  暹罗
 */
public interface MemberBillingRecordService {

    void deleteByPrimaryKey(Integer id);

    void insertSelective(MemberBillingRecord memberBillingRecord);

    MemberBillingRecord selectByPrimaryKey(MemberBillingRecordParam param);

    void updateByPrimaryKeySelective(MemberBillingRecord memberBillingRecord);

    List<MemberBillingRecord> selectByExample(MemberBillingRecordExample example);

    Page getListByPage(MemberBillingRecordParam param);

    /**
     * 查询用户某段时间内的货币增减数额
     */
    BigDecimal selectSumNumber(MemberBillingRecordDto memberBillingRecordDto, Date startTime, Date endTime);

    void settledReward();
}