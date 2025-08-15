package com.siam.system.modular.package_goods.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_order.service.CommonService;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.package_common.util.BaiduMapUtils;
import com.siam.package_common.util.BeanUtils;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.Shop;
import com.siam.system.modular.package_goods.model.dto.GoodsMenuDto;
import com.siam.system.modular.package_goods.service.GoodsService;
import com.siam.system.modular.package_goods.service.ShopService;
import com.siam.system.modular.package_order.entity.TravelingDistanceVo;
import com.siam.system.modular.package_user.model.param.AdminParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/rest/goods")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商品模块相关接口", description = "GoodsController")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShopService shopService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private BaiduMapUtils baiduMapUtils;

    /*@ApiOperation(value = "商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandName", value = "品牌名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "商品主图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subImages", value = "商品子图", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specList", value = "商品规格", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "detail", value = "商品详情", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "detailImages", value = "详情图片", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "一口价", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "salePrice", value = "折扣价", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "monthlySales", value = "月销量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "totalSales", value = "累计销量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "totalComments", value = "累计评价", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "stock", value = "库存", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "productTime", value = "制作时长(分钟)", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "exchangePoints", value = "兑换商品所需积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isHot", value = "是否热门", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "isNew", value = "是否新品", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "status", value = "状态 1=下架 0=上架", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(int pageNo, int pageSize, Goods goods){
        BasicData basicResult = new BasicData();

        Page<Goods> page = goodsService.getListByPage(param.getPageNo(), param.getPageSize(), goods);

        return BasicResult.success(page);
    }*/

    /**
     * 查看商品详情
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) Goods param){
        BasicData basicResult = new BasicData();

//        //TODO(MARK)-小程序用的是高德地图，后端用的是百度地图，所以需要转换一下
//        String[] strArray = param.getPosition().split(",");
//        Map<String, BigDecimal> coordinateMap = baiduMapUtils.gaoDeToBaidu(Double.valueOf(strArray[0]), Double.valueOf(strArray[1]));
//        log.debug("\n\ngaode-position : " + param.getPosition());
//        log.debug("\n\nbaidu-position : " + coordinateMap.get("lng") + "," + coordinateMap.get("lat"));

        Goods dbGoods = goodsService.getById(param.getId());
        if(dbGoods == null) throw new StoneCustomerException("该商品不存在");

        Shop dbShop = shopService.getById(dbGoods.getShopId());
        if(dbShop == null) throw new StoneCustomerException("该店铺不存在");
        if(dbShop.getStatus() != Quantity.INT_2) throw new StoneCustomerException("该店铺待上架或已下架，不能查看该商品");

        Map<String, Object> resultMap = BeanUtils.beanToMap(dbGoods);

        //TODO(MARK)：由于该接口请求特别频繁 且 该接口一定是在店铺未超出配送距离时才会有场景被调用，所以前端小程序暂不判断isOutofDeliveryRange标识；
        //TODO(MARK)：前端调用这个接口之前，会先调用一个店铺详情接口，直接从这个接口里面就判断isOutofDeliveryRange、isOperatingOfShop标识了；
        //如果返回值小于等于0，则代表当前位置超出配送范围 或 当前位置不合法 -- 需要将该店铺从列表中移除
        //计算配送时长、距离公里数
        /*String addressB = dbShop.getProvince() + dbShop.getCity() + dbShop.getArea() + dbShop.getStreet();*/
