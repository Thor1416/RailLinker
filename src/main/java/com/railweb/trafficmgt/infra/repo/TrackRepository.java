package com.railweb.trafficmgt.infra.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.network.Track;

public interface TrackRepository extends JpaRepository<Track, TrackId> {

}
