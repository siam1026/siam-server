package com.siam.system.modular.package_user.controller.member;

import com.siam.system.modular.package_user.service.MemberTradeRecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/memberTrade")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "用户交易模块相关接口", description = "MemberTradeController")
public class MemberTradeRecordController {
    @Autowired
    private MemberTradeRecordService memberTradeRecordService;

    /*@ApiOperation(value = "用户交易列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户交易表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "name", value = "用户交易名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "logo", value = "logo图片", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "firstLetter", value = "用户交易首字母", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "父类别id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "status", value = "状态 1=启用 0=禁用 -1=删除", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(int pageNo, int pageSize, MemberTradeRecord memberTradeRecord){
        BasicData basicResult = new BasicData();

        Page page = memberTradeRecordService.getListByPage(param.getPageNo(), param.getPageSize(), memberTradeRecord);

        return BasicResult.success(page);
    }*/
}