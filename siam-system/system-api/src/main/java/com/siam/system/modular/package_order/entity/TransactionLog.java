package com.siam.system.modular.package_order.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Sinotn
 *
 * @Author: libin
 * @CreateTime: 2020-10-28 11:15
 * @Description: 事务日志DAO
 */
@Data
public class TransactionLog implements Serializable {

    private String id;

    private String business;

    private String foreignKey;
}
