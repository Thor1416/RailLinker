package com.railweb.trafficmgt.domain.ids;

import java.util.UUID;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.trafficmgt.domain.values.NodeIdTuple;

public abstract class NetSegmentId<R> extends DomainObjectId<R> {

	public NetSegmentId(R clazz) {
		super(clazz);
	}

	@Override
	protected void assignId() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean sameValueAs(DomainObjectId<R> other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID toUUID() {
		// TODO Auto-generated method stub
		return null;
	}

}
