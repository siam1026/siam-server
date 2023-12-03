package com.siam.system.modular.package_goods.model.dto.internal;

import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MemberGoodsCollectDto extends MemberGoodsCollect {

    @ApiModelProperty(notes = "商品名称")
    private String goodsName;


    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

}