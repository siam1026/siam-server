package com.siam.system.modular.package_goods.controller.merchant.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.MerchantPermission;
import com.siam.system.modular.package_goods.service.internal.PrinterService;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.internal.Printer;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/rest/merchant/printer")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "商家端商家打印机信息模块相关接口", description = "MerchantPrinterController")
public class MerchantPrinterController {

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @Autowired
    private PrinterService printerService;

//    @Autowired
//    private MerchantService merchantService;

    @ApiOperation(value = "打印机信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "商品名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "商家打印机信息详情描述", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Printer printer, HttpServletRequest request){
        BasicData basicResult = new BasicData();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        printer.setShopId(loginMerchant.getShopId());
        Page<Printer> page = printerService.getList(printer.getPageNo(), printer.getPageSize(), printer);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "打印机信息创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "打印机信息主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "打印机信息名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "商家打印机信息详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Printer printer, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        printer.setShopId(loginMerchant.getShopId());
        printer.setCreateTime(new Date());
        printer.setUpdateTime(new Date());
        printerService.insertSelective(printer);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("创建成功");
        return basicResult;
    }

    @MerchantPermission
    @ApiOperation(value = "打印机信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "打印机信息主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "打印机信息名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "detail", value = "商家打印机信息详情描述", required = false, paramType = "query", dataType = "string")
    })
    @PutMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) Printer printer, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();

        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        Printer dbPrinter = printerService.selectByPrimaryKey(printer.getId());
        if (dbPrinter == null){
            throw new StoneCustomerException("该打印机信息不存在");
        } else if (loginMerchant.getShopId() != dbPrinter.getShopId()){
            throw new StoneCustomerException("您没有权限操作该打印机信息");
        }

        printer.setUpdateTime(new Date());
        printerService.updateByPrimaryKeySelective(printer);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }


    @MerchantPermission
    @ApiOperation(value = "打印机信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "打印机信息主键id集合(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "String")
    })
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Printer param){
        BasicResult basicResult = new BasicResult();
        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        for (Integer id : param.getIds()) {
            Printer dbPrinter = printerService.selectByPrimaryKey(id);
            if (dbPrinter == null){
                throw new StoneCustomerException("该打印机信息不存在");
            } else if (loginMerchant.getShopId() != dbPrinter.getShopId()){
                throw new StoneCustomerException("您没有权限操作该打印机信息");
            }
            printerService.deleteByPrimaryKey(id);
        }
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}