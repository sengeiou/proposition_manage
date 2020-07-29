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

import com.tkb.manage.dao.SubjectDao;
import com.tkb.manage.model.Subject;

@Repository
public class SubjectDaoImpl implements SubjectDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.subject "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(subject.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.subject "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(subject.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Subject data(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.subject "
				   + " WHERE ID = ? ";
		
		args.add(subject.getId());
		
		List<Subject> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Subject>(Subject.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Subject subject) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(subject);
		
		String sql = "";
		
		if(!"1".equals(subject.getLayer())) {
			sql = " INSERT INTO proposition_manage.subject "
			    + " (UUID, NAME, CODE, PARENT_ID, LAYER, SORT, "
			    + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
			    + " VALUES(REPLACE(UUID(), '-', ''), :name, :code, :parent_id, "
			    + " :layer, :sort, :create_by, NOW(), :update_by, NOW()) ";
		} else {
			sql = " INSERT INTO proposition_manage.subject "
			    + " (UUID, NAME, CODE, PARENT_ID, LAYER, SORT, "
			    + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
			    + " VALUES(REPLACE(UUID(), '-', ''), :name, "
			    + " (SELECT LPAD(COUNT, 2, 0) FROM (SELECT COUNT(*)+1 AS COUNT FROM proposition_manage.subject WHERE PARENT_ID = 0) L1), "
			    + " :parent_id, "
			    + " :layer, :sort, :create_by, NOW(), :update_by, NOW()) ";
		}
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.subject "
				   + " SET NAME = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(subject.getName());
		args.add(subject.getUpdate_by());
		args.add(subject.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.subject "
				   + " WHERE ID = ? ";
		
		args.add(subject.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getChild(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.subject "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(subject.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> backData(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMF1.*, PMF2.NAME AS PARENT_NAME "
				   + " FROM proposition_manage.subject PMF1 "
				   + " LEFT JOIN proposition_manage.subject PMF2 ON PMF2.ID = PMF1.PARENT_ID "
				   + " WHERE PMF1.ID = ? ";
		
		args.add(subject.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer maxSort(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MAX(SORT)+1 AS SORT FROM proposition_manage.subject "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(subject.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0 && list.get(0).get("SORT")!=null) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return 1;
		}
		
	}
	
	public void updateSortByDelete(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.subject "
				   + " SET SORT = SORT-1 "
				   + " WHERE PARENT_ID = ? "
				   + " AND SORT > ? ";
		
		args.add(subject.getParent_id());
		args.add(subject.getSort());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getList(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.subject "
				   + " WHERE LAYER = ? "
				   + " ORDER BY SORT ";
		
		args.add(subject.getLayer());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> searchName(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.subject "
				   + " WHERE INSTR(NAME, ?) > 0 ";
		
		args.add(subject.getName());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getListInId(Subject subject) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.field "
				   + " WHERE ID IN ("+subject.getId()+") ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
}
