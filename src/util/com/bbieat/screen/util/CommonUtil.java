package com.bbieat.screen.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;

import org.apache.log4j.Logger;

/**
 * @Description 公用代码工具栏
 * @author YCL
 * @time：2014-4-10
 */
public class CommonUtil {
	
	static Logger log = Logger.getLogger(CommonUtil.class);


	public static Map<String, Object> getParams(Object arg) {
		Field[] fs = arg.getClass().getDeclaredFields(); // 私有属性
		Field[] fs2 = arg.getClass().getFields();// 公有属性
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			for (Field f : fs) {
				f.setAccessible(true);
				Object v = f.get(arg);
 				if(f.getType().equals(String.class) && v != null) {
 					String k = f.getName();
 					obj.put(k, v);
 				}
			}
			for (Field f : fs2) {
				Object v = f.get(arg);
				if (f.getType().equals(String.class) && v != null) {
					String k = f.getName();
					obj.put(k, v);
				}
			}
		} catch (Exception e) {
			log.error(" =============== CommonUtil getParams ===================");
			e.printStackTrace();
			
		}
		return obj;
	}

	/**
	 * 导入Excel 行转Map
	 * @param row
	 * @param head
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> cell2Map(Cell[] row, String[] head)
			throws Exception {
		Map<String, String> m = new HashMap<String, String>();
		try {
			for (int i = 0, k = row.length; i < k; i++) {
				String v = StringUtil.getValue(row[i].getContents());
				m.put(head[i], v.trim());
			}
		} catch (Exception e) {
			log.error(" =============== CommonUtil cell2Map ===================");
			throw e;
		}
		return m;
	}

	/**
	 * MAP转对象
	 * 
	 * @param m
	 * @param bean
	 */
	public static void map2Obj(Map<String, String> m, Object bean) {
		try {
			if (m != null && !m.isEmpty()) {
				Field[] destFields = bean.getClass().getDeclaredFields();
				AccessibleObject.setAccessible(destFields, true);
				for (Field fd : destFields) {
					String fdName = fd.getName();
					String value = StringUtil.getValue(m.get(fdName
							.toLowerCase()));
					if (StringUtil.isNes(value)) {
						if (fd.getType().equals(String.class)) {
							fd.set(bean, value);
						}
						if (fd.getType().equals(Integer.class)) {
							fd.setInt(bean, new Integer(value));
						}
						if (fd.getType().equals(Long.class)) {
							fd.setLong(bean, new Long(value));
						}
						if (fd.getType().equals(Double.class)) {
							fd.setDouble(bean, new Double(value));
						}
						if (fd.getType().equals(java.util.Date.class)) {
							java.util.Date d = null;
							if (fdName.toUpperCase().endsWith("SJ")) {
								d = DateUtil.String2Date("yyyy-MM-dd HH:mm:ss",
										value);
							} else if (fdName.toUpperCase().endsWith("RQ")) {
								d = DateUtil.String2Date("yyyy-MM-dd", value);
							}
							fd.set(bean, d);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(" =============== CommonUtil map2Obj ===================");
			e.printStackTrace();
		}
	}
	
	public static String getCookie(String key, HttpServletRequest request) {
		String v = "";
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					String cName = cookie.getName();
					String cValue = cookie.getValue();
					if (cName.equals(key) && StringUtil.isNes(cValue)) {
						v = cookie.getValue();
					}
				}
			}
		} catch (Exception e) {
			log.error(" =============== CommonUtil getCookie ===================");
			e.printStackTrace();
		}
		return v;
	}
	
	public static void setCookie(String key, String value, HttpServletRequest request, HttpServletResponse rep) {
		try {
			Cookie ssid = new Cookie(key, value);
			ssid.setPath("/amr/");
			rep.addCookie(ssid);
		} catch (Exception e) {
			log.error(" =============== CommonUtil setCookie ===================");
			e.printStackTrace();
		}
	}
}
