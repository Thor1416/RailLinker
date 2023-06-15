package com.railweb.trafficmgt.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrainCycleTypeId;
import com.railweb.trafficmgt.domain.train.TrainsCycle;

import lombok.Data;

@Entity
@Data
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class TrainCycleType extends AbstractEntity<TrainCycleTypeId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	
	private Timetable timetable;
	
	@OneToMany
	private Set<TrainsCycle> cycles;
	
	@Override
	public TrainCycleTypeId getId() {
		// TODO Auto-generated method stub
		return null;
	}
	

 
}
