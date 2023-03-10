package com.railweb.admin.infra;

import com.railweb.admin.domain.id.OperatingCompagnyId;
import com.railweb.admin.domain.id.RegionId;
import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;

public class OperatingCompagnyIdType extends DomainObjectIdCustomType<OperatingCompagnyId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<OperatingCompagnyId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<OperatingCompagnyId>(OperatingCompagnyId.class, OperatingCompagnyId::new);
	
	public OperatingCompagnyIdType() {
		super(TYPE_DESCRIPTOR);
		// TODO Auto-generated constructor stub
	}

}
