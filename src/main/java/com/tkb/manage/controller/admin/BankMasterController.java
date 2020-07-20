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
import com.tkb.manage.model.BankMaster;
import com.tkb.manage.service.BankMasterService;

import jxl.Sheet;
import jxl.Workbook;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/bank")
public class BankMasterController {
	
	@Autowired
	private BankMasterService bankMasterService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute BankMaster bankMaster, Model model){
		
		//設定每頁筆數
		Integer page_count = 10;
		model.addAttribute("page_count", page_count);
		
		if(bankMaster.getPage() == null) {
			bankMaster.setPage(1);
		}
		
		bankMaster.setPage_count(page_count);
		List<Map<String, Object>> list = bankMasterService.list(bankMaster);
		model.addAttribute("list", list);
		
		int count = bankMasterService.count(bankMaster);
		bankMaster.setCount(count);
		bankMaster.setTotal_page((bankMaster.getCount()/bankMaster.getPage_count())<1 ? 1 : ((bankMaster.getCount()/bankMaster.getPage_count())+((bankMaster.getCount()%bankMaster.getPage_count()) > 0 ? 1 : 0)));
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", bankMaster.getPage());
		model.addAttribute("total_page", bankMaster.getTotal_page());
		
		return "admin/bankMaster/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute BankMaster bankMaster, Model model){
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		model.addAttribute("page", bankMaster.getPage());
		model.addAttribute("total_page", bankMaster.getTotal_page());
		
		return "admin/bankMaster/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute BankMaster bankMaster, Model model){
		
		bankMaster.setCreate_by(accountSession.getAccount());
		bankMaster.setUpdate_by(accountSession.getAccount());
		bankMasterService.add(bankMaster);
		
		//分頁
		model.addAttribute("page", bankMaster.getPage());
		model.addAttribute("total_page", bankMaster.getTotal_page());
		
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute BankMaster bankMaster, Model model){
		
		Integer page = bankMaster.getPage();
		Integer total_page = bankMaster.getTotal_page();
		
		//取得學校資料
		bankMaster = bankMasterService.data(bankMaster);
		model.addAttribute("bankMaster", bankMaster);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		//分頁
		bankMaster.setPage(page);
		bankMaster.setTotal_page(total_page);
		
		return "admin/bankMaster/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute BankMaster bankMaster, Model model){
		
		bankMaster.setUpdate_by(accountSession.getAccount());
		bankMasterService.update(bankMaster);
		
		//分頁
		model.addAttribute("page", bankMaster.getPage());
		model.addAttribute("total_page", bankMaster.getTotal_page());
		
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute BankMaster bankMaster, Model model) {
		
		bankMasterService.delete(bankMaster);
		
		//分頁
		model.addAttribute("page", bankMaster.getPage());
		model.addAttribute("total_page", bankMaster.getTotal_page());
		
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
	@RequestMapping(value = "/import/excel" , method = {RequestMethod.GET, RequestMethod.POST})
	public String importExcel(
			@SessionAttribute("accountSession")Account accountSession,
			@ModelAttribute BankMaster bankMaster,
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
	        	String address = sheet.getCell(2, i).getContents() == "" ? "" : sheet.getCell(2, i).getContents();
	        	String sort = String.valueOf(bankMasterService.nextSort());
	        	String display = "1";
	        	
	        	if("".equals(name)) {
	        		break;
	        	}
	        	
	        	bankMaster = new BankMaster();
	        	bankMaster.setCode(code);
	        	bankMaster.setName(name);
	        	bankMaster.setAddress(address);
	        	bankMaster.setSort(sort);
	        	bankMaster.setDisplay(display);
	        	bankMaster.setCreate_by(accountSession.getAccount());
	        	bankMaster.setUpdate_by(accountSession.getAccount());
	        	
				bankMasterService.add(bankMaster);

	        }
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("PATH", "/tkbrule/bank/list");
		return "admin/path";
		
	}
	
}
