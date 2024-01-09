package com.louis.kitty.dbms.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.louis.kitty.dbms.dao.IDatabaseDAO;
import com.louis.kitty.dbms.dao.IMetaDataConverter;
import com.louis.kitty.dbms.exception.DAOException;
import com.louis.kitty.dbms.exception.QueryDAOException;
import com.louis.kitty.dbms.model.Column;
import com.louis.kitty.dbms.model.ForeignKey;
import com.louis.kitty.dbms.model.Index;
import com.louis.kitty.dbms.model.PrimaryKey;
import com.louis.kitty.dbms.model.Table;
import com.louis.kitty.dbms.model.Trigger;
import com.louis.kitty.dbms.vo.ConnParam;

/**
 * 抽象数据库元信息查询类
 * @author Louis
 * @date Nov 10, 2018
 */
public abstract class AbstractDatabasetDAOImpl implements IDatabaseDAO {

	protected IMetaDataConverter converter;
	
	protected ConnParam connParam;
	private Connection connection;

	protected static final String DRIVER = "driver";
	protected static final String URL = "url";
	protected static final String QUERY_TABLE = "query_table";
	protected static final String QUERY_COLUMN = "query_column";
	protected static final String QUERY_INDEX = "query_index";
	protected static final String QUERY_PRIMARY_KEY = "query_primary_key";
	protected static final String QUERY_FOREIGN_KEY = "query_foreign_key";
	protected static final String QUERY_TRIGGER = "query_trigger";

	public AbstractDatabasetDAOImpl(ConnParam connParam) {
		this.connParam = connParam;
	}

