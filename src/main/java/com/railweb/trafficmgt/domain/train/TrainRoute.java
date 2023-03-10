package com.railweb.trafficmgt.domain.train;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.TrainRouteId;
import com.railweb.trafficmgt.domain.network.NetEdge;

import lombok.Data;

@Entity
@Data
public class TrainRoute extends AbstractEntity<TrainRouteId> {
	
	@EmbeddedId
	private TrainRouteId id;

	@ManyToMany
	@JoinTable(name="TrainRoute_NetEdge",
	joinColumns=@JoinColumn(name="routeId"),
	inverseJoinColumns=@JoinColumn(name="netEdgeId"))
	@OrderColumn(name="NetEdgeOrder")
	private List<NetEdge> netEdges;
	
	private String name;
 
	public TrainRoute(String name, List<NetEdge> netEdges) {
		this.name = name;
		this.netEdges=netEdges;
	}

}
