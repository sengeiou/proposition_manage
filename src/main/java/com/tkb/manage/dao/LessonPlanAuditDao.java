package com.tkb.manage.dao;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.LessonPlanAudit;

public interface LessonPlanAuditDao {
	
	public int add(LessonPlanAudit lessonPlanAudit);
	public List<Map<String, Object>> historyList(LessonPlanAudit lessonPlanAudit);
	
}
