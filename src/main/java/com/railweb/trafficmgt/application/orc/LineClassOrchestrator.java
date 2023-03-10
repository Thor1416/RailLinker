package com.railweb.trafficmgt.application.orc;

import javax.validation.Valid;

import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.dto.LineClassDTO;

public interface LineClassOrchestrator {

	LineClassDTO createLineClass(NetworkId netId, @Valid LineClassDTO lineClass);

	Boolean removeLineClass(Long netId, Long lineClassId);


}
