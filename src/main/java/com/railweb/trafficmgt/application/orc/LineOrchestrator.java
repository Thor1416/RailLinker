package com.railweb.trafficmgt.application.orc;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.dto.LineDTO;

public interface LineOrchestrator {

	LineDTO createLine(@NotNull NetworkId netId, @NotNull LineDTO lineDTO);

	Optional<LineDTO> getLine(Long netId, Long lineId);

	Optional<LineDTO> getLineByCode(Long netId, String code);

	Optional<LineDTO> getLineBetweenNodeId(Long netId, Long long1, Long long2);

	Optional<LineDTO> getLineBetweenNodeAbbrv(Long netId, String string, String string2);

	Optional<LineDTO> getLineBetweenNodeCode(Long netId, String string, String string2);

	Optional<LineDTO> updateLine(Long netId, Long lineId, LineDTO lineDTO);

}
