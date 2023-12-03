package com.siam.system.modular.package_order.controller.merchant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.service.AppraiseService;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.system.modular.package_order.entity.Appraise;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 商家端评价接口
 */
@RestController
@RequestMapping(value = "/rest/merchant/appraise")
@Transactional(rollbackFor = Exception.class)
public class MerchantAppraiseController {

    @Autowired
    private AppraiseService appraiseService;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    /**
     * 撤销评价
     * @param id
     * @param request
     * @return
     */
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Appraise param, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        appraiseService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }


    /**
     * 评价列表
     * @param pageNo
     * @param pageSize
     * @param appraise
     * @return
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) Appraise appraise, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        appraise.setShopId(loginMerchant.getShopId());
        Page<Map<String, Object>> page = appraiseService.getMapListByPage(appraise.getPageNo(), appraise.getPageSize(), appraise);

        return BasicResult.success(page);
    }
}