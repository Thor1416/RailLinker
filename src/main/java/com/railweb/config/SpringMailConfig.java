package com.railweb.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
@PropertySource("classpath:mail/emailConfig.properties")
public class SpringMailConfig implements ApplicationContextAware, EnvironmentAware {

	public static final String EMAIL_TEMPLATE_ENCODING ="UTF-8";
	
	private static final String JAVA_MAIL_FILE ="classpath:mail/javamail.properties";
	private static final String HOST = "mail.server.host";
	private static final String PORT = "mail.server.port";
	private static final String PROTOCOL = "mail.server.protocol";
	private static final String USERNAME = "mail.server.username";
	private static final String PASSWORD = "mail.server.password";
 
	private Environment env;
	private ApplicationContext appCTX;
	
	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appCTX = applicationContext;	
	}
	
	@Bean
	public JavaMailSender mailSender() throws IOException{
		
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		// basic mail sender configuration
		mailSender.setHost(this.env.getProperty(HOST));
		mailSender.setPort(Integer.parseInt(this.env.getProperty(PORT)));
		mailSender.setProtocol(this.env.getProperty(PROTOCOL));
		mailSender.setUsername(USERNAME);
		mailSender.setPassword(PASSWORD);
		
		//JavaMail-specific configuration
		final Properties javaMailProperties = new Properties();
		javaMailProperties.load(this.appCTX.getResource(JAVA_MAIL_FILE).getInputStream());
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	}
	
	@Bean
	public ResourceBundleMessageSource emailMessageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mail/MailMessages");
		return messageSource; 
	}
	
	@Bean
	public TemplateEngine emailTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		// add Resolver for TEXT Emails
		templateEngine.addTemplateResolver(textTemplateResolver());
		//Resolver for HTML emails except the editables
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		// Resolver for editable HTML email
		templateEngine.addTemplateResolver(stringTemplateResolver());
		//Internationalization
		templateEngine.setTemplateEngineMessageSource(emailMessageSource());
		
		return templateEngine;
	}

	private ITemplateResolver stringTemplateResolver() {
		final StringTemplateResolver templateResolver = new StringTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(3));
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}

	private ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
		templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	private ITemplateResolver textTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
		templateResolver.setPrefix("/mail/");
		templateResolver.setSuffix(".txt");
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}
}
