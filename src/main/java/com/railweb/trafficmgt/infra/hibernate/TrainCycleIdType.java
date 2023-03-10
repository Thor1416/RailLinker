package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TrainCycleId;

public class TrainCycleIdType extends DomainObjectIdCustomType<TrainCycleId> {

	private static final DomainObjectIdTypeDescriptor<TrainCycleId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrainCycleId>(TrainCycleId.class,TrainCycleId::new);
	
	public TrainCycleIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
