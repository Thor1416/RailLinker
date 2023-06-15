package com.railweb.trafficmgt.domain.engines;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.EngineGroupId;

import lombok.Data;

@Data
@Entity
public class EngineGroup extends AbstractEntity<EngineGroupId> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4020490249442057370L;
	@ManyToMany(mappedBy="engineRuns")
	private Set<EngineCycle> enginesRun;

	private String name;

}
