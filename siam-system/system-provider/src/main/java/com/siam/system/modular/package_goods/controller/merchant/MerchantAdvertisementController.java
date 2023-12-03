package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.Advertisement;
import com.siam.system.modular.package_goods.model.param.AdvertisementParam;
import com.siam.system.modular.package_goods.service.AdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest/merchant/advertisement")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端广告轮播图模块相关接口", description = "MerchantAdvertisementController")
public class MerchantAdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @ApiOperation(value = "广告轮播图列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告轮播图表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageName", value = "轮播图名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "imagePath", value = "轮播图路径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "轮播图类型 1=首页轮播图 2=菜单页轮播图", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageLinkUrl", value = "点击轮播图跳转的链接", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) AdvertisementParam advertisement){
        BasicData basicResult = new BasicData();
        Page<Advertisement> page = advertisementService.getListByPage(advertisement);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "新增广告轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageName", value = "轮播图名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "imagePath", value = "轮播图路径", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "说明", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "轮播图类型 1=首页轮播图 2=菜单页轮播图", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageLinkUrl", value = "点击轮播图跳转的链接", required = false, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) AdvertisementParam advertisement){
        BasicResult basicResult = new BasicResult();

        advertisement.setCreateTime(new Date());
        advertisement.setUpdateTime(new Date());
        advertisementService.insertSelective(advertisement);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }


    @ApiOperation(value = "修改广告轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告轮播图主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageName", value = "轮播图名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "imagePath", value = "轮播图路径", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "说明", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "轮播图类型 1=首页轮播图 2=菜单页轮播图", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageLinkUrl", value = "点击轮播图跳转的链接", required = false, paramType = "query", dataType = "string"),
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) AdvertisementParam advertisement){
        BasicResult basicResult = new BasicResult();

        //由于商家端编辑时轮播图展示不出来，所以暂时性做逻辑控制，修改时轮播图数组可为空，代表不修改
        if(advertisement.getImagePath().equals("")){
            advertisement.setImagePath(null);
        }
        advertisement.setUpdateTime(new Date());
        advertisementService.updateByPrimaryKeySelective(advertisement);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "删除广告轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告轮播图主键id", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) AdvertisementParam param){
        BasicResult basicResult = new BasicResult();

        Advertisement dbAdvertisement = advertisementService.selectByPrimaryKey(param);
        if(dbAdvertisement == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该广告轮播图不存在，删除失败");
            return basicResult;
        }

        //删除广告轮播图
        advertisementService.deleteByPrimaryKey(param);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}