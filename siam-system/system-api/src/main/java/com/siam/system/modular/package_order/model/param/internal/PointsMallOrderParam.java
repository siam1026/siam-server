package com.siam.system.modular.package_order.model.param.internal;

import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderDetail;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderRefund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "订单表")
public class PointsMallOrderParam extends PointsMallOrder {

    private List<String> ids;

    @ApiModelProperty(notes = "下单开始时间")
    private Date startCreateTime;

    @ApiModelProperty(notes = "下单结束时间")
    private Date endCreateTime;

    Date startTime;

    Date endTime;

    String idListStr;

    //waitPayment=待付款、waitDelivered待发货、waitReceived=待收货、afterSales退款/售后
    private String tabType;

    //搜索关键字(联系人手机号、订单号)
    private String keyWords;

    //订单商品详情列表
    private List<PointsMallOrderDetail> orderDetailList;

    //排除的订单id
    private Long excludeOrderId;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

    PointsMallOrder order;

    List<Integer> shoppingCartIdList;

    PointsMallOrderRefund orderRefund;

    String orderRefundGoodsListStr;

    BigDecimal actualPrice;

    @NotNull(message = "主键id不能为空", groups = {ValidGroupOfId.class})
    @ApiModelProperty(notes = "主键id")
    private Long id;

    /**
     * 审核状态(1=通过 2=不通过)
     */
    @NotNull(message = "审核状态不能为空", groups = {ValidGroupOfAudit.class})
    @Range(min = 1, max = 2, message = "审核状态必须介于1~2之间", groups = {ValidGroupOfAudit.class})
    private Integer status;

    /**
     * 审核意见(当审核状态为不通过时必填)
     */
    private String opinion;

    /**
     * 防重令牌
     */
    private String orderToken;
}