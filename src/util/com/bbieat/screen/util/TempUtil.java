package com.bbieat.screen.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

public class TempUtil {
	public static Logger log = Logger.getLogger(TempUtil.class);
	public static String SPL = "&&&";
	public static String LOCALE = System.getProperty("file.encoding");
	/**
	 * 分页取文件
	 * 
	 * @param fileName
	 * @param start
	 * @param pageSize
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> filePaging(String fileName, int start,
			int pageSize) {
		File file = new File(Constants.TEMPFILE + fileName);
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> rows = new LinkedList<String>();
		LineIterator it = null;
		String line = null;
		int i = 0;
		try {
			if(file.exists()) {
				it = FileUtils.lineIterator(file, LOCALE);
				while (it.hasNext()) {
					line = it.nextLine();
					if (i >= start && i < start + pageSize) {
						line = line.replaceAll("=", "\":\"");
						line = line.replaceAll(SPL, "\",\"");
						line = "{\"" + line + "\"}";
						rows.add(line);
					}
					i++;
				}
			}
		} catch (Exception e) {
			log.error("========== filePaging error ===========");
			e.getStackTrace();
		} finally {
			if(it!=null)
			it.close();
		}
		result.put("rows", i);
		result.put("result", rows);
		return result;
	}

	/**
	 * 写入文件
	 * 
	 * @param fileName
	 * @param lines
	 * @param key
	 * @param cover
	 * @param orders
	 * @throws IOException
	 */
	public static void listToFile(String fileName, List<?> lines, String key,
			boolean cover, String[] orders) {
		File file = new File(Constants.TEMPFILE + fileName);
		List<String> newlines = new LinkedList<String>();
		try {
			Map<String, String> idProperties = null;
			if (lines != null && lines.size() > 0) {
				idProperties = new HashMap<String, String>(lines.size());
				for (int i = 0; i < lines.size(); i++) {
					Map<?, ?> m = (Map<?, ?>) lines.get(i);
					oO o = new oO(m, orders);
					newlines.add(o.toString());
					if (StringUtil.isNes(key)) {
						idProperties.put(StringUtil.getValue(m.get(key)), null);
					}
				}
			}
			if (file.exists() && !cover) {
				List<String> ls = FileUtils.readLines(file, LOCALE); // 验证重复
				for (String line : ls) {
					String d = getV(key, line);
					if (StringUtil.isNes(d)
							&& (idProperties == null || !idProperties
									.containsKey(d))) {
						newlines.add(line);
					}
				}
			}
			FileUtils.writeLines(file, LOCALE, newlines); // 写入文件
		} catch (Exception e) {
			log.error("========== listToFile error ===========");
			e.printStackTrace();
		}
	}

