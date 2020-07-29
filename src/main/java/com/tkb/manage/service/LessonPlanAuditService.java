package com.tkb.manage.service;

import java.util.List;
import java.util.Map;

import com.tkb.manage.model.LessonPlanAudit;

public interface LessonPlanAuditService {
	
	public int add(LessonPlanAudit lessonPlanAudit);
	public List<Map<String, Object>> historyList(LessonPlanAudit lessonPlanAudit);
	
}
