package com.railweb.trafficmgt.domain.train;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.converters.TimeConverter;
import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.PenaltyTableRowId;
import com.railweb.trafficmgt.domain.network.LineGradient;

import lombok.Getter;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

@Entity
@Getter
public class PenaltyTableRow extends AbstractEntity<PenaltyTableRowId> {
	
	public static final PenaltyTableRow ZERO_ROW = new PenaltyTableRow( 
			Quantities.getQuantity(0, Units.KILOMETRE_PER_HOUR), null, Quantities.getQuantity(0, Units.SECOND),
			Quantities.getQuantity(0, Units.SECOND));

	@Column
	@Convert(converter=SpeedConverter.class)
	private final Quantity<Speed> speed;
	private final LineGradient gradient;
	@Column
	@Convert(converter=TimeConverter.class)
	private Quantity<Time> acceleration;
	@Column
	@Convert(converter=TimeConverter.class)
	private Quantity<Time> deaccelration;
	
	private TrainType trainType;
	
	public PenaltyTableRow(Quantity<Speed> speed, LineGradient gradient, Quantity<Time> acceleration,
			Quantity<Time> deaccelration) {
		this.speed = speed;
		this.gradient = gradient;
		this.acceleration = acceleration;
		this.deaccelration = deaccelration;
	}
	public boolean setAcceleration(Quantity<Time> acceleration) {
		if (!acceleration.isEquivalentTo(this.acceleration)){
			this.acceleration = acceleration;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "PenaltyTableRow [id=" + id + ", speed=" + speed + ", gradient=" + gradient + ", acceleration="
				+ acceleration + ", deaccelration=" + deaccelration + "]";
	}

}
