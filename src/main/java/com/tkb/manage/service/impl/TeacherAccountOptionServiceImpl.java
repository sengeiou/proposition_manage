package com.tkb.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.TeacherAccountOptionDao;
import com.tkb.manage.model.TeacherAccountOption;
import com.tkb.manage.service.TeacherAccountOptionService;

@Service
public class TeacherAccountOptionServiceImpl implements TeacherAccountOptionService {
	
	@Autowired
	private TeacherAccountOptionDao teacherAccountOptionDao;
	
	public void add(TeacherAccountOption teacherAccountOption) {
		teacherAccountOptionDao.add(teacherAccountOption);
	}
	
	public void delete(TeacherAccountOption teacherAccountOption) {
		teacherAccountOptionDao.delete(teacherAccountOption);
	}
	
	public Map<String, Object> option(TeacherAccountOption teacherAccountOption) {
		return teacherAccountOptionDao.option(teacherAccountOption);
	}
	
	public Map<String, Object> educationOption(TeacherAccountOption teacherAccountOption) {
		return teacherAccountOptionDao.educationOption(teacherAccountOption);
	}
	
	public Map<String, Object> fieldOption(TeacherAccountOption teacherAccountOption) {
		return teacherAccountOptionDao.fieldOption(teacherAccountOption);
	}
	
}
