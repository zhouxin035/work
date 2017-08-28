package com.bbieat.screen.serviceInf;

import java.util.List;
import java.util.Map;

//查询类接口
public interface QueryServiceInf extends BaseServiceInf {

	// 主统计
	public Map<String, Object> stat(Map<String, Object> param);

	// 汇总行
	public String[] getRows(Map<String, Object> param);
	
	// 获取图形
	public String getChart(List<Object> re);
	
	// 主查询
	public Map<String, Object> query(final Map<String, Object> param);

	// 查询明细
	public Map<String, Object> queryDetail(final Map<String, Object> param);
}
