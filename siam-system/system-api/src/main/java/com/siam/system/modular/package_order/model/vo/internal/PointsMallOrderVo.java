package com.siam.system.modular.package_order.model.vo.internal;

import com.siam.system.modular.package_order.entity.internal.PointsMallOrderDetail;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "订单表")
public class PointsMallOrderVo {

    private Object order;

    private List<PointsMallOrderDetail> orderDetailList;

}