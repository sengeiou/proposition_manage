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

import com.tkb.manage.dao.ContractDao;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.Contract;

@Repository
public class ContractDaoImpl implements ContractDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
//		String sql = " SELECT A.*, "
//				   + " IF(A.LESSON_NUM = A.L_COUNT_FILE AND A.LESSON_NUM = A.L_COUNT, 1, 0) L1, "
//				   + " IF(A.BASIC_NUM = A.P_COUNT_FILE AND A.BASIC_NUM = A.P_COUNT_UP, 1, 0) P1, "
//				   + " IF(A.QUESTIONS_GROUP_NUM = A.P_COUNT_FILE2 AND A.QUESTIONS_GROUP_NUM = A.P_COUNT_UP2, 1, 0) P2 FROM ( "
//				   + " SELECT C.ID, IF(C.TKB_CONTRACT_NUM IS NOT NULL AND C.TKB_CONTRACT_NUM <> '', 1, 0) AS TKB_CONTRACT_STATUS, "
//				   + " IF(C.CSOFE_CONTRACT_NUM IS NOT NULL AND C.CSOFE_CONTRACT_NUM <> '', 1, 0) AS CSOFE_CONTRACT_STATUS, "
//				   + " C.CONTRACT_ID, TA.NAME, "
//				   + " IF(NOW() BETWEEN BEGIN_DATE AND END_DATE, '有效', '過期') AS CONTRACT_DATE, "
//				   + " LESSON_NUM, BASIC_NUM, QUESTIONS_GROUP_NUM, "
//				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE C.CONTRACT_ID = LP.CONTRACT_ID AND FILE_STATUS = 'Y') AS L_COUNT_FILE, "
//				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE C.CONTRACT_ID = LP.CONTRACT_ID AND FILE_STATUS <> 'Y') AS L_COUNT, "
//				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE C.CONTRACT_ID = P.CONTRACT_ID AND QUESTION_TYPE = 1 AND FILE_STATUS = 'Y') AS P_COUNT_FILE, "
//				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE C.CONTRACT_ID = P.CONTRACT_ID AND QUESTION_TYPE = 1 AND FILE_STATUS <> 'Y') AS P_COUNT_UP, "
//				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE C.CONTRACT_ID = P.CONTRACT_ID AND QUESTION_TYPE = 2 AND FILE_STATUS = 'Y') AS P_COUNT_FILE2, "
//				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE C.CONTRACT_ID = P.CONTRACT_ID AND QUESTION_TYPE = 2 AND FILE_STATUS <> 'Y') AS P_COUNT_UP2 "
//				   + " FROM proposition_manage.contract C "
//				   + " LEFT JOIN proposition_manage.teacher_account TA ON C.TEACHER_ID = TA.ID "
//				   + " ) A ";
		
