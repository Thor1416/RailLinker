package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.LinePathId;

public class LinePathIdType extends DomainObjectIdCustomType<LinePathId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 389984901736444231L;
	private static final DomainObjectIdTypeDescriptor<LinePathId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<LinePathId>(LinePathId.class,LinePathId::new);
	
	public LinePathIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
