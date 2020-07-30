package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.PropositionFileDao;
import com.tkb.manage.model.PropositionFile;

@Repository
public class PropositionFileDaoImpl implements PropositionFileDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public int add(PropositionFile propositionFile) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(propositionFile);
		
		String sql = " INSERT INTO proposition_manage.proposition_file"
				   + " (PROPOSITION_ID, PROPOSITION_AUDIT_ID, DATA_TYPE, MATERIAL_TYPE_ID, "
				   + " MATERIAL_LINK, UPLOAD_NAME, FILE_NAME, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:proposition_id, :proposition_audit_id, :data_type, "
				   + " :material_type_id, :material_link, "
				   + " :upload_name, :file_name, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public List<Map<String, Object>> historyList(PropositionFile propositionFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, DATE_FORMAT(CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.proposition_file "
				   + " WHERE PROPOSITION_ID = ? ";
		
		args.add(propositionFile.getProposition_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
public List<Map<String, Object>> getFile(PropositionFile propositionFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM ( "
				   + " SELECT * FROM proposition_manage.proposition_file "
				   + " WHERE DATA_TYPE = 1 "
				   + " AND PROPOSITION_ID = ? "
				   + " ORDER BY UPDATE_TIME DESC "
				   + " LIMIT 0, 1 "
				   + " ) L1 "
				   + " UNION ALL "
				   + " SELECT * FROM ( "
				   + " SELECT * FROM proposition_manage.proposition_file "
				   + " WHERE DATA_TYPE = 2 "
				   + " AND PROPOSITION_ID = ? "
				   + " ORDER BY UPDATE_TIME DESC "
				   + " LIMIT 0, 1 "
				   + " ) L2 ";
		
		args.add(propositionFile.getProposition_id());
		args.add(propositionFile.getProposition_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}

	public List<Map<String, Object>> getAnnex(PropositionFile propositionFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.proposition_file "
				   + " WHERE DATA_TYPE = 3 "
				   + " AND PROPOSITION_ID = ? ";
		
		args.add(propositionFile.getProposition_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getMaterial(PropositionFile propositionFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.proposition_file "
				   + " WHERE DATA_TYPE = 4 "
				   + " AND PROPOSITION_ID = ? ";
		
		args.add(propositionFile.getProposition_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
}