//		String sql = " SELECT PMC.*, TA.NAME AS TEACHER_NAME, "
//				   + " COUNT(LP1.ID) AS LP1_COUNT, SUM(IF(LP2.ID IS NULL, 0, 1)) LP2_SUM, "
//				   + " COUNT(PMP1.ID) PMP1_COUNT, SUM(IF(PMP2.ID IS NULL, 0, 1)) PMP2_SUM, "
//				   + " COUNT(PMP3.ID) PMP3_COUNT, SUM(IF(PMP4.ID IS NULL, 0, 1)) PMP4_SUM, "
//				   + " (PMC.LESSON_NUM-COUNT(LP1.ID)+PMC.LESSON_NUM-SUM(IF(LP2.ID IS NULL, 0, 1))+PMC.BASIC_NUM-COUNT(PMP1.ID)+PMC.BASIC_NUM-SUM(IF(PMP2.ID IS NULL, 0, 1))+PMC.QUESTIONS_GROUP_NUM-COUNT(PMP3.ID)+PMC.QUESTIONS_GROUP_NUM-SUM(IF(PMP4.ID IS NULL, 0, 1))) AS UNDONE_NUM, "
//				   + " IF(DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN DATE_FORMAT(PMC.BEGIN_DATE, '%Y%m%d') AND DATE_FORMAT(PMC.END_DATE, '%Y%m%d'), '有效', '過期') AS CONTRACT_LIMIT "
//				   + " FROM proposition_manage.contract PMC "
//				   + " LEFT JOIN proposition_manage.lesson_plan LP1 ON LP1.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.lesson_plan LP2 ON LP2.FILE_STATUS = 'C' AND LP2.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP1 ON PMP1.QUESTION_TYPE = '1' AND PMP1.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP2 ON PMP2.QUESTION_TYPE = '1' AND PMP2.FILE_STATUS = 'C' AND PMP2.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP3 ON PMP3.QUESTION_TYPE = '2' AND PMP3.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP4 ON PMP4.QUESTION_TYPE = '2' AND PMP4.FILE_STATUS = 'C' AND PMP4.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.teacher_account TA ON PMC.TEACHER_ID = TA.ID ";
		
		String sql = " SELECT L1.*, "
				   +  " (LESSON_NUM-LP1_COUNT+LESSON_NUM-LP2_SUM+BASIC_NUM-PMP1_COUNT+BASIC_NUM-PMP2_SUM+QUESTIONS_GROUP_NUM-PMP3_COUNT+QUESTIONS_GROUP_NUM-PMP4_SUM) AS UNDONE_NUM "
				   +  " FROM ( "
				   +  " SELECT PMC.*, TA.NAME AS TEACHER_NAME, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID) AS LP1_COUNT, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'C') AS LP2_SUM, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 1) AS PMP1_COUNT, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 1 AND P.FILE_STATUS = 'C') AS PMP2_SUM, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 2) AS PMP3_COUNT, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 2 AND P.FILE_STATUS = 'C') AS PMP4_SUM, "
				   +  " IF(DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN DATE_FORMAT(PMC.BEGIN_DATE, '%Y%m%d') AND DATE_FORMAT(PMC.END_DATE, '%Y%m%d'), '有效', '過期') AS CONTRACT_LIMIT "
				   +  " FROM proposition_manage.contract PMC "
				   +  " LEFT JOIN proposition_manage.teacher_account TA ON PMC.TEACHER_ID = TA.ID "
				   +  " ) L1 ";
		
		if(contract.getTeacher_id() != null) {
			sql += " WHERE TEACHER_ID = ? ";
			args.add(contract.getTeacher_id());
		}
		
