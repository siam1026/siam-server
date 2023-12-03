package com.siam.system.modular.package_order.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.annoation.AdminPermission;
import com.siam.system.modular.package_order.service.AppraiseService;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_order.entity.Appraise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 管理端评价接口
 */
@RestController
@RequestMapping(value = "/rest/admin/appraise")
@Transactional(rollbackFor = Exception.class)
public class AdminAppraiseController {

    @Autowired
    private AppraiseService appraiseService;

    /**
     * 撤销评价
     * @return
     */
    @AdminPermission
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Appraise param) {
        appraiseService.deleteByPrimaryKey(param.getId());
        return BasicResult.success();
    }

    /**
     * 评价列表
     * @return
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Appraise param) {
        Page<Map<String, Object>> page = appraiseService.getMapListByPage(param.getPageNo(), param.getPageSize(), param);
        return BasicResult.success(page);
    }
}