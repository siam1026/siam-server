package com.siam.system.modular.package_order.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderExample;
import com.siam.system.modular.package_order.model.param.internal.PointsMallOrderParam;
import com.siam.system.modular.package_order.model.result.internal.PointsMallOrderResult;
import com.siam.system.modular.package_order.model.vo.internal.PointsMallOrderVo;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PointsMallOrderService {

    int countByExample(PointsMallOrderExample example);

    void deleteByPrimaryKey(Long id);

    /**
     * 生成防重令牌
     *
     * @return
     * @author 暹罗
     */
    String createOrderToken(PointsMallOrderParam param);

    PointsMallOrder insert(PointsMallOrderParam param) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    /**
     * [本地事务]
     *
     * @param param
     * @param transId
     * @return
     * @author 暹罗
     */
    PointsMallOrder insertByMQ(PointsMallOrderParam param, String transId) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    List<PointsMallOrder> selectByExample(PointsMallOrderExample example);

    PointsMallOrder selectByPrimaryKey(Long id);

    void updateByExampleSelective(PointsMallOrder record, PointsMallOrderExample example);

    void updateByPrimaryKeySelective(PointsMallOrder record);

    Page<PointsMallOrderResult> getListByPageWithAsc(PointsMallOrderParam param);

    Page<Map<String, Object>> getListByPageWithDesc(PointsMallOrderParam param);

    PointsMallOrder selectByOrderNo(String orderNo);

    /**
     * 关闭超时未支付的订单
     */
    void closeOverdueOrder(Long id);

    void updateByFinishOverdueOrder();

    void printRceceipts(Long id);

    int batchUpdateIsPrintedTrue(List<Long> idList);

    Integer getNextQueueNo();

    /**
     * 今日订单列表
     * @param pageNo
     * @param pageSize
     * @param orderDto
     */
    Page<PointsMallOrderResult> getListByTodayOrderWithAsc(PointsMallOrderParam param);

    /**
     * 普通订单回调
     * 支付成功回调时修改订单状态，并且触发一系列关联操作
     * @param outTradeNo 商户单号
     */
    void paymentNotify(String outTradeNo);

    /**
     * 自取订单改为发货回调
     * 支付成功回调时修改订单状态，并且触发一系列关联操作
     * @param changeToDeliveryOutTradeNo 自取订单改为发货的商户单号
     */
    void updateByChangeToDeliveryPayNotify(String changeToDeliveryOutTradeNo);

    /**
     * @description:订单统计(支付成功订单数量、取消订单数量、退款订单数量，按自取或者外卖分开)
     * @throws
     * @author Chen Qu
     * @date 2020/4/22 18:47
     */
    Map countOrder(PointsMallOrderParam order);

    /**
     * 按店铺统计近一月销量/某个时间段的销量
     *
     * @param startTime
     * @param endTime
     * @param shopId
     * @return
     * @author 暹罗
     */
    int selectLatelyMonthlySalesByShopId(Date startTime, Date endTime, Integer shopId);

    /**
     * 查询支付金额(商家实际到手金额)
     */
    BigDecimal selectSumMerchantIncome(PointsMallOrderParam order,Date startTime, Date endTime);

    /**
     * 查询用户实际支付金额
     */
    BigDecimal selectSumActualPrice(PointsMallOrderParam order,Date startTime, Date endTime);

    /**
     * 查询支付订单数量
     */
    int selectCountCompleted(PointsMallOrderParam order,Date startTime, Date endTime);

    boolean getIsAllowApplyRefund(PointsMallOrderParam order);

    boolean getIsAllowApplyAfterSales(PointsMallOrderParam order);

    boolean getIsShowLogistics(PointsMallOrderParam order);

    Page<Map<String, Object>> getAfterSalesListByPageWithAsc(PointsMallOrderParam param);

    /**
     * 检测给商家发放用户下单资金
     * @return
     **/
    void updatePayOrderFrozenBalanceOfMerchant();

    void remindOvertimeOrder();

    List<Map<String, Object>> selectStatisticOrder(Integer shopId);

    String selectStartDatePointsMallOrder(Integer shopId);

    int selectCountPayers(PointsMallOrderParam order,Date startTime, Date endTime);

    int selectCountOrderPeoples(PointsMallOrderParam order,Date startTime, Date endTime);

    void updateRefundStatus(String out_trade_no);

    void syncOrderLogisticsInfo();

    int selectCountPaid(PointsMallOrderParam order,Date startTime, Date endTime);

    void batchUpdateIsPrintedTrue(PointsMallOrderParam param);

    Map selectAllTabWaitHandleNum(PointsMallOrderParam param);

    List<PointsMallOrder> waitPrintPointsMallOrderList(PointsMallOrderParam param);

    void auditAfterSalesOrder(PointsMallOrderParam param);

    Map statistic(PointsMallOrderParam param) throws ParseException;

    void deliver(PointsMallOrderParam param);

    void updateLogisticsNo(PointsMallOrderParam param);

    void updateByAdmin(PointsMallOrderParam param);

    PointsMallOrderVo selectById(PointsMallOrderParam param);

    void cancelOrder(PointsMallOrderParam param);

    void applyRefundUndelivered(PointsMallOrderParam param);

    void onlyRefund(PointsMallOrderParam param);

    void returnGoodsWithRefund(PointsMallOrderParam param);

    void confirmReceipt(PointsMallOrderParam param);

    void delete(PointsMallOrderParam param);

    Map selectRefundProcess(PointsMallOrderParam param);

    Map selectTabNumber(PointsMallOrderParam param);

    String selectCommissionReward(PointsMallOrderParam param);

}