package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.HistoryDao;
import com.tkb.manage.model.History;

@Repository
public class HistoryDaoImpl implements HistoryDao {
	
	@Autowired
	private JdbcTemplate mysqlJdbcTemplate;
	
	public void addLog(History history) {
		
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = "INSERT INTO proposition_manage.history (USER, IP, METHOD, URL, ARGS, RESPONSE) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		args.add(history.getUser());
		args.add(history.getIp());
		args.add(history.getMethod());
		args.add(history.getUrl());
		args.add(history.getArgs());
		args.add(history.getResponse());		
		
		mysqlJdbcTemplate.update(sql, args.toArray());
		
	}
	

}
