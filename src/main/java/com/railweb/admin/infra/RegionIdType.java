package com.railweb.admin.infra;

import com.railweb.admin.domain.id.RegionId;
import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;

public class RegionIdType extends DomainObjectIdCustomType<RegionId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<RegionId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<RegionId>(RegionId.class, RegionId::new);
	
	public RegionIdType() {
		super(TYPE_DESCRIPTOR);
		// TODO Auto-generated constructor stub
	}

}
