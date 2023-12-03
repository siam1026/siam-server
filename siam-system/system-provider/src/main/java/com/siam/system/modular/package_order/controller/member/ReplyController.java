package com.siam.system.modular.package_order.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_order.service.ReplyService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_order.entity.Reply;
import com.siam.system.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 客户端回复接口
 */
@RestController
@RequestMapping(value = "/rest/member/reply")
@Transactional(rollbackFor = Exception.class)
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    /**
     * 回复
     * @param reply
     * @return
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Reply reply, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        //设置回复用户id
        reply.setMemberId(loginMember.getId());
        //保存用户
        replyService.insertSelective(reply);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    /**
     * 撤销回复
     * @param id
     * @param request
     * @return
     */
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Reply param) {
        BasicResult basicResult = new BasicResult();

        replyService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }


    /**
     * 回复列表
     * @param pageNo
     * @param pageSize
     * @param reply
     * @return
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Reply reply, HttpServletRequest request) {
        BasicData basicResult = new BasicData();

        String token = request.getHeader("token");
        if(StringUtils.isNotBlank(token)){
            Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
            reply.setMemberId(loginMember.getId());
        }

        Page<Map<String, Object>> page = replyService.getMapListByPage(reply.getPageNo(), reply.getPageSize(), reply);

        return BasicResult.success(page);
    }
}