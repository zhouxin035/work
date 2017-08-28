package com.bbieat.screen.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.holley.eemas.util.Constants;
import com.holley.eemas.util.FileUtil;
import com.holley.eemas.util.StatUtil;
import com.holley.eemas.util.StringUtil;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ibatis实现
 * 
 * @author ycl
 */

@SuppressWarnings("unchecked")
public class BaseDAOIbatis implements BaseDao {

    private SqlMapClientTemplate sqlMapClientTemplate = null;

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    private TransactionTemplate transactionTemplate = null;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    /**
     * 查询返回List
     * 
     * @param sqlId
     * @param obj
     * @return
     * @throws Exception
     */
    public List<Object> queryForList(String sqlId, Object obj) {
        List<Object> ls = null;
        try {
            long startTime = System.currentTimeMillis();
            ls = sqlMapClientTemplate.queryForList(sqlId, obj);
            long endTime = System.currentTimeMillis();

            // 结果集过大的SQL
            if (ls != null && ls.size() >= 2000) {
                FileUtil.dbLogger(sqlId, obj, "query result is too large, size is " + ls.size());
            }
            // 时间过长的SQL > 30s
            if (endTime - startTime > 1000 * 30) {
                FileUtil.dbLogger(sqlId, obj, "query result is too slow, cost is " + (endTime - startTime) / 1000 + "s");
            }
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            FileUtil.dbLogger(sqlId, obj, sqlex.getCause().toString());
        }
        return ls;
    }

    /**
     * 查询返回(clazz)Object
     * 
     * @param sqlId
     * @param obj
     * @param clazz
     * @return
     * @throws Exception
     */
    public Object queryForObject(String sqlId, Object obj, Class clazz) {
        return clazz.cast(sqlMapClientTemplate.queryForObject(sqlId, obj));
    }

    /**
     * 查询并指定导出Excel文件
     * 
     * @param sqlId
     * @param param
     * @param handler
     */
    public void queryForExcel(String sqlId, Map<String, Object> param, ExcelRowHandler handler) {
        sqlMapClientTemplate.queryWithRowHandler(sqlId, param, handler);
        handler.getExcel();
    }

    /**
     * 查询并指定导出Txt文件
     * 
     * @param sqlId
     * @param param
     * @param handler
     */
    public void queryForTxt(String sqlId, Map<String, Object> param, TxtRowHandler handler) {
        try {
            sqlMapClientTemplate.queryWithRowHandler(sqlId, param, handler);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            handler.closeOs();
        }
    }

    /**
     * 统计查询
     * 
     * @param sqlId
     * @param param
     * @param zb
     * @return
     */
    public Map<String, Object> queryForStat(String sqlId, Map<String, Object> param, String[] row) {
        Map<String, Object> re = new HashMap<String, Object>();
        List<Object> ls = queryForList(sqlId, param);
        if (ls.size() > 0 && row != null) {
            Map<String, Object> hz = StatUtil.getHz(ls, row);
            ls.add(hz);
        }
        re.put("result", ls);
        return re;
    }

    /**
     * ExtGrid 查询
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public Map<String, Object> queryForGrid(String sqlId, Map<String, Object> param) {
        Map<String, Object> mapAll = new HashMap<String, Object>();
        String isExcel = (String) param.get("isExcel");
        if (StringUtil.isEs(isExcel)) {
            param.put("count", "true");
            List ls = sqlMapClientTemplate.queryForList(sqlId, param);
            int count = Integer.parseInt(StringUtil.getValue(((Map<String, Object>) ls.get(0)).get("CNT")));
            if (count > 0) {
                param.put("count", "false");
                String start = (String) param.get("start");
                String limit = (String) param.get("limit");
                int startInt = Integer.valueOf(start);
                int endInt = Integer.valueOf(limit);
                endInt = startInt + endInt > count ? count : startInt + endInt;

                param.put("end", endInt);
                List<Object> resultList = sqlMapClientTemplate.queryForList(sqlId, param);
                mapAll.put("result", resultList);
            } else {
                mapAll.put("result", "");
            }
            mapAll.put("rows", count);
        } else {
            if ("online".equals(isExcel)) { // 在线下载
                String excelName = (String) param.get("excelName");
                String fileName = excelName + System.currentTimeMillis() + ".xls";
                String exceHeads = (String) param.get("excelHeads");
                ExcelRowHandler ehandler = new ExcelRowHandler(Constants.TEMPFILE + fileName, excelName, exceHeads);
                sqlMapClientTemplate.queryWithRowHandler(sqlId, param, ehandler);
                ehandler.getExcel();
                mapAll.put("fileName", fileName);
            } else { // 队列下载

            }
        }
        return mapAll;
    }

    /**
     * 非事务新增
     * 
     * @param sqlId
     * @param obj
     * @return
     */
    public Object insert(String sqlId, Object o) {
        Object obj = null;
        try {
            obj = sqlMapClientTemplate.insert(sqlId, o);
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            FileUtil.dbLogger(sqlId, o, sqlex.getCause().toString());
        }
        return obj;
    }

