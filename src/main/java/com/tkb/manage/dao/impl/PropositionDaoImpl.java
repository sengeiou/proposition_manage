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

import com.tkb.manage.dao.PropositionDao;
import com.tkb.manage.model.Proposition;

@Repository
public class PropositionDaoImpl implements PropositionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> manageList(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, "
				   + " CASE WHEN PMP.FILE_STATUS='Y' THEN '已上傳' "
				   + " WHEN PMP.FILE_STATUS='N' THEN '待修訂' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " CASE WHEN PMP.UPLOAD_STATUS='Y' THEN '待審核' "
				   + " WHEN PMP.UPLOAD_STATUS='N' THEN '未通過' "
				   + " WHEN PMP.UPLOAD_STATUS='C' THEN '通過' "
				   + " ELSE '' END UPLOAD_STATUS_NAME, "
				   + " PMF.NAME AS FIELD_NAME, PME.NAME AS EDUCATION_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = PMP.FIELD_ID "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " ORDER BY PMP.CREATE_TIME DESC ";
		
		args.add(proposition.getQuestion_type());
		
		sql += " LIMIT "+((proposition.getPage()-1)*proposition.getPage_count())+","+proposition.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer manageCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? ";
		
		args.add(proposition.getQuestion_type());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> auditList(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, PMF.NAME AS FIELD_NAME, PME.NAME AS EDUCATION_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = PMP.FIELD_ID "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " AND PMP.AUDITOR = ? "
				   + " AND PMP.FILE_STATUS = 'Y' "
				   + " AND PMP.UPLOAD_STATUS = 'Y' "
				   + " ORDER BY PMP.CREATE_TIME DESC ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getAuditor());
		
		sql += " LIMIT "+((proposition.getPage()-1)*proposition.getPage_count())+","+proposition.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer auditCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? "
				   + " AND AUDITOR = ? "
				   + " AND FILE_STATUS = 'Y' "
				   + " AND UPLOAD_STATUS = 'Y' ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getAuditor());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> teacherList(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, "
				   + " CASE WHEN PMP.FILE_STATUS='Y' THEN '已上傳' "
				   + " WHEN PMP.FILE_STATUS='N' THEN '待修訂' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " CASE WHEN PMP.UPLOAD_STATUS='Y' THEN '待審核' "
				   + " WHEN PMP.UPLOAD_STATUS='N' THEN '未通過' "
				   + " WHEN PMP.UPLOAD_STATUS='C' THEN '通過' "
				   + " ELSE '' END UPLOAD_STATUS_NAME, "
				   + " PMF.NAME AS FIELD_NAME, PME.NAME AS EDUCATION_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = PMP.FIELD_ID "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " AND PMP.CREATE_BY = ? "
				   + " ORDER BY PMP.CREATE_TIME DESC ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getCreate_by());
		
		sql += " LIMIT "+((proposition.getPage()-1)*proposition.getPage_count())+","+proposition.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer teacherCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? "
				   + " AND CREATE_BY = ? ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getCreate_by());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Integer uploadStatusCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? "
				   + " AND UPLOAD_STATUS = ? "
				   + " AND CREATE_BY = ? ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getUpload_status());
		args.add(proposition.getCreate_by());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Integer add(Proposition proposition) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(proposition);
		
		String sql = " INSERT INTO proposition_manage.proposition "
				   + " (UUID, CONTRACT_ID, NAME, FIELD_ID, EDUCATION_ID, QUESTION_TYPE, "
				   + " TAG, AUDITOR, FILE_STATUS, UPLOAD_STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :contract_id, :name, :field_id,  "
				   + " :education_id, :question_type, :tag, :auditor, :file_status, :upload_status, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.proposition "
				   + " SET NAME = ?, FIELD_ID = ?, EDUCATION_ID = ?, TAG = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(proposition.getName());
		args.add(proposition.getField_id());
		args.add(proposition.getEducation_id());
		args.add(proposition.getTag());
		args.add(proposition.getUpdate_by());
		args.add(proposition.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Proposition data(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.proposition "
				   + " WHERE ID = ? ";
		
		args.add(proposition.getId());
		
		List<Proposition> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Proposition>(Proposition.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public void audit(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.proposition "
				   + " SET FILE_STATUS = ?, UPLOAD_STATUS = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(proposition.getFile_status());
		args.add(proposition.getUpload_status());
		args.add(proposition.getUpdate_by());
		args.add(proposition.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
}
