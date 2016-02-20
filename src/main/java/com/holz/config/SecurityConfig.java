package com.holz.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource)
		.passwordEncoder(passwordEncoder())
		.usersByUsernameQuery(
				"select username,password, enabled, farmId from USERS where username=?")
				.authoritiesByUsernameQuery(
						"select username, role from ROLES where username=?");
	}	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/resources/**", "/login","/resetPassword", "/login?logout").permitAll()
		.antMatchers("/admin/**").access("hasRole('Admin')")
		.antMatchers("/**").access("hasRole('Default')")
		.and()
		.formLogin().loginPage("/login").failureUrl("/login?error").failureHandler(authenticationFailureHandler())
		.usernameParameter("username").passwordParameter("password")
		.and()
		.logout().logoutSuccessUrl("/login?logout")
		.and()
		.exceptionHandling().accessDeniedPage("/403")
		.and()
		.csrf();
		//.and().requiresChannel().anyRequest().requiresSecure();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler(){
		return new LoginFailureHandler();		
	}
}