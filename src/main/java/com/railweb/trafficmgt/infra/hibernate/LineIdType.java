package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.LineClassId;
import com.railweb.trafficmgt.domain.ids.LineId;

public class LineIdType extends DomainObjectIdCustomType<LineId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 389984901736444231L;
	private static final DomainObjectIdTypeDescriptor<LineId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<LineId>(LineId.class,LineId::new);
	
	public LineIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
