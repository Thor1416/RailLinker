package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TrainRouteId;

public class TrainRouteIdType extends DomainObjectIdCustomType<TrainRouteId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<TrainRouteId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TrainRouteId>(TrainRouteId.class,TrainRouteId::new);
	

	public TrainRouteIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
