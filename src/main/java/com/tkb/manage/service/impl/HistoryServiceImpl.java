package com.tkb.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.HistoryDao;
import com.tkb.manage.model.History;
import com.tkb.manage.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryDao historyDao;
	
	public void addLog(History history) {
		historyDao.addLog(history);
	}

}
