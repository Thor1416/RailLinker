package com.railweb.trafficmgt.infra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.railweb.shared.repo.BaseRepository;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.network.Node;

public interface NodeRepository extends BaseRepository<Node, NodeId> {

	@Query("Select n.id.abbr, n.name FROM Node n where n.id.net =:net")
	Page<AbbrAndName> findAbbrsUsed(@Param("net") NetworkId id, Pageable pageable);
	
	
	interface AbbrAndName{
		String getAbbr();
		String getName();
	}
}