//        TravelingDistanceVo travelingDistanceVo = commonService.selectTravelingDistance(coordinateMap.get("lng"), coordinateMap.get("lat"), dbShop.getLongitude(), dbShop.getLatitude());
//        System.out.println("\n\n" + dbShop.getName() + "'travelingDistanceVo.getDistanceValue() : " + travelingDistanceVo.getDistanceValue());
//        Setting setting = settingService.selectCurrent();
//        if(travelingDistanceVo.getDistanceValue().compareTo(BigDecimal.ZERO) == 0){
//            //如果距离为0，则代表百度地图没有计算结果
//            //还有一种情况会造成距离为0，那就是起点和终点相等--这种情况也算作地址填写错误
//            resultMap.put("isOutofDeliveryRange", true);
//        }else if(travelingDistanceVo.getDistanceValue().compareTo(setting.getDeliveryDistanceLimit()) > 0){
//            //超出5.5公里则不予配送
//            resultMap.put("isOutofDeliveryRange", true);
//        }else{
//            resultMap.put("isOutofDeliveryRange", false);
//        }
//        System.out.println("\n\n" + dbShop.getName() + "'isOutofDeliveryRange : " + resultMap.get("isOutofDeliveryRange"));

        //查询当前门店是否营业
        Boolean isOperatingOfShop = commonService.selectIsOperatingOfShop(dbShop.getId());
        resultMap.put("isOperatingOfShop", isOperatingOfShop);

        basicResult.setData(resultMap);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    /**
     * 指定菜单下的商品列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/listByMenuId")
    public BasicResult listByMenuId(@RequestBody @Validated(value = {}) GoodsMenuDto goodsMenuDto){
        BasicData basicResult = new BasicData();

        if(goodsMenuDto.getMenuId() == null){
            throw new StoneCustomerException("菜单id不能为空");
        }

        //按照近一月销量进行排序，如4-11~5-11、4-12~5-12区间
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

        goodsMenuDto.setStartTime(startTime);
        goodsMenuDto.setEndTime(endTime);

        //查询商品 2=已上架 4=售罄
        goodsMenuDto.setGoodsStatusIn2And4(true);
        Page<Map<String, Object>> page = goodsService.getListByPageJoinMenuOrderByLatelyMonthlySales(goodsMenuDto.getPageNo(), goodsMenuDto.getPageSize(), goodsMenuDto);

        return BasicResult.success(page);
    }


    /*@ApiOperation(value = "查询商品的规格组合信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/selectSpecificationById")
    public BasicResult selectSpecificationById(GoodsSpecificationDto goodsSpecificationDto){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = goodsService.getListByPageJoinSpecification(-1, 10, goodsSpecificationDto);

        List<Map<String, Object>> list = page.getRecords();

        Map<String, List> specificationMap = new LinkedHashMap<>();

        list.forEach(map -> {
            String name = (String) map.get("specificationName");
            if(specificationMap.containsKey(name)){
                specificationMap.get(name).add(map);
            }else{
                specificationMap.put(name, new ArrayList());
            }
        });

        basicResult.setData(specificationMap);
        basicResult.setSuccess(true);
        basicResult.setCode(BaseCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/

    /*@ApiOperation(value = "本月上新商品列表")
    @PostMapping(value = "/weekNewGoodsList")
    public BasicResult weekNewGoodsList(){
        BasicData basicResult = new BasicData();

        //查询最新上架的一件商品
        List<Goods> goodsList = goodsService.getListByLastestShelvesTop1();

        basicResult.setData(goodsList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/

    /*@ApiOperation(value = "好友推荐商品列表")
    @PostMapping(value = "/recommendGoodsList")
    public BasicResult recommendGoodsList(){
        BasicData basicResult = new BasicData();

        //查询近一月销量最高的前三件商品，如4-11~5-11、4-12~5-12区间
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
        startCalendar.setTime(DateUtils.subtractDays(endTime, 30));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startTime = startCalendar.getTime();
        List<Map<String, Object>> goodsList = goodsService.getListByLatelyMonthlySalesTop3(startTime, endTime);

        basicResult.setData(goodsList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/

    @ApiOperation(value = "猜你喜欢商品列表")
    @PostMapping(value = "/guessLikeGoodsList")
    public BasicResult guessLikeGoodsList(@RequestBody @Validated(value = {}) AdminParam param){
        BasicData basicResult = new BasicData();

        //查询近一月销量最高的前三件商品，如4-11~5-11、4-12~5-12区间
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
        List<Map<String, Object>> goodsList = goodsService.getListByLatelyMonthlySalesTop3(startTime, endTime, null);

        basicResult.setData(goodsList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    @ApiOperation(value = "首页商品推荐列表")
    @PostMapping(value = "/homePage/recommendGoodsList")
    public BasicResult recommendGoodsListOfHomePage(@RequestBody @Validated(value = {}) Goods param              ){
        //首页商品推荐位置一共有6件商品 -- 改成2件商品
        //1、如果在配送距离内有商家，则按照距离由近到远来展示前6个店铺中近一月销量最高的商品
        //2、如果在配送距离内无商家，则展示平台近一月销量最高的前6件商品
        //3、如果不足6个店铺，则从平台列表中补齐

        BasicData basicResult = new BasicData();
        List<Map<String, Object>> resultList = new ArrayList<>();

        //TODO(MARK)-小程序用的是高德地图，后端用的是百度地图，所以需要转换一下
        String[] strArray = param.getPosition().split(",");
        Map<String, BigDecimal> coordinateMap = baiduMapUtils.gaoDeToBaidu(Double.valueOf(strArray[0]), Double.valueOf(strArray[1]));
        log.debug("\n\ngaode-position : " + param.getPosition());
        log.debug("\n\nbaidu-position : " + coordinateMap.get("lng") + "," + coordinateMap.get("lat"));

        //TODO-按照浏览量由高到低查询出前6件商品(暂存)
        //查询近一月销量最高的前6件商品，如4-11~5-11、4-12~5-12区间
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

        //按照定位地址来查询前6个店铺
        //sql算出来的距离是米，所以这里要乘以1000进行换算
        Setting currentSetting = settingService.selectCurrent();
        Page<Shop> shopPage = shopService.selectByDistance(1, 2, coordinateMap.get("lng"), coordinateMap.get("lat"), currentSetting.getDeliveryDistanceLimit().multiply(BigDecimal.valueOf(1000)));
        for (Shop shop : shopPage.getRecords()) {
            List<Map<String, Object>> goodsList = goodsService.getListByLatelyMonthlySalesTopNumber(startTime, endTime, Quantity.INT_1, shop.getId());
            if (!goodsList.isEmpty()){
                resultList.add(goodsList.get(0));
            }
        }
        //如果不足6个店铺，则从平台列表中补齐
        int num = Quantity.INT_2 - shopPage.getRecords().size();
        if (num > 0){
            List<Map<String, Object>> goodsList = goodsService.getListByLatelyMonthlySalesTopNumber(startTime, endTime, num, null);
            resultList.addAll(goodsList);
        }

        basicResult.setData(resultList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}