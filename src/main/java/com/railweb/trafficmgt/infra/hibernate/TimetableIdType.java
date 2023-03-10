package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.TimetableId;

public class TimetableIdType extends DomainObjectIdCustomType<TimetableId> {

	private static final DomainObjectIdTypeDescriptor<TimetableId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TimetableId>(TimetableId.class,TimetableId::new);
	
	
	public TimetableIdType(DomainObjectIdTypeDescriptor<TimetableId> domainObjectIdTypeDescriptor) {
		super(TYPE_DESCRIPTOR);
	}

}
