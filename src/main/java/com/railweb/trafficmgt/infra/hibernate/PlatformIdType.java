package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.PlatformId;

public class PlatformIdType extends DomainObjectIdCustomType<PlatformId>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8774971360150772344L;
	private static final DomainObjectIdTypeDescriptor<PlatformId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<PlatformId>(PlatformId.class,PlatformId::new);
	public PlatformIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
