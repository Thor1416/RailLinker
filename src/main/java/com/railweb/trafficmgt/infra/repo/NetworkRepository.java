package com.railweb.trafficmgt.infra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.railweb.shared.repo.BaseRepository;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.network.LineClass;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.domain.network.NetworkVersion;

public interface NetworkRepository extends BaseRepository<Network,NetworkId> {

	@Query("select n from Network n where n.code=?1")
	Network findByCode(String code);

	@Query("select lineclass from Network n inner join n.lineclasses where n=:netId")
	Page<LineClass> findLineClasses(@Param("netId") NetworkId netId, Pageable pageable);

	@Query("select networkVersion from Network n inner join n.lineclases where n=:netId")
	Page<NetworkVersion> findNetworkVersions(@Param("netId") NetworkId netId, Pageable pageable);
}
