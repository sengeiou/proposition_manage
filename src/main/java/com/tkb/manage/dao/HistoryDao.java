package com.tkb.manage.dao;

import java.util.Map;

import com.tkb.manage.model.History;

public interface HistoryDao {
	
	public void add(History history);
	public Map<String, Object> getData(String databases, String table_name, String id);

}
