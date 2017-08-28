package com.bbieat.screen.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FileUtil {

	/**
	 * 创建Excel
	 * 
	 * @param path
	 * @param headList
	 * @param bodyList
	 * @param xlsName
	 * @param sheetName
	 * @return
	 */
	public static String createExcel(String path, List<String> headList, List<?> bodyList, String xlsName, String sheetName) {
		File p = new File(path);
		if (!p.exists())
			p.mkdirs();
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(new File(path + File.separator + xlsName + ".xls"));
			excel(headList, bodyList, sheetName, os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	public static void excel(List<String> headList, List<?> bodyList, String sheetName, OutputStream os) {
		WritableWorkbook wbook = null;
		try {
			wbook = Workbook.createWorkbook(os);// 建立excel文件
			if (bodyList == null) {
				bodyList = new ArrayList<Map<String, ?>>();
			}
			float sn = (float) bodyList.size() / 60000;
			int n = bodyList.size() % 60000;
			if (sn > 1) {
				int sheetNum = n > 0 ? (int) sn + 1 : (int) sn;
				for (int i = 0; i < sheetNum; i++) {
					int start = i * 60000;
					createSheet(wbook, bodyList, headList, i, start, sheetName);
				}
			} else {
				createSheet(wbook, bodyList, headList, 0, 0, sheetName);
			}
			wbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
					wbook = null;
					bodyList = null;
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void createSheet(WritableWorkbook wbook, List<?> bodyList, List<String> headList, int sheetNum, int start, String sheetName) throws RowsExceededException, WriteException {

		// 设置excel标题字体
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE); // 字体
		WritableCellFormat wcfFC = new WritableCellFormat(wfont); // 字体格式
		sheetName = sheetNum > 0 ? sheetName + (sheetNum + "") : sheetName;

		WritableSheet sheet = wbook.createSheet(sheetName, sheetNum); // sheet名称
		String[] keys = new String[headList.size()];
		for (int i = 0; i < headList.size(); i++) {
			sheet.addCell(new Label(i, 0, headList.get(i).split(",")[1], wcfFC));
			keys[i] = headList.get(i).split(",")[0];
		}

		int end = (start + 60000) > bodyList.size() ? bodyList.size() : start + 60000;
		for (int r = start; r < end; r++) {
			Map<?, ?> m = (Map<?, ?>) bodyList.get(r);
			for (int c = 0; c < keys.length; c++) {
				sheet.addCell(new Label(c, r + 1, StringUtil.getValue(m.get(keys[c])))); // 第二行开始
			}
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param path
	 * @param fileName
	 * @param os
	 */
	public static void downFile(String path, String fileName, OutputStream os, boolean del) {
		File temp = new File(path + fileName);
		FileInputStream in = null;
		try {
			if (temp.exists()) {
				in = new FileInputStream(temp);
				byte[] b = new byte[1000];
				int len;
				while ((len = in.read(b)) > 0) {
					os.write(b, 0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					if (del)
						temp.delete();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 文件复制
	 * 
	 * @param src
	 * @param dst
	 */
	public static void copyFile(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src), 16 * 1024);
				out = new BufferedOutputStream(new FileOutputStream(dst), 16 * 1024);
				byte[] buffer = new byte[16 * 1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写日志
	 * 
	 * @param path
	 * @param logFile
	 * @param content
	 */
	public static void createLogger(String path, String logFile, String content) {
		FileOutputStream out = null;
		try {
			synchronized (path) {
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File log = new File(path + logFile);
				if (!log.exists()) {
					dir.mkdirs();
				}
				out = new FileOutputStream(new File(path + logFile), true);
				IOUtils.write(content, out, System.getProperty("file.encoding"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 数据库出错日志
	 * 
	 * @param sqlId
	 * @param obj
	 * @param errorCause
	 * @return
	 */
	public static String dbLogger(String sqlId, Object obj, String errorCause) {
		StringBuffer sb = new StringBuffer("\n\n");
		sb.append("============sql exceptions==============");
		sb.append(DateUtil.getCurrentTime());
		sb.append("\n sqlId:");
		sb.append(sqlId);
		if (obj != null) {
			sb.append("\n params:");
			sb.append(paramToString(obj));
		}
		sb.append("\n errorCause:");
		sb.append(errorCause);
		String fileName = "displayDb.log";
		createLogger(Constants.LOGGER, fileName, sb.toString());
		return sb.toString();
	}

	private static String pp = "=";
	private static String pt = ";";

	@SuppressWarnings("unchecked")
	private static String paramToString(Object o) {
		if (o.getClass().equals(HashMap.class)) {
			Map param = (Map) o;
			StringBuffer sb = new StringBuffer();
			Iterator<Object> it = param.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (param.get(key) != null) {
					if (param.get(key).getClass().equals(String.class)) {
						sb.append(key + pp + param.get(key) + pt);
					}else if (param.get(key).getClass().equals(Integer.class)) {
						sb.append(key + pp + String.valueOf(param.get(key)) + pt);
					}else if (param.get(key).getClass().equals(String[].class)) {
						String[] a = (String[]) param.get(key);
						sb.append("String[]:" + key + pp);
						for (String aa : a) {
							sb.append(aa + ",");
						}
						sb.append(pt);
					}
				}
			}
			if (sb.length()>1) {
				return sb.substring(0, sb.length() - pt.length()).toString();
			}else{
				return null;
			}
			
		} else {
			return o.toString();
		}
	}

	/**
	 * 建目录
	 * 
	 * @param path
	 * @param clear
	 */
	public static void createPath(String path, boolean clear) {
		File tempfile = new File(path); // 清除缓存文件
		if (!tempfile.exists()) { // 建目录
			tempfile.mkdirs();
		}
		if (clear && tempfile.isDirectory()) { // 清除目录
			try {
				FileUtils.cleanDirectory(tempfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
