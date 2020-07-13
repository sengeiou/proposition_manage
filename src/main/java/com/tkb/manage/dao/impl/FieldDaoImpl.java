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

import com.tkb.manage.dao.FieldDao;
import com.tkb.manage.model.Education;
import com.tkb.manage.model.Field;

@Repository
public class FieldDaoImpl implements FieldDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(field.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.field "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(field.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Field data(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " WHERE ID = ? ";
		
		args.add(field.getId());
		
		List<Field> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Field>(Field.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Field field) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(field);
		
		String sql = " INSERT INTO proposition_manage.field "
				   + " (UUID, NAME, ABBREVIATION, PARENT_ID, LAYER, SORT, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :name, :abbreviation, :parent_id, "
				   + " :layer, :sort, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.field "
				   + " SET NAME = ?, ABBREVIATION = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(field.getName());
		args.add(field.getAbbreviation());
		args.add(field.getUpdate_by());
		args.add(field.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.field "
				   + " WHERE ID = ? ";
		
		args.add(field.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getChild(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(field.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> backData(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMF1.*, PMF2.NAME AS PARENT_NAME "
				   + " FROM proposition_manage.field PMF1 "
				   + " LEFT JOIN proposition_manage.field PMF2 ON PMF2.ID = PMF1.PARENT_ID "
				   + " WHERE PMF1.ID = ? ";
		
		args.add(field.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer maxSort(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MAX(SORT)+1 AS SORT FROM proposition_manage.field "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(field.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0 && list.get(0).get("SORT")!=null) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return 1;
		}
		
	}
	
	public void updateSortByDelete(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.field "
				   + " SET SORT = SORT-1 "
				   + " WHERE PARENT_ID = ? "
				   + " AND SORT > ? ";
		
		args.add(field.getParent_id());
		args.add(field.getSort());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getList(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " ORDER BY SORT ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> searchName(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " WHERE INSTR(NAME, ?) > 0 ";
		
		args.add(field.getName());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getListInId(Field field) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " WHERE ID IN ("+field.getId()+") ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
}
