package com.railweb.trafficmgt.infra.repo;

import java.time.OffsetTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.railweb.shared.repo.BaseRepository;
import com.railweb.trafficmgt.domain.ids.TrackId;
import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.train.Train;

public interface TrainRepository extends BaseRepository<Train, TrainId> {

	@Query("SELECT t FROM Train t JOIN t.timeIntervals ti "
			+ "WHERE t.id <> :trainId AND (ti.startime < :endtime AND ti.endtime > starTime)"
			+ "AND ti.trackId = :trackId")
	List<Train> findOverlappingTrains(@Param("trainId") TrainId trainId,
										@Param("startTime") OffsetTime startTime,
										@Param("endTime") OffsetTime endTime,
										@Param("trackId") TrackId trackId);

}
