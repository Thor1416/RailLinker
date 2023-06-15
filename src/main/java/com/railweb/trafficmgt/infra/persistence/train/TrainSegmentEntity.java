package com.railweb.trafficmgt.infra.persistence.train;

import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.RundayId;
import com.railweb.trafficmgt.domain.ids.TrainSegmentId;
import com.railweb.trafficmgt.domain.ids.TrainTypeId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TrainSegmentEntity extends AbstractEntity<TrainSegmentId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 565255806012375565L;
	private NodeId startNode;
	private NodeId endNode;
	private TrainTypeId trainType;
	@ElementCollection
	private Set<RundayId> runDayId;
	private Quantity<Speed> topSpeed;
}
