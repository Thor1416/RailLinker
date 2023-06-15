package com.railweb.trafficmgt.infra.persistence.train;

import java.time.ZonedDateTime;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.PlatformId;
import com.railweb.trafficmgt.domain.ids.TrainStopId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Audited
public class TrainStopEntity extends AbstractEntity<TrainStopId>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532720918750030870L;
	private NodeId stopNode;
	private ZonedDateTime arrival;
	private ZonedDateTime departure;
	private Boolean needsPlatform;
	private PlatformId platform;

}
