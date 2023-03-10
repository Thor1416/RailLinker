package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.PenaltyTableRowId;

public class PenaltyTableRowIdType extends DomainObjectIdCustomType<PenaltyTableRowId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 771079330075649151L;
	private static final DomainObjectIdTypeDescriptor<PenaltyTableRowId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<PenaltyTableRowId>(PenaltyTableRowId.class,PenaltyTableRowId::new);
	
	public PenaltyTableRowIdType(DomainObjectIdTypeDescriptor<PenaltyTableRowId> domainObjectIdTypeDescriptor) {
		super(TYPE_DESCRIPTOR);

	}

}
