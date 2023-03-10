package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TrainGroupId;

public class TrainGroupIdType extends DomainObjectIdCustomType<TrainGroupId> {

	private static final DomainObjectIdTypeDescriptor<TrainGroupId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrainGroupId>(TrainGroupId.class,TrainGroupId::new);
	
	public TrainGroupIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