    /**
     * 非事务修改
     * 
     * @param sqlId
     * @param obj
     * @return
     */
    public int update(String sqlId, Object object) {
        int i = 0;
        try {
            i = sqlMapClientTemplate.update(sqlId, object);
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            FileUtil.dbLogger(sqlId, object, sqlex.getCause().toString());
        }
        return i;
    }

    /**
     * 非事务删除
     * 
     * @param sqlId
     * @param obj
     * @return
     */
    public int delete(String sqlId, Object o) {
        int i = 0;
        try {
            i = sqlMapClientTemplate.delete(sqlId, o);
        } catch (Exception sqlex) {
            sqlex.printStackTrace();
            FileUtil.dbLogger(sqlId, o, sqlex.getCause().toString());
        }
        return i;
    }

    /**
     * 批量事务增、删、改（单参数）
     * 
     * @param sqlIds
     * @param params
     * @return
     */
    public int executeBatch(final List<String> sqlIds, Map<String, Object> params) {
        List<Object> paramObjs = new ArrayList<Object>();
        paramObjs.add(params);
        return eb(sqlIds, paramObjs);
    }

    /**
     * 批量事务增、删、改（单SQL）
     * 
     * @param sql
     * @param paramObjs
     * @return
     */
    public int executeBatch(final String sqlId, final List<?> params) {
        List<String> statementID = new ArrayList<String>();
        statementID.add(sqlId);
        return eb(statementID, params);
    }

