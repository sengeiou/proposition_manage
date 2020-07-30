package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.PropositionTagDao;
import com.tkb.manage.model.PropositionTag;
import com.tkb.manage.service.PropositionTagService;

@Service
public class PropositionTagServiceImpl implements PropositionTagService {
	
	@Autowired
	private PropositionTagDao propositionTagDao;
	
	public Integer add(PropositionTag propositionTag) {
		return propositionTagDao.add(propositionTag);
	}
	
	public void delete(PropositionTag propositionTag) {
		propositionTagDao.delete(propositionTag);
	}
	
	public List<Map<String, Object>> tagList(PropositionTag propositionTag) {
		return propositionTagDao.tagList(propositionTag);
	}
	
}
