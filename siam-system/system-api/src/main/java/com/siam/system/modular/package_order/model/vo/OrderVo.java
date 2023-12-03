package com.siam.system.modular.package_order.model.vo;

import com.siam.system.modular.package_order.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "订单表")
public class OrderVo {

    private Object order;

    private List<OrderDetail> orderDetailList;
}