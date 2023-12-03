package com.siam.system.modular.package_goods.controller.admin.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.OSSUtils;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCoupons;
import com.siam.system.modular.package_goods.entity.internal.PointsMallCouponsGoodsRelation;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsMenuDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenuGoodsRelation;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMenuGoodsRelationExample;
import com.siam.system.modular.package_goods.service.internal.*;
import com.siam.system.modular.package_goods.service.internal.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/pointsMall/goods")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台商品模块相关接口", description = "AdminPointsMallGoodsController")
public class AdminPointsMallGoodsController {
    @Autowired
    private PointsMallGoodsService pointsMallGoodsService;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private PointsMallMenuGoodsRelationService pointsMallMenuGoodsRelationService;

    @Autowired
    private PointsMallGoodsSpecificationService pointsMallGoodsSpecificationService;

    @Autowired
    private PointsMallGoodsSpecificationOptionService pointsMallGoodsSpecificationOptionService;

    @Autowired
    private PointsMallShoppingCartService pointsMallShoppingCartService;

    @Autowired
    private PointsMallCouponsService pointsMallCouponsService;

    @Autowired
    private PointsMallCouponsGoodsRelationService pointsMallCouponsGoodsRelationService;

    @ApiOperation(value = "商品列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallGoodsMenuDto goodsPointsMallMenuDto){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = pointsMallGoodsService.getListByPageJoinPointsMallMenu(goodsPointsMallMenuDto.getPageNo(), goodsPointsMallMenuDto.getPageSize(), goodsPointsMallMenuDto);

        return BasicResult.success(page);
    }


