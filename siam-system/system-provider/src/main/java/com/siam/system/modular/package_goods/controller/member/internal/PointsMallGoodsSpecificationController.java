package com.siam.system.modular.package_goods.controller.member.internal;

import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/pointsMall/goodsSpecification")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商品规格模块相关接口", description = "PointsMallGoodsSpecificationController")
public class PointsMallGoodsSpecificationController {
    @Autowired
    private PointsMallGoodsSpecificationService goodsSpecificationService;

}