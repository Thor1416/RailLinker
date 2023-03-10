package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.NetworkId;

public class NetworkIdType extends DomainObjectIdCustomType<NetworkId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final DomainObjectIdTypeDescriptor<NetworkId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<NetworkId>(NetworkId.class,NetworkId::new);
	
	public NetworkIdType() {
		super(TYPE_DESCRIPTOR);
	}

}
