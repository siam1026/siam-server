package com.siam.system.modular.package_goods.controller.merchant;

import com.siam.package_common.annoation.MerchantPermission;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.entity.ShopChangeRecord;
import com.siam.system.modular.package_goods.service.ShopChangeRecordService;
import com.siam.system.modular.package_goods.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

@RestController
@RequestMapping(value = "/rest/merchant/shop")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端门店模块相关接口", description = "MerchantShopController")
public class MerchantShopController {

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @Autowired
    private ShopService shopService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private ShopChangeRecordService shopChangeRecordService;

    @Autowired
    private SettingService settingService;

    @ApiOperation(value = "申请开店-提交门店信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "门店管理表主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "code", value = "门店编码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "name", value = "门店名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "distance", value = "距离", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省份", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "city", value = "城市", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区域", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "street", value = "街道/详细收货地址", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "isDisabled", value = "是否禁用 0=启用 1=禁用", required = true, paramType = "query", dataType = "String"),
    })
    @PostMapping(value = "/apply")
    public BasicResult apply(@RequestBody @Validated(value = {}) Shop shop, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if(loginMerchant.getAuditStatus() == Quantity.INT_2){
            throw new StoneCustomerException("您的门店信息已审核通过，不能重复申请");
        }

        if(loginMerchant.getShopId() != shop.getId()){
            throw new StoneCustomerException("您没有权限提交此门店信息");
        }

        Setting setting = settingService.selectCurrent();
        shop.setStartDeliveryPrice(setting.getStartDeliveryPrice());

        //修改申请开店的门店信息
        shop.setUpdateTime(new Date());
        shopService.updateByPrimaryKeySelective(shop);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("提交成功");
        return basicResult;
    }

    @ApiOperation(value = "获取登录商家账号的开店信息")
    @PostMapping(value = "/getLoginMerchantShopInfo")
    public BasicResult getLoginMerchantShopInfo(@RequestBody @Validated(value = {}) Shop param){
        BasicData basicResult = new BasicData();
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Shop shop = shopService.selectByPrimaryKey(loginMerchant.getShopId());
        if(shop == null){
            throw new StoneCustomerException("无法获取开店信息，请稍后重试");
        }

        basicResult.setData(shop);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    /**
     * 修改门店信息
     *
     * @return
     * @author 暹罗
     */
    @MerchantPermission
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Shop shop, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if (loginMerchant.getShopId() != shop.getId()){
            throw new StoneCustomerException("您没有权限修改此门店信息");
        }

        Shop dbShop = shopService.selectByPrimaryKey(shop.getId());
        if(dbShop == null){
            throw new StoneCustomerException("该店铺信息不存在");
        }

        //TODO-有些信息不能直接修改，需要管理员审核

        shop.setUpdateTime(new Date());
        shopService.updateByPrimaryKeySelective(shop);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    /**
     * 申请变更门店重要信息
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/applyChangeImportantData")
    public BasicResult applyChangeImportantData(@RequestBody @Validated(value = {}) ShopChangeRecord shopChangeRecord, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        if (loginMerchant.getShopId() != shopChangeRecord.getShopId()){
            throw new StoneCustomerException("您没有权限修改此门店信息");
        }

        Shop dbShop = shopService.selectByPrimaryKey(shopChangeRecord.getShopId());
        if(dbShop == null){
            throw new StoneCustomerException("该店铺信息不存在");
        }

        //获取变更内容
        String applyChangeContent = getApplyChangeContent(dbShop, shopChangeRecord);
        if(StringUtils.isBlank(applyChangeContent)){
            throw new StoneCustomerException("没有变更信息，提交失败");
        }
        shopChangeRecord.setApplyChangeContent(applyChangeContent);

        //如果申请记录为审核中，则进行修改操作，否则进行新增操作
        ShopChangeRecord dbShopChangeRecord = shopChangeRecordService.selectLastestByShopId(shopChangeRecord.getShopId());
        if(dbShopChangeRecord==null || dbShopChangeRecord.getAuditStatus()==Quantity.INT_2  || dbShopChangeRecord.getAuditStatus()==Quantity.INT_3){
            //新增操作
            shopChangeRecord.setAuditStatus(Quantity.INT_1);
            shopChangeRecord.setAuditReason(null);
            shopChangeRecord.setAuditTime(null);
            shopChangeRecord.setCreateTime(new Date());
            shopChangeRecord.setUpdateTime(new Date());
            shopChangeRecordService.insertSelective(shopChangeRecord);
        }else{
            //修改操作
            shopChangeRecord.setId(dbShopChangeRecord.getId());
            shopChangeRecord.setAuditStatus(null);
            shopChangeRecord.setAuditReason(null);
            shopChangeRecord.setAuditTime(null);
            shopChangeRecord.setUpdateTime(new Date());
            shopChangeRecordService.updateByPrimaryKeySelective(shopChangeRecord);
        }

        //TODO-比对有哪些字段进行了修改


        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    public String getApplyChangeContent(Shop dbShop, ShopChangeRecord shopChangeRecord){
        String applyChangeContent = "";
        if(shopChangeRecord.getName()!=null && !shopChangeRecord.getName().equals(dbShop.getName())){
            applyChangeContent += "店铺名称、";
        }

        if(shopChangeRecord.getProvince()!=null && !shopChangeRecord.getProvince().equals(dbShop.getProvince())){
            applyChangeContent += "所在省份、";
        }

        if(shopChangeRecord.getCity()!=null && !shopChangeRecord.getCity().equals(dbShop.getCity())){
            applyChangeContent += "所在城市、";
        }

        if(shopChangeRecord.getArea()!=null && !shopChangeRecord.getArea().equals(dbShop.getArea())){
            applyChangeContent += "所在区/县、";
        }

        if(shopChangeRecord.getStreet()!=null && !shopChangeRecord.getStreet().equals(dbShop.getStreet())){
            applyChangeContent += "门店地址、";
        }

        if(shopChangeRecord.getManagePrimary()!=null && !shopChangeRecord.getManagePrimary().equals(dbShop.getManagePrimary())){
            applyChangeContent += "营业类目、";
        }

        /*if(shopChangeRecord.getManageMinor()!=null && !shopChangeRecord.getManageMinor().equals(dbShop.getManageMinor())){
            applyChangeContent += "店铺名称、";
        }

        if(shopChangeRecord.getShopImg()!=null && !shopChangeRecord.getShopImg().equals(dbShop.getShopImg())){
            applyChangeContent += "店铺名称、";
        }*/

        if(shopChangeRecord.getShopWithinImg()!=null && !shopChangeRecord.getShopWithinImg().equals(dbShop.getShopWithinImg())){
            applyChangeContent += "店内照片、";
        }

        if(shopChangeRecord.getShopLogoImg()!=null && !shopChangeRecord.getShopLogoImg().equals(dbShop.getShopLogoImg())){
            applyChangeContent += "门店头像、";
        }

        /*if(shopChangeRecord.getContactRealname()!=null && !shopChangeRecord.getContactRealname().equals(dbShop.getContactRealname())){
            applyChangeContent += "店铺名称、";
        }*/

        if(shopChangeRecord.getContactPhone()!=null && !shopChangeRecord.getContactPhone().equals(dbShop.getContactPhone())){
            applyChangeContent += "联系电话、";
        }

        if(shopChangeRecord.getAnnouncement()!=null && !shopChangeRecord.getAnnouncement().equals(dbShop.getAnnouncement())){
            applyChangeContent += "门店公告、";
        }

        if(shopChangeRecord.getBriefIntroduction()!=null && !shopChangeRecord.getBriefIntroduction().equals(dbShop.getBriefIntroduction())){
            applyChangeContent += "门店简介、";
        }

        if(shopChangeRecord.getBusinessLicense()!=null && !shopChangeRecord.getBusinessLicense().equals(dbShop.getBusinessLicense())){
            applyChangeContent += "营业执照、";
        }

        if(shopChangeRecord.getIdCardFrontSide()!=null && !shopChangeRecord.getIdCardFrontSide().equals(dbShop.getIdCardFrontSide())){
            applyChangeContent += "身份证正面、";
        }

        if(shopChangeRecord.getIdCardBackSide()!=null && !shopChangeRecord.getIdCardBackSide().equals(dbShop.getIdCardBackSide())){
            applyChangeContent += "身份证反面、";
        }

        if(shopChangeRecord.getHouseNumber()!=null && !shopChangeRecord.getHouseNumber().equals(dbShop.getHouseNumber())){
            applyChangeContent += "门牌号、";
        }

        if(shopChangeRecord.getLongitude()!=null && dbShop.getLongitude()==null){
            applyChangeContent += "经度、";
        }else if(shopChangeRecord.getLongitude()!=null && dbShop.getLongitude()!=null && shopChangeRecord.getLongitude().compareTo(dbShop.getLongitude())!=0){
            applyChangeContent += "经度、";
        }

        if(shopChangeRecord.getLatitude()!=null && dbShop.getLatitude()==null){
            applyChangeContent += "纬度、";
        }else if(shopChangeRecord.getLatitude()!=null && dbShop.getLatitude()!=null && shopChangeRecord.getLatitude().compareTo(dbShop.getLatitude())!=0){
            applyChangeContent += "纬度、";
        }

        applyChangeContent = applyChangeContent != "" ? applyChangeContent.substring(0, applyChangeContent.length()-1) : applyChangeContent;

        return applyChangeContent;
    }
}