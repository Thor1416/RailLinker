package com.railweb.shared.converters;


import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter(autoApply=true)
public class DurationConverter implements AttributeConverter<Duration,Long> {
 
	private static Logger logger = LoggerFactory.getLogger(DurationConverter.class);
			
	@Override
	public Long convertToDatabaseColumn(Duration attribute) {
		if (attribute == null) {
			return null;
		}
		logger.info("Convert to Long");
		return attribute.toNanos();
	}

	@Override
	public Duration convertToEntityAttribute(Long dbData) {
		if(dbData == null) {
			return null;
		}
		logger.info("Convert to duration");
		return Duration.of(dbData, ChronoUnit.NANOS);
	}

}
