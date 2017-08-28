package com.bbieat.screen.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import com.holley.eemas.util.StringUtil;
import com.ibatis.sqlmap.client.event.RowHandler;

public class CsvRowHandler implements RowHandler {

	// private Logger log = Logger.getLogger(this.getClass());

	String fileName = null;
	List<String> headList = null;
	String[] keys = null;
	CsvListWriter csv = null;
	int rowNum = 0;

	public CsvRowHandler(String fileName, String exceHeads) {
		super();
		this.fileName = fileName;
		this.headList = getHeads(exceHeads);
		try {
			OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(new File(fileName)), "GBK");
			CsvPreference EXCEL_PREFERENCE = new CsvPreference.Builder('"', ',', "\n").build();
			csv = new CsvListWriter(ow, EXCEL_PREFERENCE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				csv.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}

	private List<String> getHeads(String exceHeads) {
		List<String> arr = new ArrayList<String>();
		String[] heads = exceHeads.split("@");
		for(String h : heads) {
			arr.add(h);
		}
		return arr;
	}

	private void getHead() {
		keys = new String[headList.size()];
		String[] header = new String[headList.size()];
		for (int i = 0, j = headList.size(); i < j; i++) {
			String[] head = headList.get(i).split(",");
			keys[i] = head[0];
			header[i] = head[1];
		}
		rowNum++;
		try {
			csv.writeHeader(header);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				csv.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
	}

	@SuppressWarnings("unchecked")
	public void handleRow(Object o) {
		if (rowNum == 0) {
			getHead();
		}

		String[] contents = new String[headList.size()];
		Map m = (Map) o;
		for (int i = 0, j = keys.length; i < j; i++) {
			String v = StringUtil.getValue(m.get(keys[i])).trim();
			if (keys[i].indexOf("HH") >= 0 
					|| keys[i].indexOf("ZDJH") >= 0
					|| keys[i].indexOf("BJJH") >= 0
					|| keys[i].indexOf("CLDJH") >= 0
					|| keys[i].indexOf("CBQM") >= 0
					|| keys[i].indexOf("ZDLJDZ") >= 0
					|| keys[i].indexOf("TQBM") >= 0
					|| keys[i].indexOf("BBH") >= 0
					|| keys[i].indexOf("CJQJH") >= 0
					|| keys[i].indexOf("JZQTM") >= 0
					|| keys[i].indexOf("SJ") >= 0
					|| keys[i].indexOf("RQ") >= 0
					|| keys[i].indexOf("DBZCH") >= 0
					|| keys[i].indexOf("XLID") >= 0
					|| keys[i].indexOf("GDID") >= 0
					|| keys[i].indexOf("局号") >= 0
					|| keys[i].indexOf("TQID") >= 0
					|| keys[i].indexOf("YCID") >= 0
					)
				v = v + "\t";
			contents[i] = v;
		}
		try {
			csv.write(contents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				csv.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		rowNum++;
	}

	public int closeOs() {
		if (csv != null) {
			try {
				csv.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rowNum;
	}
}
