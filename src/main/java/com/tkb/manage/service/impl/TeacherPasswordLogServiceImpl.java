package com.tkb.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tkb.manage.dao.TeacherPasswordLogDao;
import com.tkb.manage.model.TeacherPasswordLog;
import com.tkb.manage.service.TeacherPasswordLogService;

@Service
public class TeacherPasswordLogServiceImpl implements TeacherPasswordLogService {
	
	@Autowired
	private TeacherPasswordLogDao teacherPasswordLogDao;
	
	
	public TeacherPasswordLog data(TeacherPasswordLog teacherPasswordLog) {
		return teacherPasswordLogDao.data(teacherPasswordLog);
	}
	
	public int add(TeacherPasswordLog teacherPasswordLog) {
		return teacherPasswordLogDao.add(teacherPasswordLog);
	}
	
	public void update(TeacherPasswordLog teacherPasswordLog) {
		teacherPasswordLogDao.update(teacherPasswordLog);
	}
	
	public TeacherPasswordLog dataBy1Hour(TeacherPasswordLog teacherPasswordLog) {
		return teacherPasswordLogDao.dataBy1Hour(teacherPasswordLog);
	}
	
}
