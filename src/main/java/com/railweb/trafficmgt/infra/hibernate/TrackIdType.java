package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TrackId;

public class TrackIdType extends DomainObjectIdCustomType<TrackId> {
	
	private static final DomainObjectIdTypeDescriptor<TrackId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrackId>(TrackId.class,TrackId::new);
	
	public TrackIdType() {
		super(TYPE_DESCRIPTOR);
	}
	
}
