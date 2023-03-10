package com.railweb.trafficmgt.domain.network;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.envers.Audited;

import com.railweb.trafficmgt.domain.ids.LinePathId;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;

import lombok.Data;

@Data
@Entity
@Audited
public class NetEdge {

	@EmbeddedId
	private EdgeId id;
	
	@ManyToOne
	@MapsId("netId")
	private Network net;
	
	@ManyToOne
	@MapsId("toNodeId")
	private Node toNode;
	
	@ManyToOne
	@MapsId("fromNodeId")
	private Node fromNode;
	
	@ManyToOne
	@MapsId("linePathId")
	private LinePath linePath;
	
	@Data
	@Embeddable
	protected static class EdgeId implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4195194413872443847L;
		private NetworkId netId;
		private Long networkVersion;
		private NodeId fromNodeId;
		private Long fromNodeVersion;
		private NodeId toNodeId;
		private Long toNodeVersion;
		private LinePathId linePathId;
		
		public EdgeId(Network net, Node toNode, Node fromNode, LinePath linePath) {
			this.netId = net.getId();
			this.toNodeId = toNode.getId();
			this.toNodeVersion = toNode.getVersion();
			this.fromNodeId = fromNode.getId();
			this.fromNodeVersion = fromNode.getVersion();
			this.linePathId = linePath.getId();
		}
	}
	
	
	public NetEdge(Network net, Node fromNode, Node toNode, LinePath linePath) {
		this.id = new EdgeId(net,fromNode,toNode,linePath);
		this.net = net;
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.linePath = linePath;
	}
}
