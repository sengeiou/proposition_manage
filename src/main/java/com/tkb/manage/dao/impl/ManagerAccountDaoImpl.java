package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.ManagerAccountDao;
import com.tkb.manage.model.Account;

@Repository
public class ManagerAccountDaoImpl implements ManagerAccountDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public Account login(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.manager_account "
				   + " WHERE ACCOUNT = ? "
				   + " AND PASSWORD = ? ";
		
		args.add(account.getAccount());
		args.add(account.getPassword());
		
		List<Account> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Account>(Account.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> list(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MA.*, "
				   + " CASE WHEN STATUS = '0' THEN '關閉' ELSE '開啟' END AS STATUS_NAME, "
				   + " PMI.NAME AS IDENTITY_NAME "
				   + " FROM proposition_manage.manager_account MA "
				   + " LEFT JOIN proposition_manage.identity PMI ON PMI.ID = MA.IDENTITY_ID ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.manager_account ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Account data(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.manager_account "
				   + " WHERE ID = ? ";
		
		args.add(account.getId());
		
		List<Account> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Account>(Account.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Account account) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(account);
		
		String sql = " INSERT INTO proposition_manage.manager_account "
				   + " (UUID, ACCOUNT, PASSWORD, NAME, DEPT_NO, DEPT_NAME,  "
				   + " UNIT_NO, UNIT_NAME, EMAIL, COMPANY_NO, IDENTITY_ID, STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :account, :password, :name, "
				   + " :dept_no, :dept_name, :unit_no, :unit_name, :email, :company_no, "
				   + " :identity_id, :status, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		  
//		String sql = " UPDATE proposition_manage.manager_account "
//				   + " SET NAME = ?, DEPT_NO = ?, DEPT_NAME = ?, "
//				   + " UNIT_NO = ?, UNIT_NAME = ?, EMAIL = ?, COMPANY_NO = ?, "
//				   + " IDENTITY_ID = ?, STATUS = ?, UPDATE_BY = ?, UPDATE_TIME = NOW() "
//				   + " WHERE ID = ? ";
		
		String sql = " UPDATE proposition_manage.manager_account "
				   + " SET NAME = ?, "
				   + " IDENTITY_ID = ?, STATUS = ?, UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(account.getName());
//		args.add(account.getDept_no());
//		args.add(account.getDept_name());
//		args.add(account.getUnit_no());
//		args.add(account.getUnit_name());
//		args.add(account.getEmail());
//		args.add(account.getCompany_no());
		args.add(account.getIdentity_id());
		args.add(account.getStatus());
		args.add(account.getUpdate_by());
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.manager_account "
				   + " WHERE ID = ? ";
		
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
}
