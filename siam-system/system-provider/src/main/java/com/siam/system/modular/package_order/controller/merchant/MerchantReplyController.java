package com.siam.system.modular.package_order.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.modular.package_order.service.ReplyService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_order.entity.Reply;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 商家端回复接口
 */
@RestController
@RequestMapping(value = "/rest/merchant/reply")
@Transactional(rollbackFor = Exception.class)
public class MerchantReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    /**
     * 回复
     * @param reply
     * @return
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) Reply reply, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());
        //设置回复商家id
        reply.setMerchantId(loginMerchant.getId());
        reply.setReplierType(Quantity.INT_2);

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
    public BasicResult list(@RequestBody @Validated(value = {}) Reply reply) {
        BasicData basicResult = new BasicData();

        Page<Reply> page = replyService.getListByPage(reply.getPageNo(), reply.getPageSize(), reply);

        return BasicResult.success(page);
    }


}
