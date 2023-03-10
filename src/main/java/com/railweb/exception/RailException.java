package com.railweb.exception;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.railweb.config.PropertiesConfig;

@Component
public class RailException {

	private static PropertiesConfig propertiesConfig;
	
	@Autowired
	public RailException(PropertiesConfig propertiesConfig) {
		RailException.propertiesConfig = propertiesConfig;
	}
	

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
	public static RuntimeException throwException(String messageTemplate, String... args) {
		return new  RuntimeException(format(messageTemplate, args));
	}
	
	public static RuntimeException throwException(EntityType entityType, 
			ExceptionType exceptionType, String...args) {
		String messageTemplate = getMessageTemplate(entityType, exceptionType);
		return throwException(exceptionType, messageTemplate,args);
	}
	
	public static RuntimeException throwExceptionWithId(EntityType entityType,
			ExceptionType exceptionType, String id, String... args) {
		String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id);
		return throwException(exceptionType, messageTemplate,args);
	}
	
	private static String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
		return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
	}
	
	private static RuntimeException throwException(ExceptionType exceptionType, 
			String messageTemplate, String... args) {
		
		RuntimeException returnException;
		
		switch (ExceptionType.valueOf(exceptionType.getValue())) {
		case ENTITY_NOT_FOUND:
			returnException = new EntityNotFoundException(format(messageTemplate,args));
			break; 
		case DUPLICATE_ENTITY:
			returnException = new DuplicateEntityException(format(messageTemplate,args));
			break;
		case ENTITY_EXCEPTION:
			returnException = new EntityException(format(messageTemplate,args));
			break;
		default:
			returnException = new RuntimeException(format(messageTemplate,args));
			break;
		}
		
		return returnException;
	}

	private static String format(String template, String... args) {
		Optional<String> templateContent = Optional.ofNullable(propertiesConfig.getConfigValue(template));
		if(templateContent.isPresent()) {
			return MessageFormat.format(templateContent.get(), (Object[])args);
		}
		return String.format(template, (Object[])args);
	}
	
	private static class EntityNotFoundException extends RuntimeException{
		public EntityNotFoundException(String message) {
			super(message);
		}
	}
	private static class DuplicateEntityException extends RuntimeException{
		public DuplicateEntityException(String message) {
			super(message);
		}
	}private static class EntityException extends RuntimeException{
		public EntityException(String message) {
			super(message);
		}
	}
}
