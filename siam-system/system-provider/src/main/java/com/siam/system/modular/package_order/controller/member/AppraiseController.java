package com.siam.system.modular.package_order.controller.member;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_order.entity.Appraise;
import com.siam.system.modular.package_order.model.param.AppraiseParam;
import com.siam.system.modular.package_order.service.AppraiseService;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 客户端评价接口
 */
@RestController
@RequestMapping(value = "/rest/member/appraise")
@Transactional(rollbackFor = Exception.class)
public class AppraiseController {

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    /**
     * 评价
     * @param param
     * @return
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) AppraiseParam param, HttpServletRequest request) {
        BasicResult basicResult = new BasicResult();

        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Order dbOrder = orderService.getById(param.getOrderId());
        if(dbOrder == null) throw new StoneCustomerException("该订单不存在");
        if(!dbOrder.getMemberId().equals(loginMember.getId())) throw new StoneCustomerException("该订单不是你的，不允许评价");

        //如果订单已评价 或 订单已完成超过14天 或 订单非已完成状态，则不允许评价
        Appraise appraiseCondition = new Appraise();
        appraiseCondition.setOrderId(dbOrder.getId());
        appraiseCondition.setMemberId(dbOrder.getMemberId());
        boolean isAllowAppraise = appraiseService.getIsAllowAppraise(appraiseCondition);
        if(isAllowAppraise){
            if(dbOrder.getStatus() != Quantity.INT_6){
                isAllowAppraise = false;
                throw new StoneCustomerException("该订单未完成，不允许评价");
            }
            if (DateUtilsPlus.diffDays(new Date(), dbOrder.getCreateTime()) > 14){
                isAllowAppraise = false;
                throw new StoneCustomerException("该订单已完成超过14天，不允许评价");
            }
        }else{
            throw new StoneCustomerException("您已经对评价过该订单，不允许重复评价");
        }

        Appraise appraise = new Appraise();
        BeanUtils.copyProperties(param, appraise);
        if(CollectionUtil.isNotEmpty(param.getImagesUrl())){
            appraise.setImagesUrl(String.join(",", param.getImagesUrl()));
        }

        //设置评价用户id
        appraise.setMemberId(loginMember.getId());
        //保存用户
        appraiseService.insertSelective(appraise);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    /**
     * 撤销评价
     * @param id
     * @param request
     * @return
     */
    @DeleteMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) Appraise param) {
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
    public BasicResult list(@RequestBody @Validated(value = {}) Appraise appraise, HttpServletRequest request) {
        BasicData basicResult = new BasicData();

        String token = request.getHeader("token");
        if(StringUtils.isNotBlank(token)){
            Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
            appraise.setMemberId(loginMember.getId());
        }

        Page<Map<String, Object>> page = appraiseService.getMapListByPage(appraise.getPageNo(), appraise.getPageSize(), appraise);

        return BasicResult.success(page);
    }

}
