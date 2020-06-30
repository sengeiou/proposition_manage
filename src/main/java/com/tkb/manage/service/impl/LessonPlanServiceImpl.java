package com.tkb.manage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.LessonPlanDao;
import com.tkb.manage.model.LessonPlan;
import com.tkb.manage.service.LessonPlanService;

@Service
public class LessonPlanServiceImpl implements LessonPlanService {
	
	@Autowired
	private LessonPlanDao lessonPlanDao;
	
	public List<Map<String, Object>> manageList(LessonPlan lessonPlan) {
		return lessonPlanDao.manageList(lessonPlan);
	}
	
	public Integer manageCount(LessonPlan lessonPlan) {
		return lessonPlanDao.manageCount(lessonPlan);
	}
	
	public List<Map<String, Object>> auditList(LessonPlan lessonPlan) {
		return lessonPlanDao.auditList(lessonPlan);
	}
	
	public Integer auditCount(LessonPlan lessonPlan) {
		return lessonPlanDao.auditCount(lessonPlan);
	}
	
	public List<Map<String, Object>> teacherList(LessonPlan lessonPlan) {
		return lessonPlanDao.teacherList(lessonPlan);
	}
	
	public Integer teacherCount(LessonPlan lessonPlan) {
		return lessonPlanDao.teacherCount(lessonPlan);
	}
	
	public Integer uploadStatusCount(LessonPlan lessonPlan) {
		return lessonPlanDao.uploadStatusCount(lessonPlan);
	}
	
	public Integer add(LessonPlan lessonPlan) {
		return lessonPlanDao.add(lessonPlan);
	}
	
	public void update(LessonPlan lessonPlan) {
		lessonPlanDao.update(lessonPlan);
	}
	
	public LessonPlan data(LessonPlan lessonPlan) {
		return lessonPlanDao.data(lessonPlan);
	}
	
	public void audit(LessonPlan lessonPlan) {
		lessonPlanDao.audit(lessonPlan);
	}
	
}
