package com.siam.system.modular.package_order.internal;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.siam.system.modular.package_order.entity.internal.PointsMallOrder;
import com.siam.system.modular.package_order.mapper.internal.PointsMallOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class PointsMallOrderApplicationTest {

    @Autowired
    private PointsMallOrderMapper orderMapper;

    /**
     * 分库分表插入测试
     *
     * @return
     * @author 暹罗
     */
    @Test
    public void testInsert() {
        for(int i = 0; i < 1000; i++){
            PointsMallOrder order = new PointsMallOrder();
            order.setMemberId(i);
            order.setOrderNo("orderNo" + i);
            orderMapper.insertSelective(order);
        }
    }

    /**
     * 分库分表修改测试
     *
     * @return
     * @author 暹罗
     */
    @Test
    public void testUpdate() {
        PointsMallOrder order = new PointsMallOrder();
        order.setId(901801363208409089L);
        order.setOrderNo("test-update-01");
        orderMapper.updateById(order);
    }

    /**
     * 分库分表删除测试
     *
     * @return
     * @author 暹罗
     */
    @Test
    public void testDelete() {
        orderMapper.deleteByPrimaryKey(901801363208409089L);
    }

    /**
     * 分库分表查询测试
     *
     * @return
     * @author 暹罗
     */
    @Test
    public void testSelect() {
        LambdaQueryWrapper<PointsMallOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PointsMallOrder::getId, 901828162944503808L);
        queryWrapper.eq(PointsMallOrder::getMemberId, 1);
//        queryWrapper.orderByAsc(PointsMallOrder::getOrderNo);
//        queryWrapper.last("limit 10");
        List<PointsMallOrder> list = orderMapper.selectList(queryWrapper);
        System.out.println(JSON.toJSONString(list));
    }
}