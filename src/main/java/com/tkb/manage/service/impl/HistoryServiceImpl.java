package com.tkb.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.HistoryDao;
import com.tkb.manage.model.History;
import com.tkb.manage.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryDao historyDao;
	
	public void add(History history) {
		historyDao.add(history);
	}
	
	public Map<String, Object> getData(String databases, String table_name, String id) {
		return historyDao.getData(databases, table_name, id);
	}

}
