package com.bbieat.screen.service;

import com.holley.eemas.dao.BaseDao;


public class BaseManager {

	protected BaseDao dao = null;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
