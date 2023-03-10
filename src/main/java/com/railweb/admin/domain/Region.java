package com.railweb.admin.domain;

import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.railweb.admin.domain.id.RegionId;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.trafficmgt.domain.ids.NetworkId;

import lombok.Data;

@Data
@Entity
public class Region extends AbstractAggregateRoot<RegionId> {

	@EmbeddedId
	private RegionId id;
	private Set<NetworkId> nets;

	public Region(AbstractAggregateRoot<RegionId> source) {
		super(source);
		// TODO Auto-generated constructor stub
	}


	@Override
	public RegionId getId() {
		return id;
	}


	@Override
	protected AbstractAggregateRoot<RegionId>.AggregateRootBehavior<RegionId> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RegionId id() {
		// TODO Auto-generated method stub
		return null;
	}


}
