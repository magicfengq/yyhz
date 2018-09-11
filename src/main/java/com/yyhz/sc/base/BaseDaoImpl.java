package com.yyhz.sc.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.yyhz.sc.base.page.PageInfo;

@Component(value = "baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired
    protected DaoSupport dao;

    protected String sql_name_space;

    public String getSql_name_space() {
        return sql_name_space;
    }

    public void setSql_name_space(String sql_name_space) {
        this.sql_name_space = sql_name_space;
    }

    @Override
    public List<T> selectAll(T info) {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + ".selectAll", info);
    }

    @Override
    public List<T> selectAll(T info, String statementName) {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + "." + statementName, info);
    }

    @Override
    public List<T> selectAll(Map<String, Object> info, String statementName) {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + "." + statementName, info);
    }

    @Override
    public List<T> selectAll(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + ".selectAll", info);
    }

    @Override
    public List<T> selectAll() {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + ".selectAll", null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageList<T> selectAll(T info, int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList<T> result = (PageList<T>) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + "selectAll", info, pageBounds);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageList<T> selectAll(T info, int page, int pageSize, String statementName) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList<T> result = (PageList<T>) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + statementName, info, pageBounds);
        return result;
    }

    @Override
    public PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo) {
        try {
            PageList<T> results = selectAll(info, pageInfo.getPage(), pageInfo.getPageSize());
            pageInfo.setRows(results);
            Paginator paginator = results.getPaginator();
            pageInfo.setTotal(paginator.getTotalCount());
            pageInfo.initPages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo) {
        try {
            PageList<T> results = selectAll(info, pageInfo.getPage(), pageInfo.getPageSize());
            pageInfo.setRows(results);
            Paginator paginator = results.getPaginator();
            pageInfo.setTotal(paginator.getTotalCount());
            pageInfo.initPages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @Override
    public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo, String statementName) {
        try {
            PageList<T> results = selectAll(info, pageInfo.getPage(), pageInfo.getPageSize(), statementName);
            pageInfo.setRows(results);
            Paginator paginator = results.getPaginator();
            pageInfo.setTotal(paginator.getTotalCount());
            pageInfo.initPages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageInfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageList<T> selectAll(Map<String, Object> info, int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList<T> result = (PageList<T>) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + "selectAll", info, pageBounds);
        return result;
    }

    @Override
    public int selectCount(T info) {
        int count = 0;
        SqlSession session = null;
        try {
            session = dao.getSqlSessionTemplate().getSqlSessionFactory()
                    .openSession();
            Object obj = (Integer) session.selectOne(sql_name_space + "."
                    + "selectCount", info);
            if (obj != null) {
                count = (Integer) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            session.close();
        }
        return count;
    }

    @Override
    public int selectCount(Map<String, Object> info) {
        int count = 0;
        SqlSession session = null;
        try {
            session = dao.getSqlSessionTemplate().getSqlSessionFactory()
                    .openSession();
            Object obj = (Integer) session.selectOne(sql_name_space + "."
                    + "selectCount", info);
            if (obj != null) {
                count = (Integer) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            session.close();
        }
        return count;
    }

    @Override
    public T selectById(String id) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectById", id);
    }

    @Override
    public T selectById(Integer id) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectById", id);
    }

    @Override
    public int insert(T info) {
        return dao.getSqlSessionTemplate().insert(sql_name_space + ".insert", info);
    }


    @Override
	public int insert(List<T> infos, String statementName) {
    	return dao.getSqlSessionTemplate().insert(sql_name_space + "." + statementName, infos);
	}

    
    @Override
    public int insert(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().insert(sql_name_space + ".insert",
                info);
    }

    @Override
    public int update(T info) {
        return dao.getSqlSessionTemplate().update(sql_name_space + ".update",
                info);
    }

    @Override
    public int update(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().update(sql_name_space + ".update",
                info);
    }

    @Override
    public int batchUpdate(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().update(sql_name_space + ".batchUpdate", info);
    }

    @Override
    public int delete(T info) {
        return dao.getSqlSessionTemplate().delete(sql_name_space + ".delete",
                info);
    }

    @Override
	public int delete(T info, String statementName) {
    	return dao.getSqlSessionTemplate().delete(sql_name_space + "." + statementName,
                info);
	}

	@Override
    public int delete(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().delete(sql_name_space + ".delete",
                info);
    }

    @Override
    public int batchDelete(List<String> idList) {
        return dao.getSqlSessionTemplate().delete(sql_name_space + ".batchDelete", idList);
    }

    @Override
    public int batchDeleteByForeignKey(List<String> idList, String statement) {
        return dao.getSqlSessionTemplate().delete(sql_name_space + "." + statement, idList);
    }

    @Override
    public int deleteByForeignKey(String key, String statement) {
        return dao.getSqlSessionTemplate().delete(sql_name_space + "." + statement, key);
    }

    @Override
    public List<T> selectInfoByKey(List<String> idList, String statement) {
        return dao.getSqlSessionTemplate().selectList(sql_name_space + "." + statement, idList);
        //return dao.getSqlSessionTemplate().delete(sql_name_space + "." + statement,idList);
    }

    @Override
    public T selectEntity(T info) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectEntity", info);
    }

    @Override
    public T selectEntity(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectEntity", info);
    }

    @Override
    public List<Map<String, Object>> selectAllMap() {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + ".selectAllMap", null);
    }

    @Override
    public List<Map<String, Object>> selectAllMap(T info) {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + ".selectAllMap", info);
    }

    @Override
    public List<Map<String, Object>> selectAllMap(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().selectList(
                sql_name_space + ".selectAllMap", info);
    }

    @Override
    public List<Map<String, Object>> selectAllMap(T info, int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;
        int limit = pageSize;
        RowBounds rowbounds = new RowBounds(offset, limit);
        List<Map<String, Object>> results = dao.getSqlSessionTemplate().selectList(sql_name_space + "." + "selectAllMap", info,
                rowbounds);
        return results;
    }

    @Override
    public List<Map<String, Object>> selectAllMap(Map<String, Object> info,
                                                  int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        int offset = (page - 1) * pageSize;
        int limit = pageSize;
        RowBounds rowbounds = new RowBounds(offset, limit);
        List<Map<String, Object>> results = dao.getSqlSessionTemplate().selectList(sql_name_space + "." + "selectAllMap", info,
                rowbounds);
        return results;
    }

    @Override
    public Map<String, Object> selectByIdMap(String id) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectByIdMap", id);
    }

    @Override
    public Map<String, Object> selectByIdMap(Integer id) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectByIdMap", id);
    }

    @Override
    public Map<String, Object> selectEntityMap(T info) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectEntityMap", info);
    }

    @Override
    public Map<String, Object> selectEntityMap(Map<String, Object> info) {
        return dao.getSqlSessionTemplate().selectOne(
                sql_name_space + ".selectEntityMap", info);
    }


    @SuppressWarnings("unchecked")
    @Override
    public PageList<T> selectAll(String mappingId, T info, int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList<T> result = (PageList<T>) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + mappingId, info, pageBounds);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageList<T> selectAll(String mappingId, Map<String, Object> info,
                                 int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList<T> result = (PageList<T>) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + mappingId, info, pageBounds);
        return result;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public PageList<Map<String, Object>> selectAllMap(String mappingId, T info,
                                                      int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList result = (PageList) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + mappingId, info, pageBounds);
        return result;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public PageList<Map<String, Object>> selectAllMap(String mappingId,
                                                      Map<String, Object> info, int page, int pageSize) {
        if (page <= 0) {
            page = 1;
        }
        PageBounds pageBounds = new PageBounds(page, pageSize);
        PageList result = (PageList) dao.getSqlSessionTemplate().selectList(sql_name_space + "." + mappingId, info, pageBounds);
        return result;
    }

    @Override
    public List<T> selectByIds(List<String> idList) {
        return dao.getSqlSessionTemplate().selectList(sql_name_space + ".selectByIds", idList);
    }

	@Override
	public Integer selectMaxOrderList() {
		return dao.getSqlSessionTemplate().selectOne(sql_name_space + ".selectMaxOrderList");
	}

	@Override
	public Integer updateOrderList(Integer infoOrderList) {
		return dao.getSqlSessionTemplate().update(sql_name_space + ".updateOrderList",infoOrderList);
	}

}
