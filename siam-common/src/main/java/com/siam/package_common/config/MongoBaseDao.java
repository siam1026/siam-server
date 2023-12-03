package com.siam.package_common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * mongoDB基础方法封装
 */
public abstract class MongoBaseDao<T> {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 反射获取泛型类型
     */
    protected abstract Class<T> getEntityClass();

    public void insertSelective(T t){
        mongoTemplate.save(t);
    }

    public T selectByPrimaryKey(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    public void updateByPrimaryKeySelective(T t){
        Query query = new Query(Criteria.where("_id").is(getFieldValueByName("id", t)));
        Update update = getUpdateByObject(t);
        mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    public void deleteByPrimaryKey(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, this.getEntityClass());
    }

    /**
     * （废弃）
     * @return
     */
    public void countByExample(T t){
        Query query = getQueryByObject(t);
        mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * （废弃）
     * @return
     */
    public List<T> selectByExample(T t){
        Query query = getQueryByObject(t);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * （废弃）
     * @return
     */
    public List<T> getListByPage(int pageNo, int pageSize, T t){
        Query query = getQueryByObject(t);
        // skip是指跳过多少条数据，而不是页码，跟mysql不一样的
        query.skip(pageSize * (pageNo - 1));
        query.limit(pageSize);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 拼接查询条件
     * @return
     */
    private Query getQueryByObject(T t){
        Query query = new Query();
        String[] fieldNames = getfieldNames(t);
        Criteria criteria = new Criteria();
        for(int i = 0; i < fieldNames.length; i++){
            Object fieldValue = getFieldValueByName(fieldNames[i], t);
            if(fieldValue != null){
                criteria.and(fieldNames[i]).is(fieldValue);
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    /**
     * 拼接修改对象
     * @return
     */
    private Update getUpdateByObject(T t){
        Update update = new Update();
        String[] fieldNames = getfieldNames(t);
        Criteria criteria = new Criteria();
        for(int i = 0; i < fieldNames.length; i++){
            Object fieldValue = getFieldValueByName(fieldNames[i], t);
            if(fieldValue != null){
                update.set(fieldNames[i], fieldValue);
            }
        }
        return update;
    }

    /**
     * 反射获取对象属性名称
     * @return 字符串数组
     */
    private String[] getfieldNames(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 反射根据属性名称获取属性值
     * @return
     */
    private Object getFieldValueByName(String fieldName, Object obj){
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        try {
            Method method = obj.getClass().getMethod(getter, new Class[0]);
            return method.invoke(obj, new Object[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
