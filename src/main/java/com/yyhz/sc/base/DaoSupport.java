package com.yyhz.sc.base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

import com.yyhz.sc.base.page.Page;

public interface DaoSupport {

    int insert(String statement, Object parameter);

    int update(String statement, Object parameter);

    <K, V, T> T get(String statement, Map<K, V> parameter);

    <K, V> Map<K, V> findOne(String statement, Map<K, V> parameter);

    <E, K, V> E findEntityById(String statement, Map<K, V> parameter);

    <E, K, V> List<E> find(String statement, Map<K, V> parameter);

    <E> List<E> find(String statement);

    @SuppressWarnings("rawtypes")
    <E> List<E> find(String statement, List objs);

    <K, V> int getTotal(String statement, Map<K, V> parameter);

    <K, V> int delete(String statement, Map<K, V> parameter);


    <E, K, V> Page<E> page(String pageStatement, Map<K, V> parameter, int current, int pagesize);

    Connection getConnection();

    Configuration getConfiguration();

    SqlSessionTemplate getSqlSessionTemplate();
}
