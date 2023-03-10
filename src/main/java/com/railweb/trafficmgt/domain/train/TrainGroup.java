package com.railweb.trafficmgt.domain.train;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrainGroupId;

import lombok.Data;

@Data
@Entity
public class TrainGroup extends AbstractEntity<TrainGroupId> {

	@EmbeddedId
	private TrainGroupId id;

}
