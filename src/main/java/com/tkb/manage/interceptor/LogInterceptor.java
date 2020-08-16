package com.tkb.manage.interceptor;

public class LogInterceptor {
//public class LogInterceptor implements HandlerInterceptor {
	
//	@Autowired
//	HistoryService	userLogService;
//	
//	UserLog userLog = new UserLog();
//	
////	private static final String LOGGER_SEND_TIME = "_send_time";
////	private static final String LOGGER_ENTITY = "_logger_entity";
////	private HttpSession session;
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse reponse, Object o)
//	throws Exception {
//		
////		System.out.println("----------------Log Start------------------------");
//		
//		Gson gson = new Gson();
//		
//		//spring security取的方法
////		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
////		if (principal instanceof UserDetails) {
////			
////		    String username = ((UserDetails)principal).getUsername();
////		    System.out.println("username : " + username);
////		    userLog.setUsername(username);
////		    
////		} else {
////			
////		    String username = principal.toString();
////		    System.out.println("pre username : " + username);
////		    userLog.setUsername(username);
////		    
////		}
//		
//		
//		
//		
////		System.out.println("LOGGER_SEND_TIME : " + System.currentTimeMillis());
//	  	
//		ManagerAccount managerAccountSession = (ManagerAccount) request.getSession().getAttribute("managerAccountSession");
//		
//		if (managerAccountSession != null) {
////		    System.out.println("User : " + managerAccountSession.getAccount());
//		    userLog.setUser(managerAccountSession.getAccount());
//		} else {
////			System.out.println("User : 0 ");
//			userLog.setUser("0");
//		}
//		
//		
////		System.out.println("Ip : " + request.getRemoteAddr());
////		System.out.println("Method : " + request.getMethod());
//		// display /tkbrule/login
////		System.out.println("URI : " + request.getRequestURI());
//		// display http://172.16.131.38:8060/tkbrule/login
////		System.out.println("URL : " + request.getRequestURL());
//		String Args = gson.toJson(request.getParameterMap());
////		System.out.println("Args : " + Args);
//		
//        userLog.setIp(request.getRemoteAddr());
//        userLog.setMethod(request.getMethod());
//        userLog.setUrl(request.getRequestURI());
//        userLog.setArgs(Args);
//		
////		request.setAttribute(LOGGER_SEND_TIME,System.currentTimeMillis());
//		
//		// let afterCompletion() to call
////		request.setAttribute(LOGGER_ENTITY,userLog);
//		
//		return true;
//		
//	}
//	
//	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//			Object o) throws Exception {
//		
//	}
//	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o,
//			Exception e) throws Exception {
//		
//		
//		int status = response.getStatus();
//		
//		userLog.setResponse(Integer.toString(status));
//		
////		long currentTime = System.currentTimeMillis();
//		
////		long time = Long.valueOf(request.getAttribute(LOGGER_SEND_TIME).toString());
//		
////		UserLog userLog = (UserLog) request.getAttribute(LOGGER_ENTITY);
//		
//		
////		userLog.setTime(Integer.valueOf((currentTime - time)+""));
//		
////		System.out.println("status : " + status);
////		System.out.println("----------------Log End------------------------");
//		userLogService.addLog(userLog);
//		
//		
//	}
	

}
