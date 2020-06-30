package com.tkb.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.PropositionOptionDao;
import com.tkb.manage.model.PropositionOption;
import com.tkb.manage.service.PropositionOptionService;

@Service
public class PropositionOptionServiceImpl implements PropositionOptionService {
	
	@Autowired
	private PropositionOptionDao propositionOptionDao;
	
	public void add(PropositionOption propositionOption) {
		propositionOptionDao.add(propositionOption);
	}
	
	public void delete(PropositionOption propositionOption) {
		propositionOptionDao.delete(propositionOption);
	}
	
	public Map<String, Object> option(PropositionOption propositionOption) {
		return propositionOptionDao.option(propositionOption);
	}
	
	public Map<String, Object> subjectOption(PropositionOption propositionOption) {
		return propositionOptionDao.subjectOption(propositionOption);
	}
	
	public Map<String, Object> gradeOption(PropositionOption propositionOption) {
		return propositionOptionDao.gradeOption(propositionOption);
	}
	
}
