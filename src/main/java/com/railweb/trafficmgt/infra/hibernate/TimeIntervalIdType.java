package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.PowerSystemId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;

public class TimeIntervalIdType extends DomainObjectIdCustomType<TimeIntervalId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<TimeIntervalId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<TimeIntervalId>(TimeIntervalId.class,TimeIntervalId::new);
	
	public TimeIntervalIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
