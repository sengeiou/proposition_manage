package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.HistoryDao;
import com.tkb.manage.model.History;

@Repository
public class HistoryDaoImpl implements HistoryDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void add(History history) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " INSERT INTO proposition_manage.history(URL, DATA, METHOD, TABLE_NAME, "
				   + " IP, CREATE_BY, CREATE_TIME) "
				   + " VALUES(?, ?, ?, ?, ?, ?, NOW())";
		
		args.add(history.getUrl());
		args.add(history.getData());
		args.add(history.getMethod());
		args.add(history.getTable_name());
		args.add(history.getIp());
		args.add(history.getCreate_by());		
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> getData(String databases, String table_name, String id) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM "
				   + databases+"."+table_name
				   + " WHERE ID = ? ";
		
		args.add(id);
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	

}
