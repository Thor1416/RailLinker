package com.railweb.trafficmgt.domain.train;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.PlatformId;

public class Platform extends AbstractEntity<PlatformId> {


	@Override
	public PlatformId getId() {
		return id;
	}
}
