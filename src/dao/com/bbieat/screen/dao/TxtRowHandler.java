package com.bbieat.screen.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.ibatis.sqlmap.client.event.RowHandler;
import com.holley.eemas.util.StringUtil;


public class TxtRowHandler implements RowHandler {

	String fileName = null;
	String[] orders = null;
	FileOutputStream txt = null;
	int rowNum = 0;
	private String SPL = "&&&";
	
	public TxtRowHandler(String fileName, String[] orders, boolean append) {
		super();
		this.fileName = fileName;
		this.orders = orders;
		try {
			txt = new FileOutputStream(new File(fileName), append);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 
	
	@SuppressWarnings("unchecked")
	public void handleRow(Object o) {
		Map m = (Map) o;
		StringBuffer row = new StringBuffer();
		if (orders != null && orders.length > 0) {
			for (int i = 0, j = orders.length; i < j; i++) {
				row.append(orders[i]);
				row.append("=");
				row.append(StringUtil.getValue(m.get(orders[i])).trim());
				if (i < j - 1) {
					row.append(SPL);
				} else {
					row.append("\r\n");
				}
			}
		} else {
			Iterator it = m.keySet().iterator();
			String k = (String) it.next();
			row.append(k + "=" + m.get(k));
			while (it.hasNext()) {
				k = (String) it.next();
				row.append(SPL + k + "=" + StringUtil.getValue(m.get(k)).trim());
			}
			row.append("\r\n");
		}
		
		try {
			IOUtils.write(row.toString(), txt,  System.getProperty("file.encoding"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rowNum++;
	}

	public int closeOs() {
		try {
			if(rowNum == 0) { //写空文件
				FileUtils.writeLines(new File(fileName), new ArrayList<String>());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(txt != null) {
				try {
					txt.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return rowNum;
	}
}
