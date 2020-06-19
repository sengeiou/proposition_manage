package com.tkb.manage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tkb.manage.controller.admin.FunctionController;
import com.tkb.manage.model.Account;
import com.tkb.manage.model.Function;
import com.tkb.manage.service.TeacherAccountService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private FunctionController functionController;
	
	@Autowired
	private TeacherAccountService teacherAccountService;
	
	@Value("${front.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/index" , method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, @SessionAttribute("accountSession") Account accountSession) {
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "front/index";
	}
	
	@RequestMapping(value = "/path" , method = {RequestMethod.GET, RequestMethod.POST})
	public String path(Model model, @SessionAttribute("accountSession") Account accountSession,
		@ModelAttribute Function function) {
		
		return "front/path";
	}
	
	@RequestMapping(value = "/teacher" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		//設定每頁筆數
		Integer page_count = 20;
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
		
		return "front/teacherAccount/list";
    }
	
}
