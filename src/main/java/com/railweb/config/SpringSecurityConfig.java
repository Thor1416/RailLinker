package com.railweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.railweb.usermgt.repo.RoleRepository;
import com.railweb.usermgt.repo.UserRepository;
import com.railweb.usermgt.service.UserDetailsServiceImpl;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl(this.userRepo,this.roleRepo);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
//		http.csrf().disable()
//			.requiresChannel()
//				.antMatchers("/login","/admin*","/traffic/**","rail/**")
//				.requiresSecure();
//		http.sessionManagement().sessionFixation().none();
		http.authorizeRequests()
			.antMatchers("/css/**","/js/**","/images/**","/webjars/**","/login").permitAll()
			.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
			.antMatchers("/rail/**").hasAnyAuthority("RAIL")
			.antMatchers("/traffic/*").hasAnyAuthority("TRAFFIC")
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
			.and()
			.logout()
				.permitAll()
				.logoutUrl("/logout")
				.and()
			.exceptionHandling().accessDeniedPage("/error/403");
	}
	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults("");
	}
	
	
}
