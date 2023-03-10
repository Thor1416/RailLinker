package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.PowerSystemId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalListId;

public class TimeIntervalListIdType extends DomainObjectIdCustomType<TimeIntervalListId> {

	private static final DomainObjectIdTypeDescriptor<TimeIntervalListId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TimeIntervalListId>(TimeIntervalListId.class,TimeIntervalListId::new);
	
	public TimeIntervalListIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
