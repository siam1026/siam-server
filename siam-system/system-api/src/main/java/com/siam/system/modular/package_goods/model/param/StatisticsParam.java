package com.siam.system.modular.package_goods.model.param;

import lombok.Data;

import java.util.Date;

/**
 * 用户表
 *
 * @author 暹罗
 */
@Data
public class StatisticsParam {

    //开始日期
    private Date startCreateTime;

    //结束日期
    private Date endCreateTime;

}