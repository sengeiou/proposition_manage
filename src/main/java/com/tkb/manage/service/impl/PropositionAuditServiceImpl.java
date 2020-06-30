package com.tkb.manage.service.impl;

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
	
}
