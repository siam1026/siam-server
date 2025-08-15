package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.system.modular.package_goods.model.param.ShopChangeRecordParam;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.entity.ShopChangeRecord;
import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_goods.service.ShopChangeRecordService;
import com.siam.system.modular.package_goods.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/shopChangeRecord")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台门店重要信息变更记录模块相关接口", description = "AdminShopChangeRecordController")
public class AdminShopChangeRecordController {
    @Autowired
    private ShopChangeRecordService shopChangeRecordService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "门店重要信息变更记录列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) ShopChangeRecord shopChangeRecord, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = shopChangeRecordService.getListByPageJoinShop(shopChangeRecord.getPageNo(), shopChangeRecord.getPageSize(), shopChangeRecord);

        return BasicResult.success(page);
    }

    /**
     * 审核申请变更商家信息
     *
     * @return
     * @author 暹罗
     */
    @AdminPermission
    @PostMapping(value = "/audit")
    public BasicResult audit(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) ShopChangeRecordParam shopChangeRecordParam){
        BasicResult basicResult = new BasicResult();

        if(shopChangeRecordParam.getStatus() == Quantity.INT_2 && StringUtils.isBlank(shopChangeRecordParam.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        ShopChangeRecord dbShopChangeRecord = shopChangeRecordService.selectByPrimaryKey(shopChangeRecordParam.getId());
        if(dbShopChangeRecord.getAuditStatus()!=Quantity.INT_1){
            throw new StoneCustomerException("该记录已经审核过，不允许重复审核");
        }

        if(shopChangeRecordParam.getStatus() == Quantity.INT_1){
            //审核通过

            //修改审核状态
            ShopChangeRecord shopChangeRecord = new ShopChangeRecord();
            shopChangeRecord.setId(dbShopChangeRecord.getId());
            shopChangeRecord.setAuditStatus(Quantity.INT_2);
            shopChangeRecord.setAuditTime(new Date());
            shopChangeRecord.setUpdateTime(new Date());
            shopChangeRecordService.updateByPrimaryKeySelective(shopChangeRecord);

            //修改店铺信息
            Shop dbShop = shopService.getById(dbShopChangeRecord.getShopId());
            Shop updateShop = new Shop();
            updateShop.setId(dbShop.getId());
            updateShop.setName(dbShopChangeRecord.getName());
            updateShop.setProvince(dbShopChangeRecord.getProvince());
            updateShop.setCity(dbShopChangeRecord.getCity());
            updateShop.setArea(dbShopChangeRecord.getArea());
            updateShop.setStreet(dbShopChangeRecord.getStreet());
            updateShop.setManagePrimary(dbShopChangeRecord.getManagePrimary());
            updateShop.setManageMinor(dbShopChangeRecord.getManageMinor());
            updateShop.setShopImg(dbShopChangeRecord.getShopImg());
            updateShop.setShopWithinImg(dbShopChangeRecord.getShopWithinImg());
            updateShop.setShopLogoImg(dbShopChangeRecord.getShopLogoImg());
            updateShop.setCertificateType1(dbShopChangeRecord.getCertificateType1());
            updateShop.setCertificateImg1(dbShopChangeRecord.getCertificateImg1());
            updateShop.setCertificateType2(dbShopChangeRecord.getCertificateType2());
            updateShop.setCertificateImg2(dbShopChangeRecord.getCertificateImg2());
            updateShop.setSpecialType(dbShopChangeRecord.getSpecialType());
            updateShop.setSpecialImg(dbShopChangeRecord.getSpecialImg());
            updateShop.setTakeOutPhone(dbShopChangeRecord.getTakeOutPhone());
            updateShop.setContactRealname(dbShopChangeRecord.getContactRealname());
            updateShop.setContactPhone(dbShopChangeRecord.getContactPhone());
            /*updateShop.setAnnouncement(dbShopChangeRecord.getAnnouncement());*/
            updateShop.setBriefIntroduction(dbShopChangeRecord.getBriefIntroduction());
            updateShop.setBusinessLicense(dbShopChangeRecord.getBusinessLicense());
            updateShop.setIdCardFrontSide(dbShopChangeRecord.getIdCardFrontSide());
            updateShop.setIdCardBackSide(dbShopChangeRecord.getIdCardBackSide());
            updateShop.setHouseNumber(dbShopChangeRecord.getHouseNumber());
            updateShop.setLongitude(dbShopChangeRecord.getLongitude());
            updateShop.setLatitude(dbShopChangeRecord.getLatitude());
            shopService.updateById(updateShop);
        }else if(shopChangeRecordParam.getStatus() == Quantity.INT_2){
            //审核不通过

            //修改审核状态
            ShopChangeRecord shopChangeRecord = new ShopChangeRecord();
            shopChangeRecord.setId(dbShopChangeRecord.getId());
            shopChangeRecord.setAuditStatus(Quantity.INT_3);
            shopChangeRecord.setAuditReason(shopChangeRecordParam.getOpinion());
            shopChangeRecord.setAuditTime(new Date());
            shopChangeRecord.setUpdateTime(new Date());
            shopChangeRecordService.updateByPrimaryKeySelective(shopChangeRecord);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("审核成功");
        return basicResult;
    }
}