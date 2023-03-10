package com.railweb.trafficmgt.domain.train;

import java.util.Map;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Speed;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyJoinColumn;

import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.Timetable;
import com.railweb.trafficmgt.domain.ids.WeightTableRowId;

import lombok.Data;

@Data
public class WeightTableRow extends AbstractEntity<WeightTableRowId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2202777972032253579L;

	@ManyToMany(mappedBy="weightTable")
	private Timetable timetable;
	
	@ManyToMany
	@JoinTable(name="TrainType_WeightTable",
			joinColumns=@JoinColumn(name="weightTableRowId"),
			inverseJoinColumns=@JoinColumn(name="trainTypeId"))
	private TrainType trainType;
	@ManyToMany
	@JoinTable(name="TrainClass_WeightTable",
	joinColumns=@JoinColumn(name="weightTableRowId"),
	inverseJoinColumns=@JoinColumn(name="trainClassId"))
	private TrainClass trainClass; 

	@Convert(converter=SpeedConverter.class)
	private Quantity<Speed> speed;
	
	@ElementCollection(targetClass=String.class)
	@MapKeyJoinColumn(name="lineGradient_id")
	@CollectionTable(name="weightTableCell",
			joinColumns=@JoinColumn(name="weighTableRowId"))
	@Column(name="weight")
	@Convert(converter=com.railweb.shared.converters.MassConverter.class)
	private Map<Quantity<Speed>,Quantity<Mass>> weights;
	
	
}
