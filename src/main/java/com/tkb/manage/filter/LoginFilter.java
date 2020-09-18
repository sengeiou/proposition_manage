package com.tkb.manage.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;

import com.google.gson.Gson;
import com.tkb.manage.model.Account;

public class LoginFilter implements Filter {
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().cachePrivate().mustRevalidate().getHeaderValue());
		
//		Gson gson = new Gson();
//		System.out.println(request.getRemoteAddr());
//		System.out.println(request.getMethod());
//		System.out.println(request.getRequestURI());
//		System.out.println(gson.toJson(request.getParameterMap()));
		
		Account accountSession = (Account)request.getSession().getAttribute("accountSession");
		
		//API
		if (request.getServletPath().indexOf("/api") >= 0 || request.getServletPath().indexOf("swagger") >= 0) {
			request.getRequestDispatcher(((HttpServletRequest) request).getServletPath()).forward(request, response);
		//已登入
		} else if (accountSession != null) {
			//如果已登入卻輸入此網址 則導向首頁
			if(request.getServletPath().equals("/tkbrule/login") || request.getServletPath().equals("/tkbrule/") || request.getServletPath().equals("/tkbrule")) {
				response.sendRedirect(request.getContextPath() + "/tkbrule/index");
			} else if(request.getServletPath().equals("/manager/login") || request.getServletPath().equals("/teacher/login") || request.getServletPath().equals("/") || request.getServletPath().equals("")) {
				response.sendRedirect(request.getContextPath() + "/index");
			} else {
				filterChain.doFilter(request, response);
			}
		//未登入
		} else {
//			//請求至登入頁或執行登入動作 則導至請求的目的端
			if(request.getServletPath().equals("/tkbrule/login") || request.getServletPath().equals("/tkbrule/manager/doLogin") || request.getServletPath().equals("/tkbrule/teacher/doLogin") || request.getServletPath().equals("/tkbrule/logout")) {
				filterChain.doFilter(request, response);
			} else if(request.getServletPath().equals("/manager/login") || request.getServletPath().equals("/teacher/login") || request.getServletPath().equals("/teacher/register") || request.getServletPath().equals("/teacher/registerSubmit") || request.getServletPath().equals("/teacher/check") || request.getServletPath().equals("/teacher/send/verify") || request.getServletPath().equals("/manager/doLogin") || request.getServletPath().equals("/teacher/doLogin") || request.getServletPath().equals("/logout")) {
				filterChain.doFilter(request, response);
//			//請求至非登入頁 或執行非登入動作 導至登入頁	
			} else {
				//自動導至網站首頁
//				response.sendRedirect("/");
				if(request.getServletPath().indexOf("/tkbrule") >= 0) {
					request.getRequestDispatcher("/tkbrule/login").forward(request, response);
				} else {
					if(request.getServletPath().indexOf("/admin") >= 0) {
						filterChain.doFilter(request, response);
					} else if(request.getServletPath().indexOf("/front") >= 0) {
						filterChain.doFilter(request, response);
					} else {
						request.getRequestDispatcher("/").forward(request, response);
					}
				}
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
}
