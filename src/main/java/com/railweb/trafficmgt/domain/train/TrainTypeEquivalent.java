package com.railweb.trafficmgt.domain.train;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.persistence.Convert;

import com.railweb.shared.converters.MassConverter;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.EngineClassId;
import com.railweb.trafficmgt.domain.ids.TrainTypeEquivalentId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TrainTypeEquivalent extends AbstractEntity<TrainTypeEquivalentId> {

	private TrainTypeEquivalentId id;
	private EngineClassId engineClass;
	@Convert(converter=MassConverter.class, attributeName="value")
	private Quantity<Mass> weight;

}
