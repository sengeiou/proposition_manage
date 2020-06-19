package com.tkb.manage.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tkb.manage.model.Account;
import com.tkb.manage.model.Identity;
import com.tkb.manage.model.LoginLog;
import com.tkb.manage.service.IdentityService;
import com.tkb.manage.service.LoginLogService;
import com.tkb.manage.service.ManagerAccountService;
import com.tkb.manage.service.TeacherAccountService;

@Controller
@RequestMapping("/tkbrule")
public class AdminLoginController {
	
	@Autowired
	private ManagerAccountService managerAccountService;
	
	@Autowired
	private TeacherAccountService teacherAccountService;
	
//	@Autowired
//	private TKBAPIService tkbapiService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private LoginLogService loginLogService;
	
	@RequestMapping(value = "/login" , method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@ModelAttribute Account account, Model model){
		return "admin/login";
    }
	
	@RequestMapping(value = "/manager/doLogin" , method = {RequestMethod.POST})
	public String managerDoLogin(@ModelAttribute Account account, HttpSession session,
//		@RequestParam(value="account",required = true) String account,
//		@RequestParam(value="password",required = true) String password,
		Model model,
		HttpServletRequest pRequest){
		
		String msg = "";
		String ip = pRequest.getRemoteAddr();
		
//		account.setAccount(account);
//		managerAccount.setPassword(password);
		
		Account login = managerAccountService.login(account);
		
		LoginLog loginLog = new LoginLog();
		
		//檢查帳密
		if(login != null) {
			
			//檢查權限
			if("1".equals(login.getStatus())) {
				
				//取得等級
				Identity identity = new Identity();
				identity.setId(login.getIdentity_id());
				identity = identityService.data(identity);
				if(identity != null) {
					login.setLevel(identity.getLevel());
				}
				session.setAttribute("accountSession", login);
				session.setMaxInactiveInterval(60* 60);
				model.addAttribute("PATH", "/tkbrule/index");
				loginLog.setAccount(account.getAccount());
				loginLog.setPassword(account.getPassword());
				loginLog.setStatus("1");
				loginLog.setMsg("登入成功");
				loginLog.setType("1");
				loginLog.setIp(ip);
				loginLogService.add(loginLog);
				return "admin/path";
				
			} else {
				msg = "無權限登入";
				loginLog.setAccount(account.getAccount());
				loginLog.setPassword(account.getPassword());
				loginLog.setStatus("0");
				loginLog.setMsg("無權限登入");
				loginLog.setType("1");
				loginLog.setIp(ip);
				loginLogService.add(loginLog);
				model.addAttribute("msg", msg);
				return "admin/login";
			}
			
		} else  {
			msg = "帳號或密碼錯誤";
			loginLog.setAccount(account.getAccount());
			loginLog.setPassword(account.getPassword());
			loginLog.setStatus("0");
			loginLog.setMsg("帳號或密碼錯誤");
			loginLog.setType("1");
			loginLog.setIp(ip);
			loginLogService.add(loginLog);
			model.addAttribute("msg", msg);
			return "admin/login";
		}
		
	}
	
	@RequestMapping(value = "/teacher/doLogin" , method = {RequestMethod.POST})
	public String teacherDoLogin(@ModelAttribute Account account, HttpSession session,
//		@RequestParam(value="account",required = true) String account,
//		@RequestParam(value="password",required = true) String password,
		Model model,
		HttpServletRequest pRequest){
		
		String msg = "";
		String ip = pRequest.getRemoteAddr();
		
//		teacherAccount.setAccount(account);
//		teacherAccount.setPassword(password);
		
		Account login = teacherAccountService.login(account);
		
		LoginLog loginLog = new LoginLog();
		
		//檢查帳密
		if(login != null) {
			
			//檢查權限
			if("1".equals(login.getStatus())) {
				
				//取得等級
				Identity identity = new Identity();
				identity.setId(login.getIdentity_id());
				identity = identityService.data(identity);
				if(identity != null) {
					login.setLevel(identity.getLevel());
				}
				session.setAttribute("accountSession", login);
				session.setMaxInactiveInterval(60* 60);
				model.addAttribute("PATH", "index");
				loginLog.setAccount(account.getAccount());
				loginLog.setPassword(account.getPassword());
				loginLog.setStatus("1");
				loginLog.setMsg("登入成功");
				loginLog.setType("1");
				loginLog.setIp(ip);
				loginLogService.add(loginLog);
				return "admin/path";
				
			} else {
				msg = "無權限登入";
				loginLog.setAccount(account.getAccount());
				loginLog.setPassword(account.getPassword());
				loginLog.setStatus("0");
				loginLog.setMsg("無權限登入");
				loginLog.setType("1");
				loginLog.setIp(ip);
				loginLogService.add(loginLog);
				model.addAttribute("msg", msg);
				return "admin/login";
			}
			
		} else  {
			msg = "帳號或密碼錯誤";
			loginLog.setAccount(account.getAccount());
			loginLog.setPassword(account.getPassword());
			loginLog.setStatus("0");
			loginLog.setMsg("帳號或密碼錯誤");
			loginLog.setType("1");
			loginLog.setIp(ip);
			loginLogService.add(loginLog);
			model.addAttribute("msg", msg);
			return "admin/login";
		}
		
	}
	
	@RequestMapping(value = "/logout", method = {RequestMethod.GET})
	public String logout(@ModelAttribute Account account, HttpSession session, Model model){
		
		if(session.getAttribute("accountSession") != null){
			session.removeAttribute("accountSession");
		}
		
		return "admin/login";
	}
	
}
