package com.siam.system.modular.package_order.model.result.internal;

import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderDetail;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "订单表")
public class PointsMallOrderResult extends PointsMallOrder {

    //订单商品详情列表
    private List<PointsMallOrderDetail> orderDetailList;
}