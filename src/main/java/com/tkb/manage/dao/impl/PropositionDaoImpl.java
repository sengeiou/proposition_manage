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
import com.tkb.manage.model.LessonPlan;
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
				   + " CASE WHEN PMP.FILE_STATUS='A' THEN '初審中' "
				   + " WHEN PMP.FILE_STATUS='B' THEN '初審待修正' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '審核中' "
				   + " WHEN PMP.FILE_STATUS='D' THEN '審核待修正' "
				   + " WHEN PMP.FILE_STATUS='E' THEN '完稿確認' "
				   + " WHEN PMP.FILE_STATUS='F' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE, "
				   + " TA.NAME AS TEACHER_NAME, TA2.NAME AS AUDITOR2_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON TA.ACCOUNT = PMP.CREATE_BY "
				   + " LEFT JOIN proposition_manage.teacher_account TA2 ON TA2.ACCOUNT = PMP.AUDITOR2 "
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
		
		String sql = " SELECT PMP.*, "
				   + " CASE WHEN PMP.FILE_STATUS='A' THEN '初審中' "
				   + " WHEN PMP.FILE_STATUS='B' THEN '初審待修正' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '審核中' "
				   + " WHEN PMP.FILE_STATUS='D' THEN '審核待修正' "
				   + " WHEN PMP.FILE_STATUS='E' THEN '完稿確認' "
				   + " WHEN PMP.FILE_STATUS='F' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE, "
				   + " TA.NAME AS TEACHER_NAME, TA2.NAME AS AUDITOR_NAME, TA3.NAME AS AUDITOR2_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON TA.ACCOUNT = PMP.CREATE_BY "
				   + " LEFT JOIN proposition_manage.teacher_account TA2 ON TA2.ACCOUNT = PMP.AUDITOR "
				   + " LEFT JOIN proposition_manage.teacher_account TA3 ON TA3.ACCOUNT = PMP.AUDITOR2 "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " AND PMP.AUDITOR2 = ? "
				   + " AND PMP.FILE_STATUS = 'C' "
				   + " ORDER BY PMP.CREATE_TIME DESC ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getAuditor2());
		
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
				   + " AND AUDITOR2 = ? "
				   + " AND FILE_STATUS = 'C' ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getAuditor2());
		
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
				   + " CASE WHEN PMP.FILE_STATUS='A' THEN '初審中' "
				   + " WHEN PMP.FILE_STATUS='B' THEN '初審待修正' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '審核中' "
				   + " WHEN PMP.FILE_STATUS='D' THEN '審核待修正' "
				   + " WHEN PMP.FILE_STATUS='E' THEN '完稿確認' "
				   + " WHEN PMP.FILE_STATUS='F' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE, "
				   + " TA.NAME AS TEACHER_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON TA.ACCOUNT = PMP.CREATE_BY "
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
	
	public List<Map<String, Object>> principalList(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, "
				   + " CASE WHEN PMP.FILE_STATUS='A' THEN '初審中' "
				   + " WHEN PMP.FILE_STATUS='B' THEN '初審待修正' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '審核中' "
				   + " WHEN PMP.FILE_STATUS='D' THEN '審核待修正' "
				   + " WHEN PMP.FILE_STATUS='E' THEN '完稿確認' "
				   + " WHEN PMP.FILE_STATUS='F' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE, "
				   + " TA.NAME AS TEACHER_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON TA.ACCOUNT = PMP.CREATE_BY "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " AND PMP.EDUCATION_ID IN ("+proposition.getEducation_id()+") "
				   + " AND PMP.SUBJECT_ID IN ("+proposition.getSubject_id()+") "
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
	
	public Integer principalCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? "
				   + " AND EDUCATION_ID IN ("+proposition.getEducation_id()+") "
				   + " AND SUBJECT_ID IN ("+proposition.getSubject_id()+") ";
		
		args.add(proposition.getQuestion_type());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> leaderList(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, "
				   + " CASE WHEN PMP.FILE_STATUS='A' THEN '初審中' "
				   + " WHEN PMP.FILE_STATUS='B' THEN '初審待修正' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '審核中' "
				   + " WHEN PMP.FILE_STATUS='D' THEN '審核待修正' "
				   + " WHEN PMP.FILE_STATUS='E' THEN '完稿確認' "
				   + " WHEN PMP.FILE_STATUS='F' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE, "
				   + " TA1.NAME AS TEACHER_NAME, "
				   + " TA2.NAME AS AUDITOR_NAME, TA3.NAME AS AUDITOR2_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.teacher_account TA1 ON TA1.ACCOUNT = PMP.CREATE_BY "
				   + " LEFT JOIN proposition_manage.teacher_account TA2 ON TA2.ACCOUNT = PMP.AUDITOR "
				   + " LEFT JOIN proposition_manage.teacher_account TA3 ON TA3.ACCOUNT = PMP.AUDITOR2 "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " AND PMP.EDUCATION_ID IN ("+proposition.getEducation_id()+") "
				   + " AND PMP.SUBJECT_ID IN ("+proposition.getSubject_id()+") "
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
	
	public Integer leaderCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? "
				   + " AND EDUCATION_ID IN ("+proposition.getEducation_id()+") "
				   + " AND SUBJECT_ID IN ("+proposition.getSubject_id()+") ";
		
		args.add(proposition.getQuestion_type());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> secretaryGeneralList(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, "
				   + " CASE WHEN PMP.FILE_STATUS='A' THEN '初審中' "
				   + " WHEN PMP.FILE_STATUS='B' THEN '初審待修正' "
				   + " WHEN PMP.FILE_STATUS='C' THEN '審核中' "
				   + " WHEN PMP.FILE_STATUS='D' THEN '審核待修正' "
				   + " WHEN PMP.FILE_STATUS='E' THEN '完稿確認' "
				   + " WHEN PMP.FILE_STATUS='F' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " DATE_FORMAT(PMP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE, "
				   + " TA1.NAME AS TEACHER_NAME, "
				   + " TA2.NAME AS AUDITOR_NAME, TA3.NAME AS AUDITOR2_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = PMP.FIELD_ID "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.teacher_account TA1 ON TA1.ACCOUNT = PMP.CREATE_BY "
				   + " LEFT JOIN proposition_manage.teacher_account TA2 ON TA2.ACCOUNT = PMP.AUDITOR "
				   + " LEFT JOIN proposition_manage.teacher_account TA3 ON TA3.ACCOUNT = PMP.AUDITOR2 "
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
	
	public Integer secretaryGeneralCount(Proposition proposition) {
		
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
	
	public Integer uploadStatusCount(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.proposition "
				   + " WHERE QUESTION_TYPE = ? "
				   + " AND CREATE_BY = ? ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getCreate_by());
		
		if(proposition.getFile_status() != null) {
			sql += " AND FILE_STATUS = ? ";
			args.add(proposition.getFile_status());
		}
		
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
				   + " (UUID, CONTRACT_ID, PROPOSITION_NUMBER, NAME, EDUCATION_ID, SUBJECT_ID, "
				   + " QUESTION_TYPE, DISPLAY, TAG, AUDITOR, FILE_STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :contract_id, "
				   + " (SELECT CONCAT(:proposition_number, LPAD(COUNT, 3, 0)) FROM (SELECT COUNT(*)+1 AS COUNT FROM proposition_manage.proposition WHERE CONTRACT_ID = :contract_id AND INSTR(PROPOSITION_NUMBER, :proposition_number) > 0) L1), "
				   + " :name, :education_id, "
				   + " :subject_id, :question_type, :display, :tag, :auditor, :file_status, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.proposition "
				   + " SET NAME = ?, TAG = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(proposition.getName());
		args.add(proposition.getTag());
		args.add(proposition.getUpdate_by());
		args.add(proposition.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Proposition data(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMP.*, PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME "
				   + " FROM proposition_manage.proposition PMP "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMP.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMP.SUBJECT_ID "
				   + " WHERE PMP.ID = ? ";
		
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
				   + " SET ";
		
		if(proposition.getAuditor2() != null) {
			sql += " AUDITOR2 = ?, ";
			args.add(proposition.getAuditor2());
		}
		
		sql += " FILE_STATUS = ?, UPDATE_BY = ?, UPDATE_TIME = NOW() "
			+  " WHERE ID = ? ";
		
		args.add(proposition.getFile_status());
		args.add(proposition.getUpdate_by());
		args.add(proposition.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> auditNum(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT  "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'A' THEN 1 ELSE 0 END) A_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'B' THEN 1 ELSE 0 END) B_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'C' THEN 1 ELSE 0 END) C_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'D' THEN 1 ELSE 0 END) D_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'E' THEN 1 ELSE 0 END) E_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'F' THEN 1 ELSE 0 END) F_SUM "
				   + " FROM proposition_manage.proposition P "
				   + " WHERE P.AUDITOR2 = ? "
				   + " AND P.QUESTION_TYPE = ? ";
		
		args.add(proposition.getAuditor2());
		args.add(proposition.getQuestion_type());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getNum(Proposition proposition) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT L1.*, LPAD(L1.RANK, 5, 0) AS NUM FROM ( "
				   + " SELECT PMP.ID, PMP.PROPOSITION_NUMBER, (@incRank := @incRank + 1) AS RANK "
				   + " FROM proposition_manage.proposition PMP, "
				   + " (SELECT @curRank :=0, @prevRank := NULL, @incRank := 0) R "
				   + " WHERE PMP.QUESTION_TYPE = ? "
				   + " AND PMP.EDUCATION_ID = ? "
				   + " AND PMP.SUBJECT_ID = ? "
				   + " ORDER BY PMP.CREATE_TIME "
				   + " ) L1 "
				   + " WHERE L1.ID = ? ";
		
		args.add(proposition.getQuestion_type());
		args.add(proposition.getEducation_id());
		args.add(proposition.getSubject_id());
		args.add(proposition.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
