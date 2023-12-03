package com.siam.system.modular.package_goods.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchParam {

    //搜索关键字
    @NotBlank(message = "搜索关键字不能为空")
    private String keywords;

    //排序方式 1=综合排序 2=好评优先 3=起送价最低 4=配送最快 5=配送费最低 6=人均从低到高 7=人均从高到低 8=通用排序 9=距离最近 10=销量最高
    private Integer sortWay;

    //配送方式 1=蜂鸟专送
    private Integer deliveryWay;

    //人均消费 1=20以下 2=20~40 3=40以上
    private Integer perCapitaConsumption;

    //优惠活动 1=首次光顾立减 2=满减优惠 3=下单返红包 4=进店领红包 5=配送费优惠 6=赠品优惠 7=特价商品 8=品质联盟红包
    private Integer promotion;

    //商家属性(可多选，多个以逗号隔开) 1=到店自取 2=品牌商家 3=新店 4=接受预定 5=食无忧 6=开发票
    private Integer merchantAttribute;

    String position;


    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

}