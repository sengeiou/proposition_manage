package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.ManagerAccountDao;
import com.tkb.manage.model.Account;
import com.tkb.manage.service.ManagerAccountService;

@Service
public class ManagerAccountServiceImpl implements ManagerAccountService {
	
	@Autowired
	private ManagerAccountDao managerAccountDao;
	
	public Account login(Account account) {
		return managerAccountDao.login(account);
	}
	
	public List<Map<String, Object>> list(Account account) {
		return managerAccountDao.list(account);
	}
	
	public Integer count(Account account) {
		return managerAccountDao.count(account);
	}
	
	public Account data(Account account) {
		return managerAccountDao.data(account);
	}
	
	public int add(Account account) {
		return managerAccountDao.add(account);
	}
	
	public void update(Account account) {
		managerAccountDao.update(account);
	}
	
	public void delete(Account account) {
		managerAccountDao.delete(account);
	}
	
}
