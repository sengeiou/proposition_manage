package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.PropositionAuditDao;
import com.tkb.manage.model.PropositionAudit;
import com.tkb.manage.service.PropositionAuditService;

@Service
public class PropositionAuditServiceImpl implements PropositionAuditService {
	
	@Autowired
	private PropositionAuditDao propositionAuditDao;
	
	public int add(PropositionAudit propositionAudit) {
		return propositionAuditDao.add(propositionAudit);
	}
	
	public List<Map<String, Object>> historyList(PropositionAudit propositionAudit) {
		return propositionAuditDao.historyList(propositionAudit);
	}
	
}
