package com.railweb.trafficmgt.domain.train;

import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;

import com.railweb.shared.domain.base.AbstractDomainEntity;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.RundayId;
import com.railweb.trafficmgt.domain.ids.TrainSegmentId;
import com.railweb.trafficmgt.domain.ids.TrainTypeId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TrainSegment extends AbstractDomainEntity<TrainSegmentId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NodeId startNode;
	private NodeId endNode;
	private TrainTypeId trainType;
	private Set<RundayId> rundays;
	private Quantity<Speed> topSpeed;

}
