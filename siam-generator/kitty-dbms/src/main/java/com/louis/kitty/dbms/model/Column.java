package com.louis.kitty.dbms.model;

/**
 * 字段
 * @author Louis
 * @date Nov 10, 2018
 */
public class Column {

    /** 名称 */
    private String name;
    /** 表名称 */
    private String tableName;
    /** 数据类型 */
    private String dataType;
    /** 是否可空 */
    private boolean isNullable;
    /** 默认值 */
    private String defaultValue;
    /** 长度 */
    private String length;
    /** 小数位 */
    private String precision;
    /** 描述 */
    private String description;

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

