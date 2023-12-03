package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.modular.package_goods.entity.MerchantRecommendGoods;
import com.siam.system.modular.package_goods.model.example.MerchantRecommendGoodsExample;
import com.siam.system.modular.package_goods.service.MerchantRecommendGoodsService;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/merchant/merchantRecommendGoods")
@Transactional(rollbackFor = Exception.class)
    @Api(tags = "商家端商家推荐商品模块相关接口", description = "MerchantRecommendGoodsController")
public class MerchantRecommendGoodsController {
    @Autowired
    private MerchantRecommendGoodsService merchantRecommendGoodsService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "商家推荐商品信息列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MerchantRecommendGoods merchantRecommendGoods, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        merchantRecommendGoods.setShopId(loginMerchant.getShopId());
        Page<Map<String, Object>> page = merchantRecommendGoodsService.getListByPageJoinGoods(merchantRecommendGoods);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增商家推荐商品信息")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) MerchantRecommendGoods param, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        List<Integer> goodsIdList = GsonUtils.toList(param.getGoodsIdListStr(), Integer.class);

        //1、删除所有关系
        MerchantRecommendGoodsExample example = new MerchantRecommendGoodsExample();
        example.createCriteria().andShopIdEqualTo(loginMerchant.getShopId());
        merchantRecommendGoodsService.deleteByExample(example);

        //2、创建关系
        MerchantRecommendGoods merchantRecommendGoods = new MerchantRecommendGoods();
        for (Integer goodsId : goodsIdList) {
            merchantRecommendGoods.setId(null);
            merchantRecommendGoods.setShopId(loginMerchant.getShopId());
            merchantRecommendGoods.setGoodsId(goodsId);
            merchantRecommendGoods.setSortNumber(Quantity.INT_1);
            merchantRecommendGoods.setCreateTime(new Date());
            merchantRecommendGoods.setUpdateTime(new Date());
            merchantRecommendGoodsService.insertSelective(merchantRecommendGoods);
        }
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }
}