	@Override
	public List<Map<String, String>> query(String sql, String[] params)
			throws DAOException {
		if (sql == null) {
			Exception e = new IllegalArgumentException("输入的sql查询语句为空！");
			throw new DAOException(DAOException.QUERY_EXCEPTION, e.getMessage(), e);
		}
		List<Map<String, String>> result = new LinkedList<Map<String, String>>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(sql);
			if (params != null) {
				for (int paramIndex = 0; paramIndex < params.length; paramIndex++) {
					pstmt.setString(paramIndex + 1, params[paramIndex]);
				}
			}
			rs = pstmt.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnSize = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				for (int columnIndex = 1; columnIndex <= columnSize; columnIndex++) {
					// columnName�Ǵ�д
					String columnName = rsmd.getColumnLabel(columnIndex);
					String columnValue = rs.getString(columnName);
					map.put(columnName, columnValue);
				}
				result.add(map);
			}
		} catch (SQLException e) {
			throw new DAOException(DAOException.QUERY_EXCEPTION,
					e.getMessage(), e);
		} finally {
			close(rs, pstmt);
		}
		return result;
	}

	@Override
	public List<Table> getTables() throws DAOException {
		List<Table> tables = new ArrayList<Table>();

		try {
			List<Map<String, String>> result = query(getQuerySql(QUERY_TABLE), null);
			
			for (Map<String, String> map : result) {
				tables.add(converter.convertMap2Table(map));
			}
		} catch (DAOException e) {
			throw new DAOException(DAOException.QUERY_TABLE_EXCEPTION,
					e.getMessage(), e);
		}
		
		return tables;
	}
	
	@Override
	public List<Column> getColumns(String tableName) throws DAOException {
		List<Column> columns = new ArrayList<Column>();
		
		try {
			List<Map<String, String>> result = query(getQuerySql(QUERY_COLUMN),
					new String[] { tableName });
			
			for (Map<String, String> map : result) {
				columns.add(converter.convertMap2Column(map));
			}
		} catch (DAOException e) {
			throw new QueryDAOException(tableName,
					DAOException.QUERY_COLUMN_EXCEPTION, e.getMessage(), e);
		}
		
		return columns;
	}

	@Override
	public List<PrimaryKey> getPrimaryKeys(String tableName)
			throws DAOException {
		List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();
		
		try {
			List<Map<String, String>> result = query(getQuerySql(QUERY_PRIMARY_KEY),
					new String[] { tableName });
			
			for (Map<String, String> map : result) {
				primaryKeys.add(converter.convertMap2PrimaryKey(map));
			}

		} catch (DAOException e) {
			throw new QueryDAOException(tableName,
					DAOException.QUERY_PRIMARY_KEY_EXCEPTION, e.getMessage(), e);
		}
		
		return primaryKeys;
	}

	@Override
	public List<ForeignKey> getForeignKeys(String tableName)
			throws DAOException {
		List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>(0);
		
		try {
			List<Map<String, String>> result = query(getQuerySql(QUERY_FOREIGN_KEY),
					new String[] { tableName });
			
			for (Map<String, String> map : result) {
				foreignKeys.add(converter.convertMap2ForeignKey(map));
			}
		} catch (DAOException e) {
			throw new QueryDAOException(tableName,
					DAOException.QUERY_FOREIGN_KEY_EXCEPTION, e.getMessage(), e);
		}
		
		return foreignKeys;
	}

	@Override
	public List<Index> getIndexes(String tableName) throws DAOException {
		Map<String, Index> indexMap = new HashMap<String, Index>();
		
		try {
			List<Map<String, String>> result = query(getQuerySql(QUERY_INDEX),
					new String[] { tableName });
			for (Map<String, String> map : result) {
				Index index = converter.convertMap2Index(map);
				String indexName = index.getName();
				if (!indexMap.containsKey(indexName)) {
					indexMap.put(indexName, index);
				} else {
					if (!index.getCloumns().isEmpty()) {
						indexMap.get(indexName).addCloumn(index.getCloumns().get(0));
					}
				}
			}
		} catch (DAOException e) {
			throw new QueryDAOException(tableName,
					DAOException.QUERY_INDEX_EXCEPTION, e.getMessage(), e);
		}

		return new LinkedList<Index>(indexMap.values());
	}

	@Override
	public List<Trigger> getTriggers(String tableName) throws DAOException {
		List<Trigger> triggers = new LinkedList<Trigger>();

		try {
			List<Map<String, String>> result = query(getQuerySql(QUERY_TRIGGER),
					new String[] { tableName });
			for (Map<String, String> map : result) {
				triggers.add(converter.convertMap2Trigger(map));
			}
		} catch (DAOException e) {
			throw new QueryDAOException(tableName,
					DAOException.QUERY_TRIGGER_EXCEPTION, e.getMessage(), e);
		}
		
		return triggers;
	}

	@Override
	public Connection openConnection() throws DAOException{
		try {
			closeConnection();

			Class.forName(getDriver());

			Properties props = new Properties();
			props.put("remarksReporting", "true");
			props.put("user", connParam.getUserName());
			props.put("password", connParam.getPassword());
			connection = DriverManager.getConnection(
					getUrl(connParam.getHost(), connParam.getPort(),
							connParam.getDbName()), props);
		} catch (ClassNotFoundException e) {
		    String errorMsg = "连接创建失败，找不到相关驱动类(" + e.getMessage() + ")";
			throw new DAOException(DAOException.OPEN_CONNECTION_EXCEPTION, errorMsg, e);
		} catch (SQLException e) {
			throw new DAOException(DAOException.OPEN_CONNECTION_EXCEPTION, e.getMessage(), e);
		}
		return connection;
	}

	@Override
	public void closeConnection() throws DAOException {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				throw new DAOException(DAOException.CLOSE_CONNECTION_EXCEPTION,
						e.getMessage(), e);
			}
		}
	}

	/**
     * 获取驱动类字符串描述值，抽象方法，由子类实现
     * 
     * @return
     */
    abstract protected String getDriver() throws DAOException;

    /**
     * 获取要连接的数据库url，抽象方法，由子类实现
     * 
     * @param host
     *            数据库地址
     * @param port
     *            数据库端口号
     * @param dbName
     *            数据库实例名称
     * @return
     */
    abstract protected String getUrl(String host, int port, String dbName)
            throws DAOException;

    /**
     * 获取sql查询语句
     * 
     * @param sqlKey
     * @return
     */
    abstract protected String getQuerySql(String sqlKey) throws DAOException;

    /**
     * 关闭jdbc资源对象
     * 
     * @param rs
     * @param stmt
     * @throws DAOException
     */
    protected void close(ResultSet rs, Statement stmt) throws DAOException {
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                throw new DAOException(DAOException.CLOSE_JDBC_EXCEPTION, e.getMessage(), e);
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                        stmt = null;
                    } catch (SQLException e) {
                        throw new DAOException(DAOException.CLOSE_JDBC_EXCEPTION, e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * @param converter the converter to set
     */
    public void setConverter(IMetaDataConverter converter) {
        this.converter = converter;
    }
}
