package com.louis.kitty.dbms.vo;

import java.io.Serializable;

/**
 * 数据库连接参数封装
 * @author Louis
 * @date Nov 10, 2018
 */
public class ConnParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * 数据库类型
     */
    private String dbType;
    /**
     * 数据库地址
     */
    private String host;
    /**
     * 数据库端口
     */
    private int port;
    /**
     * 数据库实例名
     */
    private String dbName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    
    
    public ConnParam() {
    	
    }
    
    /**
     * @param host
     * @param port
     * @param dbName
     * @param userName
     * @param password
     */
    public ConnParam(String dbType, String host, int port, String dbName, String userName, String password) {
        super();
        this.dbType = dbType;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}