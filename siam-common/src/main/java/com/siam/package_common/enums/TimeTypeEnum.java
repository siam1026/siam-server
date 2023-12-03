package com.siam.package_common.enums;

/**
 * 日期类型相关异常枚举
 *
 * @author 
 * @date 2020/3/26 10:11
 */
public enum TimeTypeEnum {

    /**
     * 日
     */
    DATE(0, "日"),

    /**
     * 月
     */
    MONTH(1, "月"),

    /**
     * 年
     */
    YEAR(2, "年");

    private final Integer type;

    private final String message;

    TimeTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }

    public Integer getType() {
        return type;
    }
}