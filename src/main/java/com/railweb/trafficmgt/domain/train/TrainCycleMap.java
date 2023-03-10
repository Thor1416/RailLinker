package com.railweb.trafficmgt.domain.train;

import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

import com.railweb.shared.domain.base.ValueObject;
import com.railweb.trafficmgt.domain.TrainCycleType;

import lombok.Data;

@Data
public class TrainCycleMap implements ValueObject<TrainCycleMap> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToMany
	@MapKeyJoinColumn
	private Map<RunDays,TrainCycleList> dailyMap;
	
	@ManyToOne
	@JoinColumn(name="type_id", nullable=false)
	private TrainCycleType type;

	@Override
	public boolean sameValueAs(TrainCycleMap other) {
		// TODO Auto-generated method stub
		return false;
	}
}
