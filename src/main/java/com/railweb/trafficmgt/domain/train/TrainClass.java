package com.railweb.trafficmgt.domain.train;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.TextTemplate;
import com.railweb.trafficmgt.domain.ids.TrainClassId;
import com.railweb.trafficmgt.domain.train.Train.NameType;

import lombok.Data;

@Data
@Entity
public class TrainClass extends AbstractEntity<TrainClassId> {

	@EmbeddedId
	private TrainClassId id;
	
	public TextTemplate getNameTemplate(NameType nameType) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPlatform() {
		// TODO Auto-generated method stub
		return false;
	}

}
