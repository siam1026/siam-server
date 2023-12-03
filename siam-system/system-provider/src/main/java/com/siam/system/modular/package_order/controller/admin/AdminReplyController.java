package com.siam.system.modular.package_order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.service.ReplyService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_order.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端回复接口
 */
@RestController
@RequestMapping(value = "/rest/admin/reply")
@Transactional(rollbackFor = Exception.class)
public class AdminReplyController {

    @Autowired
    private ReplyService replyService;

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
