package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.SwitchId;

public class SwitchIdType extends DomainObjectIdCustomType<SwitchId> {
	
	private static final DomainObjectIdTypeDescriptor<SwitchId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<SwitchId>(SwitchId.class,SwitchId::new);
	
	public SwitchIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
