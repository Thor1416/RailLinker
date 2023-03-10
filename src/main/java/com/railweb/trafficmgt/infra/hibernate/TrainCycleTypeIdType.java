package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TimeIntervalListId;
import com.railweb.trafficmgt.domain.ids.TrainCycleTypeId;

public class TrainCycleTypeIdType extends DomainObjectIdCustomType<TrainCycleTypeId> {

	private static final DomainObjectIdTypeDescriptor<TrainCycleTypeId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrainCycleTypeId>(TrainCycleTypeId.class,TrainCycleTypeId::new);
	
	public TrainCycleTypeIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
