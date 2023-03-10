package com.railweb.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.railweb.Application;

@SpringBootApplication
@Configuration
@EnableSpringConfigured
@EnableTransactionManagement
@EnableJpaRepositories(basePackages= {
		"com.railweb.usermgt.repo",
		"com.railweb.trafficmgt.infra.repo", "com.railweb.shared.repo"},
	transactionManagerRef="jpaTranasctionManager",
	entityManagerFactoryRef="entityManagerFactory")
@EntityScan(basePackages="com.railweb.model",basePackageClasses= {Application.class, Jsr310JpaConverters.class})
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class RailwebConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
					.setFieldMatchingEnabled(true)
					.setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
					.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		return modelMapper;
	}
	
}
