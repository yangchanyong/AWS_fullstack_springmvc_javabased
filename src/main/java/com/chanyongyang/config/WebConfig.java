package com.chanyongyang.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// 05-01 SecurityConfig 추가
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override // 파일 위치 경로
	protected void customizeRegistration(Dynamic registration) {
		MultipartConfigElement configElement = new MultipartConfigElement("c:/upload/tmp");
		registration.setMultipartConfig(configElement);
	}

//	@Override // 한글깨짐처리
//	protected Filter[] getServletFilters() {
//		// 04-27
//		// security Filter 추가 
//		// 05-01 주석처리 DelegatingFilterProxy는 더이상 사용하지 않음 
////		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy(); 
//		CharacterEncodingFilter filter = new CharacterEncodingFilter("utf-8", true);
//		return new Filter[] {filter};
//	}
	
	
	
}
