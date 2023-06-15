package com.railweb.trafficmgt.domain.train;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ManagedFreightOption;

@Entity
@Table(name="node_time_interval")
public class NodeTimeInterval extends TimeInterval<NodeId> {


	public boolean shunting;
	@Enumerated
	public ManagedFreightOption freightOption;
}	
