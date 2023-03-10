package com.railweb.trafficmgt.domain.ids;

import static com.railweb.shared.domain.HashUUIDCreator.NAMESPACE_RAILWEB;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import com.railweb.shared.domain.HashUUIDCreator;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.trafficmgt.domain.network.NodeAbbr;
import com.railweb.trafficmgt.domain.network.NodePrefix;
import com.railweb.trafficmgt.domain.values.NodeIdTuple;

import lombok.Getter;
import lombok.Setter; 


public class NodeId extends DomainObjectId<NodeIdTuple>{
	@Transient
	@Getter  @Setter
	private NodeAbbr abbr;
	@Transient
	@Getter  @Setter
	private NodePrefix prefix;
	
	public NodeId(NodeAbbr abbr, NodePrefix prefix) {
		super(new NodeIdTuple(abbr, prefix));
		this.abbr = abbr;
		this.prefix = prefix;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PostLoad
	protected void recoverLoad() {
		this.abbr = id.getAbbr();
		this.prefix = id.getPrefix();
	}

	@Override
	protected void assignId() {
		this.id = new NodeIdTuple(abbr,prefix);
	}

	@Override
	public UUID toUUID() {
		return HashUUIDCreator.getSha1Uuid(NAMESPACE_RAILWEB, abbr.toString() +"_" + prefix.toString());
	}

	@Override
	public boolean sameValueAs(DomainObjectId<NodeIdTuple> other) {
		return this.id.getAbbr().sameValueAs(other.getId().getAbbr()) && 
				this.id.getPrefix().sameValueAs(other.getId().getPrefix());
	}

}
