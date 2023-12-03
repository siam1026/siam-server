package com.siam.system.modular.package_order.service_impl;

import com.siam.system.modular.package_order.entity.TransactionLog;
import com.siam.system.modular.package_order.mapper.TransactionLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sinotn
 *
 * @Author: libin
 * @CreateTime: 2020-10-28 14:10
 * @Description: 事务日志
 */
@Service
public class TransactionLogService {

    @Autowired
    private TransactionLogMapper transactionLogMapper;

    public int insert(TransactionLog transactionLog){
        return transactionLogMapper.insertTransactionLog(transactionLog);
    }

    public TransactionLog getById(String transId) {
        return transactionLogMapper.selectTransactionLogById(transId);
    }
}