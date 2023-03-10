package com.railweb.trafficmgt.domain.network;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.NetworkNodeId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
@Table(uniqueConstraints = 
		@UniqueConstraint(columnNames= {"networkId","nodeAbbr","nodePrefix"}))
public class NetworkNode extends AbstractEntity<NetworkNodeId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2765862995593880245L;

	@MapsId("networkId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Network network;
	
	@MapsId("nodeId")
	@JoinColumns({
		@JoinColumn(name="nodeAbbr",referencedColumnName="nodeAbbr", nullable = false),
		@JoinColumn(name="nodePrefix", referencedColumnName="nodePrefix")
	})
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	private Node node; 
	
	private Long networkVersion;
	private Long nodeVersion;
	
	public NetworkNode(Network network, Node node) {
		this.network = network;
		this.node = node;
		this.networkVersion = network.getVersion();
		this.nodeVersion = node.getVersion();
	}
	
	public void setNetwork(Network network) {
		this.network = network;
		this.networkVersion = network.getVersion();
	}
	public void setNode(Node node) {
		this.node = node;
		this.nodeVersion = node.getVersion();
	}

	@Override
	@AttributeOverrides({
		@AttributeOverride(name = "id.networkId", column = @Column(name = "networkId")),
		@AttributeOverride(name = "id.nodeId.nodeAbbr", column = @Column(name = "nodeAbbr")),
		@AttributeOverride(name = "id.nodeId.nodePrefix", column =@Column(name = "nodePrefix"))
		})
	public NetworkNodeId getId() {
		return id;
	}


}
