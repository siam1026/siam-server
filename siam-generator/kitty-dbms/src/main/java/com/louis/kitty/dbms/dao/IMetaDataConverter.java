package com.louis.kitty.dbms.dao;

import java.util.Map;

import com.louis.kitty.dbms.model.Column;
import com.louis.kitty.dbms.model.ForeignKey;
import com.louis.kitty.dbms.model.Index;
import com.louis.kitty.dbms.model.PrimaryKey;
import com.louis.kitty.dbms.model.Table;
import com.louis.kitty.dbms.model.Trigger;

/**
 * 元数据转换器接口
 * @author Louis
 * @date Nov 10, 2018
 */
public interface IMetaDataConverter {

    /**
     * 将map转换为Table
     * 
     * @param map
     * @return
     */
    Table convertMap2Table(Map<String, String> map);

    /**
     * 将map转换为Column
     * 
     * @param map
     * @return
     */
    Column convertMap2Column(Map<String, String> map);

    /**
     * 将map转换为PrimaryKey
     * 
     * @param map
     * @return
     */
    PrimaryKey convertMap2PrimaryKey(Map<String, String> map);

    /**
     * 将map转换为ForeignKey
     * 
     * @param map
     * @return
     */
    ForeignKey convertMap2ForeignKey(Map<String, String> map);

    /**
     * 将map转换为Index
     * 
     * @param map
     * @return
     */
    Index convertMap2Index(Map<String, String> map);

    /**
     * 将map转换为Triger
     * 
     * @param map
     * @return
     */
    Trigger convertMap2Trigger(Map<String, String> map);
}
