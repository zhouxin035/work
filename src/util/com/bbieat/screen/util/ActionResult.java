package com.bbieat.screen.util;

/**
 * DWR返回结果
 * @author YCL
 *
 */
public class ActionResult {
	// 操作结果标志
	private boolean success;
	// 返回信息
	private String msg;
	// service层返回对象
	private Object dataObject;

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	public ActionResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public ActionResult(boolean success) {
		super();
		this.success = success;
	}
	public ActionResult() {
		super();
	}

}
