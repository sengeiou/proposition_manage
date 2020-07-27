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

import com.tkb.manage.dao.TeacherAccountDao;
import com.tkb.manage.model.Account;

@Repository
public class TeacherAccountDaoImpl implements TeacherAccountDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public Account login(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TA.*, "
				   + " (SELECT GROUP_CONCAT(CODE) FROM proposition_manage.teacher_account_option WHERE TYPE = '1' AND TEACHER_ACCOUNT_ID = TA.ID) SUBJECT_LIST, "
				   + " (SELECT GROUP_CONCAT(CODE) FROM proposition_manage.teacher_account_option WHERE TYPE = '3' AND TEACHER_ACCOUNT_ID = TA.ID) EDUCATION_LIST "
				   + " FROM proposition_manage.teacher_account TA "
				   + " WHERE TA.ACCOUNT = ? "
				   + " AND TA.PASSWORD = ? ";
		
		args.add(account.getAccount());
		args.add(account.getPassword());
		
		List<Account> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Account>(Account.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> list(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TA.*, S.NAME AS SUBJECT_NAME, SM.NAME AS SCHOOL_MASTER_NAME, E.NAME AS EDUCATION_NAME, "
				   + " CASE TA.STATUS WHEN '0' THEN '未通過' WHEN '1' THEN '通過' WHEN '2' THEN '待審核' END AS STATUS_TEXE "
				   + " FROM proposition_manage.teacher_account TA "
				   + " LEFT JOIN proposition_manage.subject S ON S.ID = TA.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.school_master SM ON SM.ID = TA.SCHOOL_MASTER_ID "
				   + " LEFT JOIN proposition_manage.education E ON E.ID = TA.EDUCATION_ID "
				   + " WHERE TA.POSITION = ? ";
		
		args.add(account.getPosition());
		
		if("1".equals(account.getContent_provision()) && "1".equals(account.getContent_audit())) {
			if(account.getContent_provision().equals(account.getContent_audit())) {
				sql += " AND( TA.CONTENT_PROVISION = ? OR TA.CONTENT_AUDIT = ?) ";
			}else {
				sql += " AND( TA.CONTENT_PROVISION = ? AND TA.CONTENT_AUDIT = ?) ";
			}
			args.add(account.getContent_provision());
			args.add(account.getContent_audit());
		}

		
		sql += " LIMIT "+((account.getPage()-1)*account.getPage_count())+","+account.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.teacher_account ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Account data(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TA.*, S.NAME AS SUBJECT_NAME, SM.NAME AS SCHOOL_MASTER_NAME, "
				   + " CASE WHEN TA.CONTENT_PROVISION='0' THEN '否' ELSE '是' END CONTENT_PROVISION_NAME, "
				   + " CASE WHEN TA.CONTENT_AUDIT='0' THEN '否' ELSE '是' END CONTENT_AUDIT_NAME, "
				   + " E.NAME AS EDUCATION_NAME, "
				   + " CASE TA.STATUS WHEN '0' THEN '未通過' WHEN '1' THEN '通過' WHEN '2' THEN '待審核' END AS STATUS_TEXE "
				   + " FROM proposition_manage.teacher_account TA "
				   + " LEFT JOIN proposition_manage.subject S ON S.ID = TA.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.school_master SM ON SM.ID = TA.SCHOOL_MASTER_ID "
				   + " LEFT JOIN proposition_manage.education E ON E.ID = TA.EDUCATION_ID "
				   + " WHERE TA.ID = ? ";
		
		args.add(account.getId());
		
		List<Account> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Account>(Account.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Account account) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(account);
		
		String sql = " INSERT INTO proposition_manage.teacher_account "
				   + " (UUID, ACCOUNT, PASSWORD, NAME, TEACHER_STATUS, SCHOOL_MASTER_ID, "
				   + " ID_NO, MOBILE_PHONE, TELEPHONE, EMAIL, BANK, BRANCH, REMITTANCE_ACCOUNT, "
				   + " EDUCATION_ID, SUBJECT_ID, IDENTITY_ID, POSITION, CONTENT_PROVISION, CONTENT_AUDIT, STATUS, "
				   + " ADDRESS_ZIP, ADDRESS_CITY, ADDRESS_AREA, ADDRESS_ROAD, "
				   + " CENSUS_ZIP, CENSUS_CITY, CENSUS_AREA, CENSUS_ROAD, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :account, :password, :name, "
				   + " :teacher_status, :school_master_id, :id_no, :mobile_phone, :telephone, :email, "
				   + " :bank, :branch, :remittance_account, :education_id, :subject_id, :identity_id, :position, :content_provision, "
				   + " :content_audit, :status, :address_zip, :address_city, :address_area, :address_road, "
				   + " :census_zip, :census_city, :census_area, :census_road, "
				   + " :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.teacher_account "
				   + " SET NAME = ?, TEACHER_STATUS = ?, "
				   + " SCHOOL_MASTER_ID = ?, ID_NO = ?, MOBILE_PHONE = ?, TELEPHONE =?, EMAIL = ?, "
				   + " BANK = ?, BRANCH = ?, REMITTANCE_ACCOUNT = ?, EDUCATION_ID = ?, SUBJECT_ID = ?, "
				   + " IDENTITY_ID = ?, POSITION = ?, CONTENT_PROVISION = ?, CONTENT_AUDIT = ?, "
				   + " ADDRESS_ZIP = ?, ADDRESS_CITY = ?, ADDRESS_AREA = ?, ADDRESS_ROAD = ?, "
				   + " CENSUS_ZIP = ?, CENSUS_CITY = ?, CENSUS_AREA = ?, CENSUS_ROAD = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";

		args.add(account.getName());
		args.add(account.getTeacher_status());
		args.add(account.getSchool_master_id());
		args.add(account.getId_no());
		args.add(account.getMobile_phone());
		args.add(account.getTelephone());
		args.add(account.getEmail());
		args.add(account.getBank());
		args.add(account.getBranch());
		args.add(account.getRemittance_account());
		args.add(account.getEducation_id());
		args.add(account.getSubject_id());
		args.add(account.getIdentity_id());
		args.add(account.getPosition());
		args.add(account.getContent_provision());
		args.add(account.getContent_audit());
		args.add(account.getAddress_zip());
		args.add(account.getAddress_city());
		args.add(account.getAddress_area());
		args.add(account.getAddress_road());
		args.add(account.getCensus_zip());
		args.add(account.getCensus_city());
		args.add(account.getCensus_area());
		args.add(account.getCensus_road());
		args.add(account.getUpdate_by());
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.teacher_account "
				   + " WHERE ID = ? ";
		
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void updateIdentity(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.teacher_account "
				   + " SET IDENTITY_ID = ? "
				   + " WHERE ID = ? ";
		
		args.add(account.getIdentity_id());
		args.add(account.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> getDataByAccount(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.teacher_account "
				   + " WHERE ACCOUNT = ? ";
		
		args.add(account.getAccount());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> callNum(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.teacher_account "
				   + " WHERE CONTENT_AUDIT = '1' "
				   + " AND FIELD_ID = ? "
				   + " AND ID <> ? "
				   + " ORDER BY RAND() ";
		
		args.add(account.getField_id());
		args.add(account.getId());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getAuditor(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM proposition_manage.teacher_account "
				   + " WHERE ID <> ? ";
		
		args.add(account.getId());
		
		if("3".equals(account.getPosition())) {
			sql += " AND POSITION = ? "
				+  " AND EDUCATION_ID = ? "
				+  " AND SUBJECT_ID = ? ";
			args.add(account.getPosition());
			args.add(account.getEducation_id());
			args.add(account.getSubject_id());
		}
		
		if("1".equals(account.getPosition())) {
			sql += " AND POSITION = ? "
				+  " AND EDUCATION_ID = ? "
				+  " AND SUBJECT_ID = ? "
				+  " AND CONTENT_AUDIT = ? ";
			args.add(account.getPosition());
			args.add(account.getEducation_id());
			args.add(account.getSubject_id());
			args.add(account.getContent_audit());
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> verifyList(Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TA.*, S.NAME AS SUBJECT_NAME, SM.NAME AS SCHOOL_MASTER_NAME, E.NAME AS EDUCATION_NAME, "
				   + " CASE TA.STATUS WHEN '0' THEN '未通過' WHEN '1' THEN '通過' WHEN '2' THEN '待審核' END AS STATUS_TEXE "
				   + " FROM proposition_manage.teacher_account TA "
				   + " LEFT JOIN proposition_manage.subject S ON S.ID = TA.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.school_master SM ON SM.ID = TA.SCHOOL_MASTER_ID "
				   + " LEFT JOIN proposition_manage.education E ON E.ID = TA.EDUCATION_ID "
				   + " WHERE TA.POSITION = ? AND TA.STATUS = ? ";
		
		args.add(account.getPosition());
		args.add(account.getStatus());
		
		if(!"".equals(account.getContent_provision()) && !"".equals(account.getContent_audit())) {
			if(account.getContent_provision().equals(account.getContent_audit())) {
				sql += " AND( TA.CONTENT_PROVISION = ? OR TA.CONTENT_AUDIT = ?) ";
			}else {
				sql += " AND( TA.CONTENT_PROVISION = ? AND TA.CONTENT_AUDIT = ?) ";
			}
			args.add(account.getContent_provision());
			args.add(account.getContent_audit());
		}

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
}
