package com.tkb.manage.controller.admin;

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

import com.tkb.manage.model.Account;
import com.tkb.manage.model.Function;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule")
public class MainController {
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/index" , method = {RequestMethod.GET, RequestMethod.POST})
	public String index(Model model, @SessionAttribute("accountSession") Account accountSession) {
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/index";
	}
	
	@RequestMapping(value = "/path" , method = {RequestMethod.GET, RequestMethod.POST})
	public String path(Model model, @SessionAttribute("accountSession") Account accountSession,
		@ModelAttribute Function function) {
		
		return "admin/path";
	}
	
}
