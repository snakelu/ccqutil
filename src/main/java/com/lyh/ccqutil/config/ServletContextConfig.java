package com.lyh.ccqutil.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.lyh.ccqutil.Application;
import com.lyh.ccqutil.filter.LoginFilter;

@Configuration
@ComponentScan(basePackageClasses = Application.class, useDefaultFilters = true)
public class ServletContextConfig extends WebMvcConfigurationSupport {

	static final private String FAVICON_URL = "/index.html";

	/**
	 * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/").addResourceLocations("/**");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}

	/**
	 * 配置servlet处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginFilter()).addPathPatterns("/**").excludePathPatterns(FAVICON_URL);
	}

}