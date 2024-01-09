package com.louis.kitty.dbms.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.louis.kitty.dbms.dao.sql.DatabaseType;
import com.louis.kitty.dbms.exception.DAOException;
import com.louis.kitty.dbms.utils.Dom4jUtils;
import com.louis.kitty.dbms.vo.ConnParam;

/**
 * 通用数据库元信息查询类
 * @author Louis
 * @date Nov 10, 2018
 */
public class CommonDatabaseDAOImpl extends AbstractDatabasetDAOImpl {

    private static final String ELEMENT_DRIVER = DRIVER;
    private static final String ELEMENT_URL = URL;
    private static final String ELEMENT_SELECT = "select";
    private static final String ATTRIBUTE_NAME = "name";
    
    private String driver;
    private String url;
    private Map<String, String> selectMap = new HashMap<String,String>();
    
    private DatabaseType dbType;
    
    /**
     * @param connParam
     */
    public CommonDatabaseDAOImpl(ConnParam connParam, DatabaseType dbType) {
        super(connParam);
        
        setDbType(dbType);
        loadSqlXml(dbType);
    }

    /**
     * @param dbType the dbType to set
     */
    public void setDbType(DatabaseType dbType) {
        this.dbType = dbType;
        setConverter(dbType.getConverter());
    }
    
    @Override
    protected String getDriver() throws DAOException {
        return driver;
    }

    @Override
    protected String getUrl(String host, int port, String dbName) throws DAOException{
        return String.format(url, host, port, dbName);
    }
    
    protected String getQuerySql(String sqlKey) throws DAOException {
        if (selectMap.containsKey(sqlKey)) {
            return selectMap.get(sqlKey);
        }
        throw new DAOException(DAOException.QUERY_EXCEPTION, "获取sql查询出错，数据库枚举类型为：" + dbType + "，查询语句为：" + sqlKey, null);
    }

    @SuppressWarnings("unchecked")
    private void loadSqlXml(DatabaseType dbType) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(
                dbType.getFileName());
        Document doc = Dom4jUtils.getDocument(is);
        if (doc != null) {
            Element root = doc.getRootElement();
            
            driver = root.elementText(ELEMENT_DRIVER);
            url = root.elementText(ELEMENT_URL);
            
            for (Element selectElem : (List<Element>) root.elements(ELEMENT_SELECT)) {
                selectMap.put(selectElem.attributeValue(ATTRIBUTE_NAME), selectElem.getTextTrim());
            }
        }
        try {
            is.close();
        } catch (IOException e) {
        }
    }
}
