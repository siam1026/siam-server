package com.siam.system.modular.package_user.model.param;

import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_user.entity.MerchantWithdrawRecord;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantWithdrawRecordParam extends MerchantWithdrawRecord {
    @NotNull(message = "主键id不能为空", groups = {ValidGroupOfId.class})
    private Integer id;

    private Integer merchantId;

    private BigDecimal withdrawAmount;

    private BigDecimal platformFee;

    private BigDecimal actualAmount;

    private Integer auditStatus;

    private String auditReason;

    private Date auditTime;

    private Date createTime;

    private Date updateTime;

    /**
     * 审核状态(1=通过 2=不通过)
     */
    @NotNull(message = "审核状态不能为空", groups = {ValidGroupOfAudit.class})
    @Range(min = 1, max = 2, message = "审核状态必须介于1~2之间", groups = {ValidGroupOfAudit.class})
    private Integer status;

    /**
     * 审核意见(当审核状态为不通过时必填)
     */
    private String opinion;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;
}