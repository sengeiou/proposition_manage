package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.PropositionTag;

public interface PropositionTagDao {
	
	public Integer add(PropositionTag propositionTag);
	public void delete(PropositionTag propositionTag);
	public List<Map<String, Object>> tagList(PropositionTag propositionTag);
	
}
