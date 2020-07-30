package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.PropositionAudit;

public interface PropositionAuditService {
	
	public int add(PropositionAudit propositionAudit);
	public List<Map<String, Object>> historyList(PropositionAudit propositionAudit);
	
}
