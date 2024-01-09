package com.louis.kitty.dbms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.louis.kitty.dbms.dao.DatabaseDAOFactory;
import com.louis.kitty.dbms.dao.IDatabaseDAO;
import com.louis.kitty.dbms.exception.DAOException;
import com.louis.kitty.dbms.model.Column;
import com.louis.kitty.dbms.model.ForeignKey;
import com.louis.kitty.dbms.model.Index;
import com.louis.kitty.dbms.model.PrimaryKey;
import com.louis.kitty.dbms.model.Table;
import com.louis.kitty.dbms.model.Trigger;
import com.louis.kitty.dbms.service.DatabaseService;
import com.louis.kitty.dbms.vo.ConnParam;
/**
 * 数据库元信息查询服务实现类
 * @author Louis
 * @date Nov 10, 2018
 */
@Component
public class DatabaseServiceImpl implements DatabaseService {

	@Override
	public List<Map<String, String>> query(ConnParam connParam, String sql, String[] params) {
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		if(connParam == null) {
			return maps;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    long start = System.currentTimeMillis();
		    dao.openConnection();
		    maps = dao.query(sql, params);
		    dao.closeConnection();
		    long end = System.currentTimeMillis();
		    System.out.println("反向获取数据库表信息耗时：" + (end - start) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}

	@Override
	public List<Table> getTables(ConnParam connParam) {
		List<Table> tables = new ArrayList<Table>();
		if(connParam == null) {
			return tables;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    long start = System.currentTimeMillis();
		    dao.openConnection();
		    tables = dao.getTables();
		    dao.closeConnection();
		    long end = System.currentTimeMillis();
		    System.out.println("反向获取数据库表信息耗时：" + (end - start) + "毫秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public List<Column> getColumns(ConnParam connParam, String tableName) {
		List<Column> columns = new ArrayList<Column>();
		if(connParam == null) {
			return columns;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    dao.openConnection();
		    columns = dao.getColumns(tableName);
		    dao.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return columns;
	}

	@Override
	public List<PrimaryKey> getPrimaryKeys(ConnParam connParam, String tableName) {
		List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();
		if(connParam == null) {
			return primaryKeys;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    dao.openConnection();
		    primaryKeys = dao.getPrimaryKeys(tableName);
		    dao.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return primaryKeys;
	}

	@Override
	public List<ForeignKey> getForeignKeys(ConnParam connParam, String tableName) {
		List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
		if(connParam == null) {
			return foreignKeys;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    dao.openConnection();
		    foreignKeys = dao.getForeignKeys(tableName);
		    dao.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foreignKeys;
	}

	@Override
	public List<Index> getIndexes(ConnParam connParam, String tableName) {
		List<Index> indexes = new ArrayList<Index>();
		if(connParam == null) {
			return indexes;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    dao.openConnection();
		    indexes = dao.getIndexes(tableName);
		    dao.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexes;
	}

	@Override
	public List<Trigger> getTriggers(ConnParam connParam, String tableName) {
		List<Trigger> trigger = new ArrayList<Trigger>();
		if(connParam == null) {
			return trigger;
		}
		try {
		    IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		    dao.openConnection();
		    trigger = dao.getTriggers(tableName);
		    dao.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trigger;
	}

	@Override
	public boolean canConnect(ConnParam connParam) {
		IDatabaseDAO dao = DatabaseDAOFactory.getDAO(connParam);
		if (dao == null) {
			return false;
		}
		try {
			dao.openConnection();
			System.out.println("数据库连接成功!");
			return true;
		} catch (Exception e) {
			System.out.println("数据库连接失败,请检查端口号、用户名或密码 !");
		} finally {
			try {
				dao.closeConnection();
			} catch (DAOException e) {
			}
		}
		return false;
	}
	
}
