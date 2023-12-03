package com.siam.system.modular.package_goods.controller.merchant;

/*
@RestController
@RequestMapping(value = "/rest/merchant/setting")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端基础数据设置模块相关接口", description = "MerchantSettingController")
public class MerchantSettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @ApiOperation(value = "基础数据设置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "基础数据设置表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "purchaseRewardPoints", value = "购买一杯奶茶赠送积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "registrationRewardPoints", value = "新用户注册奖励积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(int pageNo, int pageSize, Setting setting){
        BasicData basicResult = new BasicData();
        Page<Setting> page = settingService.getListByPage(param.getPageNo(), param.getPageSize(), setting);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "修改基础数据设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "基础数据设置主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "purchaseRewardPoints", value = "购买一杯奶茶赠送积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "registrationRewardPoints", value = "新用户注册奖励积分数量", required = false, paramType = "query", dataType = "int"),
    })
    @PutMapping(value = "/update")
    public BasicResult update(Setting setting){
        BasicResult basicResult = new BasicResult();

        setting.setUpdateTime(new Date());
        settingService.updateByPrimaryKeySelective(setting);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }
}*/
