package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.PropositionOptionDao;
import com.tkb.manage.model.PropositionOption;

@Repository
public class PropositionOptionDaoImpl implements PropositionOptionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void add(PropositionOption propositionOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " INSERT INTO proposition_manage.proposition_option(PROPOSITION_ID, TYPE, "
				   + " CODE, CREATE_BY, CREATE_TIME) "
				   + " VALUES(?, ?, ?, ?, NOW()) ";
		
		args.add(propositionOption.getProposition_id());
		args.add(propositionOption.getType());
		args.add(propositionOption.getCode());
		args.add(propositionOption.getCreate_by());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(PropositionOption propositionOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.proposition_option "
				   + " WHERE PROPOSITION_ID = ? ";
		
		args.add(propositionOption.getProposition_id());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> option(PropositionOption propositionOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(CODE) AS OPTION "
				   + " FROM proposition_manage.proposition_option "
				   + " WHERE PROPOSITION_ID = ? "
				   + " AND TYPE = ? ";
		
		args.add(propositionOption.getProposition_id());
		args.add(propositionOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> subjectOption(PropositionOption propositionOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PMS.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.proposition_option PO "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = CODE "
				   + " WHERE PO.PROPOSITION_ID = ? "
				   + " AND PO.TYPE = ? ";
		
		args.add(propositionOption.getProposition_id());
		args.add(propositionOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> gradeOption(PropositionOption propositionOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PME.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.proposition_option PO "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = CODE "
				   + " WHERE PO.PROPOSITION_ID = ? "
				   + " AND PO.TYPE = ? ";
		
		args.add(propositionOption.getProposition_id());
		args.add(propositionOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
