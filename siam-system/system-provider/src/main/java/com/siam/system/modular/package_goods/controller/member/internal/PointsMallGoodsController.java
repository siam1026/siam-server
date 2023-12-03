package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.package_common.util.BeanUtils;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsMenuDto;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsService;
import com.siam.system.modular.package_user.model.param.AdminParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/pointsMall/goods")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商品模块相关接口", description = "PointsMallGoodsController")
public class PointsMallGoodsController {
    @Autowired
    private PointsMallGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private CommonService commonService;

    @Autowired
    private SettingService settingService;

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
    public BasicResult list(int pageNo, int pageSize, PointsMallGoods goods){
        BasicData basicResult = new BasicData();

        Page<PointsMallGoods> page = goodsService.getListByPage(param.getPageNo(), param.getPageSize(), goods);

        return BasicResult.success(page);
    }*/

    /**
     * 查看商品详情
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) PointsMallGoods param){
        BasicData basicResult = new BasicData();

        PointsMallGoods dbPointsMallGoods = goodsService.selectByPrimaryKey(param.getId());
        if(dbPointsMallGoods == null) throw new StoneCustomerException("该商品不存在");

        Map<String, Object> resultMap = BeanUtils.beanToMap(dbPointsMallGoods);

        //查询近一月销量
        long latelyMonthlySales = goodsService.selectLatelyMonthlySalesById(dbPointsMallGoods.getId());
        resultMap.put("latelyMonthlySales", latelyMonthlySales);

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
    public BasicResult listByPointsMallMenuId(@RequestBody @Validated(value = {}) PointsMallGoodsMenuDto goodsPointsMallMenuDto){
        BasicData basicResult = new BasicData();

        if(goodsPointsMallMenuDto.getMenuId() == null){
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

        goodsPointsMallMenuDto.setStartTime(startTime);
        goodsPointsMallMenuDto.setEndTime(endTime);

        //查询商品 2=已上架 4=售罄
        goodsPointsMallMenuDto.setGoodsStatusIn2And4(true);
        Page<Map<String, Object>> page = goodsService.getListByPageJoinPointsMallMenuPointsMallOrderByLatelyMonthlySales(goodsPointsMallMenuDto.getPageNo(), goodsPointsMallMenuDto.getPageSize(), goodsPointsMallMenuDto);

        return BasicResult.success(page);
    }


    /*@ApiOperation(value = "查询商品的规格组合信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/selectSpecificationById")
    public BasicResult selectSpecificationById(PointsMallGoodsSpecificationDto goodsSpecificationDto){
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
    public BasicResult weekNewPointsMallGoodsList(){
        BasicData basicResult = new BasicData();

        //查询最新上架的一件商品
        List<PointsMallGoods> goodsList = goodsService.getListByLastestShelvesTop1();

        basicResult.setData(goodsList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/

    /*@ApiOperation(value = "好友推荐商品列表")
    @PostMapping(value = "/recommendGoodsList")
    public BasicResult recommendPointsMallGoodsList(){
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
    public BasicResult guessLikePointsMallGoodsList(@RequestBody @Validated(value = {}) AdminParam param){
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

        List<Map<String, Object>> goodsList = goodsService.getListByLatelyMonthlySalesTopNumber(startTime, endTime, Quantity.INT_6);

        basicResult.setData(goodsList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }

    /*@ApiOperation(value = "首页商品推荐列表")
    @PostMapping(value = "/homePage/recommendGoodsList")
    public BasicResult recommendPointsMallGoodsListOfHomePage(){
        BasicData basicResult = new BasicData();

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
        startCalendar.setTime(DateUtils.subtractDays(endTime, 30));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startTime = startCalendar.getTime();
        List<Map<String, Object>> goodsList = goodsService.getListByLatelyMonthlySalesTopNumber(startTime, endTime, Quantity.INT_6);

        basicResult.setData(goodsList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/

    @ApiOperation(value = "推荐展示的商品列表")
    @PostMapping(value = "/recommendGoodsList")
    public BasicResult recommendGoodsList(@RequestBody @Validated(value = {}) PointsMallGoods goods){
        BasicData basicResult = new BasicData();
        
        Page<Map<String, Object>> page = goodsService.recommendGoodsList(goods.getPageNo(), goods.getPageSize(), goods);

        return BasicResult.success(page);
    }
}