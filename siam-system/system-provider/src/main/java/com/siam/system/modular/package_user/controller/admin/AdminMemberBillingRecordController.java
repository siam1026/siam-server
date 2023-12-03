//package com.siam.package_user.controller.admin;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.siam.package_common.entity.BasicData;
//import com.siam.package_common.entity.BasicResult;
//import com.siam.package_common.constant.BasicResultCode;
//import com.siam.package_user.entity.MemberBillingRecord;
//import com.siam.package_user.service.MemberBillingRecordService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/rest/admin/memberBillingRecord")
//@Transactional(rollbackFor = Exception.class)
//@Api(tags = "后台用户账单记录模块相关接口", description = "AdminMemberBillingRecordController")
//public class AdminMemberBillingRecordController {
//
//    @Autowired
//    private MemberBillingRecordService memberBillingRecordService;
//
//    @ApiOperation(value = "用户账单记录列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "商品表主键id", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "name", value = "商品名称", required = false, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "detail", value = "用户账单记录详情描述", required = false, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
//            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
//    })
//    @PostMapping(value = "/list")
//    public BasicResult list(int pageNo, int pageSize, MemberBillingRecord memberBillingRecord){
//        BasicData basicResult = new BasicData();
//        Page<MemberBillingRecord> page = memberBillingRecordService.getListByPage(param.getPageNo(), param.getPageSize(), memberBillingRecord);
//
//        basicResult.setData(page);
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("查询成功");
//        return basicResult;
//    }
//
//    @ApiOperation(value = "用户账单记录创建")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户账单记录主键id", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "name", value = "用户账单记录名称", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "sortNumber", value = "排序", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "isDisabled", value = "是否启用 0-启用、1-禁用", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "detail", value = "用户账单记录详情描述", required = false, paramType = "query", dataType = "string")
//    })
//    @PostMapping(value = "/insert")
//    public BasicResult insert(MemberBillingRecord memberBillingRecord){
//        BasicResult basicResult = new BasicResult();
//
//        memberBillingRecord.setCreateTime(new Date());
//        memberBillingRecordService.insertSelective(memberBillingRecord);
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("创建成功");
//        return basicResult;
//    }
//}