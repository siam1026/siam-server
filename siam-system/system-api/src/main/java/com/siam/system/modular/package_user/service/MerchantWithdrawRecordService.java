package com.siam.system.modular.package_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.MerchantWithdrawRecord;
import com.siam.system.modular.package_user.model.example.MerchantWithdrawRecordExample;
import com.siam.system.modular.package_user.model.param.MerchantWithdrawRecordParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 *  jianyang
 */
public interface MerchantWithdrawRecordService extends IService<MerchantWithdrawRecord> {

    int countByExample(MerchantWithdrawRecordExample example);

    void deleteByPrimaryKey(Integer id);

    void insert(MerchantWithdrawRecordParam param);

    MerchantWithdrawRecord selectByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(MerchantWithdrawRecord merchantWithdrawRecord);

    Page getListByPage(MerchantWithdrawRecordParam param);

    Page getListByPageJoinShop(MerchantWithdrawRecordParam param);

    Map<String, Object> statisticalAmount(MerchantWithdrawRecordParam param);

    /**
     * 审核申请体现商家信息
     *
     * @return
     * @author 暹罗
     */
    void auditApplyWithdraw(MerchantWithdrawRecordParam param);

    BasicResult countByAuditStatus(int auditStatus);
}
