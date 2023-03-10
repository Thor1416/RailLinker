package com.railweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages= {
		"com.railweb.usermgt.repo",
		"com.railweb.trafficmgt.repo"},
	transactionManagerRef="jpaTranasctionManager",
	entityManagerFactoryRef="jpaEntityManager")
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class PersistenceConfig {

	@Autowired
	private Environment env;
	
	public PersistenceConfig() {
		super();
	}
	
}
