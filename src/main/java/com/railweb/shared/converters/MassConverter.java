package com.railweb.shared.converters;

import javax.measure.Quantity;
import javax.measure.format.QuantityFormat;
import javax.measure.quantity.Mass;
import javax.measure.spi.ServiceProvider;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MassConverter implements AttributeConverter<Quantity<Mass>,String> {

	private static final QuantityFormat FORMAT =
			ServiceProvider.current().getFormatService().getQuantityFormat();
	
	@Override
	public String convertToDatabaseColumn(Quantity<Mass> attribute) {
		if(attribute==null) {
			return null;
		}
		return FORMAT.format(attribute);
	}

	@Override
	public Quantity<Mass> convertToEntityAttribute(String dbData) {
		if(dbData==null) {
			return null;
		}
		return (Quantity<Mass>) FORMAT.parse(dbData);
	}

}
