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

import com.tkb.manage.dao.BankMasterDao;
import com.tkb.manage.model.BankMaster;

@Repository
public class BankMasterDaoImpl implements BankMasterDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(BankMaster bankMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, "
				   + " CASE WHEN DISPLAY = '0' THEN '關閉' ELSE '開啟' END AS DISPLAY_NAME "
				   + " FROM proposition_manage.bank_master ";
		
		sql += " LIMIT "+((bankMaster.getPage()-1)*bankMaster.getPage_count())+","+bankMaster.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(BankMaster bankMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.bank_master ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public BankMaster data(BankMaster bankMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.bank_master "
				   + " WHERE ID = ? ";
		
		args.add(bankMaster.getId());
		
		List<BankMaster> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<BankMaster>(BankMaster.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(BankMaster bankMaster) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(bankMaster);
		
		String sql = " INSERT INTO proposition_manage.bank_master "
				   + " (UUID, CODE, NAME, ADDRESS, SORT, DISPLAY, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :code, :name, "
				   + " :address, :sort, "
				   + " :display, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(BankMaster bankMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.bank_master "
				   + " SET CODE = ?, NAME = ?,  "
				   + " ADDRESS = ?, SORT = ?, DISPLAY = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(bankMaster.getCode());
		args.add(bankMaster.getName());
		args.add(bankMaster.getAddress());
		args.add(bankMaster.getSort());
		args.add(bankMaster.getDisplay());
		args.add(bankMaster.getUpdate_by());
		args.add(bankMaster.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(BankMaster bankMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.bank_master "
				   + " WHERE ID = ? ";
		
		args.add(bankMaster.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getList(BankMaster bankMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * "
				   + " FROM proposition_manage.bank_master ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer nextSort() {
		
		String sql = " SELECT IFNULL(MAX(SORT), 0)+1 AS SORT "
				   + " FROM proposition_manage.bank_master ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return null;
		}
		
	}
	
}
