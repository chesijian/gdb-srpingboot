package com.jrsoft.engine.config;

import com.jrsoft.engine.interceptor.BaseInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2020-07-13 9:51 AM
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private static final String[] ORIGINS = new String[] { "GET", "POST", "PUT", "DELETE" };
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new BaseInterceptor())
				.addPathPatterns("/*/**")
				.excludePathPatterns("/api_v1/token")
				.excludePathPatterns("/api_v1/wx/token")
				.excludePathPatterns("/api_v1/vcode")
				.excludePathPatterns("/api_v1/corp/**/*")
				.excludePathPatterns("/webjars/**")
				.excludePathPatterns("/swagger-resources/**")
				.excludePathPatterns("/swagger-ui.html")
				.excludePathPatterns("/v2/api-docs/**")
				.excludePathPatterns("/api/**")
				.excludePathPatterns("/configuration/ui/**")
				.excludePathPatterns("/configuration/security/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/**
		 * SpringBoot自动配置本身并不会把/swagger-ui.html
		 * 这个路径映射到对应的目录META-INF/resources/下面
		 * 采用WebMvcConfigurerAdapter将swagger的静态文件进行发布;
		 */
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		//将所有/static/** 访问都映射到classpath:/static/ 目录下
		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX +"/static/");

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods(ORIGINS)
				.maxAge(3600);
	}
}
