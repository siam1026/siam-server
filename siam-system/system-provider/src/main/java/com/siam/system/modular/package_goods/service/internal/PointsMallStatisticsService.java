package com.siam.system.modular.package_goods.service.internal;

import com.siam.system.modular.package_goods.model.param.StatisticsParam;

import java.util.Map;

/**
 *  暹罗
 */
public interface PointsMallStatisticsService {

    /**
     * 今日数据实时统计(含积分商城数据) - 管理端
     *
     * @author 暹罗
     */
    Map todayStatisticWithPointsMallByAdmin(StatisticsParam param);

    /**
     * 积分商城-今日数据实时统计 - 管理端
     *
     * @author 暹罗
     */
    Map pointsMallTodayStatisticByAdmin(StatisticsParam param);
}