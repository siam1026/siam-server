package com.siam.system.modular.package_goods.service;

import com.siam.system.modular.package_goods.model.param.StatisticsParam;

import java.util.Map;

/**
 *  暹罗
 */
public interface StatisticsService {

    /**
     * 今日数据实时统计 - 管理端
     *
     * @author 暹罗
     */
    Map todayStatisticByAdmin(StatisticsParam param);

    /**
     * 今日数据实时统计 - 商家端
     *
     * @author 暹罗
     */
    Map todayStatisticByMerchant(StatisticsParam param);
}