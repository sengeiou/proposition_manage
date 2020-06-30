package com.tkb.manage.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.PropositionAuditDao;
import com.tkb.manage.model.PropositionAudit;

@Repository
public class PropositionAuditDaoImpl implements PropositionAuditDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public int add(PropositionAudit propositionAudit) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(propositionAudit);
		
		String sql = " INSERT INTO proposition_manage.proposition_audit"
				   + " (PROPOSITION_ID, AUDITOR, FILE_STATUS, UPLOAD_STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:proposition_id, :auditor, :file_status, :upload_status, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
}
