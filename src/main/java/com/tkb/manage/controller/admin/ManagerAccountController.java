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
import com.tkb.manage.model.Identity;
import com.tkb.manage.service.IdentityService;
import com.tkb.manage.service.ManagerAccountService;

@Controller
@SessionAttributes("accountSession")
@RequestMapping("/tkbrule/manager/account")
public class ManagerAccountController {
	
	@Autowired
	private ManagerAccountService managerAccountService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private FunctionController functionController;
	
	@Value("${admin.menu.name}") 
	private String menuName;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.POST, RequestMethod.GET})
    public String list(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		List<Map<String, Object>> list = managerAccountService.list(account);
		model.addAttribute("list", list);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/managerAccount/list";
    }
	
	@RequestMapping(value = "/add" , method = {RequestMethod.POST, RequestMethod.GET})
    public String add(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		//取得身份清單
		Identity identity = new Identity();
		identity.setParent_id("0");
		List<Map<String, Object>> identityList = identityService.list(identity);
		model.addAttribute("identityList", identityList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/managerAccount/form";
    }
	
	@RequestMapping(value = "/addSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String addSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		account.setCreate_by(accountSession.getAccount());
		account.setUpdate_by(accountSession.getAccount());
		managerAccountService.add(account);
		
//		model.addAttribute("id", id);
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/update" , method = {RequestMethod.POST, RequestMethod.GET})
    public String update(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		//取得管理者資料
		account = managerAccountService.data(account);
		model.addAttribute("account", account);
		
		//取得身份清單
		Identity identity = new Identity();
		identity.setParent_id("0");
		List<Map<String, Object>> identityList = identityService.list(identity);
		model.addAttribute("identityList", identityList);
		
		//選單
		List<Map<String, Object>> menu = functionController.menu(accountSession, menuName);
		model.addAttribute("menu", menu);
		
		return "admin/managerAccount/form";
    }
	
	@RequestMapping(value = "/updateSubmit" , method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model){
		
		account.setUpdate_by(accountSession.getAccount());
		managerAccountService.update(account);
		
		model.addAttribute("PATH", "list");
		return "admin/path";
    }
	
	@RequestMapping(value = "/delete" , method = {RequestMethod.POST})
	public String delete(@SessionAttribute("accountSession") Account accountSession, @ModelAttribute Account account, Model model) {
		
		managerAccountService.delete(account);
		
		model.addAttribute("PATH", "list");
		return "admin/path";
	}
	
}
