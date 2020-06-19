package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.LoginLogDao;
import com.tkb.manage.model.LoginLog;

@Repository
public class LoginLogDaoImpl implements LoginLogDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void add(LoginLog loginLog) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " INSERT INTO proposition_manage.login_log "
				   + " (ACCOUNT, PASSWORD, STATUS, MSG, TYPE, IP, CREATE_TIME) "
				   + " VALUES(?, ?, ?, ?, ?, ?, NOW()) ";
		
		args.add(loginLog.getAccount());
		args.add(loginLog.getPassword());
		args.add(loginLog.getStatus());
		args.add(loginLog.getMsg());
		args.add(loginLog.getType());
		args.add(loginLog.getIp());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
}
