package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.PropositionDao;
import com.tkb.manage.model.Proposition;
import com.tkb.manage.service.PropositionService;

@Service
public class PropositionServiceImpl implements PropositionService {
	
	@Autowired
	private PropositionDao propositionDao;
	
	public List<Map<String, Object>> manageList(Proposition proposition) {
		return propositionDao.manageList(proposition);
	}
	
	public Integer manageCount(Proposition proposition) {
		return propositionDao.manageCount(proposition);
	}
	
	public List<Map<String, Object>> auditList(Proposition proposition) {
		return propositionDao.auditList(proposition);
	}
	
	public Integer auditCount(Proposition proposition) {
		return propositionDao.auditCount(proposition);
	}
	
	public List<Map<String, Object>> teacherList(Proposition proposition) {
		return propositionDao.teacherList(proposition);
	}
	
	public Integer teacherCount(Proposition proposition) {
		return propositionDao.teacherCount(proposition);
	}
	
	public List<Map<String, Object>> principalList(Proposition proposition) {
		return propositionDao.principalList(proposition);
	}
	
	public Integer principalCount(Proposition proposition) {
		return propositionDao.principalCount(proposition);
	}
	
	public List<Map<String, Object>> leaderList(Proposition proposition) {
		return propositionDao.leaderList(proposition);
	}
	
	public Integer leaderCount(Proposition proposition) {
		return propositionDao.leaderCount(proposition);
	}
	
	public List<Map<String, Object>> secretaryGeneralList(Proposition proposition) {
		return propositionDao.secretaryGeneralList(proposition);
	}
	
	public Integer secretaryGeneralCount(Proposition proposition) {
		return propositionDao.secretaryGeneralCount(proposition);
	}
	
	public Integer uploadStatusCount(Proposition proposition) {
		return propositionDao.uploadStatusCount(proposition);
	}
	
	public Integer add(Proposition proposition) {
		return propositionDao.add(proposition);
	}
	
	public void update(Proposition proposition) {
		propositionDao.update(proposition);
	}
	
	public Proposition data(Proposition proposition) {
		return propositionDao.data(proposition);
	}
	
	public void audit(Proposition proposition) {
		propositionDao.audit(proposition);
	}
	
	public Map<String, Object> auditNum(Proposition proposition) {
		return propositionDao.auditNum(proposition);
	}
	
	public Map<String, Object> getNum(Proposition proposition) {
		return propositionDao.getNum(proposition);
	}
	
}
