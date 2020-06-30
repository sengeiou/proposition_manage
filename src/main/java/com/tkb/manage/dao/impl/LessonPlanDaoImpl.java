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

import com.tkb.manage.dao.LessonPlanDao;
import com.tkb.manage.model.LessonPlan;

@Repository
public class LessonPlanDaoImpl implements LessonPlanDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> manageList(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT LP.*, "
				   + " CASE WHEN LP.FILE_STATUS='Y' THEN '已上傳' "
				   + " WHEN LP.FILE_STATUS='N' THEN '待修訂' "
				   + " WHEN LP.FILE_STATUS='C' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " CASE WHEN LP.UPLOAD_STATUS='Y' THEN '待審核' "
				   + " WHEN LP.UPLOAD_STATUS='N' THEN '未通過' "
				   + " WHEN LP.UPLOAD_STATUS='C' THEN '通過' "
				   + " ELSE '' END UPLOAD_STATUS_NAME, "
				   + " PMF.NAME AS FIELD_NAME, "
				   + " DATE_FORMAT(LP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.lesson_plan LP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = LP.FIELD_ID "
				   + " ORDER BY LP.CREATE_TIME DESC ";
		
		sql += " LIMIT "+((lessonPlan.getPage()-1)*lessonPlan.getPage_count())+","+lessonPlan.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer manageCount(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> auditList(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT LP.*, PMF.NAME AS FIELD_NAME, "
				   + " DATE_FORMAT(LP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.lesson_plan LP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = LP.FIELD_ID "
				   + " WHERE LP.AUDITOR = ? "
				   + " AND LP.FILE_STATUS = 'Y' "
				   + " AND LP.UPLOAD_STATUS = 'Y' "
				   + " ORDER BY LP.CREATE_TIME DESC ";
		
		args.add(lessonPlan.getAuditor());
		
		sql += " LIMIT "+((lessonPlan.getPage()-1)*lessonPlan.getPage_count())+","+lessonPlan.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer auditCount(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.lesson_plan "
				   + " WHERE AUDITOR = ? "
				   + " AND FILE_STATUS = 'Y' "
				   + " AND UPLOAD_STATUS = 'Y' ";
		
		args.add(lessonPlan.getAuditor());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> teacherList(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT LP.*, "
				   + " CASE WHEN LP.FILE_STATUS='Y' THEN '已上傳' "
				   + " WHEN LP.FILE_STATUS='N' THEN '待修訂' "
				   + " WHEN LP.FILE_STATUS='C' THEN '完稿' "
				   + " ELSE '' END FILE_STATUS_NAME, "
				   + " CASE WHEN LP.UPLOAD_STATUS='Y' THEN '待審核' "
				   + " WHEN LP.UPLOAD_STATUS='N' THEN '未通過' "
				   + " WHEN LP.UPLOAD_STATUS='C' THEN '通過' "
				   + " ELSE '' END UPLOAD_STATUS_NAME, "
				   + " PMF.NAME AS FIELD_NAME, "
				   + " DATE_FORMAT(LP.CREATE_TIME, '%Y/%m/%d') AS CREATE_DATE "
				   + " FROM proposition_manage.lesson_plan LP "
				   + " LEFT JOIN proposition_manage.field PMF ON PMF.ID = LP.FIELD_ID "
				   + " WHERE LP.CREATE_BY = ? "
				   + " ORDER BY LP.CREATE_TIME DESC ";
		
		args.add(lessonPlan.getCreate_by());
		
		sql += " LIMIT "+((lessonPlan.getPage()-1)*lessonPlan.getPage_count())+","+lessonPlan.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer teacherCount(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.lesson_plan "
				   + " WHERE CREATE_BY = ? ";
		
		args.add(lessonPlan.getCreate_by());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Integer uploadStatusCount(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT "
				   + " FROM proposition_manage.lesson_plan "
				   + " WHERE UPLOAD_STATUS = ? "
				   + " AND CREATE_BY = ? ";
		
		args.add(lessonPlan.getUpload_status());
		args.add(lessonPlan.getCreate_by());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Integer add(LessonPlan lessonPlan) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(lessonPlan);
		
		String sql = " INSERT INTO proposition_manage.lesson_plan "
				   + " (UUID, CONTRACT_ID, NAME, FIELD_ID, EDUCATION_ID, TAG, "
				   + " AUDITOR, FILE_STATUS, UPLOAD_STATUS, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :contract_id, :name, :field_id, :education_id, :tag, "
				   + " :auditor, :file_status, :upload_status, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.lesson_plan "
				   + " SET NAME = ?, FIELD_ID = ?, EDUCATION_ID = ?, TAG = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(lessonPlan.getName());
		args.add(lessonPlan.getField_id());
		args.add(lessonPlan.getEducation_id());
		args.add(lessonPlan.getTag());
		args.add(lessonPlan.getUpdate_by());
		args.add(lessonPlan.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public LessonPlan data(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.lesson_plan "
				   + " WHERE ID = ? ";
		
		args.add(lessonPlan.getId());
		
		List<LessonPlan> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<LessonPlan>(LessonPlan.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public void audit(LessonPlan lessonPlan) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.lesson_plan "
				   + " SET FILE_STATUS = ?, UPLOAD_STATUS = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(lessonPlan.getFile_status());
		args.add(lessonPlan.getUpload_status());
		args.add(lessonPlan.getUpdate_by());
		args.add(lessonPlan.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
}
