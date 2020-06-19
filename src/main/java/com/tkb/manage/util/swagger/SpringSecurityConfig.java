package com.tkb.manage.util.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 因為再註冊時寫入密碼是經過Security加密演算法加密 所以Spring security如果解密長度不到會直接判定是錯誤的密碼
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/**
	 * 配置白名單
	 */
//	private static final String[] AUTH_WHITELIST = {
			// -- swagger ui
			// "/swagger-resources/**",
			// "/swagger-ui.html",
//			"/v2/api-docs"
			// "/webjars/**"
//	};

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		String password = passwordEncoder().encode("password");
//		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("user").password(password)
//				.roles("USER");
//	}

	/**
	 * Spring security 簡單的路由權限設定 API與Web不同於路由會設定到static 一般的css,js,image資料夾權限
	 * 但是API只需對路由的身份(權限)做驗證
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/api/**").permitAll();
//				.permitAll();
//				.permitAll().antMatchers();
//				.access("hasIpAddress('123.51.220.96/32')");
		// .antMatchers("/v2/api-docs").authenticated().and()
		// .httpBasic();

		// .antMatcher()
		// .anyRequest()
		// .authenticated();

		/**
		 * 再使用路由前先使用jwt驗證是否有jwt的token
		 */
		// Add our custom JWT security filter
		// http.addFilterAfter(KeyAuthenticationFilter(),BasicAuthenticationFilter.class);
		// http.addFilterBefore(jwtAuthenticationFilter(),
		// UsernamePasswordAuthenticationFilter.class);

	}
	
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//		registry.addResourceHandler("/download/**").addResourceLocations("classpath:/static/upload/");
//		registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/static/file_system_upload/");
//		registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/static/upload/");
	}
	
}
