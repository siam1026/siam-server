package com.siam.system.modular.package_goods.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MerchantService;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.model.example.ShopExample;
import com.siam.system.modular.package_goods.model.param.ShopParam;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.modular.package_user.model.example.MemberExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/shop")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台门店模块相关接口", description = "AdminShopController")
public class AdminShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "门店信息列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Shop shop){
        BasicData basicResult = new BasicData();
        Page<Map<String, Object>> page = shopService.getMapListByPageJoinMerchant(shop.getPageNo(), shop.getPageSize(), shop);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增门店信息")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Shop shop){
        BasicResult basicResult = new BasicResult();
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        shopService.insertSelective(shop);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改门店信息")
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Shop shop, String memberMobile) throws IOException {
        BasicResult basicResult = new BasicResult();

        Shop dbShop = shopService.selectByPrimaryKey(shop.getId());
        if(dbShop == null) throw new StoneCustomerException("该店铺不存在");

        if(StringUtils.isNotBlank(memberMobile)){
            MemberExample memberExample = new MemberExample();
            memberExample.createCriteria().andMobileEqualTo(memberMobile);
            List<Member> memberList = memberService.selectByExample(memberExample);
            if(memberList==null || memberList.isEmpty()){
                throw new StoneCustomerException("绑定的小程序用户手机号不正确，请重新输入");
            }

            //修改商家绑定的小程序用户id
            Merchant dbMerchant = merchantService.selectByPrimaryKey(dbShop.getMerchantId());
            Merchant updateMerchant = new Merchant();
            updateMerchant.setId(dbMerchant.getId());
            updateMerchant.setMemberId(memberList.get(0).getId());
            merchantService.updateByPrimaryKeySelective(updateMerchant);

            //如果绑定的小程序用户的 微信公众号openid为空，则触发 openid查询操作
            if(StringUtils.isBlank(memberList.get(0).getWxPublicPlatformOpenId())){
                /*basicResult = memberService.querySingleWxPublicPlatformOpenId(memberList.get(0).getId());*/
            }
        }

        //修改店铺起送价格
        Shop updateShop = new Shop();
        updateShop.setId(shop.getId());
        updateShop.setStatus(shop.getStatus());
        updateShop.setStartDeliveryPrice(shop.getStartDeliveryPrice());
        updateShop.setUpdateTime(new Date());
        shopService.updateByPrimaryKeySelective(updateShop);

        if(basicResult.getSuccess()==null || basicResult.getSuccess()){
            basicResult.setSuccess(true);
            basicResult.setCode(BasicResultCode.SUCCESS);
            basicResult.setMessage("修改成功");
        }
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "删除门店信息")
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Shop param){
        BasicResult basicResult = new BasicResult();
        for (Integer id : param.getIds()) {
            //判断菜单下面有关联商品，则不能删除
            ShopExample example = new ShopExample();
            example.createCriteria().andIdEqualTo(id);
            int result = shopService.countByExample(example);
            if(result == 0){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("该门店信息不存在");
                return basicResult;
            }
            shopService.deleteByPrimaryKey(id);
        }
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    /**
     * 审核申请入驻门店信息
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/auditApplySettled")
    public BasicResult auditApplySettled(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) ShopParam shopParam){
        BasicResult basicResult = new BasicResult();

        if(shopParam.getStatus() == Quantity.INT_2 && StringUtils.isBlank(shopParam.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        if(shopParam.getStatus() == Quantity.INT_1){
            //审核通过
            //修改门店信息
            Shop updateShop = new Shop();
            updateShop.setId(shopParam.getId());
            updateShop.setAuditStatus(Quantity.INT_2);
            updateShop.setAuditTime(new Date());
            shopService.updateByPrimaryKeySelective(updateShop);
            //修改商家信息
//            MerchantExample merchantExample = new MerchantExample();
//            merchantExample.createCriteria().andShopIdEqualTo(shopParam.getId());
//            Merchant updateMerchant = new Merchant();
//            updateMerchant.setAuditStatus(Quantity.INT_2);
//            updateMerchant.setUpdateTime(new Date());
//            merchantService.updateByExampleSelective(updateMerchant, merchantExample);
            //TODO-发消息通知用户
        }else if(shopParam.getStatus() == Quantity.INT_2){
            //审核不通过
            //修改门店信息
            Shop updateShop = new Shop();
            updateShop.setId(shopParam.getId());
            updateShop.setAuditStatus(Quantity.INT_3);
            updateShop.setAuditReason(shopParam.getOpinion());
            updateShop.setAuditTime(new Date());
            shopService.updateByPrimaryKeySelective(updateShop);
            //修改商家信息
//            MerchantExample merchantExample = new MerchantExample();
//            merchantExample.createCriteria().andShopIdEqualTo(shopParam.getId());
//            Merchant updateMerchant = new Merchant();
//            updateMerchant.setAuditStatus(Quantity.INT_3);
//            updateMerchant.setUpdateTime(new Date());
//            merchantService.updateByExampleSelective(updateMerchant, merchantExample);
            //TODO-发消息通知用户
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("审核成功");
        return basicResult;
    }

    /**
     * 审核申请变更资料门店信息
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/auditApplyChangeData")
    public BasicResult auditApplyChangeData(@RequestBody @Validated(value = {ValidGroupOfId.class, ValidGroupOfAudit.class}) ShopParam shopParam){
        BasicResult basicResult = new BasicResult();

        if(shopParam.getStatus() == Quantity.INT_2 && StringUtils.isBlank(shopParam.getOpinion())){
            throw new StoneCustomerException("审核不通过时，审核意见不能为空");
        }

        //TODO-进行资料变更
        if(shopParam.getStatus() == Quantity.INT_1){
            //审核通过
            //修改门店信息
//            Shop updateShop = new Shop();
//            updateShop.setId(shopParam.getId());
//            updateShop.setAuditStatus(Quantity.INT_2);
//            updateShop.setAuditTime(new Date());
//            shopService.updateByPrimaryKeySelective(updateShop);
//            //修改商家信息
//            MerchantExample merchantExample = new MerchantExample();
//            merchantExample.createCriteria().andShopIdEqualTo(shopParam.getId());
//            Merchant updateMerchant = new Merchant();
//            updateMerchant.setAuditStatus(Quantity.INT_2);
//            updateMerchant.setUpdateTime(new Date());
//            merchantService.updateByExampleSelective(updateMerchant, merchantExample);
            //TODO-发消息通知用户
        }else if(shopParam.getStatus() == Quantity.INT_2){
            //审核不通过
            //修改门店信息
//            Shop updateShop = new Shop();
//            updateShop.setId(shopParam.getId());
//            updateShop.setAuditStatus(Quantity.INT_3);
//            updateShop.setAuditReason(shopParam.getOpinion());
//            updateShop.setAuditTime(new Date());
//            shopService.updateByPrimaryKeySelective(updateShop);
//            //修改商家信息
//            MerchantExample merchantExample = new MerchantExample();
//            merchantExample.createCriteria().andShopIdEqualTo(shopParam.getId());
//            Merchant updateMerchant = new Merchant();
//            updateMerchant.setAuditStatus(Quantity.INT_3);
//            updateMerchant.setUpdateTime(new Date());
//            merchantService.updateByExampleSelective(updateMerchant, merchantExample);
            //TODO-发消息通知用户
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("审核成功");
        return basicResult;
    }
}