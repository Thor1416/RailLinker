package com.railweb.admin.infra;

import com.railweb.admin.domain.id.InfrastructureManagerId;
import com.railweb.admin.domain.id.RegionId;
import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;

public class InfrastructureManagerIdType extends DomainObjectIdCustomType<InfrastructureManagerId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<InfrastructureManagerId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<InfrastructureManagerId>(InfrastructureManagerId.class,InfrastructureManagerId::new);
	
	public InfrastructureManagerIdType() {
		super(TYPE_DESCRIPTOR);
		// TODO Auto-generated constructor stub
	}

}
