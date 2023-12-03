package com.siam.system.modular.package_order.model.param;

import com.siam.package_common.model.valid_group.ValidGroupOfAudit;
import com.siam.package_common.model.valid_group.ValidGroupOfId;
import com.siam.system.modular.package_order.entity.Order;
import com.siam.system.modular.package_order.entity.OrderRefund;
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
public class OrderParam extends Order {

    private Date startTime;

    private Date endTime;

    //标识 1=处理订单 2=标记完成(自取订单) 3=标记配送 4=标记完成(配送订单)
    private Integer flag;

    private String idListStr;

    private List<String> ids;

    private String orderDetailListStr;

    private List<Integer> shoppingCartIdList;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

    //开始日期
    private Date startCreateTime;

    //结束日期
    private Date endCreateTime;

    //waitPayment=待付款、waitReceived=待收货、waitPickedUp待自提、afterSales退款/售后
    private String tabType;

    //搜索关键字(联系人手机号、订单号)
    private String keyWords;

    //排除的订单id
    private Integer excludeOrderId;

    @NotNull(message = "主键id不能为空", groups = {ValidGroupOfId.class})
    @ApiModelProperty(notes = "主键id")
    private Integer id;

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

    private BigDecimal actualPrice;

    private OrderRefund orderRefund;

    private String orderRefundGoodsListStr;

}