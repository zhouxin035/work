package com.bbieat.screen.web.actionInf;

import java.util.Map;

import com.holley.eemas.util.ActionResult;


//数据库操作类接口
public interface DbWorkWebInf extends BaseWebInf {

	// 执行数据库操作(dwr 调用)
	public ActionResult dbWorks(final String workType, final Map<String, Object> param);

	// 记录数据库操作日志(dwr 调用)
	public void dbLogger(final Map<String, Object> param);

}
