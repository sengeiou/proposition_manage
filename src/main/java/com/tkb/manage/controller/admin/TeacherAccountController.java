package com.tkb.manage.controller.admin;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tkb.manage.model.Account;
import com.tkb.manage.model.Field;
import com.tkb.manage.model.Identity;
import com.tkb.manage.model.SchoolMaster;
import com.tkb.manage.service.FieldService;
import com.tkb.manage.service.IdentityService;
import com.tkb.manage.service.SchoolMasterService;
import com.tkb.manage.service.TeacherAccountService;

import jxl.Sheet;
import jxl.Workbook;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/teacher/account")
public class TeacherAccountController {
	
	@Autowired
	private TeacherAccountService teacherAccountService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private FieldService fieldService;
	
	@Autowired
	private SchoolMasterService schoolMasterService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		//設定每頁筆數
		Integer page_count = 10;
		model.addAttribute("page_count", page_count);
		
		if(account.getPage() == null) {
			account.setPage(1);
		}
		
		account.setPage_count(page_count);
		List<Map<String, Object>> list = teacherAccountService.list(account);
		model.addAttribute("list", list);
		
		int count = teacherAccountService.count(account);
		account.setCount(count);
		account.setTotal_page((account.getCount()/account.getPage_count())<1 ? 1 : ((account.getCount()/account.getPage_count())+((account.getCount()%account.getPage_count()) > 0 ? 1 : 0)));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", account.getPage());
		model.addAttribute("total_page", account.getTotal_page());
		
		return "admin/teacherAccount/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得身份清單
		Identity identity = new Identity();
		identity.setParent_id("0");
		List<Map<String, Object>> identityList = identityService.list(identity);
		model.addAttribute("identityList", identityList);
		
		//取得學校清單
		SchoolMaster schoolMaster = new SchoolMaster();
		List<Map<String, Object>> schoolMasterList = schoolMasterService.getList(schoolMaster);
		model.addAttribute("schoolMasterList", schoolMasterList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/teacherAccount/add";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession,
    		@ModelAttribute Account account,
    		HttpServletRequest pRequest,
    		Model model){
		
		String content_provision = pRequest.getParameter("content_provision") == null ? "0" : "1";
		String content_audit = pRequest.getParameter("content_audit") == null ? "0" : "1";
		
		account.setAccount(account.getEmail());
//		account.setPassword(account.getPhone());
		account.setContent_provision(content_provision);
		account.setContent_audit(content_audit);
		account.setStatus("1");
		account.setCreate_by(accountSession.getAccount());
		account.setUpdate_by(accountSession.getAccount());
		teacherAccountService.add(account);
		
//		model.addAttribute("id", id);
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		//取得管理者資料
		Account teacherAccount = teacherAccountService.data(account);
		model.addAttribute("account", teacherAccount);
		
		//取得領域清單
		Field field = new Field();
		List<Map<String, Object>> fieldList = fieldService.getList(field);
		model.addAttribute("fieldList", fieldList);
		
		//取得身份清單
		Identity identity = new Identity();
		identity.setParent_id("0");
		List<Map<String, Object>> identityList = identityService.list(identity);
		model.addAttribute("identityList", identityList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/teacherAccount/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		account.setUpdate_by(accountSession.getAccount());
		teacherAccountService.update(account);
		
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model) {
		
		teacherAccountService.delete(account);
		
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	@RequestMapping(value = "/import/excel" , method = {RequestMethod.GET, RequestMethod.POST})
	public String importExcel(
			@SessionAttribute("accountSession")Account accountSession,
			@ModelAttribute Account account,
			Model model,
			HttpServletRequest pRequest) {
		
		try {
			
			Part filePart = pRequest.getPart("import");
			InputStream is = filePart.getInputStream();
			Workbook book = Workbook.getWorkbook(is);  
	        Sheet sheet = book.getSheet(0);
	        
	        int rowCount = sheet.getRows();
	        
	        for(int i=1	; i<rowCount; i++) {
	        	
	        	String name = sheet.getCell(1, i).getContents() == "" ? null : sheet.getCell(1, i).getContents();
//	        	String school_name = sheet.getCell(2, i).getContents() == "" ? null : sheet.getCell(2, i).getContents();
	        	String id_no = sheet.getCell(3, i).getContents() == "" ? null : sheet.getCell(3, i).getContents();
	        	String phone = sheet.getCell(4, i).getContents() == "" ? null : sheet.getCell(4, i).getContents();
	        	String email = sheet.getCell(5, i).getContents() == "" ? null : sheet.getCell(5, i).getContents();
	        	String address = sheet.getCell(6, i).getContents() == "" ? null : sheet.getCell(6, i).getContents();
	        	String bank = sheet.getCell(7, i).getContents() == "" ? null : sheet.getCell(7, i).getContents();
	        	String branch = sheet.getCell(8, i).getContents() == "" ? null : sheet.getCell(8, i).getContents();
	        	String remittance_account = sheet.getCell(11, i).getContents() == "" ? null : sheet.getCell(11, i).getContents();
	        	String field_name = sheet.getCell(12, i).getContents() == "" ? null : sheet.getCell(12, i).getContents();
	        	String content_provision = "1";
	        	String content_audit = "0";
	        	String status = "1";
	        	
	        	if(name == null) {
	        		break;
	        	}
	        	
	        	if(email == null) {
	        		continue;
	        	}
	        	
	        	if(phone == null) {
	        		continue;
	        	}
	        	
//	        	SchoolMaster schoolMaster = new SchoolMaster();
//	        	schoolMaster.setName(school_name);
//	        	Map<String ,Object> schoolId = schoolMasterService.searchName(schoolMaster);
//	        	String school_master_id = schoolId!=null ? schoolId.get("ID").toString() : null;
	        	
	        	Field field = new Field();
	        	field.setName(field_name);
	        	Map<String ,Object> fieldId = fieldService.searchName(field);
	        	String field_id = fieldId!=null ? fieldId.get("ID").toString() : null;
	        	
	        	account = new Account();
	        	account.setAccount(email);
	        	account.setPassword(phone);
	        	account.setName(name);
	        	account.setId_no(id_no);
//	        	account.setPhone(phone);
	        	account.setEmail(email);
//	        	account.setAddress(address);
	        	account.setBank(bank);
	        	account.setBranch(branch);
	        	account.setRemittance_account(remittance_account);
//	        	account.setAddress(address);
	        	account.setField_id(field_id);
	        	account.setContent_provision(content_provision);
	        	account.setContent_audit(content_audit);
	        	account.setStatus(status);
	        	account.setCreate_by(accountSession.getAccount());
	        	account.setUpdate_by(accountSession.getAccount());
	        	
				teacherAccountService.add(account);

	        }
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("PATH", "/tkbrule/teacher/account/list");
		return "admin/path";
		
	}
	
}
