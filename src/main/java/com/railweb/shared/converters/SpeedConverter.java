package com.railweb.shared.converters;

import javax.measure.Quantity;
import javax.measure.format.QuantityFormat;
import javax.measure.quantity.Speed;
import javax.measure.spi.ServiceProvider;
import javax.persistence.AttributeConverter;

public class SpeedConverter implements AttributeConverter<Quantity<Speed>, String> {
	
	private static final QuantityFormat FORMAT = ServiceProvider.current().getFormatService().getQuantityFormat();

	@Override
	public String convertToDatabaseColumn(Quantity<Speed> attribute) {
		if (attribute == null) {
			return null;
		}
		return FORMAT.format(attribute);
	}

	@Override
	public Quantity<Speed> convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return null;
		}
		return (Quantity<Speed>) FORMAT.parse(dbData);
	}

}
