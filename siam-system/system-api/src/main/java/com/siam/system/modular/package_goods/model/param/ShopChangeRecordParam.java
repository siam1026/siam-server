package com.siam.system.modular.package_goods.model.param;

import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ShopChangeRecordParam {
    @NotNull(message = "主键id不能为空", groups = {ValidGroupOfId.class})
    private Integer id;

    private Integer shopId;

    private String name;

    private String province;

    private String city;

    private String area;

    private String street;

    private String managePrimary;

    private String manageMinor;

    private String shopImg;

    private String shopWithinImg;

    private String shopLogoImg;

    private String certificateType1;

    private String certificateImg1;

    private String certificateType2;

    private String certificateImg2;

    private String specialType;

    private String specialImg;

    private String takeOutPhone;

    private String contactRealname;

    private String contactPhone;

    private String announcement;

    private String briefIntroduction;

    private String businessLicense;

    private String idCardFrontSide;

    private String idCardBackSide;

    private String applyChangeContent;

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
}