package com.siam.system.modular.package_goods.model.dto;

import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.Coupons;
import lombok.Data;

@Data
public class CouponsDto extends Coupons {

    //店铺id
    private Integer shopId;

}