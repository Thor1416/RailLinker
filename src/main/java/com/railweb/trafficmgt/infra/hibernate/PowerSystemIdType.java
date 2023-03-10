package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.PowerSystemId;

public class PowerSystemIdType extends DomainObjectIdCustomType<PowerSystemId>{
	
	private static final DomainObjectIdTypeDescriptor<PowerSystemId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<PowerSystemId>(PowerSystemId.class,PowerSystemId::new);
	
	public PowerSystemIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
