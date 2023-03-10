package com.railweb.trafficmgt.application.orc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.validation.Valid;

import org.springframework.data.domain.Sort.Direction;

import com.railweb.dto.RegionDTO;
import com.railweb.shared.domain.command.CommandFailure;
import com.railweb.trafficmgt.domain.commands.CreateNetwork;
import com.railweb.trafficmgt.domain.commands.NodeSearchCommandDTO;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.TrainClassId;
import com.railweb.trafficmgt.dto.AbbrDTO;
import com.railweb.trafficmgt.dto.CompagnyDTO;
import com.railweb.trafficmgt.dto.LineClassDTO;
import com.railweb.trafficmgt.dto.NetworkDTO;
import com.railweb.trafficmgt.dto.NetworkVersionDTO;
import com.railweb.trafficmgt.dto.NodeDTO;
import com.railweb.trafficmgt.dto.PageDTO;
import com.railweb.trafficmgt.dto.TrainClassDTO;
import com.railweb.trafficmgt.exception.CommandValidationException;
import com.railweb.trafficmgt.exception.network.NetworkCreationException;

import io.vavr.Tuple2;
import io.vavr.control.Either;

public interface NetworkOrchestrator {

	PageDTO<NetworkDTO> getAllNets(int page, int size, String sortField, Direction direction, List<String> status, String search);

	NetworkDTO getNetById(NetworkId id);

	Optional<NetworkDTO> updateNet(NetworkId id, @Valid NetworkDTO net);

	void deleteNet(NetworkId id);

	PageDTO<NodeDTO> getAllNodes(NetworkId id,@Valid NodeSearchCommandDTO command);

	NodeDTO getNodeFromAbbr(NetworkId netId, String abbr);

	CompletionStage<Either<CommandFailure, Tuple2<UUID, NetworkDTO>>> createNetwork(@Valid CreateNetwork newNetwork) throws CommandValidationException, NetworkCreationException;

	Boolean netExist(NetworkId netId);

	Boolean isAbbrUsed(NetworkId netId, String abbr);

	NetworkDTO getNetByCode(String code);

	Optional<PageDTO<AbbrDTO>> getUsedAbbr(NetworkId netId, int page, int size, String sortField, Direction direction);

	NodeDTO getNodeFromNet(NetworkId netId, String nodeId);

	Optional<PageDTO<LineClassDTO>> getLineClasses(NetworkId netId, int page, int size, String sortField, String direction, List<String> status);

	CompletionStage<Either<CommandFailure, Tuple2<UUID, NetworkDTO>>> copyNetwork(NetworkId originNetId, String newName);

	PageDTO<NetworkVersionDTO> getVersionsOfNetwork(NetworkId netId, int page, int size, String sortField, Direction direction);

	Optional<NetworkVersionDTO> getNetworkVersion(NetworkId netId, Long versionId);
 
	Boolean deleteLine(NetworkId netId, UUID lineId);

	void addTrainClass(NetworkId netId, TrainClassId id);

	List<TrainClassDTO> getTrainClasses();

	Optional<CompagnyDTO> getOwner(NetworkId netId);

	Optional<CompagnyDTO> setOwner(NetworkId netId, Long compagnyId);

	List<RegionDTO> getRegions(NetworkId netId);

	Optional<RegionDTO> getRegionById(NetworkId netId, UUID regionId);

	Boolean doesRegionExist(NetworkId netId, RegionDTO region);

	RegionDTO addRegion(NetworkId netId, RegionDTO region);

	Boolean deleteRegion(NetworkId netId, UUID regionId);

	RegionDTO updateRegion(NetworkId netId, UUID regionId, RegionDTO region);

}
