package com.tkb.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tkb.manage.dao.ContractMaterialOptionDao;
import com.tkb.manage.model.ContractMaterialOption;

@Repository
public class ContractMaterialOptionDaoImpl implements ContractMaterialOptionDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void add(ContractMaterialOption contractMaterialOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " INSERT INTO proposition_manage.contract_material_option(CONTRACT_MATERIAL_ID, "
				   + " MATERIAL_TYPE, CREATE_BY, CREATE_TIME) "
				   + " VALUES(?, ?, ?, NOW()) ";
		
		args.add(contractMaterialOption.getContract_material_id());
		args.add(contractMaterialOption.getMaterial_type());
		args.add(contractMaterialOption.getCreate_by());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public void delete(ContractMaterialOption contractMaterialOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " DELETE FROM proposition_manage.contract_material_option "
				   + " WHERE CONTRACT_MATERIAL_ID = ? ";
		
//		args.add(contractMaterialOption.getLesson_plan_id());
		
		jdbcTemplate.update(sql, args.toArray());
		
	}
	
	public Map<String, Object> option(ContractMaterialOption contractMaterialOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(CODE) AS OPTION "
				   + " FROM proposition_manage.contract_material_option "
				   + " WHERE CONTRACT_MATERIAL_ID = ? "
				   + " AND MATERIAL_TYPE = ? ";
		
//		args.add(contractMaterialOption.getLesson_plan_id());
//		args.add(contractMaterialOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> subjectOption(ContractMaterialOption contractMaterialOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PMS.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.contract_material_option LPO "
				   + " LEFT JOIN proposition_manage.subject PMS ON PMS.ID = CODE "
				   + " WHERE LPO.LESSON_PLAN_ID = ? "
				   + " AND LPO.TYPE = ? ";
		
//		args.add(contractMaterialOption.getLesson_plan_id());
//		args.add(contractMaterialOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
	public Map<String, Object> gradeOption(ContractMaterialOption contractMaterialOption) {
		
		List<Object> args = new ArrayList<Object>();
		
		String sql = " SELECT GROUP_CONCAT(PME.NAME SEPARATOR '、') AS OPTION "
				   + " FROM proposition_manage.contract_material_option CMO "
				   + " LEFT JOIN proposition_manage.education PME ON PME.ID = CODE "
				   + " WHERE CMO.LESSON_PLAN_ID = ? "
				   + " AND CMO.TYPE = ? ";
		
//		args.add(contractMaterialOption.getLesson_plan_id());
//		args.add(contractMaterialOption.getType());
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args.toArray());
		
		if(list!=null && list.size()>0 && list.get(0).get("OPTION")!=null) {
			return list.get(0);
		} else {
			return null;
		}
		
	}
	
}
