package com.louis.kitty.dbms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引
 * @author Louis
 * @date Nov 10, 2018
 */
public class Index {
    
    /** 名称 */
    private String name;
    /** 所属类型 */
    private String indexType;
    /** 所属表名称 */
    private String tableName;
    /** 是否唯一索引 */
    private boolean unique;
    /** 字段列表 */
    private List<String> cloumns = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public List<String> getCloumns() {
        return cloumns;
    }

    public void addCloumn(String cloumn) {
        this.cloumns.add(cloumn);
    }

}