    /*
     * (non-Javadoc)批量事务增删改
     * @see com.holley.eemas.dao.BaseDao#executeBatchInsTransaction(java.lang.String, java.util.List)
     */
    public List executeBatchInsTransaction(final String statementID, final List paramObjs) {
        List result = null;
        result = ((List) transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                List rstObj = new ArrayList();
                try {
                    rstObj = exeInsBatch(statementID, paramObjs);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    e.printStackTrace();
                    return null;
                }
                return rstObj;
            }
        }));
        return result;
    }

    private List exeInsBatch(final String insertStatementID, final List paramObjs) {
        List result = null;
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                List newKeys = new ArrayList();
                executor.startBatch();
                for (int i = 0; i < paramObjs.size(); i++)
                    newKeys.add(executor.insert(insertStatementID, paramObjs.get(i)));

                executor.executeBatch();
                return newKeys;
            }
        };
        if (insertStatementID != null && paramObjs != null && !paramObjs.isEmpty()) {
            result = (List) sqlMapClientTemplate.execute(callback);
        }
        return result;
    }

    // 批量事务update
    public int executeBatchUpdTransaction(final String statementID, final List paramObjs) {
        int result = -1;
        result = ((Integer) transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                Integer rstObj = new Integer(-1);
                try {
                    rstObj = exeBatch(statementID, paramObjs);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    e.printStackTrace();
                    return new Integer(-1);
                }
                return rstObj;
            }
        })).intValue();
        return result;
    }

    private Integer exeBatch(final String updateStatementID, final List updateParamObjs) {
        Integer result = new Integer(-1);
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                Integer rows = new Integer(-1);
                executor.startBatch();
                for (int i = 0; i < updateParamObjs.size(); i++)
                    executor.update(updateStatementID, updateParamObjs.get(i));

                rows = new Integer(executor.executeBatch());
                return rows;
            }
        };
        if (updateStatementID != null && updateParamObjs != null && !updateParamObjs.isEmpty()) {
            result = (Integer) sqlMapClientTemplate.execute(callback);
        }
        return result;
    }

    /**
     * 批量事务增、删、改 参数与语句交替执行
     * 
     * @param statementID
     * @param paramObjs
     * @return
     */
    public int executeBatchs(final List<String> statementID, final List<?> paramObjs) {
        Integer result = null;
        result = ((Integer) transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                Integer rstObj = new Integer(-1);
                try {
                    rstObj = eb(statementID, paramObjs);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    e.printStackTrace();
                    return null;
                }
                return rstObj;
            }
        }));
        return result;
    }

    private int eb(final List<String> ls, final List<?> paramObjs) {
        Integer result = null;
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                Integer rows = new Integer(-1);
                executor.startBatch();
                for (String insertStatementID : ls) {
                    for (int i = 0; i < paramObjs.size(); i++) {
                        if (insertStatementID.indexOf("ins") > -1 || insertStatementID.indexOf("save") > -1) {
                            executor.insert(insertStatementID, paramObjs.get(i));
                        } else if (insertStatementID.indexOf("upd") > -1) {
                            executor.update(insertStatementID, paramObjs.get(i));
                        } else if (insertStatementID.indexOf("del") > -1) {
                            executor.delete(insertStatementID, paramObjs.get(i));
                        }
                    }
                }
                rows = new Integer(executor.executeBatch());
                ;
                return rows;
            }
        };
        if (ls != null && ls.size() > 0 && paramObjs != null) {
            result = (Integer) sqlMapClientTemplate.execute(callback);
        }
        return result;
    }

    @Override
    public int executeBatchs(List<String> statementID, List<?> paramObjs, boolean hasCross) {
        if (hasCross) {
            return executeBatchs(statementID, paramObjs);
        } else {
            return executeBatchs2(statementID, paramObjs);
        }
    }

    /**
     * 批量操作且语句与参数对应
     * 
     * @param statementID
     * @param paramObjs
     * @return
     */
    private int executeBatchs2(final List<String> statementID, final List<?> paramObjs) {
        Integer result = null;
        result = ((Integer) transactionTemplate.execute(new TransactionCallback() {

            public Object doInTransaction(TransactionStatus status) {
                Integer rstObj = new Integer(-1);
                try {
                    rstObj = eb2(statementID, paramObjs);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    e.printStackTrace();
                    return null;
                }
                return rstObj;
            }
        }));
        return result;
    }

    private int eb2(final List<String> ls, final List<?> paramObjs) {
        Integer result = null;
        SqlMapClientCallback callback = new SqlMapClientCallback() {

            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                Integer rows = new Integer(-1);
                executor.startBatch();
                for (int i = 0; i < paramObjs.size(); i++) {
                    String insertStatementID = ls.get(i);
                    if (insertStatementID.indexOf("ins") > -1) {
                        executor.insert(insertStatementID, paramObjs.get(i));
                    } else if (insertStatementID.indexOf("upd") > -1) {
                        executor.update(insertStatementID, paramObjs.get(i));
                    } else if (insertStatementID.indexOf("del") > -1) {
                        executor.delete(insertStatementID, paramObjs.get(i));
                    }
                }
                rows = new Integer(executor.executeBatch());
                ;
                return rows;
            }
        };
        if (ls != null && ls.size() > 0 && paramObjs != null) {
            result = (Integer) sqlMapClientTemplate.execute(callback);
        }
        return result;
    }

    @Override
    public void dbLogger(Map<String, Object> param, String menuId) {
        param.put("menuId", menuId);
        param.put("czfl", "01");
        insert("common.insLogger", param);
    }

    @Override
    public List<Object> queryForFileds(String sqlnr) throws SQLException {
        List<Object> resultList = new ArrayList<Object>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = sqlMapClientTemplate.getDataSource().getConnection();
            pstmt = conn.prepareStatement(sqlnr);
            rs = pstmt.executeQuery(sqlnr);
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                resultList.add(rsmd.getColumnName(i + 1) + "@@" + rsmd.getColumnTypeName(i + 1));
            }

        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
            throw sqlex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
}
