package com.tkb.manage.util.swagger;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2的設定檔 UrL:http://localhost:8562/swagger-ui.html#/
 * 
 * @author cheng
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
	@Bean
	public Docket api() {
		/**
		 * clazz 存放想要過慮位於Swagger下方Model屬性類別
		 * apis:是Controller可以設定用package方式掃描否則會掃描全專案
		 * paths:路徑可以再做更進階的掃描只掃描某個Url底下的路由 build:建立Swagger
		 */
		Class[] clazz = { Accessible.class, AccessibleContext.class };
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.tkb.manage.controller.api"))
				.paths(PathSelectors.any()).build().apiInfo(metaData()).ignoredParameterTypes(clazz);

	}

	/**
	 * 設定位於Swaager上方的敘述
	 * 
	 * @return
	 */
	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("素養教案與命題管理 API 服務").description("\"素養教案與命題管理 API 服務\"").version("1.0.0")
				.license("中華未來教育學會 Version 1.0").licenseUrl("").build();
	}

}
