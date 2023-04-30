package com.ianhearne.dungeonnotes;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery("select email, password, enabled from users where email=?")
				.authoritiesByUsernameQuery("select email, authority from authorities where email=?");
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
					.antMatchers("/", "/register").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin((form) -> form
					.loginPage("/login")
					.permitAll()
			)
			.logout((logout) -> logout.permitAll());
		
		return http.build();
	}	
}
