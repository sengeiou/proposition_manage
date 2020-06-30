package com.tkb.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.LessonPlanAuditDao;
import com.tkb.manage.model.LessonPlanAudit;
import com.tkb.manage.service.LessonPlanAuditService;

@Service
public class LessonPlanAuditServiceImpl implements LessonPlanAuditService {
	
	@Autowired
	private LessonPlanAuditDao lessonPlanAuditDao;
	
	public int add(LessonPlanAudit lessonPlanAudit) {
		return lessonPlanAuditDao.add(lessonPlanAudit);
	}
	
}
