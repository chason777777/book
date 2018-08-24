package com.chason.book.book;

import com.chason.book.book.loginInterceptor.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@MapperScan("com.chason.book.book.repository")
public class BookApplication extends WebMvcConfigurerAdapter {
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		//注册自定义拦截器，添加拦截路径和排除拦截路径
//		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
//				.excludePathPatterns("/user/login").excludePathPatterns("/user/logout");
//	}

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}
}
