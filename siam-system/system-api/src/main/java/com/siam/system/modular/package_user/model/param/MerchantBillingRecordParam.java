package com.siam.system.modular.package_user.model.param;

import com.siam.system.modular.package_user.entity.MerchantBillingRecord;
import lombok.Data;

/**
 * 用户账单记录表
 *
 * @author 暹罗
 */
@Data
public class MerchantBillingRecordParam extends MerchantBillingRecord {

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;
}