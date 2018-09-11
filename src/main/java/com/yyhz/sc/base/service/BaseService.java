package com.yyhz.sc.base.service;

import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.page.PageInfo;

public interface BaseService<T> {
    List<T> selectAll();

    List<T> selectAll(T info);

    List<T> selectAll(T info, String statementName);

    List<T> selectAll(Map<String, Object> info, String statementName);

    List<T> selectAll(Map<String, Object> info);

    List<T> selectAll(T info, int page, int pageSize);

    /**
     * 根据条件查询分页数据
     *
     * @param info          条件参数
     * @param page          页码
     * @param pageSize      条数
     * @param statementName SqlMapperId
     * @return
     */
    List<T> selectAll(T info, int page, int pageSize, String statementName);

    List<T> selectAll(Map<String, Object> info, int page, int pageSize);

    PageInfo<T> selectAll(T info, PageInfo<T> pageInfo);

    /**
     * 根据条件查询分页数据
     *
     * @param info          条件参数
     * @param pageInfo      分页对象
     * @param statementName SqlMapperId
     * @return
     */
    PageInfo<T> selectAll(T info, PageInfo<T> pageInfo, String statementName);

    PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo);

    int selectCount(T info);

    int selectCount(Map<String, Object> info);

    T selectById(String id);

    T selectById(Integer id);

    int insert(T info);
    int insert(List<T> infos, String statementName);

    int insert(Map<String, Object> info);

    int update(T info);

    int update(Map<String, Object> info);

    int batchUpdate(Map<String, Object> info);

    int delete(T info);
    
    int delete(T info, String statementName);

    int delete(Map<String, Object> info);

    int batchDelete(List<String> idList);

    T selectEntity(T info);

    T selectEntity(Map<String, Object> info);

    List<T> selectByIds(List<String> idList);
    
    public void setOrderList(T info) throws Exception;
    
    public void updateOrderList(T info) throws Exception;
}
