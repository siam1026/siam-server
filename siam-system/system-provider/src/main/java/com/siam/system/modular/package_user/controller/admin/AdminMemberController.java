package com.siam.system.modular.package_user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.model.param.MemberParam;
import com.siam.system.modular.package_user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/admin/member")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台用户模块相关接口", description = "AdminMemberController")
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "用户列表")
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MemberParam param) {
        Page page = memberService.getListByPage(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "查询已购买用户列表")
    @PostMapping(value = "/purchasedList")
    public BasicResult purchasedList(@RequestBody @Validated(value = {}) MemberParam param) {
        Page page = memberService.purchasedList(param);
        return BasicResult.success(page);
    }

    @ApiOperation(value = "查询未购买用户列表")
    @PostMapping(value = "/unPurchasedList")
    public BasicResult unPurchasedList(@RequestBody @Validated(value = {}) MemberParam param) {
        Page page = memberService.unPurchasedList(param);
        return BasicResult.success(page);
    }

    @AdminPermission
    @ApiOperation(value = "修改用户信息")
    @PostMapping(value = "/update")
    public BasicResult update(@RequestBody @Validated(value = {}) MemberParam param) {
        //管理员只准修改个别字段信息
        Member updateMember = new Member();
        updateMember.setId(param.getId());
        updateMember.setIsDisabled(param.getIsDisabled());
        memberService.updateByPrimaryKeySelective(updateMember);
        return BasicResult.success();
    }

    /*@ApiOperation(value = "启用用户(含批量操作)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户表主键id(批量启用时id以逗号分隔)", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/enable")
    public BasicResult enable(@RequestParam(value = "ids", required = true) List<String> ids){
        BasicResult basicResult = new BasicResult();

        // 这个用来标记失败序号
        int flag = 0;
        for(String id : ids){
            flag++;
            Member dbMember = memberService.selectByPrimaryKey(Integer.valueOf(id));
            if(dbMember.getIsDeleted()){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("用户状态为已删除，启用失败，失败序号为" + flag);
                return basicResult;
            }
            if(!dbMember.getIsDisabled()){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("用户状态为已启用，启用失败，失败序号为" + flag);
                return basicResult;
            }
            Member updateMember = new Member();
            updateMember.setId(Integer.valueOf(id));
            updateMember.setIsDisabled(false);
            memberService.updateByPrimaryKeySelective(updateMember);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("启用成功");
        return basicResult;
    }


    @ApiOperation(value = "禁用用户(含批量操作)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户表主键id(批量禁用时id以逗号分隔)", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/disable")
    public BasicResult disable(@RequestParam(value = "ids", required = true) List<String> ids){
        BasicResult basicResult = new BasicResult();

        // 这个用来标记失败序号
        int flag = 0;
        for(String id : ids){
            flag++;
            Member dbMember = memberService.selectByPrimaryKey(Integer.valueOf(id));
            if(dbMember.getIsDeleted()){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("用户状态为已删除，禁用失败，失败序号为" + flag);
                return basicResult;
            }
            if(dbMember.getIsDisabled()){
                basicResult.setSuccess(false);
                basicResult.setCode(BasicResultCode.ERR);
                basicResult.setMessage("用户状态为已禁用，禁用失败，失败序号为" + flag);
                return basicResult;
            }
            Member updateMember = new Member();
            updateMember.setId(Integer.valueOf(id));
            updateMember.setIsDisabled(true);
            memberService.updateByPrimaryKeySelective(updateMember);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("禁用成功");
        return basicResult;
    }*/


    /*@ApiOperation(value = "删除用户(含批量操作)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "用户表主键id(批量删除时id以逗号分隔)", required = true, paramType = "query", dataType = "string"),
    })
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestParam(value = "ids", required = true) List<String> ids){
        BasicResult basicResult = new BasicResult();

        // 这个用来标记失败序号
        int flag = 0;
        for(String id : ids){
            flag++;
            Member dbMember = memberService.selectByPrimaryKey(Integer.valueOf(id));
            if(dbMember.getIsDeleted() == Quantity.INT_1){
                basicResult.setSuccess(false);
                basicResult.setCode(BaseCode.ERR);
                basicResult.setMessage("用户状态为已删除，删除失败，失败序号为" + flag);
                return basicResult;
            }
            Member updateMember = new Member();
            updateMember.setId(Integer.valueOf(id));
            updateMember.setIsDeleted(Quantity.INT_1);
            memberService.updateByPrimaryKeySelective(updateMember);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BaseCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }*/
}