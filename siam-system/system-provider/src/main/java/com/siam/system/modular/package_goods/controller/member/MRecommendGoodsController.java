package com.siam.system.modular.package_goods.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.MerchantRecommendGoods;
import com.siam.system.modular.package_goods.service.MerchantRecommendGoodsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/merchantRecommendGoods")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家推荐商品模块相关接口", description = "MRecommendGoodsController")
public class MRecommendGoodsController {

    @Autowired
    private MerchantRecommendGoodsService merchantRecommendGoodsService;

    /**
     * 商家推荐商品列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MerchantRecommendGoods merchantRecommendGoods){
        BasicData basicResult = new BasicData();

        if(merchantRecommendGoods.getShopId() == null){
            throw new StoneCustomerException("店铺id不能为空");
        }

        List<Map<String, Object>> resultList = new ArrayList<>();
        Page<Map<String, Object>> page = merchantRecommendGoodsService.getListByPageJoinGoods(merchantRecommendGoods);
        page.getRecords().forEach(map -> {
            //只查询2=已上架 4=售罄的商品
            if(Quantity.INT_2 == (int) map.get("goodsStatus") || Quantity.INT_4 == (int) map.get("goodsStatus")){
                resultList.add(map);
            }
        });
        page.setRecords(resultList);

        return BasicResult.success(page);
    }
}