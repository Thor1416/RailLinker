package com.railweb.trafficmgt.infra.repo;

import com.railweb.shared.repo.BaseRepository;
import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.train.Train;

public interface TrainRepository extends BaseRepository<Train, TrainId> {

}
