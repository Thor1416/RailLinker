package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.EngineClassId;

public class EngineClassIdType extends DomainObjectIdCustomType<EngineClassId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<EngineClassId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<EngineClassId>(EngineClassId.class,EngineClassId::new);
	
	public EngineClassIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
