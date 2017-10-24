package com.chinawiserv.core.service;

import com.chinawiserv.core.dao.mybatis.BaseMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sungang on 2016/6/9.
 */
public class BaseService<T extends Serializable> implements IService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> select(T entity) {
        return mapper.select(entity);
    }

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> selectByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }


    @Override
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }


    @Override
    public int selectCountByExample(Example example) {
        return mapper.selectCountByExample(example);
    }


    @Override
    public List<T> selectByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public int selectCountByCondition(Condition condition) {
        return mapper.selectCountByExample(condition);
    }

    @Override
    public int insert(T entity) {
        return mapper.insert(entity);
    }


    @Override
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }


    @Override
    public int insertList(List<T> entityList) {
        return mapper.insertList(entityList);
    }

    @Override
    public int insertUseGeneratedKeys(T entity) {
        return mapper.insertUseGeneratedKeys(entity);
    }


    @Override
    public int updateByPrimaryKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }


    @Override
    public int updateByExample(T entity, Example example) {
        return mapper.updateByExample(entity, example);
    }

    @Override
    public int updateByExampleSelective(T entity, Example example) {
        return mapper.updateByExampleSelective(entity, example);
    }

    @Override
    public int updateByCondition(T entity, Condition condition) {
        return mapper.updateByCondition(entity, condition);
    }

    @Override
    public int updateByConditionSelective(T entity, Condition condition) {
        return mapper.updateByConditionSelective(entity, condition);
    }


    @Override
    public int delete(T entity) {
        return mapper.delete(entity);
    }


    @Override
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int deleteByIds(String ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public int deleteByExample(Example example) {
        return mapper.deleteByExample(example);
    }

    @Override
    public int deleteByCondition(Condition condition) {
        return mapper.deleteByCondition(condition);
    }


    @Override
    public T selectOneByProperty(Class<T> entityClass, String property, Object value) {
        List<T> list = selectListByProperty(entityClass, property, value);
        if (list.isEmpty()) {
            return null;
        }
        return list.iterator().next();
    }

    @Override
    public List<T> selectListByProperty(Class<T> entityClass, String property, Object value) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(property, value);
        return selectByExample(example);
    }

    /**
     * map中都是与的关系
     */
    @Override
    public int deleteByMap(Class<T> entityClass, Map<String, Object> map) {
        Example example = makeExample(entityClass, map);
        return this.deleteByExample(example);
    }

    @Override
    public int deleteByProperty(Class<T> entityClass, String key, Object val) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, val);
        return this.deleteByMap(entityClass, map);
    }

    /**
     * map中都是与的关系
     */
    @Override
    public int updateByMap(T entity, Class<T> entityClass, Map<String, Object> map) {
        Example example = makeExample(entityClass, map);
        return this.updateByExample(entity, example);
    }

    @Override
    public int updateByProperty(T entity, Class<T> entityClass, String key, Object val) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, val);
        return this.updateByMap(entity, entityClass, map);
    }

    @Override
    public int updateByMapSelective(T entity, Class<T> entityClass, Map<String, Object> map) {
        Example example = makeExample(entityClass, map);
        return this.updateByExampleSelective(entity, example);
    }

    @Override
    public int updateByPropertySelective(T entity, Class<T> entityClass, String key, Object val) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, val);
        return this.updateByMapSelective(entity, entityClass, map);
    }

    @Override
    public List<T> selectByMap(Class<T> entityClass, Map<String, Object> map) {
        Example example = makeExample(entityClass, map);
        return selectByExample(example);
    }

    @Override
    public List<T> selectByProperty(Class<T> entityClass, String key, Object val) {
        Map<String, Object> map = Maps.newHashMap();
        map.put(key, val);
        return this.selectByMap(entityClass, map);
    }

    private Example makeExample(Class<T> entityClass, Map<String, Object> map) {
        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            Object val = map.get(key);
            criteria.andEqualTo(key, val);
        }
        return example;
    }
}
