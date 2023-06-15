package com.railweb.shared.converters;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LengthConverter implements AttributeConverter<Quantity<Length>,Float> {

	@Override
	public Float convertToDatabaseColumn(Quantity<Length> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quantity<Length> convertToEntityAttribute(Float dbData) {
		// TODO Auto-generated method stub
		return null;
	}

}
