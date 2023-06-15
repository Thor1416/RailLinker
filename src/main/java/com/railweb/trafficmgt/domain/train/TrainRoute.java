package com.railweb.trafficmgt.domain.train;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrainRouteId;
import com.railweb.trafficmgt.domain.network.NetEdge;
import com.railweb.trafficmgt.domain.network.NetworkNode;
import com.railweb.trafficmgt.domain.network.Node;

import lombok.Data;

@Entity
@Data
public class TrainRoute extends AbstractEntity<TrainRouteId> {
	
	private TrainRouteId id;

	
	private String name;
 
	public TrainRoute(String name, List<NetworkNode> routeNodes) {
		this.name = name;
	
	}

}