    @ApiOperation(value = "新增商品")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallGoods goods){
        BasicResult basicResult = new BasicResult();

        //商品的主图 等于 商品轮播图的第一张图
        if(StringUtils.isNotBlank(goods.getSubImages())){
            goods.setMainImage(goods.getSubImages().split(",")[0]);
        }

        //添加商品记录
        //设置默认库存为0
        goods.setStock(Quantity.INT_0);
        goods.setMonthlySales(Quantity.INT_0);
        goods.setTotalSales(Quantity.INT_0);
        goods.setTotalComments(Quantity.INT_0);
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        //兑换商品所需积分数量新增时默认等于折扣价
        goods.setExchangePoints(goods.getPrice().intValue());
        pointsMallGoodsService.insertSelective(goods);

        //建立商品与类别的关系
        PointsMallMenuGoodsRelation insertPointsMallMenuGoodsRelation = new PointsMallMenuGoodsRelation();
        insertPointsMallMenuGoodsRelation.setGoodsId(goods.getId());
        insertPointsMallMenuGoodsRelation.setMenuId(goods.getMenuId());
        insertPointsMallMenuGoodsRelation.setCreateTime(new Date());
        insertPointsMallMenuGoodsRelation.setUpdateTime(new Date());
        pointsMallMenuGoodsRelationService.insertSelective(insertPointsMallMenuGoodsRelation);

        //生成商品公共规格
        pointsMallGoodsSpecificationService.insertPublicGoodsSpecification(goods.getId());

        //建立商品与系统默认优惠券ID-新人3折卷的关联关系
        PointsMallCoupons dbPointsMallCoupons = pointsMallCouponsService.selectByPrimaryKey(BusinessType.NEW_PEOPLE_COUPONS_ID);
        if(dbPointsMallCoupons == null){
            throw new StoneCustomerException("系统默认优惠券-新人3折卷不存在");
        }
        PointsMallCouponsGoodsRelation insertPointsMallCouponsGoodsRelation = new PointsMallCouponsGoodsRelation();
        insertPointsMallCouponsGoodsRelation.setCouponsId(BusinessType.NEW_PEOPLE_COUPONS_ID);
        insertPointsMallCouponsGoodsRelation.setGoodsId(goods.getId());
        insertPointsMallCouponsGoodsRelation.setCreateTime(new Date());
        pointsMallCouponsGoodsRelationService.insertSelective(insertPointsMallCouponsGoodsRelation);

        //建立商品与系统默认优惠券ID-推荐新人3折卷的关联关系
        PointsMallCoupons dbPointsMallCouponsInvite = pointsMallCouponsService.selectByPrimaryKey(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID);
        if(dbPointsMallCouponsInvite == null){
            throw new StoneCustomerException("系统默认优惠券ID-推荐新人3折卷不存在");
        }
        PointsMallCouponsGoodsRelation insertPointsMallCouponsGoodsRelationInvite = new PointsMallCouponsGoodsRelation();
        insertPointsMallCouponsGoodsRelationInvite.setCouponsId(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID);
        insertPointsMallCouponsGoodsRelationInvite.setGoodsId(goods.getId());
        insertPointsMallCouponsGoodsRelationInvite.setCreateTime(new Date());
        pointsMallCouponsGoodsRelationService.insertSelective(insertPointsMallCouponsGoodsRelationInvite);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "修改商品")
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) PointsMallGoods goods){
        BasicResult basicResult = new BasicResult();

        //商品的主图 等于 商品轮播图的第一张图
        if(StringUtils.isNotBlank(goods.getSubImages())){
            goods.setMainImage(goods.getSubImages().split(",")[0]);
        }

        // 修改商品信息
        goods.setUpdateTime(new Date());
        pointsMallGoodsService.updateByPrimaryKeySelective(goods);

        //判断商品类别关系  目前不考虑一个商品有多个类别的情况
        PointsMallMenuGoodsRelationExample example = new PointsMallMenuGoodsRelationExample();
        example.createCriteria().andPointsMallGoodsIdEqualTo(goods.getId());
        List<PointsMallMenuGoodsRelation> list = pointsMallMenuGoodsRelationService.selectByExample(example);
        if(list!=null && list.size()>0){
            //如果商品原先的类别被修改了，现在就修改数据重新建立关系
            PointsMallMenuGoodsRelation dbPointsMallMenuGoodsRelation = list.get(0);
            if(goods.getMenuId() != dbPointsMallMenuGoodsRelation.getMenuId()){
                PointsMallMenuGoodsRelation updatePointsMallMenuGoodsRelation = new PointsMallMenuGoodsRelation();
                updatePointsMallMenuGoodsRelation.setId(dbPointsMallMenuGoodsRelation.getId());
                updatePointsMallMenuGoodsRelation.setMenuId(goods.getMenuId());
                updatePointsMallMenuGoodsRelation.setUpdateTime(new Date());
                pointsMallMenuGoodsRelationService.updateByPrimaryKeySelective(updatePointsMallMenuGoodsRelation);
            }
        }else{
            //如果商品原先没有类别，现在就建立关系
            PointsMallMenuGoodsRelation insertPointsMallMenuGoodsRelation = new PointsMallMenuGoodsRelation();
            insertPointsMallMenuGoodsRelation.setGoodsId(goods.getId());
            insertPointsMallMenuGoodsRelation.setMenuId(goods.getMenuId());
            insertPointsMallMenuGoodsRelation.setCreateTime(new Date());
            insertPointsMallMenuGoodsRelation.setUpdateTime(new Date());
            pointsMallMenuGoodsRelationService.insertSelective(insertPointsMallMenuGoodsRelation);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @AdminPermission
    @ApiOperation(value = "删除商品")
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallGoods param){
        BasicResult basicResult = new BasicResult();

        PointsMallGoods dbPointsMallGoods = pointsMallGoodsService.selectByPrimaryKey(param.getId());
        if(dbPointsMallGoods == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商品不存在，删除失败");
            return basicResult;
        }

        //删除菜单分类商品关系
        pointsMallMenuGoodsRelationService.deleteByPointsMallGoodsId(dbPointsMallGoods.getId());

        //先级联删除关联的商品规格选项、商品规格
        pointsMallGoodsSpecificationOptionService.deleteByPointsMallGoodsId(dbPointsMallGoods.getId());
        pointsMallGoodsSpecificationService.deleteByPointsMallGoodsId(dbPointsMallGoods.getId());

        //再将关联购物车数据设为无效
        pointsMallShoppingCartService.updateIsGoodsExistsTo0ByPointsMallGoodsId(dbPointsMallGoods.getId());

        //级联删除商品与优惠券的关联关系
        pointsMallCouponsGoodsRelationService.deleteByPointsMallGoodsId(dbPointsMallGoods.getId());

        //删除商品
        pointsMallGoodsService.deleteByPrimaryKey(dbPointsMallGoods.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    @ApiOperation(value = "导入商品Excel报表")
    @PostMapping(value = "/import")
    public BasicResult importPointsMallGoods(@RequestParam(value = "file", required = true) MultipartFile file){
        BasicResult basicResult = new BasicResult();

        if(file==null || file.getSize()==0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("上传文件不能为空");
            return basicResult;
        }

        if(!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            //basicResult.setMessage("上传文件类型错误，后缀名只允许为.xls和.xlsx");
            basicResult.setMessage("请上传Excel文件");
            return basicResult;
        }

        try {
            InputStream inputStream = file.getInputStream();
            //List<PointsMallGoods> goodsList = pointsMallGoodsService.parseExcel(inputStream);
            List<PointsMallGoods> goodsList = pointsMallGoodsService.parseExcel_plus(inputStream);
            goodsList.forEach(goods -> {
                goods.setMonthlySales(Quantity.INT_0);
                goods.setTotalSales(Quantity.INT_0);
                goods.setTotalComments(Quantity.INT_0);

                goods.setCreateTime(new Date());
                goods.setUpdateTime(new Date());
                pointsMallGoodsService.insertSelective(goods);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("导入商品成功");
        return basicResult;
    }

    @ApiOperation(value = "导出商品Excel报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @GetMapping(value = "/export")
    public void exportPointsMallGoods(int pageNo, int pageSize, PointsMallGoods goods, HttpServletResponse response){
        try {
            // 生成Excel数据
            Page<PointsMallGoods> page = pointsMallGoodsService.getListByPage(goods.getPageNo(), goods.getPageSize(), goods);
            XSSFWorkbook workbook = pointsMallGoodsService.createExcel(page.getRecords());

            // 设置响应体
            String fileName = "商品信息表_"+ System.currentTimeMillis() +".xlsx";
            // 这里必须用ISO8859-1重新编码，否则文件名里面的中文无法解析，会以下划线代替
            fileName = new String(fileName.getBytes(),"ISO8859-1");
            response.setContentType("application/octet-stream;charset=UTF-8"); //这里的编码写啥都可以
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            // 将Excel数据写入输出流
            OutputStream os = response.getOutputStream();
            workbook.write(os);

            // 关闭资源
            os.flush();
            os.close();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "菜单状态修改（启用或禁用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "商品主键id集合(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "是否启用 0-上架、1-下架", required = true, paramType = "query", dataType = "int")
    })
    @DeleteMapping(value = "/update_status")
    public BasicResult updateStatus(@RequestBody @Validated(value = {}) PointsMallGoods param){
        BasicResult basicResult = new BasicResult();

        pointsMallGoodsService.updateStatus(param.getIds(), param.getStatus());
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @ApiOperation(value = "获取单个商品详情信息")
    @PostMapping(value = "/getById")
    public BasicResult getById(@RequestBody @Validated(value = {}) PointsMallGoods param){
        BasicData basicResult = new BasicData();

        PointsMallGoodsMenuDto goodsPointsMallMenuDto = new PointsMallGoodsMenuDto();
        goodsPointsMallMenuDto.setId(param.getId());

        Page<Map<String, Object>> page = pointsMallGoodsService.getListByPageJoinPointsMallMenu(-1, 10, goodsPointsMallMenuDto);
        List<Map<String, Object>> list = page.getRecords();
        if(list==null || list.size()==0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("不存在该商品信息");
            return basicResult;
        }

        basicResult.setData(list.get(0));
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }


    /*@ApiOperation(value = "商品规格信息列表")
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
            @ApiImplicitParam(name = "isHot", value = "是否热门", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "isNew", value = "是否新品", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "specificationId", value = "规格id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "specificationName", value = "规格名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specificationOptionId", value = "商品规格选项id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "specificationOptionName", value = "商品规格选项名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specificationOptionPrice", value = "商品规格选项单价/加价金额", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "specificationOptionStock", value = "商品规格选项库存 1代表有货，0代表无货", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/listSpecification")
    public BasicResult listSpecification(int pageNo, int pageSize, PointsMallGoodsSpecificationDto goodsSpecificationDto){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = pointsMallGoodsService.getListByPageJoinSpecification(param.getPageNo(), param.getPageSize(), goodsSpecificationDto);

        basicResult.setData(page);
        basicResult.setSuccess(true);
        basicResult.setCode(BaseCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/
}