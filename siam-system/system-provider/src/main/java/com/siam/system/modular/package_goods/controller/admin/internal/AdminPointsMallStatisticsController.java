package com.siam.system.modular.package_goods.controller.admin.internal;

import com.siam.package_common.entity.BasicResult;
import com.siam.system.modular.package_goods.model.param.StatisticsParam;
import com.siam.system.modular.package_goods.service.internal.PointsMallStatisticsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/rest/admin/statistics/pointsMall")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "后台统计模块相关接口", description = "AdminAdvertisementController")
public class AdminPointsMallStatisticsController {

    @Autowired
    private PointsMallStatisticsService pointsMallStatisticsService;

    /**
     * 今日数据实时统计(含有积分商城数据)
     *
     * @author 暹罗
     */
    @PostMapping(value = "/todayStatisticWithPointsMall")
    public BasicResult todayStatisticWithPointsMall(@RequestBody @Validated(value = {}) StatisticsParam param) {
        Map result = pointsMallStatisticsService.todayStatisticWithPointsMallByAdmin(param);
        return BasicResult.success(result);
    }

    /**
     * 积分商城-今日数据实时统计
     *
     * @author 暹罗
     */
    @PostMapping(value = "/todayStatistic")
    public BasicResult pointsMallTodayStatistic(@RequestBody @Validated(value = {}) StatisticsParam param) {
        Map result = pointsMallStatisticsService.pointsMallTodayStatisticByAdmin(param);
        return BasicResult.success(result);
    }
}