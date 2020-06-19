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

import com.tkb.manage.dao.FunctionDao;
import com.tkb.manage.model.Function;

@Repository
public class FunctionDaoImpl implements FunctionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.function "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(function.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.function "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(function.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Function data(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.function "
				   + " WHERE ID = ? ";
		
		args.add(function.getId());
		
		List<Function> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Function>(Function.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Function function) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(function);
		
		String sql = " INSERT INTO proposition_manage.function "
				   + " (UUID, NAME, PARENT_ID, LAYER, SORT, LEVEL, LINK, ICON, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :name, :parent_id, :layer, "
				   + " :sort, :level, :link, :icon, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.function "
				   + " SET NAME = ?, LEVEL = ?, LINK = ?, ICON = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(function.getName());
		args.add(function.getLevel());
		args.add(function.getLink());
		args.add(function.getIcon());
		args.add(function.getUpdate_by());
		args.add(function.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.function "
				   + " WHERE ID = ? ";
		
		args.add(function.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getChild(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.function "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(function.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> backData(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMF1.*, PMF2.NAME AS PARENT_NAME "
				   + " FROM proposition_manage.function PMF1 "
				   + " LEFT JOIN proposition_manage.function PMF2 ON PMF2.ID = PMF1.PARENT_ID "
				   + " WHERE PMF1.ID = ? ";
		
		args.add(function.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer maxSort(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MAX(SORT)+1 AS SORT FROM proposition_manage.function "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(function.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0 && list.get(0).get("SORT")!=null) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return 1;
		}
		
	}
	
	public void updateSortByDelete(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.function "
				   + " SET SORT = SORT-1 "
				   + " WHERE PARENT_ID = ? "
				   + " AND SORT > ? ";
		
		args.add(function.getParent_id());
		args.add(function.getSort());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getMenu(Function function) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.function ";
		
		if(function.getName() != null) {
			sql += " WHERE PARENT_ID = (SELECT ID FROM proposition_manage.function WHERE NAME = ?) ";
			args.add(function.getName());
		} else {
			sql += " WHERE PARENT_ID = ? ";
			args.add(function.getParent_id());
		}
		
		sql += " AND LEVEL >= ? "
			+  " ORDER BY SORT ";
		
		args.add(function.getLevel());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list = jdbcTemplate.queryForList(sql.toString() , args.toArray());
		
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
}
