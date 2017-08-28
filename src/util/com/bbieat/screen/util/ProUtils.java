package com.bbieat.screen.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class ProUtils {

	public static String getPv(String proFile, Class clz, String key) {
		String v = null;
		InputStream in = clz.getResourceAsStream("/" + proFile + ".properties");
		Properties pro = new Properties();
		if (in != null) {
			try {
				pro.load(in);
				v = (String) pro.get(key);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return v;
	}

	public static Map<String, String> p2m(String proFile, Class clz) {
		Map<String, String> properties = new HashMap<String, String>();
		InputStream in = clz.getResourceAsStream("/" + proFile + ".properties");
		Properties pro = new Properties();
		if (in != null) {
			try {
				pro.load(in);
				Iterator<Object> it = pro.keySet().iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					String v = pro.getProperty(key);
					properties.put(key, new String(v.getBytes("ISO-8859-1"),
							"UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}
}
