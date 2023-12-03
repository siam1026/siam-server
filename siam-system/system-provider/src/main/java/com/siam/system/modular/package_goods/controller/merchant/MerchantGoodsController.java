package com.siam.system.modular.package_goods.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.MerchantPermission;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.service.*;
import com.siam.system.modular.package_goods.service.*;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.util.OSSUtils;
import com.siam.system.modular.package_goods.model.dto.GoodsMenuDto;
import com.siam.system.modular.package_goods.model.example.MenuGoodsRelationExample;
import com.siam.system.modular.package_goods.model.example.PictureUploadRecordExample;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.MenuGoodsRelation;
import com.siam.system.modular.package_goods.entity.PictureUploadRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/merchant/goods")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端商品模块相关接口", description = "MerchantGoodsController")
public class MerchantGoodsController {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private MenuGoodsRelationService menuGoodsRelationService;

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private GoodsRawmaterialRelationService goodsRawmaterialRelationService;

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private CouponsGoodsRelationService couponsGoodsRelationService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private PictureUploadRecordService pictureUploadRecordService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @ApiOperation(value = "商品列表")
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
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) GoodsMenuDto goodsMenuDto, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        goodsMenuDto.setShopId(loginMerchant.getShopId());
        Page<Map<String, Object>> page = goodsService.getListByPageJoinMenu(goodsMenuDto.getPageNo(), goodsMenuDto.getPageSize(), goodsMenuDto);

        return BasicResult.success(page);
    }


    @ApiOperation(value = "新增商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandName", value = "品牌名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "商品主图", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subImages", value = "商品子图(多图上传)", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specList", value = "商品规格", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "detail", value = "商品详情", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "detailImages", value = "详情图片(多图上传)", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "一口价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "salePrice", value = "折扣价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "monthlySales", value = "月销量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "totalSales", value = "累计销量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "totalComments", value = "累计评价", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "productTime", value = "制作时长(分钟)", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "exchangePoints", value = "兑换商品所需积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isHot", value = "是否热门", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "isNew", value = "是否新品", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "menuId", value = "菜单id", required = false, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Goods goods, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        //商品的主图 等于 商品轮播图的第一张图
        if(StringUtils.isNotBlank(goods.getSubImages())){
            goods.setMainImage(goods.getSubImages().split(",")[0]);
        }

        //添加商品记录
        //设置默认库存为0
        goods.setShopId(loginMerchant.getShopId());
        goods.setStock(Quantity.INT_0);
        goods.setMonthlySales(Quantity.INT_0);
        goods.setTotalSales(Quantity.INT_0);
        goods.setTotalComments(Quantity.INT_0);
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        //兑换商品所需积分数量新增时默认等于折扣价
        goods.setExchangePoints(goods.getPrice().intValue());
        goodsService.insertSelective(goods);

        //建立商品与类别的关系
        MenuGoodsRelation insertMenuGoodsRelation = new MenuGoodsRelation();
        insertMenuGoodsRelation.setGoodsId(goods.getId());
        insertMenuGoodsRelation.setMenuId(goods.getMenuId());
        insertMenuGoodsRelation.setCreateTime(new Date());
        insertMenuGoodsRelation.setUpdateTime(new Date());
        menuGoodsRelationService.insertSelective(insertMenuGoodsRelation);

        //生成商品公共规格
        goodsSpecificationService.insertPublicGoodsSpecification(goods.getId());

        //TODO(MARK)-目前只能通过图片地址来判别重复，后续可以优化成那个啥位数来判定唯一
        //添加图片上传记录
        if(StringUtils.isNotBlank(goods.getSubImages())){
            String[] array = goods.getSubImages().split(",");
            for (String str : array) {
                PictureUploadRecordExample uploadRecordExample = new PictureUploadRecordExample();
                uploadRecordExample.createCriteria().andUrlEqualTo(str);
                int count = pictureUploadRecordService.countByExample(uploadRecordExample);
                if(count == 0){
                    PictureUploadRecord uploadRecord = new PictureUploadRecord();
                    uploadRecord.setShopId(loginMerchant.getShopId());
                    uploadRecord.setUrl(str);
                    uploadRecord.setModule(Quantity.INT_1);
                    uploadRecord.setCreateTime(new Date());
                    uploadRecord.setUpdateTime(new Date());
                    pictureUploadRecordService.insertSelective(uploadRecord);
                }
            }
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "brandName", value = "品牌名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "mainImage", value = "商品主图", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "subImages", value = "商品子图", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "specList", value = "商品规格", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "detail", value = "商品详情", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "detailImages", value = "详情图片", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "price", value = "一口价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "salePrice", value = "折扣价", required = true, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "monthlySales", value = "月销量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "totalSales", value = "累计销量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "totalComments", value = "累计评价", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "stock", value = "库存", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "productTime", value = "制作时长(分钟)", required = false, paramType = "query", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "exchangePoints", value = "兑换商品所需积分数量", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isHot", value = "是否热门", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "isNew", value = "是否新品", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Goods goods, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Goods dbGoods = goodsService.selectByPrimaryKey(goods.getId());
        if (dbGoods == null){
            throw new StoneCustomerException("该商品不存在");
        } else if (loginMerchant.getShopId() != dbGoods.getShopId()){
            throw new StoneCustomerException("您没有权限操作该商品");
        }

        //商品的主图 等于 商品轮播图的第一张图
        if(StringUtils.isNotBlank(goods.getSubImages())){
            goods.setMainImage(goods.getSubImages().split(",")[0]);
        }

        // 修改商品信息
        goods.setUpdateTime(new Date());
        goodsService.updateByPrimaryKeySelective(goods);

        //判断商品类别关系  目前不考虑一个商品有多个类别的情况
        MenuGoodsRelationExample example = new MenuGoodsRelationExample();
        example.createCriteria().andGoodsIdEqualTo(goods.getId());
        List<MenuGoodsRelation> list = menuGoodsRelationService.selectByExample(example);
        if(list!=null && list.size()>0){
            //如果商品原先的类别被修改了，现在就修改数据重新建立关系
            MenuGoodsRelation dbMenuGoodsRelation = list.get(0);
            if(goods.getMenuId() != dbMenuGoodsRelation.getMenuId()){
                MenuGoodsRelation updateMenuGoodsRelation = new MenuGoodsRelation();
                updateMenuGoodsRelation.setId(dbMenuGoodsRelation.getId());
                updateMenuGoodsRelation.setMenuId(goods.getMenuId());
                updateMenuGoodsRelation.setUpdateTime(new Date());
                menuGoodsRelationService.updateByPrimaryKeySelective(updateMenuGoodsRelation);
            }
        }else{
            //如果商品原先没有类别，现在就建立关系
            MenuGoodsRelation insertMenuGoodsRelation = new MenuGoodsRelation();
            insertMenuGoodsRelation.setGoodsId(goods.getId());
            insertMenuGoodsRelation.setMenuId(goods.getMenuId());
            insertMenuGoodsRelation.setCreateTime(new Date());
            insertMenuGoodsRelation.setUpdateTime(new Date());
            menuGoodsRelationService.insertSelective(insertMenuGoodsRelation);
        }

        //TODO(MARK)-目前只能通过图片地址来判别重复，后续可以优化成那个啥位数来判定唯一
        //添加图片上传记录
        if(StringUtils.isNotBlank(goods.getSubImages())){
            String[] array = goods.getSubImages().split(",");
            for (String str : array) {
                PictureUploadRecordExample uploadRecordExample = new PictureUploadRecordExample();
                uploadRecordExample.createCriteria().andUrlEqualTo(str);
                int count = pictureUploadRecordService.countByExample(uploadRecordExample);
                if(count == 0){
                    PictureUploadRecord uploadRecord = new PictureUploadRecord();
                    uploadRecord.setShopId(loginMerchant.getShopId());
                    uploadRecord.setUrl(str);
                    uploadRecord.setModule(Quantity.INT_1);
                    uploadRecord.setCreateTime(new Date());
                    uploadRecord.setUpdateTime(new Date());
                    pictureUploadRecordService.insertSelective(uploadRecord);
                }
            }
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Goods param, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Goods dbGoods = goodsService.selectByPrimaryKey(param.getId());
        if(dbGoods == null){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商品不存在，删除失败");
            return basicResult;
        } else if (loginMerchant.getShopId() != dbGoods.getShopId()){
            throw new StoneCustomerException("您没有权限操作该商品");
        }

        //删除菜单分类商品关系
        menuGoodsRelationService.deleteByGoodsId(dbGoods.getId());

        //先级联删除关联的商品规格选项、商品规格
        goodsSpecificationOptionService.deleteByGoodsId(dbGoods.getId());
        goodsSpecificationService.deleteByGoodsId(dbGoods.getId());

        //再将关联购物车数据设为无效
        shoppingCartService.updateIsGoodsExistsTo0ByGoodsId(dbGoods.getId());

        //再将关联的原料配比数据级联删除
        goodsRawmaterialRelationService.deleteByGoodsId(dbGoods.getId());

        //级联删除商品与优惠券的关联关系
        couponsGoodsRelationService.deleteByGoodsId(dbGoods.getId());

        //删除商品
        goodsService.deleteByPrimaryKey(dbGoods.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

//    @ApiOperation(value = "导入商品Excel报表")
//    @PostMapping(value = "/import")
//    public BasicResult importGoods(@RequestParam(value = "file", required = true) MultipartFile file){
//        BasicResult basicResult = new BasicResult();
//
//        if(file==null || file.getSize()==0){
//            basicResult.setSuccess(false);
//            basicResult.setCode(BasicResultCode.ERR);
//            basicResult.setMessage("上传文件不能为空");
//            return basicResult;
//        }
//
//        if(!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")){
//            basicResult.setSuccess(false);
//            basicResult.setCode(BasicResultCode.ERR);
//            //basicResult.setMessage("上传文件类型错误，后缀名只允许为.xls和.xlsx");
//            basicResult.setMessage("请上传Excel文件");
//            return basicResult;
//        }
//
//        try {
//            InputStream inputStream = file.getInputStream();
//            //List<Goods> goodsList = goodsService.parseExcel(inputStream);
//            List<Goods> goodsList = goodsService.parseExcel_plus(inputStream);
//            goodsList.forEach(goods -> {
//                goods.setMonthlySales(Quantity.INT_0);
//                goods.setTotalSales(Quantity.INT_0);
//                goods.setTotalComments(Quantity.INT_0);
//
//                goods.setCreateTime(new Date());
//                goods.setUpdateTime(new Date());
//                goodsService.insertSelective(goods);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("导入商品成功");
//        return basicResult;
//    }
//
//    @ApiOperation(value = "导出商品Excel报表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
//            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
//    })
//    @GetMapping(value = "/export")
//    public void exportGoods(int pageNo, int pageSize, Goods goods, HttpServletResponse response){
//        try {
//            // 生成Excel数据
//            Page<Goods> page = goodsService.getListByPage(param.getPageNo(), param.getPageSize(), goods);
//            XSSFWorkbook workbook = goodsService.createExcel(page.getRecords());
//
//            // 设置响应体
//            String fileName = "商品信息表_"+ System.currentTimeMillis() +".xlsx";
//            // 这里必须用ISO8859-1重新编码，否则文件名里面的中文无法解析，会以下划线代替
//            fileName = new String(fileName.getBytes(),"ISO8859-1");
//            response.setContentType("application/octet-stream;charset=UTF-8"); //这里的编码写啥都可以
//            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
//            response.addHeader("Pargam", "no-cache");
//            response.addHeader("Cache-Control", "no-cache");
//
//            // 将Excel数据写入输出流
//            OutputStream os = response.getOutputStream();
//            workbook.write(os);
//
//            // 关闭资源
//            os.flush();
//            os.close();
//            workbook.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @ApiOperation(value = "菜单状态修改（启用或禁用）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "ids", value = "商品主键id集合(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "String"),
//            @ApiImplicitParam(name = "status", value = "是否启用 0-上架、1-下架", required = true, paramType = "query", dataType = "int")
//    })
//    @DeleteMapping(value = "/update_status")
//    public BasicResult updateStatus(List<Integer> ids,Integer status){
//        BasicResult basicResult = new BasicResult();
//
//        goodsService.updateStatus(ids, status);
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("修改成功");
//        return basicResult;
//    }


    @ApiOperation(value = "获取单个商品详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = true, paramType = "query", dataType = "int"),
    })
    @PostMapping(value = "/getById")
    public BasicResult getById(@RequestBody @Validated(value = {}) Goods param, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Goods dbGoods = goodsService.selectByPrimaryKey(param.getId());
        if (dbGoods == null){
            throw new StoneCustomerException("该商品不存在");
        } else if (loginMerchant.getShopId() != dbGoods.getShopId()){
            throw new StoneCustomerException("您没有权限操作该商品");
        }

        GoodsMenuDto goodsMenuDto = new GoodsMenuDto();
        goodsMenuDto.setId(param.getId());

        Page<Map<String, Object>> page = goodsService.getListByPageJoinMenu(-1, 10, goodsMenuDto);
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
    public BasicResult listSpecification(int pageNo, int pageSize, GoodsSpecificationDto goodsSpecificationDto){
        BasicData basicResult = new BasicData();

        Page<Map<String, Object>> page = goodsService.getListByPageJoinSpecification(param.getPageNo(), param.getPageSize(), goodsSpecificationDto);

        basicResult.setData(page);
        basicResult.setSuccess(true);
        basicResult.setCode(BaseCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }*/
}