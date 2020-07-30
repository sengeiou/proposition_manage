package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.PropositionAudit;

public interface PropositionAuditDao {
	
	public int add(PropositionAudit propositionAudit);
	public List<Map<String, Object>> historyList(PropositionAudit propositionAudit);
	
}
