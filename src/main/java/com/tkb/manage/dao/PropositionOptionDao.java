package com.tkb.manage.dao;

import java.util.Map;

import com.tkb.manage.model.PropositionOption;

public interface PropositionOptionDao {
	
	public void add(PropositionOption propositionOption);
	public void delete(PropositionOption propositionOption);
	public Map<String, Object> option(PropositionOption propositionOption);
	public Map<String, Object> subjectOption(PropositionOption propositionOption);
	public Map<String, Object> gradeOption(PropositionOption propositionOption);
	
}
