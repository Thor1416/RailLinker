package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.RundayId;

public class RundayIdType extends DomainObjectIdCustomType<RundayId> {

	private static final DomainObjectIdTypeDescriptor<RundayId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<RundayId>(RundayId.class,RundayId::new);
	
	public RundayIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
