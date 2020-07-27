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

import com.tkb.manage.dao.ContractMaterialDao;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.ContractMaterial;

@Repository
public class ContractMaterialDaoImpl implements ContractMaterialDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcNameTemplate;
	
	public List<Map<String, Object>> list(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT CM.*, TA.NAME AS TEACHER_NAME, "
				   + " CASE WHEN CM.LP_TYPE = '1' THEN '教案' "
				   + " WHEN CM.LP_TYPE = '2' THEN '基本題' "
				   + " WHEN CM.LP_TYPE = '3' THEN '題組題' "
				   + " END LP_TYPE_NAME, "
				   + " CASE WHEN CM.LP_TYPE = '1' THEN LP.NAME ELSE PMP.NAME END NAME, "
				   + " GROUP_CONCAT( "
				   + " (CASE WHEN CMO.MATERIAL_TYPE = '1' THEN '圖片' "
				   + " WHEN CMO.MATERIAL_TYPE = '2' THEN '文本' "
				   + " WHEN CMO.MATERIAL_TYPE = '3' THEN '影片' "
				   + " WHEN CMO.MATERIAL_TYPE = '4' THEN '音檔' "
				   + " END)ORDER BY CMO.MATERIAL_TYPE) MATERIAL_TYPE_NAME, "
				   + " IF(DATE_FORMAT(NOW(), '%Y%m%d') BETWEEN DATE_FORMAT(CM.BEGIN_DATE, '%Y%m%d') AND DATE_FORMAT(CM.END_DATE, '%Y%m%d'), '有效', '過期') AS CONTRACT_LIMIT "
				   + " FROM proposition_manage.contract_material CM "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON TA.ID = CM.TEACHER_ID "
				   + " LEFT JOIN proposition_manage.lesson_plan LP ON LP.ID = CM.LP_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP ON PMP.ID = CM.LP_ID "
				   + " LEFT JOIN proposition_manage.contract_material_option CMO ON CMO.CONTRACT_MATERIAL_ID = CM.ID ";
		
		if(contractMaterial.getTeacher_id() != null) {
			sql += " WHERE CM.TEACHER_ID = ? ";
			args.add(contractMaterial.getTeacher_id());
		}
		
		sql += " GROUP BY CM.ID "
			+  " LIMIT "+((contractMaterial.getPage()-1)*contractMaterial.getPage_count())+","+contractMaterial.getPage_count();
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Integer count(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.contract_material ";
		
		if(contractMaterial.getTeacher_id() != null) {
			sql += " WHERE TEACHER_ID = ? ";
			args.add(contractMaterial.getTeacher_id());
		}
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public ContractMaterial data(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT CM.ID, CM.UUID, CM.TEACHER_ID, CM.TKB_CONTRACT_NUM, CM.TKB_CONTRACT_FILE, "
				   + " CM.TKB_CONTRACT_NAME, CM.TKB_PARTYA, CM.TKB_PARTYB, CM.CSOFE_CONTRACT_NUM, CM.CSOFE_CONTRACT_FILE, "
				   + " CM.CSOFE_CONTRACT_NAME, CM.CSOFE_PARTYA, CM.CSOFE_PARTYB, CM.EDUCATION_ID, CM.SUBJECT_ID, "
				   + " DATE_FORMAT(CM.BEGIN_DATE, '%Y/%m/%d') AS BEGIN_DATE, "
				   + " DATE_FORMAT(CM.END_DATE, '%Y/%m/%d') AS END_DATE, "
				   + " CM.LESSON_NUM, CM.BASIC_NUM, CM.QUESTIONS_GROUP_NUM, "
				   + " CM.CREATE_BY, CM.CREATE_TIME, CM.UPDATE_BY, CM.UPDATE_TIME, "
				   + " TA.NAME AS TEACHER_NAME, "
				   + " PME.NAME AS EDUCATION_NAME, PMS.NAME AS SUBJECT_NAME, "
				   + " CASE WHEN CM.LP_TYPE = '1' THEN '教案' "
				   + " WHEN CM.LP_TYPE = '2' THEN '基本題' "
				   + " WHEN CM.LP_TYPE = '3' THEN '題組題' "
				   + " END LP_TYPE_NAME, "
				   + " CASE WHEN CM.LP_TYPE = '1' THEN LP.NAME ELSE PMP.NAME END NAME, "
				   + " GROUP_CONCAT( "
				   + " (CASE WHEN CMO.MATERIAL_TYPE = '1' THEN '圖片' "
				   + " WHEN CMO.MATERIAL_TYPE = '2' THEN '文本' "
				   + " WHEN CMO.MATERIAL_TYPE = '3' THEN '影片' "
				   + " WHEN CMO.MATERIAL_TYPE = '4' THEN '音檔' "
				   + " END)ORDER BY CMO.MATERIAL_TYPE) MATERIAL_TYPE_NAME "
				   + " FROM proposition_manage.contract_material CM "
				   + " LEFT JOIN proposition_manage.teacher_account TA ON TA.ID = CM.TEACHER_ID "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = CM.EDUCATION_ID "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMF.ID = CM.SUBJECT_ID "
				   + " LEFT JOIN proposition_manage.lesson_plan LP ON LP.ID = CM.LP_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP ON PMP.ID = CM.LP_ID "
				   + " LEFT JOIN proposition_manage.contract_material_option CMO ON CMO.CONTRACT_MATERIAL_ID = CM.ID "
				   + " WHERE CM.ID = ? "
				   + " GROUP BY CM.ID ";
		
		args.add(contractMaterial.getId());
		
		List<ContractMaterial> list = jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<ContractMaterial>(ContractMaterial.class));
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public int add(ContractMaterial contractMaterial) {
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(contractMaterial);
		
		String sql = " INSERT INTO proposition_manage.contract_material "
				   + " (UUID, TEACHER_ID, TKB_CONTRACT_NUM, TKB_CONTRACT_FILE, TKB_CONTRACT_NAME, TKB_PARTYA, "
				   + " TKB_PARTYB, CSOFE_CONTRACT_NUM, CSOFE_CONTRACT_FILE, CSOFE_CONTRACT_NAME, CSOFE_PARTYA, "
				   + " CSOFE_PARTYB, LP_ID, LP_TYPE, EDUCATION_ID, SUBJECT_ID, BEGIN_DATE, END_DATE, "
				   + " LESSON_NUM, BASIC_NUM, QUESTIONS_GROUP_NUM, "
				   + " CREATE_BY, CREATE_TIME, UPDATE_BY, UPDATE_TIME) "
				   + " VALUES(REPLACE(UUID(), '-', ''), :teacher_id, :tkb_contract_num, "
				   + " :tkb_contract_file, :tkb_contract_name, :tkb_partya, :tkb_partyb, :csofe_contract_num, "
				   + " :csofe_contract_file, :csofe_contract_name, :csofe_partya, :csofe_partyb, :lp_id, :lp_type, "
				   + " :education_id, :subject_id, :begin_date, :end_date, :lesson_num, :basic_num, "
				   + " :questions_group_num, :create_by, NOW(), :update_by, NOW()) ";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcNameTemplate.update(sql, paramSource, keyHolder);
		
		return keyHolder.getKey().intValue();
		
	}
	
	public void update(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.contract_material "
				   + " SET TKB_CONTRACT_NUM = ?, TKB_CONTRACT_FILE = ?, TKB_CONTRACT_NAME = ?, "
				   + " TKB_PARTYA = ?, TKB_PARTYB = ?, CSOFE_CONTRACT_NUM = ?, "
				   + " CSOFE_CONTRACT_FILE = ?, CSOFE_CONTRACT_NAME = ?, CSOFE_PARTYA = ?, CSOFE_PARTYB = ?, "
				   + " EDUCATION_ID = ?, SUBJECT_ID = ?, BEGIN_DATE = ?, END_DATE = ?, "
				   + " LESSON_NUM = ?, BASIC_NUM = ?, QUESTIONS_GROUP_NUM = ?, "
				   + " UPDATE_BY = ?, UPDATE_TIME = NOW() "
				   + " WHERE ID = ? ";
		
		args.add(contractMaterial.getTkb_contract_num());
		args.add(contractMaterial.getTkb_contract_file());
		args.add(contractMaterial.getTkb_contract_name());
		args.add(contractMaterial.getTkb_partya());
		args.add(contractMaterial.getTkb_partyb());
		args.add(contractMaterial.getCsofe_contract_num());
		args.add(contractMaterial.getCsofe_contract_file());
		args.add(contractMaterial.getCsofe_contract_name());
		args.add(contractMaterial.getCsofe_partya());
		args.add(contractMaterial.getCsofe_partyb());
		args.add(contractMaterial.getEducation_id());
		args.add(contractMaterial.getSubject_id());
		args.add(contractMaterial.getBegin_date());
		args.add(contractMaterial.getEnd_date());
		args.add(contractMaterial.getLesson_num());
		args.add(contractMaterial.getBasic_num());
		args.add(contractMaterial.getQuestions_group_num());
		args.add(contractMaterial.getUpdate_by());
		args.add(contractMaterial.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.contract_material "
				   + " WHERE ID = ? ";
		
		args.add(contractMaterial.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void updateTeacherId(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " UPDATE proposition_manage.contract_material "
				   + " SET CONTRACT_ID = (SELECT CONCAT(DATE_FORMAT(NOW(), '%y%m%d'), (CASE WHEN LENGTH(?)>=4 THEN SUBSTRING(?,-4) ELSE LPAD(?, 4, 0) END))) "
				   + " WHERE ID = ? ";
		
		args.add(contractMaterial.getId());
		args.add(contractMaterial.getId());
		args.add(contractMaterial.getId());
		args.add(contractMaterial.getId());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> uploadNum(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT IFNULL(SUM(LESSON_NUM), 0) AS LESSON_NUM, "
				   + " IFNULL(SUM(BASIC_NUM), 0) AS BASIC_NUM, "
				   + " IFNULL(SUM(QUESTIONS_GROUP_NUM), 0) AS QUESTIONS_GROUP_NUM "
				   + " FROM proposition_manage.contract_material "
				   + " WHERE TEACHER_ID = ? ";
		
		args.add(contractMaterial.getTeacher_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer undoneCount(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM ( "
				   + " SELECT PMC.LESSON_NUM, PMC.BASIC_NUM, PMC.QUESTIONS_GROUP_NUM, COUNT(LP.ID) AS C_NUM "
				   + " FROM proposition_manage.contract_material PMC "
				   + " LEFT JOIN proposition_manage.lesson_plan LP ON LP.CONTRACT_ID = PMC.CONTRACT_ID AND LP.UPLOAD_STATUS = 'C' "
				   + " WHERE PMC.TEACHER_ID = ? "
				   + " GROUP BY PMC.CONTRACT_ID "
				   + " ) L1 "
				   + " WHERE LESSON_NUM <> C_NUM ";
		
		args.add(contractMaterial.getTeacher_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.size();
		} else {
			return null;
		}
		
	}
	
	public List<Map<String, Object>> getList(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT * FROM ( "
				   + " SELECT PMC.*, "
				   + " COUNT(LP1.ID) AS LP1_COUNT, SUM(IF(LP2.ID IS NULL, 0, 1)) LP2_SUM, "
				   + " COUNT(PMP1.ID) PMP1_COUNT, SUM(IF(PMP2.ID IS NULL, 0, 1)) PMP2_SUM, "
				   + " COUNT(PMP3.ID) PMP3_COUNT, SUM(IF(PMP4.ID IS NULL, 0, 1)) PMP4_SUM, "
				   + " (PMC.LESSON_NUM-COUNT(LP1.ID)+PMC.LESSON_NUM-SUM(IF(LP2.ID IS NULL, 0, 1))+PMC.BASIC_NUM-COUNT(PMP1.ID)+PMC.BASIC_NUM-SUM(IF(PMP2.ID IS NULL, 0, 1))+PMC.QUESTIONS_GROUP_NUM-COUNT(PMP3.ID)+PMC.QUESTIONS_GROUP_NUM-SUM(IF(PMP4.ID IS NULL, 0, 1))) AS UNDONE_NUM, "
				   + " IF(NOW() BETWEEN PMC.BEGIN_DATE AND PMC.END_DATE, '有效', '過期') AS CONTRACT_LIMIT "
				   + " FROM proposition_manage.contract_material PMC "
				   + " LEFT JOIN proposition_manage.lesson_plan LP1 ON LP1.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.lesson_plan LP2 ON LP2.UPLOAD_STATUS = 'C' AND LP2.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP1 ON PMP1.QUESTION_TYPE = '1' AND PMP1.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP2 ON PMP2.QUESTION_TYPE = '1' AND PMP2.UPLOAD_STATUS = 'C' AND PMP2.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP3 ON PMP3.QUESTION_TYPE = '2' AND PMP3.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP4 ON PMP4.QUESTION_TYPE = '2' AND PMP4.UPLOAD_STATUS = 'C' AND PMP4.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " WHERE PMC.TEACHER_ID = ? "
				   + " GROUP BY PMC.CONTRACT_ID "
				   + " ) L1 "
				   + " WHERE UNDONE_NUM > 0 "
//				   + " AND NOW() BETWEEN BEGIN_DATE AND END_DATE "
				   + " ORDER BY CONTRACT_ID ";
		
		args.add(contractMaterial.getTeacher_id());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list;
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> callNum(ContractMaterial contractMaterial, Account account) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT TC.* FROM proposition_manage.contract_material PMC "
				   + " LEFT JOIN proposition_manage.teacher_account TC ON TC.FIELD_ID = PMC.FIELD_ID AND TC.CONTENT_AUDIT = '1' "
				   + " WHERE PMC.CONTRACT_ID = ? "
				   + " AND TC.ACCOUNT <> ? "
				   + " ORDER BY RAND() ";
		
		args.add(contractMaterial.getContract_id());
		args.add(account.getAccount());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Integer allUndoneNum(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) COUNT FROM ( "
				   + " SELECT PMC.*, "
				   + " COUNT(LP1.ID) AS LP1_COUNT, SUM(IF(LP2.ID IS NULL, 0, 1)) LP2_SUM, "
				   + " COUNT(PMP1.ID) PMP1_COUNT, SUM(IF(PMP2.ID IS NULL, 0, 1)) PMP2_SUM, "
				   + " COUNT(PMP3.ID) PMP3_COUNT, SUM(IF(PMP4.ID IS NULL, 0, 1)) PMP4_SUM, "
				   + " (PMC.LESSON_NUM-COUNT(LP1.ID)+PMC.LESSON_NUM-SUM(IF(LP2.ID IS NULL, 0, 1))+PMC.BASIC_NUM-COUNT(PMP1.ID)+PMC.BASIC_NUM-SUM(IF(PMP2.ID IS NULL, 0, 1))+PMC.QUESTIONS_GROUP_NUM-COUNT(PMP3.ID)+PMC.QUESTIONS_GROUP_NUM-SUM(IF(PMP4.ID IS NULL, 0, 1))) AS UNDONE_NUM "
				   + " FROM proposition_manage.contract_material PMC "
				   + " LEFT JOIN proposition_manage.lesson_plan LP1 ON LP1.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.lesson_plan LP2 ON LP2.UPLOAD_STATUS = 'C' AND LP2.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP1 ON PMP1.QUESTION_TYPE = '1' AND PMP1.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP2 ON PMP2.QUESTION_TYPE = '1' AND PMP2.UPLOAD_STATUS = 'C' AND PMP2.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP3 ON PMP3.QUESTION_TYPE = '2' AND PMP3.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " LEFT JOIN proposition_manage.proposition PMP4 ON PMP4.QUESTION_TYPE = '2' AND PMP4.UPLOAD_STATUS = 'C' AND PMP4.CONTRACT_ID = PMC.CONTRACT_ID "
				   + " GROUP BY PMC.CONTRACT_ID "
				   + " ) L1 "
				   + " WHERE UNDONE_NUM > 0 ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
	public Integer expired(ContractMaterial contractMaterial) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT COUNT(*) AS COUNT FROM proposition_manage.contract_material "
				   + " WHERE NOW() NOT BETWEEN BEGIN_DATE AND END_DATE ";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		if(list!=null && list.size()>0) {
			return Integer.valueOf(list.get(0).get("COUNT").toString());
		} else {
			return null;
		}
		
	}
	
}
