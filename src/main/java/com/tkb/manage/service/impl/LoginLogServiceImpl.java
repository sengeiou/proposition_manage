package com.tkb.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.LoginLogDao;
import com.tkb.manage.model.LoginLog;
import com.tkb.manage.service.LoginLogService;

@Service
public class LoginLogServiceImpl implements LoginLogService {
	
	@Autowired
	private LoginLogDao loginLogDao;
	
	public void add(LoginLog loginLog) {
		loginLogDao.add(loginLog);
	}
	
}
