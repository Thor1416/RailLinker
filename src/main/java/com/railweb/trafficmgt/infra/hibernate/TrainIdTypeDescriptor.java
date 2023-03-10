package com.railweb.trafficmgt.infra.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import com.railweb.trafficmgt.domain.ids.TrainId;

public class TrainIdTypeDescriptor extends AbstractTypeDescriptor<TrainId> {
	public TrainIdTypeDescriptor() {
		super(TrainId.class);
	}

	@Override
	public TrainId fromString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> X unwrap(TrainId value, Class<X> type, WrapperOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> TrainId wrap(X value, WrapperOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

}
