package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.siam.system.modular.package_user.entity.MemberWithdrawRecord;
import com.siam.system.modular.package_user.model.example.MemberWithdrawRecordExample;
import com.siam.system.modular.package_user.model.param.MemberWithdrawRecordParam;

import java.util.Map;

/**
 *  暹罗
 */
public interface MemberWithdrawRecordService extends IService<MemberWithdrawRecord> {

    int countByExample(MemberWithdrawRecordExample example);

    void deleteByPrimaryKey(Integer id);

    void insert(MemberWithdrawRecordParam param);

    MemberWithdrawRecord selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(MemberWithdrawRecord memberWithdrawRecord);

    Page getListByPage(MemberWithdrawRecordParam param);

    Page getListByPageJoinMember(MemberWithdrawRecordParam param);

    Map<String, Object> statisticalAmount(MemberWithdrawRecordParam param);

    void autoPayment();

    /**
     * 审核申请体现用户信息
     *
     * @return
     * @author 暹罗
     */
    void auditApplyWithdraw(MemberWithdrawRecordParam param);
}
