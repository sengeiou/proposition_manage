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

import com.tkb.manage.dao.MaterialTypeDao;
import com.tkb.manage.model.MaterialType;

@Repository
public class MaterialTypeDaoImpl implements MaterialTypeDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.material_type "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(materialType.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.material_type "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(materialType.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public MaterialType data(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.material_type "
				   + " WHERE ID = ? ";
		
		args.add(materialType.getId());
		
		List<MaterialType> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<MaterialType>(MaterialType.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(MaterialType materialType) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(materialType);
		
		String sql = " INSERT INTO proposition_manage.material_type "
				   + " (UUID, NAME, PARENT_ID, LAYER, SORT, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :name, :parent_id, "
				   + " :layer, :sort, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.material_type "
				   + " SET NAME = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(materialType.getName());
		args.add(materialType.getUpdate_by());
		args.add(materialType.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.material_type "
				   + " WHERE ID = ? ";
		
		args.add(materialType.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getChild(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.material_type "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(materialType.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> backData(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMF1.*, PMF2.NAME AS PARENT_NAME "
				   + " FROM proposition_manage.material_type PMF1 "
				   + " LEFT JOIN proposition_manage.material_type PMF2 ON PMF2.ID = PMF1.PARENT_ID "
				   + " WHERE PMF1.ID = ? ";
		
		args.add(materialType.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer maxSort(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT MAX(SORT)+1 AS SORT FROM proposition_manage.material_type "
				   + " WHERE PARENT_ID = ? ";
		
		args.add(materialType.getParent_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0 && list.get(0).get("SORT")!=null) {
			return Integer.valueOf(list.get(0).get("SORT").toString());
		} else {
			return 1;
		}
		
	}
	
	public void updateSortByDelete(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.material_type "
				   + " SET SORT = SORT-1 "
				   + " WHERE PARENT_ID = ? "
				   + " AND SORT > ? ";
		
		args.add(materialType.getParent_id());
		args.add(materialType.getSort());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getList(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.material_type "
				   + " WHERE LAYER = ? "
				   + " ORDER BY SORT ";
		
		args.add(materialType.getLayer());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> searchName(MaterialType materialType) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.material_type "
				   + " WHERE INSTR(NAME, ?) > 0 ";
		
		args.add(materialType.getName());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
