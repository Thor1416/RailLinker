package com.railweb.trafficmgt.domain.train;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.shared.domain.util.Day;
import com.railweb.trafficmgt.domain.ids.RundayId;

public class RunDays extends AbstractEntity<RundayId> {

	@EmbeddedId
	private RundayId id;

	private String name;
	@ElementCollection
	@CollectionTable(name="Days",joinColumns =@JoinColumn(name="Runday_ID"))
	private Set<Day> days;
	@Override
	public RundayId getId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
