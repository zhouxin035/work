package com.bbieat.screen.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BaseDao {

	/**
	 * 查询返回List
	 * 
	 * @param sqlId
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<Object> queryForList(String sqlId, Object obj);

	/**
	 * 查询返回(clazz)Object
	 * 
	 * @param sqlId
	 * @param obj
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public Object queryForObject(String sqlId, Object obj, Class clazz);

	/**
	 * 查询并指定导出Excel文件
	 * 
	 * @param sqlId
	 * @param param
	 * @param handler
	 */
	public void queryForExcel(String sqlId, Map<String, Object> param,
			ExcelRowHandler handler);

	/**
	 * 查询并指定导出Txt文件
	 * 
	 * @param sqlId
	 * @param param
	 * @param handler
	 */
	public void queryForTxt(String sqlId, Map<String, Object> param,
			TxtRowHandler handler);

 
	/**
	 * 统计查询
	 * 
	 * @param sqlId
	 * @param param
	 * @param zb
	 * @param percents
	 *            a@b@c a/b * 100% = c
	 * @return
	 */
	public Map<String, Object> queryForStat(String sqlId,
			Map<String, Object> param, String[] row);

	/**
	 * ExtGrid 查询
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public Map<String, Object> queryForGrid(String sqlId,
			Map<String, Object> param);

	/**
	 * 记录日志
	 * 
	 * @param param
	 */
	public void dbLogger(Map<String, Object> param, String menuId);

	/**
	 * 非事务新增
	 * 
	 * @param sqlId
	 * @param obj
	 * @return
	 */
	public Object insert(String sqlId, Object o);

	/**
	 * 非事务修改
	 * 
	 * @param sqlId
	 * @param obj
	 * @return
	 */
	public int update(String sqlId, Object o);

	/**
	 * 非事务删除
	 * 
	 * @param sqlId
	 * @param obj
	 * @return
	 */
	public int delete(String sqlId, Object o);

	/**
	 * 批量事务增、删、改（单参数）
	 * 
	 * @param sqlIds
	 * @param params
	 * @return
	 */
	public int executeBatch(final List<String> sqlIds,
			Map<String, Object> params);

	/**
	 * 批量事务增、删、改（单SQL）
	 * 
	 * @param sql
	 * @param paramObjs
	 * @return
	 */
	public int executeBatch(final String sqlId, final List<?> params);

	/**
	 * 批量事务增、删、改
	 * 
	 * @param statementID
	 * @param paramObjs
	 * @return
	 */
	public int executeBatchs(final List<String> statementID,
			final List<?> paramObjs);

	/**
	 * 批量事务增、删、改
	 * 
	 * @param statementID
	 *            执行语句
	 * @param paramObjs
	 *            参数
	 * @param hasCross
	 *            语句与参数是否交替执行<br>
	 *            (true-交替执行, false-一一对应执行)
	 * @return
	 */
	public int executeBatchs(final List<String> statementID,
			final List<?> paramObjs, boolean hasCross);

	public List<?> executeBatchInsTransaction(String sqlId, List<?> param);

	public int executeBatchUpdTransaction(String string, List param);

	public List<Object> queryForFileds(String sqlnr) throws SQLException;
}
