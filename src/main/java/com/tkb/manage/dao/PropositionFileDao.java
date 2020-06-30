package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.PropositionFile;

public interface PropositionFileDao {
	
	public int add(PropositionFile propositionFile);
	public List<Map<String, Object>> historyList(PropositionFile propositionFile);
	
}
