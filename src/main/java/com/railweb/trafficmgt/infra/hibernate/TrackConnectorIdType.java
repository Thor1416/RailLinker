package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TrackConnectorId;

public class TrackConnectorIdType extends DomainObjectIdCustomType<TrackConnectorId> {
	
	private static final DomainObjectIdTypeDescriptor<TrackConnectorId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrackConnectorId>(TrackConnectorId.class,TrackConnectorId::new);
	
	public TrackConnectorIdType(){
		super(TYPE_DESCRIPTOR);
	}
}
