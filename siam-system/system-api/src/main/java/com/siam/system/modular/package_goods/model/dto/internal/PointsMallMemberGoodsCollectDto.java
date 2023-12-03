package com.siam.system.modular.package_goods.model.dto.internal;

import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PointsMallMemberGoodsCollectDto extends PointsMallMemberGoodsCollect {

    @ApiModelProperty(notes = "商品名称")
    private String goodsName;

}