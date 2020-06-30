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

import com.tkb.manage.dao.EducationDao;
import com.tkb.manage.model.Education;

@Repository
public class EducationDaoImpl implements EducationDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.education "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(education.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.education "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(education.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Education data(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.education "
				   + " WHERE ID = ? ";
		
		args.add(education.getId());
		
		List<Education> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Education>(Education.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Education education) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(education);
		
		String sql = " INSERT INTO proposition_manage.education "
				   + " (UUID, NAME, PARENT_ID, LAYER, SORT, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :name, :parent_id, "
				   + " :layer, :sort, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.education "
				   + " SET NAME = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(education.getName());
		args.add(education.getUpdate_by());
		args.add(education.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.education "
				   + " WHERE ID = ? ";
		
		args.add(education.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getChild(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.education "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(education.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> backData(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMF1.*, PMF2.NAME AS PARENT_NAME "
				   + " FROM proposition_manage.education PMF1 "
				   + " LEFT JOIN proposition_manage.education PMF2 ON PMF2.ID = PMF1.PARENT_ID "
				   + " WHERE PMF1.ID = ? ";
		
		args.add(education.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer maxSort(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MAX(SORT)+1 AS SORT FROM proposition_manage.education "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(education.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0 && list.get(0).get("SORT")!=null) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return 1;
		}
		
	}
	
	public void updateSortByDelete(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.education "
				   + " SET SORT = SORT-1 "
				   + " WHERE PARENT_ID = ? "
				   + " AND SORT > ? ";
		
		args.add(education.getParent_id());
		args.add(education.getSort());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getList(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.education "
				   + " WHERE LAYER = ? "
				   + " ORDER BY SORT ";
		
		args.add(education.getLayer());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> searchName(Education education) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.education "
				   + " WHERE INSTR(NAME, ?) > 0 ";
		
		args.add(education.getName());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
