package com.tkb.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.LessonPlanOptionDao;
import com.tkb.manage.model.LessonPlanOption;
import com.tkb.manage.service.LessonPlanOptionService;

@Service
public class LessonPlanOptionServiceImpl implements LessonPlanOptionService {
	
	@Autowired
	private LessonPlanOptionDao lessonPlanOptionDao;
	
	public void add(LessonPlanOption lessonPlanOption) {
		lessonPlanOptionDao.add(lessonPlanOption);
	}
	
	public void delete(LessonPlanOption lessonPlanOption) {
		lessonPlanOptionDao.delete(lessonPlanOption);
	}
	
	public Map<String, Object> option(LessonPlanOption lessonPlanOption) {
		return lessonPlanOptionDao.option(lessonPlanOption);
	}
	
	public Map<String, Object> subjectOption(LessonPlanOption lessonPlanOption) {
		return lessonPlanOptionDao.subjectOption(lessonPlanOption);
	}
	
	public Map<String, Object> gradeOption(LessonPlanOption lessonPlanOption) {
		return lessonPlanOptionDao.gradeOption(lessonPlanOption);
	}
	
}
