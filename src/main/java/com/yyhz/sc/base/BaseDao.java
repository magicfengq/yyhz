package com.yyhz.sc.base;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.yyhz.sc.base.page.PageInfo;


public interface BaseDao<T> {
    /**
     * 查询所有记录
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据条件查询数据
     *
     * @param info 参数对象
     * @return
     */
    List<T> selectAll(T info);

    /**
     * 根据条件自定义查询数据
     *
     * @param info 参数对象
     * @return
     */
    List<T> selectAll(T info, String statementName);

    /**
     * 根据条件自定义查询数据
     *
     * @param info 参数对象
     * @return
     */
    List<T> selectAll(Map<String, Object> info, String statementName);


    /**
     * 根据条件查询数据
     *
     * @param info 参数对象
     * @return
     */
    List<T> selectAll(Map<String, Object> info);

    /**
     * 根据外键查询信息
     *
     * @param idList
     * @return
     */
    List<T> selectInfoByKey(List<String> idList, String statement);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param page     页码
     * @param pageSize 条数
     * @return
     */
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


    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param page     页码
     * @param pageSize 条数
     * @return
     */
    List<T> selectAll(Map<String, Object> info, int page, int pageSize);

    /**
     * 查询所有数据
     *
     * @return
     */
    List<Map<String, Object>> selectAllMap();

    /**
     * 根据条件查询数据
     *
     * @param info
     * @return
     */
    List<Map<String, Object>> selectAllMap(T info);

    /**
     * 根据条件查询数据
     *
     * @param info
     * @return
     */
    List<Map<String, Object>> selectAllMap(Map<String, Object> info);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件
     * @param page     页码
     * @param pageSize 数量
     * @return
     */
    List<Map<String, Object>> selectAllMap(T info, int page, int pageSize);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件
     * @param page     页码
     * @param pageSize 数量
     * @return
     */
    List<Map<String, Object>> selectAllMap(Map<String, Object> info,
                                           int page, int pageSize);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param pageInfo 分页对象
     * @return
     */
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


    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param pageInfo 分页对象
     * @return
     */
    PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo);


    /**
     * 根据条件查询行数
     *
     * @param info
     * @return
     */
    int selectCount(T info);

    /**
     * 根据条件查询行数
     *
     * @param info
     * @return
     */
    int selectCount(Map<String, Object> info);

    /**
     * 根据id查询对象
     *
     * @param id
     * @return
     */
    T selectById(String id);

    /**
     * 根据id查询对象
     *
     * @param id
     * @return
     */
    T selectById(Integer id);

    /**
     * 根据条件查询对象
     *
     * @param info
     * @return
     */
    T selectEntity(T info);

    /**
     * 根据条件查询对象
     *
     * @param info
     * @return
     */
    T selectEntity(Map<String, Object> info);

    /**
     * 根据id查询对象map
     *
     * @param id
     * @return
     */
    Map<String, Object> selectByIdMap(String id);

    /**
     * 根据id查询对象map
     *
     * @param id
     * @return
     */
    Map<String, Object> selectByIdMap(Integer id);

    /**
     * 根据条件查询对象map
     *
     * @param info
     * @return
     */
    Map<String, Object> selectEntityMap(T info);

    /**
     * 根据条件查询对象map
     *
     * @param info
     * @return
     */
    Map<String, Object> selectEntityMap(Map<String, Object> info);

    /**
     * 插入实体
     *
     * @param info
     * @return
     */
    int insert(T info);
    
    /**
     * 插入实体
     *
     * @param info
     * @return
     */
    int insert(List<T> infos, String statementName);

    /**
     * 插入实体
     *
     * @param info
     * @return
     */
    int insert(Map<String, Object> info);

    /**
     * 根据id更新实体
     *
     * @param info
     * @return
     */
    int update(T info);

    /**
     * 根据id更新实体
     *
     * @param info
     * @return
     */
    int update(Map<String, Object> info);


    /**
     * 根据id更新实体
     *
     * @param info
     * @return
     */
    int batchUpdate(Map<String, Object> info);

    /**
     * 根据条件删除实体
     *
     * @param info
     * @return
     */
    int delete(T info);
    
    /**
     * 根据条件删除实体
     *
     * @param info
     * @return
     */
    int delete(T info, String statementName);

    /**
     * 根据条件删除实体
     *
     * @param info
     * @return
     */
    int delete(Map<String, Object> info);

    /**
     * 根据主键ID做批量删除
     *
     * @param idList
     * @return
     */
    int batchDelete(List<String> idList);

    /**
     * 根据外做批量删除
     *
     * @param statement
     * @return
     */
    int batchDeleteByForeignKey(List<String> idList, String statement);

    /**
     * 根据外键删除实体
     *
     * @return
     */
    int deleteByForeignKey(String key, String statement);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param page     页码
     * @param pageSize 条数
     * @return
     */
    PageList<T> selectAll(String mappingId, T info, int page, int pageSize);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param page     页码
     * @param pageSize 条数
     * @return
     */
    PageList<T> selectAll(String mappingId, Map<String, Object> info, int page, int pageSize);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param page     页码
     * @param pageSize 条数
     * @return
     */
    PageList<Map<String, Object>> selectAllMap(String mappingId, T info, int page, int pageSize);

    /**
     * 根据条件查询分页数据
     *
     * @param info     条件参数
     * @param page     页码
     * @param pageSize 条数
     * @return
     */
    PageList<Map<String, Object>> selectAllMap(String mappingId, Map<String, Object> info, int page, int pageSize);

    List<T> selectByIds(List<String> idList);

	Integer selectMaxOrderList();

	Integer updateOrderList(Integer infoOrderList);


}
