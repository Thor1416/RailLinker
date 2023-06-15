package com.railweb.trafficmgt.domain.engines;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.EngineClassId;

import lombok.Data;

@Data
@Entity
public class EngineClass extends AbstractEntity<EngineClassId> {
	@EmbeddedId
	private EngineClassId id;
	private String name;
}