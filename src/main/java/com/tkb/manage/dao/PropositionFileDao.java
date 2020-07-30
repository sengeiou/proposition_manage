package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.PropositionFile;

public interface PropositionFileDao {
	
	public int add(PropositionFile propositionFile);
	public List<Map<String, Object>> historyList(PropositionFile propositionFile);
	public List<Map<String, Object>> getFile(PropositionFile propositionFile);
	public List<Map<String, Object>> getAnnex(PropositionFile propositionFile);
	public List<Map<String, Object>> getMaterial(PropositionFile propositionFile);
	
}
