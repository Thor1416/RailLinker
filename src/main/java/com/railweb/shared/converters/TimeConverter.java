package com.railweb.shared.converters;

import javax.measure.Quantity;
import javax.measure.format.QuantityFormat;
import javax.measure.quantity.Time;
import javax.measure.spi.ServiceProvider;
import javax.persistence.AttributeConverter;

public class TimeConverter implements AttributeConverter<Quantity<Time>, String> {
	private static final QuantityFormat FORMAT = ServiceProvider.current().getFormatService().getQuantityFormat();

	@Override
	public String convertToDatabaseColumn(Quantity<Time> attribute) {
		if (attribute == null) {
			return null;
		}
		return FORMAT.format(attribute);
	}

	@Override
	public Quantity<Time> convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return null;
		}
		return (Quantity<Time>) FORMAT.parse(dbData);
	}

}
