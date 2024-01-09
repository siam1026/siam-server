package com.louis.kitty.dbms.model;

/**
 * 主键
 * @author Louis
 * @date Nov 10, 2018
 */
public class PrimaryKey {

    /** 名称 */
    private String name;
    /** 所属表名称 */
    private String tableName;
    /** 字段 */
    private String cloumn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCloumn() {
        return cloumn;
    }

    public void setCloumn(String cloumn) {
        this.cloumn = cloumn;
    }

}
