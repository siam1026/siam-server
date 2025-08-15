package com.siam.system.modular.package_order.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderLogisticsService;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderLogistics;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/pointsMall/orderLogistics")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "订单物流跟踪信息模块相关接口", description = "PointsMallOrderLogisticsController")
public class PointsMallOrderLogisticsController {

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService orderService;

    @Autowired
    private PointsMallOrderLogisticsService orderLogisticsService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    /**
     * 订单物流跟踪信息列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallOrderLogistics orderLogistics, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallOrder dbPointsMallOrder = orderService.getById(orderLogistics.getOrderId());
        if(dbPointsMallOrder == null){
            throw new StoneCustomerException("该订单不存在");
        }
        if(!dbPointsMallOrder.getMemberId().equals(loginMember.getId())){
            throw new StoneCustomerException("该订单不是你的，不允许查看");
        }

        Page<Map<String, Object>> page = orderLogisticsService.getListByPage(orderLogistics.getPageNo(), orderLogistics.getPageSize(), orderLogistics);

        return BasicResult.success(page);
    }
}