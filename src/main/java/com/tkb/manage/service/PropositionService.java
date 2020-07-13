package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.Proposition;

public interface PropositionService {
	
	public List<Map<String, Object>> manageList(Proposition proposition);
	public Integer manageCount(Proposition proposition);
	public List<Map<String, Object>> auditList(Proposition proposition);
	public Integer auditCount(Proposition proposition);
	public List<Map<String, Object>> teacherList(Proposition proposition);
	public Integer teacherCount(Proposition proposition);
	public List<Map<String, Object>> principalList(Proposition proposition);
	public Integer principalCount(Proposition proposition);
	public List<Map<String, Object>> leaderList(Proposition proposition);
	public Integer leaderCount(Proposition proposition);
	public List<Map<String, Object>> secretaryGeneralList(Proposition proposition);
	public Integer secretaryGeneralCount(Proposition proposition);
	public Integer uploadStatusCount(Proposition proposition);
	public Integer add(Proposition proposition);
	public void update(Proposition proposition);
	public Proposition data(Proposition proposition);
	public void audit(Proposition proposition);
	
}
