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
import com.tkb.manage.model.SchoolMaster;
import com.tkb.manage.service.SchoolMasterService;

import jxl.Sheet;
import jxl.Workbook;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/school")
public class SchoolMasterController {
	
	@Autowired
	private SchoolMasterService schoolMasterService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute SchoolMaster schoolMaster, Model model){
		
		//設定每頁筆數
		Integer page_count = 10;
		model.addAttribute("page_count", page_count);
		
		if(schoolMaster.getPage() == null) {
			schoolMaster.setPage(1);
		}
		
		schoolMaster.setPage_count(page_count);
		List<Map<String, Object>> list = schoolMasterService.list(schoolMaster);
		model.addAttribute("list", list);
		
		int count = schoolMasterService.count(schoolMaster);
		schoolMaster.setCount(count);
		schoolMaster.setTotal_page((schoolMaster.getCount()/schoolMaster.getPage_count())<1 ? 1 : ((schoolMaster.getCount()/schoolMaster.getPage_count())+((schoolMaster.getCount()%schoolMaster.getPage_count()) > 0 ? 1 : 0)));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", schoolMaster.getPage());
		model.addAttribute("total_page", schoolMaster.getTotal_page());
		
		return "admin/schoolMaster/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute SchoolMaster schoolMaster, Model model){
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", schoolMaster.getPage());
		model.addAttribute("total_page", schoolMaster.getTotal_page());
		
		return "admin/schoolMaster/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute SchoolMaster schoolMaster, Model model){
		
		schoolMaster.setCreate_by(accountSession.getAccount());
		schoolMaster.setUpdate_by(accountSession.getAccount());
		schoolMasterService.add(schoolMaster);
		
		//分頁
		model.addAttribute("page", schoolMaster.getPage());
		model.addAttribute("total_page", schoolMaster.getTotal_page());
		
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute SchoolMaster schoolMaster, Model model){
		
		Integer page = schoolMaster.getPage();
		Integer total_page = schoolMaster.getTotal_page();
		
		//取得學校資料
		schoolMaster = schoolMasterService.data(schoolMaster);
		model.addAttribute("schoolMaster", schoolMaster);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		schoolMaster.setPage(page);
		schoolMaster.setTotal_page(total_page);
		
		return "admin/schoolMaster/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute SchoolMaster schoolMaster, Model model){
		
		schoolMaster.setUpdate_by(accountSession.getAccount());
		schoolMasterService.update(schoolMaster);
		
		//分頁
		model.addAttribute("page", schoolMaster.getPage());
		model.addAttribute("total_page", schoolMaster.getTotal_page());
		
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute SchoolMaster schoolMaster, Model model) {
		
		schoolMasterService.delete(schoolMaster);
		
		//分頁
		model.addAttribute("page", schoolMaster.getPage());
		model.addAttribute("total_page", schoolMaster.getTotal_page());
		
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	@RequestMapping(value = "/import/excel" , method = {RequestMethod.GET, RequestMethod.POST})
	public String importExcel(
			@SessionAttribute("accountSession")Account accountSession,
			@ModelAttribute SchoolMaster schoolMaster,
			Model model,
			HttpServletRequest pRequest) {
		
		try {
			
			Part filePart = pRequest.getPart("import");
			InputStream is = filePart.getInputStream();
			Workbook book = Workbook.getWorkbook(is);  
	        Sheet sheet = book.getSheet(0);
	        
	        int rowCount = sheet.getRows();
	        
	        for(int i=1	; i<rowCount; i++) {
	        	String code = sheet.getCell(0, i).getContents() == "" ? "" : sheet.getCell(0, i).getContents();
	        	String name = sheet.getCell(1, i).getContents() == "" ? "" : sheet.getCell(1, i).getContents();
	        	String school_type = sheet.getCell(2, i).getContents() == "" ? "" : sheet.getCell(2, i).getContents();
	        	String country_name = sheet.getCell(3, i).getContents() == "" ? "" : sheet.getCell(3, i).getContents();
	        	String address = sheet.getCell(4, i).getContents() == "" ? "" : sheet.getCell(4, i).getContents();
	        	String telphone = sheet.getCell(5, i).getContents() == "" ? "" : sheet.getCell(5, i).getContents();
	        	String website = sheet.getCell(6, i).getContents() == "" ? "" : sheet.getCell(6, i).getContents();
	        	String system_type = sheet.getCell(7, i).getContents() == "" ? "" : sheet.getCell(7, i).getContents();
	        	String type = sheet.getCell(8, i).getContents() == "" ? "" : sheet.getCell(8, i).getContents();
	        	String display = "1";
	        	
	        	if("".equals(name)) {
	        		break;
	        	}
	        	
	        	schoolMaster = new SchoolMaster();
	        	schoolMaster.setCode(code);
	        	schoolMaster.setName(name);
	        	schoolMaster.setSchool_type(school_type);
	        	schoolMaster.setCountry_name(country_name);
	        	schoolMaster.setAddress(address);
	        	schoolMaster.setTelphone(telphone);
	        	schoolMaster.setWebsite(website);
	        	schoolMaster.setSystem_type(system_type);
	        	schoolMaster.setType(type);
	        	schoolMaster.setDisplay(display);
	        	schoolMaster.setCreate_by(accountSession.getAccount());
	        	schoolMaster.setUpdate_by(accountSession.getAccount());
	        	
				schoolMasterService.add(schoolMaster);

	        }
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("PATH", "/tkbrule/school/list");
		return "admin/path";
		
	}
	
}
