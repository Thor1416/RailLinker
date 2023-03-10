package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TimeIntervalListId;
import com.railweb.trafficmgt.domain.ids.TrainClassId;

public class TrainClassIdType extends DomainObjectIdCustomType<TrainClassId> {

	private static final DomainObjectIdTypeDescriptor<TrainClassId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrainClassId>(TrainClassId.class,TrainClassId::new);
	
	public TrainClassIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
