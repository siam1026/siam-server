package com.siam.system.modular.package_goods.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.model.dto.GoodsSpecificationOptionDto;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/goodsSpecificationOption")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商品规格选项模块相关接口", description = "GoodsSpecificationOptionController")
public class GoodsSpecificationOptionController {
    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @ApiOperation(value = "查询商品规格选项信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/selectByGoodsId")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsSpecificationOptionDto goodsSpecificationOptionDto){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = goodsSpecificationOptionService.getListByPageJoinGoods(-1, 10, goodsSpecificationOptionDto);

        List<Map<String, Object>> list = page.getRecords();

        //循环列表 将规格信息 与 商品规格选项值匹配好
        Map<String, List> specificationMap = new LinkedHashMap<>();
        list.forEach(map -> {
            String name = (String) map.get("specificationName");
            if(!specificationMap.containsKey(name)){
                specificationMap.put(name, new ArrayList());
            }
            specificationMap.get(name).add(map);
        });

        basicResult.setData(specificationMap);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}