//		sql += " GROUP BY PMC.CONTRACT_ID "
//			+  " LIMIT "+((contract.getPage()-1)*contract.getPage_count())+","+contract.getPage_count();
		sql += " ORDER BY CREATE_TIME DESC "
			+  " LIMIT "+((contract.getPage()-1)*contract.getPage_count())+","+contract.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.contract ";
		
		if(contract.getTeacher_id() != null) {
			sql += " WHERE TEACHER_ID = ? ";
			args.add(contract.getTeacher_id());
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Contract data(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMC.ID, PMC.UUID, PMC.TEACHER_ID, PMC.TKB_CONTRACT_NUM, PMC.TKB_CONTRACT_FILE,"
				   + " PMC.TKB_CONTRACT_NAME, PMC.TKB_PARTYA, PMC.TKB_PARTYB, PMC.CSOFE_CONTRACT_NUM, PMC.CSOFE_CONTRACT_FILE, "
				   + " PMC.CSOFE_CONTRACT_NAME, PMC.CSOFE_PARTYA, PMC.CSOFE_PARTYB, PMC.EDUCATION_ID, PMC.SUBJECT_ID, "
				   + " DATE_FORMAT(PMC.BEGIN_DATE, '%Y/%m/%d') AS BEGIN_DATE, "
				   + " DATE_FORMAT(PMC.END_DATE, '%Y/%m/%d') AS END_DATE, "
				   + " PMC.LESSON_NUM, PMC.BASIC_NUM, PMC.QUESTIONS_GROUP_NUM, "
				   + " PMC.CREATE_BY, PMC.CREATE_TIME, PMC.UPDATE_BY, PMC.UPDATE_TIME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMC.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMC.SUBJECT_ID "
				   + " WHERE PMC.ID = ? ";
		
		args.add(contract.getId());
		
		List<Contract> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<Contract>(Contract.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(Contract contract) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(contract);
		
		String sql = " INSERT INTO proposition_manage.contract "
				   + " (UUID, "
				   + " CONTRACT_ID, "
				   + " TEACHER_ID, TKB_CONTRACT_NUM, TKB_CONTRACT_FILE, TKB_CONTRACT_NAME, TKB_PARTYA, "
				   + " TKB_PARTYB, CSOFE_CONTRACT_NUM, CSOFE_CONTRACT_FILE, CSOFE_CONTRACT_NAME, CSOFE_PARTYA, "
				   + " CSOFE_PARTYB, EDUCATION_ID, SUBJECT_ID, BEGIN_DATE, END_DATE, "
				   + " LESSON_NUM, BASIC_NUM, QUESTIONS_GROUP_NUM, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), "
				   + " (SELECT CONCAT(:contract_id, DATE_FORMAT(NOW(), '%y%m'), (SELECT LPAD(COUNT, 3, 0) FROM (SELECT COUNT(*)+1 AS COUNT FROM proposition_manage.contract WHERE SUBSTR(CONTRACT_ID, 1, 8) = CONCAT(:contract_id, DATE_FORMAT(NOW(), '%y%m'))) L1))), "
				   + " :teacher_id, :tkb_contract_num, "
				   + " :tkb_contract_file, :tkb_contract_name, :tkb_partya, :tkb_partyb, :csofe_contract_num, "
				   + " :csofe_contract_file, :csofe_contract_name, :csofe_partya, :csofe_partyb, "
				   + " :education_id, :subject_id, :begin_date, :end_date, :lesson_num, :basic_num, "
				   + " :questions_group_num, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.contract "
				   + " SET TKB_CONTRACT_NUM = ?, TKB_CONTRACT_FILE = ?, TKB_CONTRACT_NAME = ?, "
				   + " TKB_PARTYA = ?, TKB_PARTYB = ?, CSOFE_CONTRACT_NUM = ?, "
				   + " CSOFE_CONTRACT_FILE = ?, CSOFE_CONTRACT_NAME = ?, CSOFE_PARTYA = ?, CSOFE_PARTYB = ?, "
				   + " EDUCATION_ID = ?, SUBJECT_ID = ?, BEGIN_DATE = ?, END_DATE = ?, "
				   + " LESSON_NUM = ?, BASIC_NUM = ?, QUESTIONS_GROUP_NUM = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(contract.getTkb_contract_num());
		args.add(contract.getTkb_contract_file());
		args.add(contract.getTkb_contract_name());
		args.add(contract.getTkb_partya());
		args.add(contract.getTkb_partyb());
		args.add(contract.getCsofe_contract_num());
		args.add(contract.getCsofe_contract_file());
		args.add(contract.getCsofe_contract_name());
		args.add(contract.getCsofe_partya());
		args.add(contract.getCsofe_partyb());
		args.add(contract.getEducation_id());
		args.add(contract.getSubject_id());
		args.add(contract.getBegin_date());
		args.add(contract.getEnd_date());
		args.add(contract.getLesson_num());
		args.add(contract.getBasic_num());
		args.add(contract.getQuestions_group_num());
		args.add(contract.getUpdate_by());
		args.add(contract.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.contract "
				   + " WHERE ID = ? ";
		
		args.add(contract.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> uploadNum(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT IFNULL(SUM(LESSON_NUM), 0) AS LESSON_NUM, "
				   + " IFNULL(SUM(BASIC_NUM), 0) AS BASIC_NUM, "
				   + " IFNULL(SUM(QUESTIONS_GROUP_NUM), 0) AS QUESTIONS_GROUP_NUM "
				   + " FROM proposition_manage.contract "
				   + " WHERE TEACHER_ID = ? ";
		
		args.add(contract.getTeacher_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer undoneCount(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM ( "
				   + " SELECT PMC.LESSON_NUM, PMC.BASIC_NUM, PMC.QUESTIONS_GROUP_NUM, COUNT(LP.ID) AS C_NUM "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.lesson_plan LP ON LP.CONTRACT_ID = PMC.CONTRACT_ID AND LP.FILE_STATUS = 'C' "
				   + " WHERE PMC.TEACHER_ID = ? "
				   + " GROUP BY PMC.CONTRACT_ID "
				   + " ) L1 "
				   + " WHERE LESSON_NUM <> C_NUM ";
		
		args.add(contract.getTeacher_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.size();
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getList(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
//		String sql = " SELECT * FROM ( "
//				   + " SELECT PMC.*, "
//				   + " COUNT(LP1.ID) AS LP1_COUNT, SUM(IF(LP2.ID IS NULL, 0, 1)) LP2_SUM, "
//				   + " COUNT(PMP1.ID) PMP1_COUNT, SUM(IF(PMP2.ID IS NULL, 0, 1)) PMP2_SUM, "
//				   + " COUNT(PMP3.ID) PMP3_COUNT, SUM(IF(PMP4.ID IS NULL, 0, 1)) PMP4_SUM, "
//				   + " (PMC.LESSON_NUM-COUNT(LP1.ID)+PMC.LESSON_NUM-SUM(IF(LP2.ID IS NULL, 0, 1))+PMC.BASIC_NUM-COUNT(PMP1.ID)+PMC.BASIC_NUM-SUM(IF(PMP2.ID IS NULL, 0, 1))+PMC.QUESTIONS_GROUP_NUM-COUNT(PMP3.ID)+PMC.QUESTIONS_GROUP_NUM-SUM(IF(PMP4.ID IS NULL, 0, 1))) AS UNDONE_NUM, "
//				   + " IF(NOW() BETWEEN PMC.BEGIN_DATE AND PMC.END_DATE, '有效', '過期') AS CONTRACT_LIMIT "
//				   + " FROM proposition_manage.contract PMC "
//				   + " LEFT JOIN proposition_manage.lesson_plan LP1 ON LP1.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.lesson_plan LP2 ON LP2.FILE_STATUS = 'C' AND LP2.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP1 ON PMP1.QUESTION_TYPE = '1' AND PMP1.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP2 ON PMP2.QUESTION_TYPE = '1' AND PMP2.FILE_STATUS = 'C' AND PMP2.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP3 ON PMP3.QUESTION_TYPE = '2' AND PMP3.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP4 ON PMP4.QUESTION_TYPE = '2' AND PMP4.FILE_STATUS = 'C' AND PMP4.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " WHERE PMC.TEACHER_ID = ? "
//				   + " GROUP BY PMC.CONTRACT_ID "
//				   + " ) L1 "
//				   + " WHERE UNDONE_NUM > 0 "
////				   + " AND NOW() BETWEEN BEGIN_DATE AND END_DATE "
//				   + " ORDER BY CONTRACT_ID ";
		
		String sql = " SELECT L1.*, "
				   + " (LESSON_NUM-LP1_COUNT+LESSON_NUM-LP2_SUM+BASIC_NUM-PMP1_COUNT+BASIC_NUM-PMP2_SUM+QUESTIONS_GROUP_NUM-PMP3_COUNT+QUESTIONS_GROUP_NUM-PMP4_SUM) AS UNDONE_NUM "
				   + " FROM ( "
				   + " SELECT PMC.*, TA.NAME AS TEACHER_NAME, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID) AS LP1_COUNT, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'F') AS LP2_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 1) AS PMP1_COUNT, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 1 AND P.FILE_STATUS = 'F') AS PMP2_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 2) AS PMP3_COUNT, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.QUESTION_TYPE = 2 AND P.FILE_STATUS = 'F') AS PMP4_SUM, "
				   + " IF(DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN DATE_FORMAT(PMC.BEGIN_DATE, '%Y%m%d') AND DATE_FORMAT(PMC.END_DATE, '%Y%m%d'), '有效', '過期') AS CONTRACT_LIMIT "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON PMC.TEACHER_ID = TA.ID "
				   + " WHERE PMC.TEACHER_ID = ? "
				   + " ) L1 "
				   + " WHERE (LESSON_NUM-LP1_COUNT+LESSON_NUM-LP2_SUM+BASIC_NUM-PMP1_COUNT+BASIC_NUM-PMP2_SUM+QUESTIONS_GROUP_NUM-PMP3_COUNT+QUESTIONS_GROUP_NUM-PMP4_SUM) > 0 ";
		
		args.add(contract.getTeacher_id());
		
		if(contract.getLesson_num() != null) {
			sql += " AND LESSON_NUM > 0 ";
		}
		
		if(contract.getBasic_num() != null) {
			sql += " AND BASIC_NUM > 0 ";
		}
		
		if(contract.getQuestions_group_num() != null) {
			sql += " AND QUESTIONS_GROUP_NUM > 0 ";
		}
		
		sql += " ORDER BY CONTRACT_ID ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> callNum(Contract contract, Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TC.* FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.teacher_account TC ON TC.FIELD_ID = PMC.FIELD_ID AND TC.CONTENT_AUDIT = '1' "
				   + " WHERE PMC.CONTRACT_ID = ? "
				   + " AND TC.ACCOUNT <> ? "
				   + " ORDER BY RAND() ";
		
		args.add(contract.getContract_id());
		args.add(account.getAccount());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
//	public Integer allUndoneNum(Contract contract) {
//		
//		List<Object> args = new ArrayList<Object>();
//		
//		String sql = " SELECT COUNT(*) COUNT FROM ( "
//				   + " SELECT PMC.*, "
//				   + " COUNT(LP1.ID) AS LP1_COUNT, SUM(IF(LP2.ID IS NULL, 0, 1)) LP2_SUM, "
//				   + " COUNT(PMP1.ID) PMP1_COUNT, SUM(IF(PMP2.ID IS NULL, 0, 1)) PMP2_SUM, "
//				   + " COUNT(PMP3.ID) PMP3_COUNT, SUM(IF(PMP4.ID IS NULL, 0, 1)) PMP4_SUM, "
//				   + " (PMC.LESSON_NUM-COUNT(LP1.ID)+PMC.LESSON_NUM-SUM(IF(LP2.ID IS NULL, 0, 1))+PMC.BASIC_NUM-COUNT(PMP1.ID)+PMC.BASIC_NUM-SUM(IF(PMP2.ID IS NULL, 0, 1))+PMC.QUESTIONS_GROUP_NUM-COUNT(PMP3.ID)+PMC.QUESTIONS_GROUP_NUM-SUM(IF(PMP4.ID IS NULL, 0, 1))) AS UNDONE_NUM "
//				   + " FROM proposition_manage.contract PMC "
//				   + " LEFT JOIN proposition_manage.lesson_plan LP1 ON LP1.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.lesson_plan LP2 ON LP2.FILE_STATUS = 'C' AND LP2.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP1 ON PMP1.QUESTION_TYPE = '1' AND PMP1.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP2 ON PMP2.QUESTION_TYPE = '1' AND PMP2.FILE_STATUS = 'C' AND PMP2.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP3 ON PMP3.QUESTION_TYPE = '2' AND PMP3.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " LEFT JOIN proposition_manage.proposition PMP4 ON PMP4.QUESTION_TYPE = '2' AND PMP4.FILE_STATUS = 'C' AND PMP4.CONTRACT_ID = PMC.CONTRACT_ID "
//				   + " GROUP BY PMC.CONTRACT_ID "
//				   + " ) L1 "
//				   + " WHERE UNDONE_NUM > 0 ";
//		
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
//		if(list!=null && list.size()>0) {
//			return Integer.valueOf(list.get(0).get("COUNT").toString());
//		} else {
//			return null;
//		}
//		
//	}
//	
//	public Integer expired(Contract contract) {
//		
//		List<Object> args = new ArrayList<Object>();
//		
//		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.contract "
//				   + " WHERE NOW() NOT BETWEEN BEGIN_DATE AND END_DATE ";
//		
//		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
//		if(list!=null && list.size()>0) {
//			return Integer.valueOf(list.get(0).get("COUNT").toString());
//		} else {
//			return null;
//		}
//		
//	}
	
	public Map<String, Object> getDataByFieldEducation(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT EDUCATION_NAME, EDUCATION_ID, SUBJECT_NAME, SUBJECT_ID, "
				   + " SUM(L_UPLOAD+P_UPLOAD) AS UPLOAD_NUM, "
				   + " SUM(L_COMPLETE+P_COMPLETE) AS COMPLETE_NUM, "
				   + " SUM(LESSON_NUM+BASIC_NUM+QUESTIONS_GROUP_NUM) AS TOTAL_NUM "
				   + " FROM( "
				   + " SELECT PMC.CONTRACT_ID, "
				   + " PMC.LESSON_NUM, PMC.BASIC_NUM, PMC.QUESTIONS_GROUP_NUM, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, PME.ID AS EDUCATION_ID, PMS.ID AS SUBJECT_ID, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID ) AS L_UPLOAD, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND FILE_STATUS = 'C') AS L_COMPLETE, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition PMP WHERE PMC.CONTRACT_ID = PMP.CONTRACT_ID) AS P_UPLOAD, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition PMP WHERE PMC.CONTRACT_ID = PMP.CONTRACT_ID AND FILE_STATUS = 'C') AS P_COMPLETE "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.education PME ON PMC.EDUCATION_ID = PME.ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMC.SUBJECT_ID = PMS.ID "
				   + " WHERE PMC.EDUCATION_ID = ? "
				   + " AND PMC.SUBJECT_ID = ? "
				   + " ) L1 "
				   + " GROUP BY EDUCATION_NAME, EDUCATION_ID, SUBJECT_NAME, SUBJECT_ID ";
		
		args.add(contract.getSubject_id());
		args.add(contract.getEducation_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getDataByContractId(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT PMC.ID, PMC.CONTRACT_ID, PMC.UUID, PMC.TEACHER_ID, PMC.TKB_CONTRACT_NUM, PMC.TKB_CONTRACT_FILE,"
				   + " PMC.TKB_CONTRACT_NAME, PMC.TKB_PARTYA, PMC.TKB_PARTYB, PMC.CSOFE_CONTRACT_NUM, PMC.CSOFE_CONTRACT_FILE, "
				   + " PMC.CSOFE_CONTRACT_NAME, PMC.CSOFE_PARTYA, PMC.CSOFE_PARTYB, PMC.EDUCATION_ID, PMC.SUBJECT_ID, "
				   + " DATE_FORMAT(PMC.BEGIN_DATE, '%Y/%m/%d') AS BEGIN_DATE, "
				   + " DATE_FORMAT(PMC.END_DATE, '%Y/%m/%d') AS END_DATE, "
				   + " PMC.LESSON_NUM, PMC.BASIC_NUM, PMC.QUESTIONS_GROUP_NUM, "
				   + " PMC.CREATE_BY, PMC.CREATE_TIME, PMC.UPDATE_BY, PMC.UPDATE_TIME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMC.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMC.SUBJECT_ID "
				   + " WHERE PMC.CONTRACT_ID = ? ";
		
		args.add(contract.getContract_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> contractNum(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();

		String sql = " SELECT "
				   +  " (CASE WHEN (LESSON_NUM = F_SUML AND BASIC_NUM = F_SUM1 AND QUESTIONS_GROUP_NUM = F_SUM2) THEN 1 ELSE 0 END) COMPLETE, "
				   +  " EFFECTIVE_NUM, (TOTAL-EFFECTIVE_NUM) EXPIRE_NUM, TOTAL "
				   +  " FROM ( "
				   +  " SELECT LESSON_NUM, BASIC_NUM, QUESTIONS_GROUP_NUM, COUNT(PMC.CONTRACT_ID) AS TOTAL, "
				   +  " SUM((CASE WHEN DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN DATE_FORMAT(PMC.BEGIN_DATE, '%Y%m%d') AND DATE_FORMAT(PMC.END_DATE, '%Y%m%d') THEN 1 ELSE 0 END)) AS EFFECTIVE_NUM, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan P WHERE PMC.CONTRACT_ID = P.CONTRACT_ID AND P.FILE_STATUS = 'F') AS F_SUML, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'F' AND LP.QUESTION_TYPE = '1') AS F_SUM1, "
				   +  " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'F' AND LP.QUESTION_TYPE = '2') AS F_SUM2 "
				   +  " FROM proposition_manage.contract PMC   "
				   +  " )A ";
		


		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getSubjectEducation(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();

		String sql = " SELECT PMC.EDUCATION_ID, PMC.SUBJECT_ID, PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " SUM(LESSON_NUM) LESSON_NUM, SUM(BASIC_NUM) BASIC_NUM, SUM(QUESTIONS_GROUP_NUM) QUESTIONS_GROUP_NUM "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMC.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMC.SUBJECT_ID "
				   + " WHERE PMC.EDUCATION_ID = ? AND PMC.SUBJECT_ID = ? "
				   + "  ";
		
		args.add(contract.getEducation_id());
		args.add(contract.getSubject_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getSubjectEducationByTeacher(Contract contract) {
		
		List<Object> args = new ArrayList<Object>();

		String sql = " SELECT PMC.EDUCATION_ID, PMC.SUBJECT_ID, PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, PMC.TEACHER_ID, "
				   + " SUM(LESSON_NUM) LESSON_NUM, SUM(BASIC_NUM) BASIC_NUM, SUM(QUESTIONS_GROUP_NUM) QUESTIONS_GROUP_NUM "
				   + " FROM proposition_manage.contract PMC "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = PMC.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = PMC.SUBJECT_ID "
				   + " WHERE PMC.TEACHER_ID = ? "
				   + " GROUP BY PMC.EDUCATION_ID, PMC.SUBJECT_ID, PMC.TEACHER_ID ";
		
		args.add(contract.getTeacher_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getLessonPlanNum(String teacher, String education, String subject) {
		
		List<Object> args = new ArrayList<Object>();

		String sql = " SELECT SUM(LP1_COUNT) LP1_COUNT, SUM(A_SUM) A_SUM, SUM(B_SUM) B_SUM, "
				   + " SUM(C_SUM) C_SUM, SUM(D_SUM) D_SUM, SUM(E_SUM) E_SUM, SUM(F_SUM) F_SUM "
				   + " FROM ( "
				   + " SELECT "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID) AS LP1_COUNT, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'A') AS A_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'B') AS B_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'C') AS C_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'D') AS D_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'E') AS E_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.lesson_plan LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'F') AS F_SUM "
				   + " FROM proposition_manage.contract PMC "
				   + " WHERE PMC.EDUCATION_ID = ? AND PMC.SUBJECT_ID = ? ";
		if(!"".equals(teacher)) {
			sql+= " AND PMC.TEACHER_ID = ? ";
		}
		sql+= " ) L1  ";
		
		args.add(education);
		args.add(subject);
		if(!"".equals(teacher)) {
			args.add(teacher);
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}

	public Map<String, Object> getPropositionNum(String teacher, String education, String subject, String questionType) {
		
		List<Object> args = new ArrayList<Object>();
	
		String sql = " SELECT SUM(LP1_COUNT) LP1_COUNT, SUM(A_SUM) A_SUM, SUM(B_SUM) B_SUM, "
				   + " SUM(C_SUM) C_SUM, SUM(D_SUM) D_SUM, SUM(E_SUM) E_SUM, SUM(F_SUM) F_SUM "
				   + " FROM ( "
				   + " SELECT "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.QUESTION_TYPE = ?) AS LP1_COUNT, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'A' AND LP.QUESTION_TYPE = ?) AS A_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'B' AND LP.QUESTION_TYPE = ?) AS B_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'C' AND LP.QUESTION_TYPE = ?) AS C_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'D' AND LP.QUESTION_TYPE = ?) AS D_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'E' AND LP.QUESTION_TYPE = ?) AS E_SUM, "
				   + " (SELECT COUNT(*) AS COUNT FROM proposition_manage.proposition LP WHERE PMC.CONTRACT_ID = LP.CONTRACT_ID AND LP.FILE_STATUS = 'F' AND LP.QUESTION_TYPE = ?) AS F_SUM "
				   + " FROM proposition_manage.contract PMC "
				   + " WHERE PMC.EDUCATION_ID = ? AND PMC.SUBJECT_ID = ? ";
		if(!"".equals(teacher)) {
			sql+= " AND PMC.TEACHER_ID = ? ";
		}
		sql+= " ) L2  ";
		
		args.add(questionType);
		args.add(questionType);
		args.add(questionType);
		args.add(questionType);
		args.add(questionType);
		args.add(questionType);
		args.add(questionType);
		args.add(education);
		args.add(subject);
		if(!"".equals(teacher)) {
			args.add(teacher);
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> getLessonPlanProposition(Contract contract, String teacher) {
		
		List<Object> args = new ArrayList<Object>();

		String sql = " SELECT SUM(A_SUM+B_SUM+C_SUM+D_SUM+E_SUM+F_SUM) P1_COUNT,  "
				   + " SUM(A_SUM) A_SUM , SUM(B_SUM) B_SUM,SUM(C_SUM) C_SUM, SUM(D_SUM) D_SUM, SUM(E_SUM) E_SUM, SUM(F_SUM) F_SUM "
				   + " FROM( "
				   + " SELECT "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'A' THEN 1 ELSE 0 END) A_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'B' THEN 1 ELSE 0 END) B_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'C' THEN 1 ELSE 0 END) C_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'D' THEN 1 ELSE 0 END) D_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'E' THEN 1 ELSE 0 END) E_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'F' THEN 1 ELSE 0 END) F_SUM "
				   + " FROM proposition_manage.proposition P "
				   + " LEFT JOIN proposition_manage.contract PMC ON PMC.CONTRACT_ID = P.CONTRACT_ID "
				   + " WHERE PMC.EDUCATION_ID = ? AND PMC.SUBJECT_ID = ? "
				   + " AND P.AUDITOR = ? "
				   + " UNION "
				   + " SELECT "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'A' THEN 1 ELSE 0 END) A_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'B' THEN 1 ELSE 0 END) B_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'C' THEN 1 ELSE 0 END) C_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'D' THEN 1 ELSE 0 END) D_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'E' THEN 1 ELSE 0 END) E_SUM, "
				   + " SUM(CASE WHEN P.FILE_STATUS = 'F' THEN 1 ELSE 0 END) F_SUM "
				   + " FROM proposition_manage.lesson_plan P "
				   + " LEFT JOIN proposition_manage.contract PMC ON PMC.CONTRACT_ID = P.CONTRACT_ID "
				   + " WHERE PMC.EDUCATION_ID = ? AND PMC.SUBJECT_ID = ? "
				   + " AND P.AUDITOR = ? "
				   + " )A ";

		args.add(contract.getEducation_id());
		args.add(contract.getSubject_id());
		args.add(teacher);
		args.add(contract.getEducation_id());
		args.add(contract.getSubject_id());
		args.add(teacher);

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
