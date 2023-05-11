package com.tenco.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * 
 * 권한 부여 페이지 설정
 *	/auth/joinPage
 *	/auth/loginPage
 *	/auth/**
 *
 */


@Configuration // IoC 등록
@EnableWebSecurity // 시큐리티 필터로 등록을 해라 (필터 커스텀)
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증 처리를 미리 체크 하겠다. 
public class SecurityConfig  extends WebSecurityConfigurerAdapter{ 

	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	// 특정 주소 필터를 설정할 예정 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable(); // csrf 토큰 비활성화 처리(테스트시 사용권장)
		
		http.authorizeHttpRequests()
		.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/images/**")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/auth/loginPage");
	}
}
