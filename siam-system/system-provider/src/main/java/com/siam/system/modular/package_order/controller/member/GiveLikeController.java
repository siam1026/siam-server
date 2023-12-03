package com.siam.system.modular.package_order.controller.member;

import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_order.model.example.GiveLikeExample;
import com.siam.system.modular.package_order.service.GiveLikeService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_order.entity.GiveLike;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 客户端点赞接口
 */
@RestController
@RequestMapping(value = "/rest/member/giveLike")
@Transactional(rollbackFor = Exception.class)
public class GiveLikeController {

    @Autowired
    private GiveLikeService giveLikeService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    /**
     * 点赞
     * @param giveLike
     * @return
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) GiveLike giveLike, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        //设置点赞用户id
        giveLike.setMemberId(loginMember.getId());
        giveLike.setCreateTime(new Date());
        //保存用户
        giveLikeService.insertSelective(giveLike);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    /**
     * 取消点赞
     * @param request
     * @return
     */
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) GiveLike giveLike, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        if(giveLike.getType() == Quantity.INT_1){
            GiveLikeExample giveLikeExample = new GiveLikeExample();
            giveLikeExample.createCriteria().andMemberIdEqualTo(loginMember.getId()).andTypeEqualTo(giveLike.getType()).andAppraiseIdEqualTo(giveLike.getAppraiseId());
            giveLikeService.deleteByExample(giveLikeExample);
        }else if(giveLike.getType() == Quantity.INT_2){
            GiveLikeExample giveLikeExample = new GiveLikeExample();
            giveLikeExample.createCriteria().andMemberIdEqualTo(loginMember.getId()).andTypeEqualTo(giveLike.getType()).andReplyIdEqualTo(giveLike.getReplyId());
            giveLikeService.deleteByExample(giveLikeExample);
        }

        return BasicResult.success();
    }
}