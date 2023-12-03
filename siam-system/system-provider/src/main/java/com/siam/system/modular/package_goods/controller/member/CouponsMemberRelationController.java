package com.siam.system.modular.package_goods.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;
import com.siam.system.modular.package_goods.service.CouponsMemberRelationService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
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
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/member/couponsMemberRelation")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "优惠卷用关系接口", description = "CouponsMemberRelationController")
public class CouponsMemberRelationController {

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

//    @Autowired
//    private MemberService memberService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @ApiOperation(value = "新增优惠卷用户关系")
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) CouponsMemberRelation couponsMemberRelation) {
        BasicResult basicResult = new BasicResult();

        couponsMemberRelationService.insertSelective(couponsMemberRelation);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    @ApiOperation(value = "查看优惠卷关系详情（包含关联优惠卷）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, paramType = "query", dataType = "Integer"),
    })
    @PostMapping(value = "/selectById")
    public BasicResult selectById(@RequestBody @Validated(value = {}) CouponsMemberRelation param){
        BasicData basicResult = new BasicData();

        CouponsMemberRelation couponsMemberRelation = couponsMemberRelationService.selectCouponsMemberRelationByPrimaryKey(param.getId());

        basicResult.setData(couponsMemberRelation);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }


    @ApiOperation(value = "优惠卷用户关系列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "couponsId", value = "优惠卷id", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "couponsName", value = "优惠卷名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "memberId", value = "用户id", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "startTime", value = "生效时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "过期时间", required = false, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "isUsed", value = "是否已经使用，0=未使用，1=已使用", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isExpired", value = "是否过期，0=未过期，1=已过期", required = false, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "isValid", value = "是否生效，0-未生效，1-生效中", required = false, paramType = "query", dataType = "Boolean"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20")
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) CouponsMemberRelation couponsMemberRelation, HttpServletRequest request) {
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //查询当前登录用户持有的优惠券
        couponsMemberRelation.setMemberId(loginMember.getId());
        Page<Map<String, Object>> page = couponsMemberRelationService.getListByPage(couponsMemberRelation.getPageNo(), couponsMemberRelation.getPageSize(), couponsMemberRelation);

        return BasicResult.success(page);
    }

    @ApiOperation(value = "查询优惠卷数量")
    @PostMapping(value = "/selectCounts")
    public BasicResult selectCounts( HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Integer memberId = loginMember.getId();

        Integer counts = couponsMemberRelationService.getCountsByMemberId(memberId);

        basicResult.setData(counts);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("获取成功");
        return basicResult;
    }

}