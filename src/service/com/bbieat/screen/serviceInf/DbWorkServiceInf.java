package com.bbieat.screen.serviceInf;

import java.util.Map;

import com.holley.eemas.util.ActionResult;

//数据库操作类接口
public interface DbWorkServiceInf extends BaseServiceInf {

	// 执行数据库操作
	public ActionResult doDbWorks(final String workType,
			final Map<String, Object> param);

	// 记录数据库操作日志
	public void dbLogger(final Map<String, Object> param);
}