	/**
	 * 从文件中删除
	 * 
	 * @param fileName
	 * @param key
	 * @param ids
	 * @throws IOException
	 */
	public static void removeFormFile(String fileName, String key, String[] ss) {
		File file = new File(Constants.TEMPFILE + fileName);
		List<String> lines = new LinkedList<String>();
		Map<String, String> ids = new HashMap<String, String>(ss.length);
		for (String s : ss) {
			ids.put(s, null);
		}
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE);
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String d = getV(key, line);
						if (!ids.containsKey(d)) {
							lines.add(line);
						}
					}
				}
				FileUtils.writeLines(file, LOCALE, lines);
			} catch (Exception e) {
				log.error("========== removeFormFile error ===========");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除成功或失败
	 * 
	 * @param fileName
	 * @param result
	 * @param fail
	 * @throws IOException
	 */
	public static void removeScOrFail(String fileName, String result,
			boolean fail) {
		File file = new File(Constants.TEMPFILE + fileName);
		List<String> lines = new LinkedList<String>();
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE);
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						boolean remove = false;
						String d = getV(result, line);
						if (fail && d.indexOf("成功") < 0) { // 删除失败
							remove = true;
						}
						if (!fail && d.indexOf("成功") > -1) { // 删除成功
							remove = true;
						}
						if (StringUtil.isEs(d)) {
							remove = false;
						}
						if (!remove) {
							lines.add(line);
						}
					}
				}
				FileUtils.writeLines(file, LOCALE, lines);
			} catch (Exception e) {
				log.error("========== removeScOrFail error ===========");
				e.getStackTrace();
			}
		}
	}

	/**
	 * 比较某列内容并删除
	 * 
	 * @param fileName
	 * @param key
	 * @param compare
	 * @throws IOException
	 */
	public static void removeRecord(String fileName, String key, String compare) {
		File file = new File(Constants.TEMPFILE + fileName);
		List<String> lines = new LinkedList<String>();
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE);
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						boolean remove = false;
						String v = getV(key, line);
						if (StringUtil.isNes(v) && compare.equals(v)) { // 删除
							remove = true;
						}
						if (!remove) {
							lines.add(line);
						}
					}
				}
				FileUtils.writeLines(file, LOCALE, lines);
			} catch (Exception e) {
				log.error("========== removeRecord error ===========");
				e.getStackTrace();
			}
		}
	}

	/**
	 * 虚拟全选的时候调用文件，取里面所有的ID列
	 * 
	 * @param fileName
	 * @param id
	 * @return
	 */
	public static String getIdFromFile(String fileName, String key) {
		String idArray = "";
		File file = new File(Constants.TEMPFILE + fileName);
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE); // 验证重复
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String v = getV(key, line);
						if (StringUtil.isNes(v)) {
							idArray += v + ",";
						}
					}
				}
			} catch (Exception e) {
				log.error("========== getIdFromFile error ===========");
				e.getStackTrace();
			}
		}
		if (StringUtil.isNes(idArray)) {
			idArray = idArray.substring(0, idArray.length() - 1);
		}
		return idArray;
	}

	private static String getV(String key, String line) {
		int index = line.indexOf(key);
		if (index < 0) {
			return "";
		}
		int start = index + key.length() + 1;
		line = line.substring(start);
		start = line.indexOf(SPL);
		if (start < 0) {
			return line;
		} else {
			return line.substring(0, start);
		}
	}

	/**
	 * 将匹配内容重写入文件
	 * 
	 * @param file
	 * @param idColNum
	 * @param resultMap
	 * @param totalCol
	 * @throws IOException
	 */
	public static void addToFile(String fileName, String key,
			Map<String, String> resultMap, int totalCol) {
		List<String> newlines = new LinkedList<String>();
		File file = new File(Constants.TEMPFILE + fileName);
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE); // 验证重复
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						String id = getV(key, line);
						String replaceStr = resultMap.get(id);
						String newline = toLine(line, totalCol, replaceStr);
						newlines.add(newline);
					}
				}
				FileUtils.writeLines(file, LOCALE, newlines);
			} catch (Exception e) {
				log.error("========== addToFile error ===========");
				e.getStackTrace();
			}
		}
	}

	private static String toLine(String line, int total, String re) {
		String[] str = line.split(SPL);
		String newline = "";
		int len;
		if (str.length < total) {
			len = str.length;
		} else {
			len = total;
		}
		for (int i = 0; i < len; i++) {
			newline += str[i] + SPL;
		}
		if (StringUtil.isNes(re)) {
			newline += re;
		} else {
			newline = newline.substring(0, newline.length() - SPL.length());
		}
		return newline;
	}

	/**
	 * 清空文件
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static void clearFile(String fileName) {
		File f = new File(Constants.TEMPFILE + fileName);
		if (!f.exists()) {
			return;
		}
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(Constants.TEMPFILE + fileName, "rw");
			rf.setLength(0);
		} catch (Exception e) {
			log.error("========== clearFile error ===========");
			e.printStackTrace();
		} finally {
			if (rf != null) {
				try {
					rf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 把文件里的内容转化为List(里面是Map) 导出Excel用
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public static List<Object> getFileToList(String fileName) {
		List<Object> ls = new ArrayList<Object>();
		File file = new File(Constants.TEMPFILE + fileName);
		if (file.exists()) {
			try {
				List<String> list = FileUtils.readLines(file, LOCALE); // 验证重复

				if (list != null && list.size() > 0) {
					for (String line : list) {
						Map<String, Object> resMap = new HashMap<String, Object>();
						String[] idProperties = line.split(SPL);
						for (String idPropertie : idProperties) {
							String [] kv = idPropertie.split("="); 
							String id = kv[0];
							String value = "";
							if(kv.length>=2){
								value = idPropertie.split("=")[1];
							}
							resMap.put(id, value);
						}

						ls.add(resMap);
					}
				}
			} catch (Exception e) {
				log.error("========== getFileToList error ===========");
				e.getStackTrace();
			}
		}
		return ls;
	}
	
	public static List<String> getLineById(String fileName, String ids) {
		List<String> re = new ArrayList<String>();
		File file = new File(Constants.TEMPFILE + fileName);
		String[] id = ids.split(",");
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE); // 验证重复
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						for(String key : id) {
							if (line.indexOf(key) > -1) {
								re.add(line);
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("========== getLineById error ===========");
				e.getStackTrace();
			}
		}
		return re;
	}
	
	public static void removeLineById(String fileName, String ids) {
		List<String> newline = new ArrayList<String>();
		File file = new File(Constants.TEMPFILE + fileName);
		String[] id = ids.split(",");
		if (file.exists()) {
			try {
				List<String> ls = FileUtils.readLines(file, LOCALE); // 验证重复
				if (ls != null && ls.size() > 0) {
					for (String line : ls) {
						boolean within = false;
						for(String key : id) {
							if (line.indexOf(key) > -1) {
								within = true;
								break;
							}
						}
						if(!within) {
							newline.add(line);
						}
					}
				}
				FileUtils.writeLines(file, LOCALE, newline);
			} catch (Exception e) {
				log.error("========== removeLineById error ===========");
				e.getStackTrace();
			}
		}
	}
	
	public static void addToFile(String fileName, List<String> lines) {
		File file = new File(Constants.TEMPFILE + fileName);
		List<String> newlines = null;
		try {
			if (file.exists()) {
				try {
					newlines = FileUtils.readLines(file, LOCALE); // 验证重复
					for (String line : lines)
						newlines.add(line);
				} catch (Exception e) {
					log.info(e.getStackTrace());
					e.getStackTrace();
				}
			}
			FileUtils.writeLines(file, LOCALE, newlines);
		} catch (Exception e) {
			log.error("========== addToFile error ===========");
			e.getStackTrace();
		}
	}
}

/**
 * 重写Map的toString
 * 
 * @author Administrator
 * 
 */
class oO {
	private Map<?, ?> o = null;
	private String[] b = null;
	private static String SPL = "&&&";

	public oO(Map<?, ?> mp, String[] orderToFile) {
		o = mp;
		b = orderToFile;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (o != null && o.size() > 0) {
			if (b == null) {
				Iterator<?> it = o.keySet().iterator();
				String k = (String) it.next();
				sb.append(k + "=" + o.get(k));
				while (it.hasNext()) {
					k = (String) it.next();
					sb.append(SPL + k + "=" + o.get(k));
				}
			} else {
				sb.append(b[0] + "=" + o.get(b[0]));
				for (int j = 1; j < b.length; j++) {
					String orederName = b[j];
					if (o.get(orederName) != null) {
						sb.append(SPL + orederName + "=" + o.get(orederName));
					} else {
						sb.append(SPL + orederName + "=");
					}
				}
			}
		}
		String s = sb.toString().replaceAll("\r\n", "");
		return s;
	}
}
