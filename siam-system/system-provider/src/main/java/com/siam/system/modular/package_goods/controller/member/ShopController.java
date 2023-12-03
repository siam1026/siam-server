package com.siam.system.modular.package_goods.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.CommonUtils;
import com.siam.system.modular.package_order.service.CommonService;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.package_common.util.BaiduMapUtils;
import com.siam.package_common.util.BeanUtils;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_goods.entity.FullReductionRule;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.model.example.GoodsExample;
import com.siam.system.modular.package_goods.model.example.ShopExample;
import com.siam.system.modular.package_goods.model.param.SearchParam;
import com.siam.system.modular.package_goods.model.vo.PromotionVo;
import com.siam.system.modular.package_goods.model.vo.ShopAdditionalVo;
import com.siam.system.modular.package_goods.model.vo.ShopDetailVo;
import com.siam.system.modular.package_goods.service.FullReductionRuleService;
import com.siam.system.modular.package_goods.service.GoodsService;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.system.modular.package_order.entity.TravelingDistanceVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/rest/shop")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "门店模块相关接口", description = "ShopController")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private FullReductionRuleService fullReductionRuleService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private BaiduMapUtils baiduMapUtils;

    /**
     * 门店列表/首页推荐商家
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Shop shop){
        BasicData basicResult = new BasicData();

        //TODO(MARK)-小程序用的是高德地图，后端用的是百度地图，所以需要转换一下
        String[] strArray = shop.getPosition().split(",");
        log.debug("\n\n经纬度：");
        log.debug("position：" + shop.getPosition());
        log.debug("strArray：" + strArray.length);
        Map<String, BigDecimal> coordinateMap = baiduMapUtils.gaoDeToBaidu(Double.valueOf(strArray[0]), Double.valueOf(strArray[1]));
        log.debug("\n\ngaode-position : " + shop.getPosition());
        log.debug("\n\nbaidu-position : " + coordinateMap.get("lng") + "," + coordinateMap.get("lat"));

        //需要返回店铺星级、月售数量、起送费、配送费、配送时长、距离公里数、满减列表
        //超出配送距离的商家不展示
        //门店多的时候该如何进行距离的计算，我这里要的可不是直线距离，而是行车距离，所以不能直接通过数据库进行计算 -- 这里就得用直线距离去计算，美团/饿了么等平台都是用的直线距离进行查询(不知道在查询结果后，有没有再计算行车距离)，下单的时候我觉得应该都是用的行车距离进行计算
        //TODO-优惠列表不只是返回满减信息，还有首次光顾优惠、开发票这类的信息要显示出来 -- 所以应该定义一个折扣优惠VO类

        //查询各店铺近一月的销量
        //计算结束时间
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        Date endTime = endCalendar.getTime();
        //计算开始时间
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtilsPlus.subtractDays(endTime, 30));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startTime = startCalendar.getTime();

        List<Map<String, Object>> resultList = new ArrayList();

        Setting setting = settingService.selectCurrent();

        //查询出在准许配送距离内已上架的商家
        //sql算出来的距离是米，所以这里要乘以1000进行换算
        Page<Map<String, Object>> page = shopService.selectMapByDistance(shop.getPageNo(), shop.getPageSize(), coordinateMap.get("lng"), coordinateMap.get("lat"), setting.getDeliveryDistanceLimit().multiply(BigDecimal.valueOf(1000)));
        page.getRecords().forEach(map -> {
            ShopAdditionalVo shopAdditionalVo = new ShopAdditionalVo();
            //统计月销量
            int latelyMonthlySales = orderService.selectLatelyMonthlySalesByShopId(startTime, endTime, (int) map.get("id"));

            //计算配送时长、距离公里数
            /*String addressB = (String) map.get("province") + map.get("city") + map.get("area") + map.get("street");*/
            TravelingDistanceVo travelingDistanceVo = commonService.selectTravelingDistance(coordinateMap.get("lng"), coordinateMap.get("lat"), (BigDecimal) map.get("longitude"), (BigDecimal) map.get("latitude"));
            System.out.println("\n\n" + map.get("name") + "'travelingDistanceVo.getDistanceValue() : " + travelingDistanceVo.getDistanceValue());
            String distanceText = travelingDistanceVo.getDistanceText().replace("米", "m").replace("公里", "km");
            //计算配送费
            Shop tempShop = new Shop();
            /*BeanUtils.copyProperties(map, tempShop);*/
            BeanUtils.mapToBean(map, tempShop);
            BigDecimal deliveryFee = this.shopService.selectDeliveryFee(tempShop, travelingDistanceVo.getDistanceValue());
            //如果返回值小于等于0，则代表当前位置超出配送范围 或 当前位置不合法 -- 需要将该店铺从列表中移除
            if(deliveryFee.compareTo(BigDecimal.ZERO) <= 0){
                return;
            }

            //查询优惠活动列表
            List<PromotionVo> promotionList = new ArrayList<>();
            //1、满减规则列表
            FullReductionRule fullReductionRule = new FullReductionRule();
            fullReductionRule.setShopId((int) map.get("id"));
            List<FullReductionRule> fullReductionRuleList = fullReductionRuleService.selectByShopId(fullReductionRule);
            fullReductionRuleList.forEach(dbFullReductionRule -> {
                PromotionVo promotionVo = new PromotionVo();
                promotionVo.setType(Quantity.INT_1);
                promotionVo.setName(dbFullReductionRule.getName());
                promotionList.add(promotionVo);
            });

            shopAdditionalVo.setLatelyMonthlySales(latelyMonthlySales);
            shopAdditionalVo.setDeliveryFee(deliveryFee);
            String durationText = CommonUtils.getHourBySecond((travelingDistanceVo.getDurationValue().add(setting.getMerchantMealPreparationTime().multiply(BigDecimal.valueOf(60)))).intValue());
            shopAdditionalVo.setDeliveryDurationText(durationText);
            shopAdditionalVo.setDeliveryDistanceText(distanceText);
            shopAdditionalVo.setPromotionList(promotionList);
            map.put("shopAdditionalVo", shopAdditionalVo);
            map.put("distanceValue", travelingDistanceVo.getDistanceValue()); //为排序做准备

            resultList.add(map);
        });

        if(!resultList.isEmpty()){
            //商家列表 按照 距离升序排列
            resultList.sort((x, y) -> Double.compare(((BigDecimal) x.get("distanceValue")).doubleValue(), ((BigDecimal) y.get("distanceValue")).doubleValue()));
        }

        page.setRecords(resultList);
        return BasicResult.success(page);
    }

    /**
     * 查看门店详情信息
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/detail")
    public BasicResult detail(@RequestBody @Validated(value = {}) Shop shop){
        BasicData basicResult = new BasicData();
        ShopDetailVo shopDetailVo = new ShopDetailVo();

        //TODO(MARK)-小程序用的是高德地图，后端用的是百度地图，所以需要转换一下
        String[] strArray = shop.getPosition().split(",");
        Map<String, BigDecimal> coordinateMap = baiduMapUtils.gaoDeToBaidu(Double.valueOf(strArray[0]), Double.valueOf(strArray[1]));
        log.debug("\n\ngaode-position : " + shop.getPosition());
        log.debug("\n\nbaidu-position : " + coordinateMap.get("lng") + "," + coordinateMap.get("lat"));

        if(shop.getId() == null){
            throw new StoneCustomerException("门店id不能为空");
        }

        Shop dbShop = this.shopService.selectByPrimaryKey(shop.getId());
        if(dbShop == null){
            throw new StoneCustomerException("该门店信息不存在");
        }
        if(dbShop.getStatus() != Quantity.INT_2) throw new StoneCustomerException("该店铺待上架或已下架，不允许查看");

        //如果返回值小于等于0，则代表当前位置超出配送范围 或 当前位置不合法 -- 需要将该店铺从列表中移除
        //计算配送时长、距离公里数
        /*String addressB = dbShop.getProvince() + dbShop.getCity() + dbShop.getArea() + dbShop.getStreet();*/
        TravelingDistanceVo travelingDistanceVo = commonService.selectTravelingDistance(coordinateMap.get("lng"), coordinateMap.get("lat"), dbShop.getLongitude(), dbShop.getLatitude());
        System.out.println("\n\n" + dbShop.getName() + "'travelingDistanceVo.getDistanceValue() : " + travelingDistanceVo.getDistanceValue());
        Setting setting = settingService.selectCurrent();
        if(travelingDistanceVo.getDistanceValue().compareTo(BigDecimal.ZERO) == 0){
            //如果距离为0，则代表百度地图没有计算结果
            //还有一种情况会造成距离为0，那就是起点和终点相等--这种情况也算作地址填写错误
            shopDetailVo.setIsOutofDeliveryRange(true);
        }else if(travelingDistanceVo.getDistanceValue().compareTo(setting.getDeliveryDistanceLimit()) > 0){
            //超出5.5公里则不予配送
            shopDetailVo.setIsOutofDeliveryRange(true);
        }else{
            shopDetailVo.setIsOutofDeliveryRange(false);
        }

        //查询当前门店是否营业
        Boolean isOperatingOfShop = commonService.selectIsOperatingOfShop(dbShop.getId());
        shopDetailVo.setIsOperatingOfShop(isOperatingOfShop);

        //查询优惠活动列表
        List<PromotionVo> promotionList = new ArrayList<>();
        //1、满减规则列表
        FullReductionRule fullReductionRule = new FullReductionRule();
        fullReductionRule.setShopId(dbShop.getId());
        List<FullReductionRule> fullReductionRuleList = fullReductionRuleService.selectByShopId(fullReductionRule);
        fullReductionRuleList.forEach(dbFullReductionRule -> {
            PromotionVo promotionVo = new PromotionVo();
            promotionVo.setType(Quantity.INT_1);
            promotionVo.setName(dbFullReductionRule.getName());
            promotionList.add(promotionVo);
        });

        shopDetailVo.setShop(dbShop);
        shopDetailVo.setPromotionList(promotionList);
        shopDetailVo.setFullReductionRuleList(fullReductionRuleList);

        basicResult.setData(shopDetailVo);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    /**
     * 根据商家、商品名称进行模糊搜索
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/search")
    public BasicResult search(@RequestBody @Validated(value = {}) SearchParam searchParam){
        BasicData basicResult = new BasicData();

        //TODO(MARK)-小程序用的是高德地图，后端用的是百度地图，所以需要转换一下
        String[] strArray = searchParam.getPosition().split(",");
        Map<String, BigDecimal> coordinateMap = baiduMapUtils.gaoDeToBaidu(Double.valueOf(strArray[0]), Double.valueOf(strArray[1]));
        log.debug("\n\ngaode-position : " + searchParam.getPosition());
        log.debug("\n\nbaidu-position : " + coordinateMap.get("lng") + "," + coordinateMap.get("lat"));

        //TODO-利用ES在店铺数据库、商品数据库进行精确匹配，然后再把两个结果集合并到一起，形成店铺+商品结果集
        //TODO-店铺中的匹配商品只展示3个
        //暂时先用数据库的模糊查询

        //需要返回店铺星级、月售数量、起送费、配送费、配送时长、距离公里数、满减列表

        //TODO-超出配送距离的商家不展示

        //TODO-门店多的时候该如何进行距离的计算，我这里要的可不是直线距离，而是行车距离，所以不能直接通过数据库进行计算

        //TODO-优惠列表不只是返回满减信息，还有首次光顾优惠、开发票这类的信息要显示出来 -- 所以应该定义一个折扣优惠VO类

        //查询各店铺近一月的销量
        //计算结束时间
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        Date endTime = endCalendar.getTime();
        //计算开始时间
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtilsPlus.subtractDays(endTime, 30));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startTime = startCalendar.getTime();

        List resultList = new ArrayList();

        Setting setting = settingService.selectCurrent();

        //符合搜索条件的店铺id列表
        List<Integer> matchShopIdList = new ArrayList<>();

        //1、通过店铺名称查询
        ShopExample shopExample = new ShopExample();
        shopExample.createCriteria().andNameLike("%"+ searchParam.getKeywords() +"%");
        List<Shop> shopList = shopService.selectByExample(shopExample);
        List<Integer> filterList1 = new ArrayList<>();
        shopList.forEach(shop -> {
            filterList1.add(shop.getId());
        });

        //2、通过商品名称查询，排除已查询出来的店铺
        GoodsExample goodsExample = new GoodsExample();
        if(!filterList1.isEmpty()){
            goodsExample.createCriteria().andShopIdNotIn(filterList1).andNameLike("%"+ searchParam.getKeywords() +"%");
        }else{
            goodsExample.createCriteria().andNameLike("%"+ searchParam.getKeywords() +"%");
        }

        List<Goods> goodsList = goodsService.selectByExample(goodsExample);
        //筛选出店铺id
        Map<Integer, String> filterMap = new HashMap();
        goodsList.forEach(goods -> {
            filterMap.put(goods.getShopId(), null);
        });
        //把map-key转成list
        List<Integer> filterList2 = filterMap.entrySet().stream().map(et -> et.getKey()).collect(Collectors.toList());

        matchShopIdList.addAll(filterList1);
        matchShopIdList.addAll(filterList2);

        //有匹配商家则进行查询
        if(matchShopIdList.size() > 0){
            //查询出在准许配送距离内、已上架的商家
            Shop shop = new Shop();
            shop.setStatus(Quantity.INT_2);
            /*shop.setName(searchParam.getKeywords());*/
            shop.setShopIdList(matchShopIdList);
            Page<Map<String, Object>> page = shopService.getMapListByPage(searchParam.getPageNo(), searchParam.getPageSize(), shop);
            page.getRecords().forEach(map -> {
                ShopAdditionalVo shopAdditionalVo = new ShopAdditionalVo();
                //统计月销量
                int latelyMonthlySales = orderService.selectLatelyMonthlySalesByShopId(startTime, endTime, (int) map.get("id"));

                //计算配送时长、距离公里数
                /*String addressB = (String) map.get("province") + map.get("city") + map.get("area") + map.get("street");*/
                TravelingDistanceVo travelingDistanceVo = commonService.selectTravelingDistance(coordinateMap.get("lng"), coordinateMap.get("lat"), (BigDecimal) map.get("longitude"), (BigDecimal) map.get("latitude"));
                String distanceText = travelingDistanceVo.getDistanceText().replace("米", "m").replace("公里", "km");
                //计算配送费
                Shop tempShop = new Shop();
                /*BeanUtils.copyProperties(map, tempShop);*/
                BeanUtils.mapToBean(map, tempShop);
                BigDecimal deliveryFee = this.shopService.selectDeliveryFee(tempShop, travelingDistanceVo.getDistanceValue());
                //如果返回值小于等于0，则代表当前位置超出配送范围 或 当前位置不合法
                if(deliveryFee.compareTo(BigDecimal.ZERO) <= 0){
                    return;
                }

                //查询优惠活动列表
                List<PromotionVo> discountList = new ArrayList<>();
                //1、满减规则列表
                FullReductionRule fullReductionRule = new FullReductionRule();
                fullReductionRule.setShopId((int) map.get("id"));
                List<FullReductionRule> fullReductionRuleList = fullReductionRuleService.selectByShopId(fullReductionRule);
                fullReductionRuleList.forEach(dbFullReductionRule -> {
                    PromotionVo promotionVo = new PromotionVo();
                    promotionVo.setType(Quantity.INT_1);
                    promotionVo.setName(dbFullReductionRule.getName());
                    discountList.add(promotionVo);
                });

                shopAdditionalVo.setLatelyMonthlySales(latelyMonthlySales);
                shopAdditionalVo.setDeliveryFee(deliveryFee);
                String durationText = CommonUtils.getHourBySecond((travelingDistanceVo.getDurationValue().add(setting.getMerchantMealPreparationTime().multiply(BigDecimal.valueOf(60)))).intValue());
                shopAdditionalVo.setDeliveryDurationText(durationText);
                shopAdditionalVo.setDeliveryDistanceText(distanceText);
                shopAdditionalVo.setPromotionList(discountList);
                map.put("shopAdditionalVo", shopAdditionalVo);

                //查询店铺内匹配的商品
                GoodsExample goodsExampleTemp = new GoodsExample();
                goodsExampleTemp.createCriteria().andShopIdEqualTo((int) map.get("id")).andNameLike("%"+ searchParam.getKeywords() +"%");
                List<Goods> goodsListTemp = goodsService.selectByExample(goodsExampleTemp);
                map.put("goodsList", goodsListTemp);

                resultList.add(map);
            });
            page.setRecords(resultList);
            basicResult.setData(page);
        }else{
            Page<Map<String, Object>> page = new Page<>();
            page.setRecords(null);
            basicResult.setData(page);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}