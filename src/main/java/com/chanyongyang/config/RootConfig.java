package com.chanyongyang.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j;

@Configuration
@ComponentScan({"com.chanyongyang.task", "com.chanyongyang.service", "com.chanyongyang.domain"})
@MapperScan("com.chanyongyang.mapper") //mapper 스캔
@PropertySource("classpath:/jdbc.properties")
@Log4j
public class RootConfig {
	
	@Autowired
	private Properties properties;
	
	@Value("${db.derverClassName}")
	private String driverClassName;
	@Value("${db.jdbcUrl}")
	private String jdbcUrl;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	
//	hikari 설정
	@Bean
	public DataSource dataSource() throws IOException {
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		
		
//		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
//		config.setJdbcUrl("jdbc:log4jdbc:mariadb://np.chanyongyang.com:3306/spring_db");
//		config.setUsername("SAMPLE");
//		config.setPassword("1234");
		
		return new HikariDataSource(config);
	}
	
	// mybatis 설정
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setTypeAliasesPackage("com.chanyongyang.domain");
		return bean.getObject();
	}
//	트랜잭션 설정
	@Bean
	public DataSourceTransactionManager transactionManager() throws IOException {
		return new DataSourceTransactionManager(dataSource());
	}
	
	//Gson 등록
	@Bean
	public Gson gson() {
		return new Gson();
	}
}
