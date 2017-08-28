package com.bbieat.screen.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.event.RowHandler;
import com.holley.eemas.util.StringUtil;

public class ExcelRowHandler implements RowHandler {

	private Logger log = Logger.getLogger(this.getClass());

	WritableWorkbook wbook = null;
	String sheetName = null;
	String fileName = null;
	WritableSheet sheet = null;
	List<String> headList = null;

	int sheetNum = 0;
	int recordNum = 0;
	int recordCount = 0;

	String[] keys = null;

	public ExcelRowHandler(String fileName, String sheetName,
			String exceHeads) {
		super();
		this.sheetName = sheetName;
		this.headList = getHeads(exceHeads);
		this.fileName = fileName;
		
		File file = new File(fileName);
		try {
			this.wbook = Workbook.createWorkbook(file);
			createSheet();
		} catch (Exception e) {
			e.printStackTrace();
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

	private void createSheet() {
		String sn = sheetNum > 0 ? sheetName + String.valueOf(sheetNum)
				: sheetName;
		sheet = wbook.createSheet(sn, sheetNum);

		// 设置excel标题字体
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 9,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE); // 字体
		WritableCellFormat wcfFC = new WritableCellFormat(wfont); // 字体格式

		keys = new String[headList.size()];
		for (int i = 0; i < headList.size(); i++) {
			String[] head = headList.get(i).split(","); //"cModel:dataIndex,header"
			keys[i] = head[0];
			try {
				sheet.addCell(new Label(i, 0, head[1], wcfFC));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sheetNum++;
		recordNum = 1;
	}

	@SuppressWarnings("unchecked")
	public void handleRow(Object o) {
		if (recordCount % 50000 == 0 && recordCount > 0) {
			createSheet();
		}
		Map m = (Map) o;
		for (int c = 0; c < keys.length; c++) {
			try {
				sheet.addCell(new Label(c, recordNum, StringUtil.getValue(m
						.get(keys[c]))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		recordNum++;
		recordCount++;
	}

	public int getExcel() {
		try {
			if (wbook != null)
				wbook.write();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wbook != null) {
				try {
					wbook.close();
					log.debug(" output excel file " + fileName + " size is "
							+ recordCount);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return recordCount;
	}

}
