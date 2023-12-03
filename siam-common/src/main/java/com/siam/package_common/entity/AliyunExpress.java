package com.siam.package_common.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class AliyunExpress {

    //快递单号
    private String number;

    //快递公司简称
    private String type;

    //物流状态描述信息及对应时间
    private List<Map<String, Object>> list;

    //快递状态 0=快递收件(揽件) 1=在途中 2=正在派件 3=已签收 4=派送失败 5=疑难件 6=退件签收
    private Integer deliverystatus;

    //TODO(MARK)-Gson转换的时候把1转成了false，不知道怎么回事儿
    //是否签收
    private Integer issign;

    //快递公司名称
    private String expName;

    //快递公司官网
    private String expSite;

    //快递公司电话
    private String expPhone;

    //快递员 或 快递站(没有则为空)
    private String courier;

    //快递员电话 (没有则为空)
    private String courierPhone;

    //快递轨迹信息最新时间
    private Date updateTime;

    //发货到收货消耗时长 (截止最新轨迹)
    private String takeTime;

    //快递公司LOGO
    private String logo;

}