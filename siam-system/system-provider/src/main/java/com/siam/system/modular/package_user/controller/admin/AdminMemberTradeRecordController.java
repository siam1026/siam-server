//package com.siam.package_user.controller.admin;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.siam.package_common.entity.BasicData;
//import com.siam.package_common.entity.BasicResult;
//import com.siam.package_common.constant.BasicResultCode;
//import com.siam.package_user.entity.MemberTradeRecord;
//import com.siam.package_user.service.MemberTradeRecordService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "/rest/admin/memberTrade")
//@Transactional(rollbackFor = Exception.class)
//@Api(tags = "后台用户交易模块相关接口", description = "AdminMemberTradeController")
//public class AdminMemberTradeRecordController {
//    @Autowired
//    private MemberTradeRecordService memberTradeRecordService;
//
//    @ApiOperation(value = "用户交易列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户交易表主键id", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "name", value = "用户交易名称", required = false, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "logo", value = "logo图片", required = false, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "firstLetter", value = "用户交易首字母", required = false, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "parentId", value = "父类别id", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
//            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
//    })
//    @PostMapping(value = "/list")
//    public BasicResult list(int pageNo, int pageSize, MemberTradeRecord memberTradeRecord){
//        BasicData basicResult = new BasicData();
//
//        Page page = memberTradeRecordService.getListByPage(param.getPageNo(), param.getPageSize(), memberTradeRecord);
//
//        basicResult.setData(page);
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("查询成功");
//        return basicResult;
//    }
//
//
//    @ApiOperation(value = "新增用户交易")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "name", value = "用户交易名称", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "logo", value = "logo图片", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "firstLetter", value = "用户交易首字母", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "parentId", value = "父类别id", required = true, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
//    })
//    @PostMapping(value = "/insert")
//    public BasicResult insert(MemberTradeRecord memberTradeRecord){
//        BasicResult basicResult = new BasicResult();
//
//        memberTradeRecordService.insertSelective(memberTradeRecord);
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("新增成功");
//        return basicResult;
//    }
//
//    @ApiOperation(value = "修改用户交易")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户交易表主键id", required = true, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "name", value = "用户交易名称", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "logo", value = "logo图片", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "firstLetter", value = "用户交易首字母", required = true, paramType = "query", dataType = "string"),
//            @ApiImplicitParam(name = "parentId", value = "父类别id", required = true, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
//    })
//    @PostMapping(value = "/update")
//    public BasicResult update(MemberTradeRecord memberTradeRecord){
//        BasicResult basicResult = new BasicResult();
//
//        memberTradeRecordService.updateByPrimaryKeySelective(memberTradeRecord);
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("修改成功");
//        return basicResult;
//    }
//
//    @ApiOperation(value = "删除用户交易(含批量操作)")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "ids", value = "用户交易表主键id(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "string"),
//    })
//    @PostMapping(value = "/delete")
//    public BasicResult delete(@RequestParam(value = "ids", required = true) List<String> ids){
//        BasicResult basicResult = new BasicResult();
//
//        for(String id : ids){
//            memberTradeRecordService.deleteByPrimaryKey(Integer.valueOf(id));
//        }
//
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("删除成功");
//        return basicResult;
//    }
//}