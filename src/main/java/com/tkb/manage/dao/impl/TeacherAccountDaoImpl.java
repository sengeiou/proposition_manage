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

import com.tkb.manage.dao.TeacherAccountDao;
import com.tkb.manage.model.Account;

@Repository
public class TeacherAccountDaoImpl implements TeacherAccountDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public Account login(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.teacher_account "
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
		
		String sql = " SELECT TA.*, PMF.NAME AS FIELD_NAME, SM.NAME AS SCHOOL_MASTER_NAME "
				   + " FROM proposition_manage.teacher_account TA "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = TA.FIELD_ID "
				   + " LEFT JOIN proposition_manage.school_master SM ON SM.ID = TA.SCHOOL_MASTER_ID ";
		
		sql += " LIMIT "+((account.getPage()-1)*account.getPage_count())+","+account.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.teacher_account ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Account data(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TA.*, PMF.NAME AS FIELD_NAME, SM.NAME AS SCHOOL_MASTER_NAME, "
				   + " CASE WHEN TA.CONTENT_PROVISION='0' THEN '否' ELSE '是' END CONTENT_PROVISION_NAME, "
				   + " CASE WHEN TA.CONTENT_AUDIT='0' THEN '否' ELSE '是' END CONTENT_AUDIT_NAME "
				   + " FROM proposition_manage.teacher_account TA "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = TA.FIELD_ID "
				   + " LEFT JOIN proposition_manage.school_master SM ON SM.ID = TA.SCHOOL_MASTER_ID "
				   + " WHERE TA.ID = ? ";
		
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
		
		String sql = " INSERT INTO proposition_manage.teacher_account "
				   + " (UUID, ACCOUNT, PASSWORD, NAME, TEACHER_STATUS, SCHOOL_MASTER_ID, "
				   + " ID_NO, PHONE, EMAIL, ADDRESS, BANK, BRANCH, REMITTANCE_ACCOUNT, "
				   + " FIELD_ID, IDENTITY_ID, CONTENT_PROVISION, CONTENT_AUDIT, STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :account, :password, :name, "
				   + " :teacher_status, :school_master_id, :id_no, :phone, :email, :address, "
				   + " :bank, :branch, :remittance_account, :field_id, :identity_id, :content_provision, "
				   + " :content_audit, :status, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.teacher_account "
				   + " SET NAME = ?, TEACHER_STATUS = ?, "
				   + " SCHOOL_MASTER_ID = ?, ID_NO = ?, PHONE = ?, EMAIL = ?, ADDRESS = ?, "
				   + " BANK = ?, BRANCH = ?, REMITTANCE_ACCOUNT = ?, FIELD_ID = ?, "
				   + " IDENTITY_ID = ?, CONTENT_PROVISION = ?, CONTENT_AUDIT = ?, STATUS = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
//		args.add(account.getAccount());
//		args.add(account.getPassword());
		args.add(account.getName());
		args.add(account.getTeacher_status());
		args.add(account.getSchool_master_id());
		args.add(account.getId_no());
		args.add(account.getPhone());
		args.add(account.getEmail());
		args.add(account.getAddress());
		args.add(account.getBank());
		args.add(account.getBranch());
		args.add(account.getRemittance_account());
		args.add(account.getField_id());
		args.add(account.getIdentity_id());
		args.add(account.getContent_provision());
		args.add(account.getContent_audit());
		args.add(account.getStatus());
		args.add(account.getUpdate_by());
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.teacher_account "
				   + " WHERE ID = ? ";
		
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void updateIdentity(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.teacher_account "
				   + " SET IDENTITY_ID = ? "
				   + " WHERE ID = ? ";
		
		args.add(account.getIdentity_id());
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
}
