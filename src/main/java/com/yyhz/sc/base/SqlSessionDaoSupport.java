package com.yyhz.sc.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.yyhz.sc.base.page.Page;
import com.yyhz.sc.base.page.PageContainer;

public class SqlSessionDaoSupport implements DaoSupport {

    private SqlSessionTemplate sqlSession;

    //是否需要启动系统时初始化基础数据
    private String init;

    //数据库初始化脚本路径
    private String databaseScriptPath;

    public SqlSessionDaoSupport(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getDatabaseScriptPath() {
        return databaseScriptPath;
    }

    public void setDatabaseScriptPath(String databaseScriptPath) {
        this.databaseScriptPath = databaseScriptPath;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return sqlSession.insert(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        return sqlSession.update(statement, parameter);
    }

    @Override
    public <K, V, T> T get(String statement, Map<K, V> parameter) {
        return sqlSession.selectOne(statement, parameter);
    }

    @Override
    public <K, V> Map<K, V> findOne(String statement, Map<K, V> parameter) {
        return sqlSession.selectOne(statement, parameter);
    }

    @Override
    public <K, V> int delete(String statement, Map<K, V> parameter) {
        return sqlSession.delete(statement, parameter);
    }

    @Override
    public <E, K, V> List<E> find(String statement, Map<K, V> parameter) {
        return sqlSession.selectList(statement, parameter);
    }

    @Override
    public <E> List<E> find(String statement) {
        return sqlSession.selectList(statement);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E, K, V> Page<E> page(String pageStatement, Map<K, V> parameter, int current, int pagesize) {
        PageBounds pageBounds = new PageBounds(current, pagesize);
        PageList<E> result = (PageList<E>) sqlSession.selectList(pageStatement, parameter, pageBounds);
        Paginator paginator = result.getPaginator();
        return new PageContainer<E, K, V>(paginator.getTotalCount(), paginator.getTotalPages(), result);
    }

    @Override
    public Connection getConnection() {
        return sqlSession.getConnection();
    }

    @Override
    public Configuration getConfiguration() {
        return sqlSession.getConfiguration();
    }

    @Override
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSession;
    }

    @Override
    public <K, V> int getTotal(String statement, Map<K, V> parameter) {
        return sqlSession.selectOne(statement, parameter);
    }

    @Override
    public <E, K, V> E findEntityById(String statement, Map<K, V> parameter) {
        return sqlSession.selectOne(statement, parameter);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public <E> List<E> find(String statement, List objs) {
        return sqlSession.selectList(statement, objs);
    }


    /*
     * 初始化数据库基础数据
     */
    public void init() throws SQLException {
        if (null != init && !"".equals(init.trim())) {
            if ("true".equals(init.trim())) {
                if (checkDB()) {
                    //读取初始化脚本文件初始化基础数据.
                    List<String> statementList = getInitailDatabaseStatement(databaseScriptPath);
                    initDatabase(statementList);
                }
            }
        }
    }

    private boolean checkDB() {
        Connection conn = this.getConnection();
        if (null == conn) {
            return false;
        }
        return true;
    }

    /*
     * 获取数据库初始化语句列表.
     */
    private List<String> getInitailDatabaseStatement(String databaseScriptPath) {
        try {
            return getStatementList(databaseScriptPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 执行初始化基础数据的操作.
     */
    private void initDatabase(List<String> statementList) throws SQLException {

    }

    /*
     * 获取数据库初始化脚本文件.
     */
    private List<String> getStatementList(String databaseScriptPath) throws IOException {

        String scriptPath = getCompleteDatabaseScriptPath(databaseScriptPath);

        File file = new File(scriptPath);
        BufferedReader reader = null;
        List<String> statementList = new ArrayList<String>();

        try {

            if (null == file || !file.isFile()) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            String str = null;
            while ((str = reader.readLine()) != null) {
                statementList.add(str);
            }
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        return statementList;
    }

    /*
     * 获取完整的数据库脚本路径.
     */
    private String getCompleteDatabaseScriptPath(String databaseScriptPath) {
        if (null == databaseScriptPath || "".equals(databaseScriptPath.trim())) {
            return null;
        }
        return getCorrectScriptPath(databaseScriptPath);
    }

    /*
     * 获取正确的初始化脚本路径.
     */
    private String getCorrectScriptPath(String databaseScriptPath) {
        /*String webRootPath = System.getProperty("tywy");
        webRootPath = webRootPath.replaceAll("\\\\", "/");
		databaseScriptPath = databaseScriptPath.replaceAll("\\\\", "/");		
		if (webRootPath.endsWith("/")) {
			webRootPath = webRootPath.substring(0, webRootPath.lastIndexOf("/"));
		}*/

        return "";
    }
}
