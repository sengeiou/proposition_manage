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

import com.tkb.manage.dao.IdentityDao;
import com.tkb.manage.model.Identity;

@Repository
public class IdentityDaoImpl implements IdentityDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.identity "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(identity.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.identity "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(identity.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Identity data(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.identity "
				   + " WHERE ID = ? ";
		
		args.add(identity.getId());
		
		List<Identity> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Identity>(Identity.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Identity identity) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(identity);
		
		String sql = " INSERT INTO proposition_manage.identity "
				   + " (UUID, NAME, PARENT_ID, LAYER, SORT, LEVEL, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :name, :parent_id, :layer, "
				   + " :sort, :level, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.identity "
				   + " SET NAME = ?, LEVEL = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(identity.getName());
		args.add(identity.getLevel());
		args.add(identity.getUpdate_by());
		args.add(identity.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.identity "
				   + " WHERE ID = ? ";
		
		args.add(identity.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getChild(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.identity "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(identity.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> backData(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMF1.*, PMF2.NAME AS PARENT_NAME "
				   + " FROM proposition_manage.identity PMF1 "
				   + " LEFT JOIN proposition_manage.identity PMF2 ON PMF2.ID = PMF1.PARENT_ID "
				   + " WHERE PMF1.ID = ? ";
		
		args.add(identity.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer maxSort(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MAX(SORT)+1 AS SORT FROM proposition_manage.identity "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(identity.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0 && list.get(0).get("SORT")!=null) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return 1;
		}
		
	}
	
	public void updateSortByDelete(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.identity "
				   + " SET SORT = SORT-1 "
				   + " WHERE PARENT_ID = ? "
				   + " AND SORT > ? ";
		
		args.add(identity.getParent_id());
		args.add(identity.getSort());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getMenu(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.identity ";
		
		if(identity.getName() != null) {
			sql += " WHERE PARENT_ID = (SELECT ID FROM proposition_manage.identity WHERE NAME = ?) ";
			args.add(identity.getName());
		} else {
			sql += " WHERE PARENT_ID = ? ";
			args.add(identity.getParent_id());
		}
		
		sql += " AND LEVEL >= ? "
			+  " ORDER BY SORT ";
		
		args.add(identity.getLevel());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list = jdbcTemplate.queryForList(sql.toString() , args.toArray());
		
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getDataByLevel(Identity identity) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.identity "
				   + " WHERE LEVEL = ? ";
		
		args.add(identity.getLevel());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list = jdbcTemplate.queryForList(sql.toString() , args.toArray());
		
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
