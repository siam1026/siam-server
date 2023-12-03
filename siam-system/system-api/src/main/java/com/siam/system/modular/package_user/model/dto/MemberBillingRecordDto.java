package com.siam.system.modular.package_user.model.dto;

import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import lombok.Data;

import java.util.List;

@Data
public class MemberBillingRecordDto extends MemberBillingRecord {

    //账单类型列表
    private List<Integer> typeList;

}