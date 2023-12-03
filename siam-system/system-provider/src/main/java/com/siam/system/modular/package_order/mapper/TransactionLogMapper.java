package com.siam.system.modular.package_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siam.system.modular.package_order.entity.TransactionLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Sinotn
 *
 * @Author: libin
 * @CreateTime: 2020-10-28 11:32
 * @Description: 事务日志表
 */
@Mapper
public interface TransactionLogMapper extends BaseMapper<TransactionLog> {

    /**
     * 插入事务日志
     *
     * @param transactionLog 事务日志实体
     * @return 结果
     */
    int insertTransactionLog(TransactionLog transactionLog);

    /**
     * 查询事务日志
     * @param transId 事务ID
     * @return
     */
    TransactionLog selectTransactionLogById(String transId);
}
