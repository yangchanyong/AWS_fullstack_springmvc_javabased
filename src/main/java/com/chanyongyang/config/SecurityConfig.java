package com.chanyongyang.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.chanyongyang.security.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	
	@Override //secutiry-context의 security-http와 같음
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new CharacterEncodingFilter("utf-8", true), CsrfFilter.class);
		http.authorizeRequests()
		.antMatchers("/sample/admin").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/sample/member").access("hasRole('ROLE_MANAGER')");
		
		http.formLogin().loginPage("/member/login");
		http.logout().invalidateHttpSession(true).logoutUrl("/member/logout").logoutSuccessUrl("/");
		
//		remember-me 추가2 / 																7일
		http.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60 * 60 * 24 * 7);
	}

	@Override // security-context의 authentication-manager와 같음
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		String user_by_username_query = "select userid username, userpw password, enabled from tbl_member where userid = ?";
//		String authorities_by_username_query = "select userid username, auth authority from tbl_auth where userid = ?";
//		auth.jdbcAuthentication()
//		.dataSource(dataSource)
//		.passwordEncoder(passwordEncoder())
//		.usersByUsernameQuery(user_by_username_query)
//		.authoritiesByUsernameQuery(authorities_by_username_query);
		// 더이상 위 구문을 사용하지 않고 아래 구문을 사용하겠다는 뜻
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	remember-me 추가
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl repositoryImpl = new JdbcTokenRepositoryImpl();
		repositoryImpl.setDataSource(dataSource);
		return repositoryImpl;
	}
	
}
