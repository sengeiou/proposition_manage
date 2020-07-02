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

import com.tkb.manage.dao.SchoolMasterDao;
import com.tkb.manage.model.SchoolMaster;

@Repository
public class SchoolMasterDaoImpl implements SchoolMasterDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, "
				   + " CASE WHEN TYPE = '1' THEN '國小' "
				   + " WHEN TYPE = '2' THEN '國中' "
				   + " WHEN TYPE = '3' THEN '高中' "
				   + " WHEN TYPE = '4' THEN '大專院校' END TYPE_NAME, "
				   + " CASE WHEN SCHOOL_TYPE = '1' THEN '公立' ELSE '私立' END SCHOOL_TYPE_NAME, "
				   + " CASE WHEN DISPLAY = '0' THEN '否' ELSE '是' END DISPLAY_NAME "
				   + " FROM proposition_manage.school_master ";
		
		sql += " LIMIT "+((schoolMaster.getPage()-1)*schoolMaster.getPage_count())+","+schoolMaster.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.school_master ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public SchoolMaster data(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.school_master "
				   + " WHERE ID = ? ";
		
		args.add(schoolMaster.getId());
		
		List<SchoolMaster> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<SchoolMaster>(SchoolMaster.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(SchoolMaster schoolMaster) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(schoolMaster);
		
		String sql = " INSERT INTO proposition_manage.school_master "
				   + " (UUID, TYPE, CODE, NAME, SCHOOL_TYPE, COUNTRY_NAME, ADDRESS, "
				   + " TELPHONE, WEBSITE, SYSTEM_TYPE, DISPLAY, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :type, :code, :name, :school_type, "
				   + " :country_name, :address, :telphone, :website, :system_type, :display, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.school_master "
				   + " SET TYPE = ?, CODE = ?, NAME = ?, SCHOOL_TYPE = ?, COUNTRY_NAME = ?, "
				   + " ADDRESS = ?, TELPHONE = ?, WEBSITE = ?, SYSTEM_TYPE = ?, DISPLAY = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(schoolMaster.getType());
		args.add(schoolMaster.getCode());
		args.add(schoolMaster.getName());
		args.add(schoolMaster.getSchool_type());
		args.add(schoolMaster.getCountry_name());
		args.add(schoolMaster.getAddress());
		args.add(schoolMaster.getTelphone());
		args.add(schoolMaster.getWebsite());
		args.add(schoolMaster.getSystem_type());
		args.add(schoolMaster.getDisplay());
		args.add(schoolMaster.getUpdate_by());
		args.add(schoolMaster.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.school_master "
				   + " WHERE ID = ? ";
		
		args.add(schoolMaster.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public List<Map<String, Object>> getList(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, "
				   + " CASE WHEN TYPE = '1' THEN '國小' "
				   + " WHEN TYPE = '2' THEN '國中' "
				   + " WHEN TYPE = '3' THEN '高中' "
				   + " WHEN TYPE = '4' THEN '大專院校' END TYPE_NAME, "
				   + " CASE WHEN SCHOOL_TYPE = '1' THEN '公立' ELSE '私立' END SCHOOL_TYPE_NAME, "
				   + " CASE WHEN DISPLAY = '0' THEN '否' ELSE '是' END DISPLAY_NAME "
				   + " FROM proposition_manage.school_master ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> searchName(SchoolMaster schoolMaster) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.school_master "
				   + " WHERE INSTR(COUNTRY_NAME, ?) > 0 "
				   + " AND INSTR(NAME, ?) > 0 ";
		
		args.add(schoolMaster.getCountry_name());
		args.add(schoolMaster.getName());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
