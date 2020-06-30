package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.IdentityDao;
import com.tkb.manage.model.Identity;
import com.tkb.manage.service.IdentityService;

@Service
public class IdentityServiceImpl implements IdentityService {
	
	@Autowired
	private IdentityDao identityDao;
	
	public List<Map<String, Object>> list(Identity identity) {
		return identityDao.list(identity);
	}
	
	public Integer count(Identity identity) {
		return identityDao.count(identity);
	}
	
	public Identity data(Identity identity) {
		return identityDao.data(identity);
	}
	
	public int add(Identity identity) {
		return identityDao.add(identity);
	}
	
	public void update(Identity identity) {
		identityDao.update(identity);
	}
	
	public void delete(Identity identity) {
		identityDao.delete(identity);
	}
	
	public List<Map<String, Object>> getChild(Identity identity) {
		return identityDao.getChild(identity);
	}
	
	public Map<String, Object> backData(Identity identity) {
		return identityDao.backData(identity);
	}
	
	public Integer maxSort(Identity identity) {
		return identityDao.maxSort(identity);
	}
	
	public void updateSortByDelete(Identity identity) {
		identityDao.updateSortByDelete(identity);
	}
	
	public List<Map<String, Object>> getMenu(Identity identity) {
		return identityDao.getMenu(identity);
	}
	
	public Map<String, Object> getDataByLevel(Identity identity) {
		return identityDao.getDataByLevel(identity);
	}
	
}
