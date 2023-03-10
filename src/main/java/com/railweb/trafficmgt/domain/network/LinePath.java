package com.railweb.trafficmgt.domain.network;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.LinePathId;

import lombok.Data;

@Data
@Entity
public class LinePath extends AbstractEntity<LinePathId>{

	@ManyToOne
	private Line line;
	@ManyToOne
	@JoinColumn(name="from_node")
	private Node fromNode;
	@ManyToOne
	@JoinColumn(name="to_node")
	private Node toNode;
	@ManyToOne
	private LineTrack track;
	
	public LinePath(Line line,Node fromNode, Node toNode) {
		this.line = line;
		this.line.addLinePath(this);
		this.fromNode = fromNode;
		this.toNode = toNode;
	}


}
