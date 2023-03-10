package com.railweb.trafficmgt.domain.train;

import java.util.Iterator;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Speed;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.context.ApplicationContext;

import com.railweb.shared.converters.MassConverter;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.trafficmgt.domain.ids.EngineClassId;
import com.railweb.trafficmgt.domain.ids.TrainTypeId;
import com.railweb.trafficmgt.domain.network.LineGradient;

import lombok.Data;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

@Data
public class TrainType extends AbstractAggregateRoot<TrainTypeId> {

	@EmbeddedId
	private TrainTypeId id;
	@OneToMany
	private Set<PenaltyTableRow> penaltyRows;
	@ManyToOne
	private EngineClassId primaryEngineClass;
	@Convert(converter=MassConverter.class)
	private Quantity<Mass> weight;
	@ManyToMany
	@JoinTable(name="TrainType_TTEquivalent",
		joinColumns=@JoinColumn(name="TrainType_id"),
		inverseJoinColumns=@JoinColumn(name="TTEquiv_id"))
	private Set<TrainTypeEquivalent> equivalents;
	@ManyToMany(mappedBy="trainType")
	private Set<WeightTableRow> weightTable;
	
	
	public PenaltyTableRow getRowForSpeed(LineGradient gradient, Quantity<Speed> speed) {
		// zero is a special case
		if(speed == Quantities.getQuantity(0, Units.KILOMETRE_PER_HOUR )) {
			return PenaltyTableRow.ZERO_ROW;		
		}
		
		//other rows
		Iterator<PenaltyTableRow> i = penaltyRows.iterator();
		while(i.hasNext()) {
			PenaltyTableRow row = i.next();
			if(row.getSpeed().to(speed.getUnit()).getValue().doubleValue() <= speed.getValue().doubleValue() &&
					row.getGradient() == gradient) {
				return row;
			}
		}
		// otherwise return null
		return null;
	}

	public WeightTableRow getWeightTableRowForSpeed(Quantity<Speed> speed) {
		// TODO Auto-generated method stub
		return null;
	}

	public TrainType(AbstractAggregateRoot<TrainTypeId> source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public TrainType(ApplicationContext applicationContext, TrainTypeId entityId) {
		super(applicationContext,entityId);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AbstractAggregateRoot<TrainTypeId>.AggregateRootBehavior<?> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
