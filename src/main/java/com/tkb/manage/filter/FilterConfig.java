package com.tkb.manage.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	
	@Bean
    public LoginFilter adminLoginFilter(){
        return new LoginFilter();
    }
	
	@Bean
    public FilterRegistrationBean<LoginFilter> setAdminLoginFilter() {
        FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<LoginFilter>();
        filterRegistrationBean.setFilter(adminLoginFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(1);   //order的数值越小，在所有的filter中优先级越高
        return filterRegistrationBean;
    }
	
	@Bean
    public APIFilter apiFilter(){
        return new APIFilter();
    }
	
	@Bean
    public FilterRegistrationBean<APIFilter> setAPIFilter() {
        FilterRegistrationBean<APIFilter> filterRegistrationBean = new FilterRegistrationBean<APIFilter>();
        filterRegistrationBean.setFilter(apiFilter());
        filterRegistrationBean.addUrlPatterns("/api/*");
        filterRegistrationBean.setOrder(2);   //order的数值越小，在所有的filter中优先级越高
        return filterRegistrationBean;
    }
	
}
