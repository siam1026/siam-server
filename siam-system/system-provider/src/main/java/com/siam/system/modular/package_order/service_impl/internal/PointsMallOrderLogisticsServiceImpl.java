package com.siam.system.modular.package_order.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.entity.AliyunExpress;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.package_common.util.AliyunExpressUtils;
import com.siam.package_common.util.CommonUtils;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrderLogistics;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderLogisticsMapper;
import com.siam.system.modular.package_order.model.example.internal.PointsMallOrderLogisticsExample;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderLogisticsService;
import com.siam.system.modular.package_order.service.internal.PointsMallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallOrderLogisticsServiceImpl implements PointsMallOrderLogisticsService {

    @Autowired
    private PointsMallOrderLogisticsMapper orderLogisticsMapper;

    @Autowired
    private AliyunExpressUtils aliyunExpressUtils;

    @Resource(name = "pointsMallOrderServiceImpl")
    private PointsMallOrderService orderService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    public int countByExample(PointsMallOrderLogisticsExample example){
        return orderLogisticsMapper.countByExample(example);
    }

    public void deleteByPrimaryKey(Integer id){
        orderLogisticsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByExample(PointsMallOrderLogisticsExample example) {
        orderLogisticsMapper.deleteByExample(example);
    }

    public void insertSelective(PointsMallOrderLogistics record){
        orderLogisticsMapper.insertSelective(record);
    }

    public List<PointsMallOrderLogistics> selectByExample(PointsMallOrderLogisticsExample example){
        return orderLogisticsMapper.selectByExample(example);
    }

    public PointsMallOrderLogistics selectByPrimaryKey(Integer id){
        return orderLogisticsMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(PointsMallOrderLogistics record, PointsMallOrderLogisticsExample example){
        orderLogisticsMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(PointsMallOrderLogistics record){
        orderLogisticsMapper.updateByPrimaryKeySelective(record);
    }

    public Page getListByPage(int pageNo, int pageSize, PointsMallOrderLogistics orderLogistics) {
        Page<Map<String, Object>> page = orderLogisticsMapper.getListByPage(new Page(pageNo, pageSize), orderLogistics);
        return page;
    }

    public Boolean updateOrderLogisticsInfo(Long orderId, String logisticsNo) {
        AliyunExpress aliyunExpress = aliyunExpressUtils.query(logisticsNo);
        if (aliyunExpress != null){
            //更新订单信息
            PointsMallOrder updateOrder = new PointsMallOrder();
            updateOrder.setId(orderId);
            updateOrder.setLogisticsWay(aliyunExpress.getExpName());
            updateOrder.setCourierName(aliyunExpress.getCourier());
            updateOrder.setCourierPhone(aliyunExpress.getCourierPhone());
            updateOrder.setDeliveryStatus(aliyunExpress.getDeliverystatus());
            updateOrder.setIsSign(aliyunExpress.getIssign()==1 ? true : false);
            updateOrder.setDeliveryLastUpdateTime(aliyunExpress.getUpdateTime());
            updateOrder.setTakeTime(aliyunExpress.getTakeTime());
            orderService.updateByPrimaryKeySelective(updateOrder);

            //更新订单物流跟踪信息，采用先删后增的形式，排除系统默认插入的两条信息
            List<String> excludeDescriptionList = new ArrayList<>();
            excludeDescriptionList.add(PointsMallOrderLogistics.DESCRIPTION_OF_FIRST_COMMIT_ORDER);
            excludeDescriptionList.add(PointsMallOrderLogistics.DESCRIPTION_OF_SECOND_DELIVERED);
            PointsMallOrderLogisticsExample logisticsExample = new PointsMallOrderLogisticsExample();
            logisticsExample.createCriteria().andOrderIdEqualTo(orderId).andDescriptionNotIn(excludeDescriptionList);
            orderLogisticsMapper.deleteByExample(logisticsExample);
            //添加订单物流跟踪信息
            aliyunExpress.getList().forEach(map -> {
                PointsMallOrderLogistics logistics = new PointsMallOrderLogistics();
                logistics.setOrderId(orderId);
                logistics.setDescription(map.get("status").toString());
                logistics.setDescriptionTime(new Date(map.get("time").toString().replaceAll("-", "/")));
                logistics.setCreateTime(new Date());
                orderLogisticsMapper.insertSelective(logistics);
            });
            return true;
        }else{
            //查询不到物流跟踪信息
            //微信公众号消息通知管理员
            String errorMsg = "物流跟踪信息同步失败，请检查失败原因";
            wxPublicPlatformNotifyService.sendFatalErrorMessage(WxPublicPlatformNotifyService.jpOpenId, errorMsg, CommonUtils.getFunctionLocation(), "无", new Date(), "请管理员及时处理");
            return false;
        }
    }
}