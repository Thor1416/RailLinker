package com.railweb.trafficmgt.infra.hibernate;

import com.railweb.shared.infra.hibernate.DomainObjectIdCustomType;
import com.railweb.shared.infra.hibernate.DomainObjectIdTypeDescriptor;
import com.railweb.trafficmgt.domain.ids.ImageId;
import com.railweb.trafficmgt.domain.ids.NetworkId;

public class ImageIdType extends DomainObjectIdCustomType<ImageId> {

	private static final DomainObjectIdTypeDescriptor<ImageId> TYPE_DESCRIPTOR =
			new DomainObjectIdTypeDescriptor<ImageId>(ImageId.class,ImageId::new);


	public ImageIdType() {
		super(TYPE_DESCRIPTOR);
	}
}
