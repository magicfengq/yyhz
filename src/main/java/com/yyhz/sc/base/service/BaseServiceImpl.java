package com.yyhz.sc.base.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yyhz.sc.base.BaseDao;
import com.yyhz.sc.base.page.PageInfo;

public class BaseServiceImpl<T> implements BaseService<T> {

    private BaseDao<T> baseDao;

    public void setBaseDao(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public List<T> selectAll() {
        return baseDao.selectAll();
    }

    @Override
    public List<T> selectAll(T info) {
        return baseDao.selectAll(info);
    }


	@Override
    public List<T> selectAll(T info, String statementName) {
        return baseDao.selectAll(info, statementName);
    }

    @Override
    public List<T> selectAll(Map<String, Object> info, String statementName) {
        return baseDao.selectAll(info, statementName);
    }

    @Override
    public List<T> selectAll(Map<String, Object> info) {
        return baseDao.selectAll(info);
    }

    @Override
    public List<T> selectAll(T info, int page, int pageSize) {
        return baseDao.selectAll(info, page, pageSize);
    }

    @Override
    public List<T> selectAll(Map<String, Object> info, int page, int pageSize) {
        return baseDao.selectAll(info, page, pageSize);
    }

    @Override
    public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo) {
        return baseDao.selectAll(info, pageInfo);
    }

    @Override
    public List<T> selectAll(T info, int page, int pageSize, String statementName) {
        return baseDao.selectAll(info, page, pageSize, statementName);
    }

    @Override
    public PageInfo<T> selectAll(T info, PageInfo<T> pageInfo, String statementName) {
        return baseDao.selectAll(info, pageInfo, statementName);
    }

    @Override
    public PageInfo<T> selectAll(Map<String, Object> info, PageInfo<T> pageInfo) {
        return baseDao.selectAll(info, pageInfo);
    }

    @Override
    public int selectCount(T info) {
        return baseDao.selectCount(info);
    }

    @Override
    public int selectCount(Map<String, Object> info) {
        return baseDao.selectCount(info);
    }

    @Override
    public T selectById(String id) {
        return baseDao.selectById(id);
    }

    @Override
    public T selectById(Integer id) {
        return baseDao.selectById(id);
    }

    @Override
    public int insert(T info) {
        return baseDao.insert(info);
    }
    
    @Override
	public int insert(List<T> infos, String statementName) {
    	return baseDao.insert(infos, statementName);
	}


    @Override
    public int insert(Map<String, Object> info) {
        return baseDao.insert(info);
    }

    @Override
    public int update(T info) {
        return baseDao.update(info);
    }

    @Override
    public int update(Map<String, Object> info) {
        return baseDao.update(info);
    }

    @Override
    public int delete(T info) {
        return baseDao.delete(info);
    }

    @Override
	public int delete(T info, String statementName) {
    	return baseDao.delete(info, statementName);
	}

	@Override
    public int delete(Map<String, Object> info) {
        return baseDao.delete(info);
    }

    @Override
    public T selectEntity(T info) {
        return baseDao.selectEntity(info);
    }

    @Override
    public T selectEntity(Map<String, Object> info) {
        return baseDao.selectEntity(info);
    }

    @Override
    public int batchDelete(List<String> idList) {
        return baseDao.batchDelete(idList);
    }

    @Override
    public int batchUpdate(Map<String, Object> info) {
        return baseDao.batchUpdate(info);
    }

    @Override
    public List<T> selectByIds(List<String> idList) {
        return baseDao.selectByIds(idList);
    }

	@Override
	public void setOrderList(T t) throws Exception{
		Object orderObj = getProperty(t,"orderList");
		if(orderObj == null){
			Integer maxOrderList = baseDao.selectMaxOrderList();
			if(maxOrderList == null){
				setProperty(t,1,"orderList");
			}else{
				setProperty(t,maxOrderList + 1,"orderList");
			}
		}
	}
	
	@Override
	public void updateOrderList(T info) throws Exception {
		Object idObj = getProperty(info,"id");
		T entity = selectById((String)idObj);
		Integer infoOrderList = (Integer)getProperty(info,"orderList");
		Integer entityOrderList = (Integer)getProperty(entity,"orderList");
		if(infoOrderList != null && !entityOrderList.equals(infoOrderList)){
			//序号变化了
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("orderList", infoOrderList);
			List<T> list = selectAll(params);
			if(list != null && !list.isEmpty()){
				baseDao.updateOrderList(infoOrderList);
			}
		}
	}
	
	private String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {  
            return null;
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "set"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    } 
    
    private String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {  
            return null;
        }  
        int startIndex = 0;  
        if (fieldName.charAt(0) == '_')  
            startIndex = 1;  
        return "get"  
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()  
                + fieldName.substring(startIndex + 1);  
    }

    private Object getProperty(Object entity,String propName) throws Exception{
    	Field imgField = entity.getClass().getDeclaredField(propName);
		String fieldGetName = parGetName(imgField.getName());
		Method fieldGetMet = entity.getClass().getMethod(fieldGetName);
		return fieldGetMet.invoke(entity);
    }
    
    private void setProperty(Object entity,Integer value,String propName) throws Exception{
		Field field = entity.getClass().getDeclaredField(propName);        				
		String fieldSetName = parSetName(field.getName());
		Method fieldSetMet = entity.getClass().getMethod(fieldSetName,field.getType());
		fieldSetMet.invoke(entity, value);
    }

	
}
