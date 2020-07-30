package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.PropositionFileDao;
import com.tkb.manage.model.PropositionFile;
import com.tkb.manage.service.PropositionFileService;

@Service
public class PropositionFileServiceImpl implements PropositionFileService {
	
	@Autowired
	private PropositionFileDao propositionFileDao;
	
	public int add(PropositionFile propositionFile) {
		return propositionFileDao.add(propositionFile);
	}
	
	public List<Map<String, Object>> historyList(PropositionFile propositionFile) {
		return propositionFileDao.historyList(propositionFile);
	}
	
	public List<Map<String, Object>> getFile(PropositionFile propositionFile) {
		return propositionFileDao.getFile(propositionFile);
	}
	
	public List<Map<String, Object>> getAnnex(PropositionFile propositionFile) {
		return propositionFileDao.getAnnex(propositionFile);
	}
	
	public List<Map<String, Object>> getMaterial(PropositionFile propositionFile) {
		return propositionFileDao.getMaterial(propositionFile);
	}
	
}
