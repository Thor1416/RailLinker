package com.railweb.trafficmgt.infra.repo;

import com.railweb.shared.repo.BaseRepository;
import com.railweb.trafficmgt.domain.ids.LineId;
import com.railweb.trafficmgt.domain.network.Line;

public interface LineRepository extends BaseRepository<Line, LineId> {

}
