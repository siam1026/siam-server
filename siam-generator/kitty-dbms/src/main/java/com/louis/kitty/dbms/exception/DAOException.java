package com.louis.kitty.dbms.exception;

/**
 * DAO操作异常
 * @author Louis
 * @date Nov 10, 2018
 */
public class DAOException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1041464715086167865L;

    public static final int OPEN_CONNECTION_EXCEPTION = 1;
    public static final int CLOSE_CONNECTION_EXCEPTION = 2;
    public static final int CLOSE_JDBC_EXCEPTION = 3;
    public static final int QUERY_EXCEPTION = 8;
    public static final int QUERY_TABLE_EXCEPTION = 9;
    public static final int QUERY_COLUMN_EXCEPTION = 10;
    public static final int QUERY_PRIMARY_KEY_EXCEPTION = 11;
    public static final int QUERY_FOREIGN_KEY_EXCEPTION = 12;
    public static final int QUERY_INDEX_EXCEPTION = 13;
    public static final int QUERY_TRIGGER_EXCEPTION = 14;
    
    private final int type;

    public DAOException(int type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        String errorMsg = null;
        switch (type) {
        case OPEN_CONNECTION_EXCEPTION:
            errorMsg = "创建数据库连接失败：";
            break;
        case CLOSE_CONNECTION_EXCEPTION:
            errorMsg = "关闭数据库连接失败：";
            break;
        case CLOSE_JDBC_EXCEPTION:
            errorMsg = "关闭jdbc资源出错：";
            break;
        default:
            errorMsg = "查询出错：";
        }
        return errorMsg + getMessage();
    }
}
