package com.bbieat.screen.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//统计工具类
public class StatUtil {

	//初始化BigDecimal数组
	public static BigDecimal[] getBgdArray(int size) {
		BigDecimal[] array = new BigDecimal[size];
		for(int i=0;i<array.length;i++) {
			array[i] = BigDecimal.ZERO;
		}
		return array;
	}

	//保留两位四舍五入
	public static String round(double d) {
		String re = String.valueOf(new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		re = re.lastIndexOf(".0") == re.length() - 2 ? re.substring(0, re.lastIndexOf(".0")) : re;
		return re;
	}
	
	//算百分比a / b * 100
	public static BigDecimal getBdgPersent(BigDecimal a, BigDecimal b) {
		if(a  == null || b == null ||  BigDecimal.ZERO.equals(b) || BigDecimal.ZERO.equals(a)) {
			return BigDecimal.ZERO; 
		} else {
			return getBdgPersent(a, b, true);
		}
	}
	
	//算百分比a / b * 100 或算比值a / b
	public static BigDecimal getBdgPersent(BigDecimal a, BigDecimal b, boolean percent) {
		if(a  == null || b == null ||  BigDecimal.ZERO.equals(b) || BigDecimal.ZERO.equals(a)) {
			return BigDecimal.ZERO; 
		} else {
			double d = (a.doubleValue() / b.doubleValue()) * (percent ? 100 : 1);
			return new BigDecimal(round(d));
		}
	}
 
	//map 取值
	private static BigDecimal getBgdValue(Map<?,?> m, String key) {
		 Object o = m.get(key);
		 if(o instanceof BigDecimal && o != null) {
			 return (BigDecimal)o;
		 } else {
			 return BigDecimal.ZERO;
		 }
	}
	
	/**
	 * 算汇总行
	 * @param resultList
	 * @param row  申明 String[]{"A","B","A/B=C"}
	 * @return
	 */
	public static Map<String, Object> getHz(List<Object> resultList, String[] row) {
		BigDecimal[] bdArray = new BigDecimal[row.length];
		//所有列值相加
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : row) {
				if(s.indexOf("=") > 0) continue;
				if( bdArray[index] == null) {
					bdArray[index] = new BigDecimal(0);
				}
				bdArray[index] = bdArray[index].add(getBgdValue(m,s));
				index ++;
			}
		}
		
		//形成MAP
		Map<String, Object> hzMap =new HashMap<String, Object>();
		hzMap.put("DWMC", "汇  总");
		hzMap.put("SFXZ", "FALSE");
		hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : row) {
			if(s.indexOf("=") > 0) continue;
			hzMap.put(s, bdArray[index]);
			index ++;
		}
		
		//处理相除
		for(String abc : row) {
			if(abc.indexOf("=") > 0) {
				String ab = abc.split("=")[0];
				String c = abc.split("=")[1];
				boolean percent = true; 
				if(c.indexOf("*")>0){
					c = c.split("\\*")[0];
					percent = false;
				}
				String a = ab.split("/")[0];
				//不考虑括号，先算+、-
				if(a.indexOf("+") > 0) {
					String a1 = a.split("\\+")[0];
					String a2 = a.split("\\+")[1];
					a = ((BigDecimal) hzMap.get(a1)).add((BigDecimal) hzMap.get(a2)).toString();
				}
				if(a.indexOf("-") > 0) {
					String a1 = a.split("-")[0];
					String a2 = a.split("-")[1];
					a = ((BigDecimal) hzMap.get(a1)).subtract((BigDecimal) hzMap.get(a2)).toString();
				}
				String b = ab.split("/")[1];
				if(b.indexOf("+") > 0) {
					String b1 = b.split("\\+")[0];
					String b2 = b.split("\\+")[1];
					b = ((BigDecimal) hzMap.get(b1)).add((BigDecimal) hzMap.get(b2)).toString();
				}
				if(a.indexOf("-") > 0) {
					String b1 = b.split("-")[0];
					String b2 = b.split("-")[1];
					b = ((BigDecimal) hzMap.get(b1)).subtract((BigDecimal) hzMap.get(b2)).toString();
				}
				hzMap.put(c, StatUtil.getBdgPersent(null == hzMap.get(a) ? new BigDecimal(a) : (BigDecimal) hzMap.get(a),
							null == hzMap.get(b) ? new BigDecimal(b) : (BigDecimal) hzMap.get(b), percent));
			}
		}
		return hzMap;
	}
	
	//算汇总
	public static Map<String, Object> getHz(List<Object> resultList, BigDecimal[] bdArray, String[] zb) {
		Map<String, Object> hzMap =new HashMap<String, Object>();
		for(int i=0;i< resultList.size();i++){
			Map<?,?> m = (Map<?,?>)resultList.get(i);
			int index = 0;
			for(String s : zb) {
				bdArray[index] = bdArray[index].add(getBgdValue(m,s));
				index ++;
			}
		}
		
		hzMap.put("DWMC", "汇  总");
		hzMap.put("SFXZ", null);
		hzMap.put("DWDM", "DWDM");
		int index = 0;
		for(String s : zb) {
			hzMap.put(s, bdArray[index]);
			index ++;
		}
		return hzMap;
	}
}
