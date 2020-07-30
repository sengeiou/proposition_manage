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

import com.tkb.manage.dao.LessonPlanFileDao;
import com.tkb.manage.model.LessonPlanFile;

@Repository
public class LessonPlanFileDaoImpl implements LessonPlanFileDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public int add(LessonPlanFile lessonPlanFile) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(lessonPlanFile);
		
		String sql = " INSERT INTO proposition_manage.lesson_plan_file"
				   + " (LESSON_PLAN_ID, LESSON_PLAN_AUDIT_ID, DATA_TYPE, MATERIAL_TYPE_ID, "
				   + " MATERIAL_LINK, UPLOAD_NAME, FILE_NAME, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(:lesson_plan_id, :lesson_plan_audit_id, :data_type, :material_type_id, "
				   + " :material_link, :upload_name, :file_name, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
				
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public List<Map<String, Object>> historyList(LessonPlanFile lessonPlanFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT *, DATE_FORMAT(CREATE_TIME, '%Y-%m-%d') AS CREATE_DATE "
				   + " FROM proposition_manage.lesson_plan_file "
				   + " WHERE LESSON_PLAN_ID = ? ";
		
		args.add(lessonPlanFile.getLesson_plan_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getFile(LessonPlanFile lessonPlanFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM ( "
				   + " SELECT * FROM proposition_manage.lesson_plan_file "
				   + " WHERE DATA_TYPE = 1 "
				   + " AND LESSON_PLAN_ID = ? "
				   + " ORDER BY UPDATE_TIME DESC "
				   + " LIMIT 0, 1 "
				   + " ) L1 "
				   + " UNION ALL "
				   + " SELECT * FROM ( "
				   + " SELECT * FROM proposition_manage.lesson_plan_file "
				   + " WHERE DATA_TYPE = 2 "
				   + " AND LESSON_PLAN_ID = ? "
				   + " ORDER BY UPDATE_TIME DESC "
				   + " LIMIT 0, 1 "
				   + " ) L2 ";
		
		args.add(lessonPlanFile.getLesson_plan_id());
		args.add(lessonPlanFile.getLesson_plan_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}

	public List<Map<String, Object>> getAnnex(LessonPlanFile lessonPlanFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.lesson_plan_file "
				   + " WHERE DATA_TYPE = 3 "
				   + " AND LESSON_PLAN_ID = ? ";
		
		args.add(lessonPlanFile.getLesson_plan_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getMaterial(LessonPlanFile lessonPlanFile) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.lesson_plan_file "
				   + " WHERE DATA_TYPE = 4 "
				   + " AND LESSON_PLAN_ID = ? ";
		
		args.add(lessonPlanFile.getLesson_plan_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	
}
