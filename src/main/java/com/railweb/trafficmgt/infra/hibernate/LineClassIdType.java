package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.LineClassId;

public class LineClassIdType extends DomainObjectIdCustomType<LineClassId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 389984901736444231L;
	private static final DomainObjectIdTypeDescriptor<LineClassId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<LineClassId>(LineClassId.class,LineClassId::new);
	
	public LineClassIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
