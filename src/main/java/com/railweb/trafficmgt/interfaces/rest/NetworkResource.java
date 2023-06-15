package com.railweb.trafficmgt.interfaces.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.railweb.dto.RegionDTO;
import com.railweb.shared.domain.command.CommandFailure;
import com.railweb.trafficmgt.application.command.CreateNodeCommand;
import com.railweb.trafficmgt.application.orc.LineClassOrchestrator;
import com.railweb.trafficmgt.application.orc.LineOrchestrator;
import com.railweb.trafficmgt.application.orc.NetworkOrchestrator;
import com.railweb.trafficmgt.application.orc.NodeOrchestrator;
import com.railweb.trafficmgt.application.orc.TrainClassOrchestrator;
import com.railweb.trafficmgt.domain.commands.CreateNetwork;
import com.railweb.trafficmgt.domain.commands.NodeSearchCommandDTO;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.TrainClassId;
import com.railweb.trafficmgt.domain.train.TrainClass;
import com.railweb.trafficmgt.dto.AbbrDTO;
import com.railweb.trafficmgt.dto.CompagnyDTO;
import com.railweb.trafficmgt.dto.LineClassDTO;
import com.railweb.trafficmgt.dto.LineDTO;
import com.railweb.trafficmgt.dto.NetworkDTO;
import com.railweb.trafficmgt.dto.NetworkVersionDTO;
import com.railweb.trafficmgt.dto.NodeDTO;
import com.railweb.trafficmgt.dto.PageDTO;
import com.railweb.trafficmgt.dto.TrainClassDTO;
import com.railweb.trafficmgt.exception.CommandValidationException;
import com.railweb.trafficmgt.exception.network.NetworkCreationException;
import com.railweb.trafficmgt.interfaces.rest.error.ResourceAlreadyExistException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.vavr.Tuple2;
import io.vavr.control.Either;

/**
 * @author Thorbjoern Simonsen
 * @since 09-12-2021
 */

@RestController
@RequestMapping("/api/v1/nets")
public class NetworkResource {

	private static final Logger logger = LoggerFactory.getLogger(NetworkResource.class);
	private static final String NEW_NETWORK_LOG = "New network was created id: {}";
	private static final String UPDATED_NETWORK_LOG = "Updated network with id: {} with information {}";
	private static final String DELETED_NETWORK_LOG = "Deleted network with id: {}";
	private static final String NEW_NODE_LOG = "New node was created id: {}";
	private static final String NEW_LINE_LOG = "New line was created with id: {}";
	private static final String LINE_UPDATED_LOG = "Line: {} was updated";
	private static final String LINECLASS_UPDATED_LOG = "Lineclass: {} was updated";

	private NetworkOrchestrator netOrc;
	private NodeOrchestrator nodeOrc;
	private LineOrchestrator lineOrc;
	private LineClassOrchestrator lineClassOrc;
	private TrainClassOrchestrator trainClassOrc;

	@Autowired
	public NetworkResource(NetworkOrchestrator netOrchestrator, NodeOrchestrator nodeOrchestrator, LineOrchestrator lineOrchestrator,
			LineClassOrchestrator lineClassOrchestrator, TrainClassOrchestrator trainClassOrchestrator) {
		this.netOrc = netOrchestrator;
		this.nodeOrc = nodeOrchestrator;
		this.lineOrc = lineOrchestrator;
		this.lineClassOrc = lineClassOrchestrator;
		this.trainClassOrc = trainClassOrchestrator;
	}

	@Operation(summary = "Return a list of railway networks and sorted/filtered based on query parameters")
	@ApiResponse(responseCode = "200", description = "Railway networks was retrieved", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PageDTO.class)) })
	@GetMapping("")
	public ResponseEntity<PageDTO<NetworkDTO>> getAllNet(@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "20") int size,
			@RequestParam(required = false, name = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction,
			@RequestParam(required = false, name = "status") List<String> status, @RequestParam(required = false, name = "search") String search) {

		final PageDTO<NetworkDTO> networkPage = netOrc.getAllNets(page, size, sortField, getSortDirection(direction), status, search);
		return ResponseEntity.ok(networkPage);
	}

	@Operation(summary = "Get a raiway network by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the railway network", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = NetworkDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Railway networket not found", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	@GetMapping("/{networkId}")
	public ResponseEntity<NetworkDTO> getNetById(@Parameter(description = "Id of the network") @PathVariable("networkId") NetworkId id) {
		NetworkDTO network = netOrc.getNetById(id);

		if (network == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(network);
	}

	@GetMapping("/code/{code}")
	public ResponseEntity<NetworkDTO> getByCode(@PathVariable("code") String code) {

		NetworkDTO network = netOrc.getNetByCode(code);

		if (network == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(network);
	}

	@GetMapping("/{netId}/versions")
	public ResponseEntity<PageDTO<NetworkVersionDTO>> getVersions(@PathVariable("netId") NetworkId netId,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "20") int size,
			@RequestParam(required = false, name = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction) {

		PageDTO<NetworkVersionDTO> result = netOrc.getVersionsOfNetwork(netId, page, size, sortField, getSortDirection(direction));
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{netId}/versions/{versionId}")
	public ResponseEntity<NetworkVersionDTO> getVersionOfNetwork(@PathVariable("netId") NetworkId netId, @PathVariable("versionId") Long versionId) {
		Optional<NetworkVersionDTO> result = netOrc.getNetworkVersion(netId, versionId);
		if (result.isPresent()) {
			return ResponseEntity.ok(result.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Creates a new network
	 * 
	 * @throws NetworkCreationException
	 * @throws CommandValidationException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Operation(summary = "Create a new rail network")
	@ApiResponse(responseCode = "200", description = "Created a network")
	@PutMapping("")
	public ResponseEntity<NetworkDTO> createNet(@RequestBody @Valid CreateNetwork newNetwork)
			throws CommandValidationException, NetworkCreationException, InterruptedException, ExecutionException {

		Either<CommandFailure, Tuple2<UUID, NetworkDTO>> res = netOrc.createNetwork(newNetwork).toCompletableFuture().get();
		if (res.isRight()) {
			UUID netId = res.get()._1;
			logger.info(NEW_NETWORK_LOG, netId);
			NetworkDTO createdNet = res.get()._2;
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdNet.getId()).toUri();
			return ResponseEntity.created(uri).body(createdNet);
		} else {
			return (ResponseEntity<NetworkDTO>) ResponseEntity.badRequest();
		}

	}

	/**
	 * Copy a network
	 * 
	 * @return a new network with same properties
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@PostMapping("/{netId}")
	public ResponseEntity<NetworkDTO> copyNetwork(@RequestParam("netId") NetworkId originNetId, @RequestParam("name") String newName) throws InterruptedException, ExecutionException {

		Either<CommandFailure, Tuple2<UUID, NetworkDTO>> createdNet;

		createdNet = netOrc.copyNetwork(originNetId, newName).toCompletableFuture().get();
		if (createdNet.isRight()) {
			logger.info(NEW_NETWORK_LOG, createdNet.get().toString());
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdNet.get()._1).toUri();
			return ResponseEntity.created(uri).body(createdNet.get()._2);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@Operation(summary = "Update network details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Updated the network", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = NetworkDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Railway network not found", content = @Content) })
	@PatchMapping("/{id}")
	public ResponseEntity<NetworkDTO> updateNetwork(@RequestBody @Valid NetworkDTO net,
			@Parameter(description = "Id of the network") @PathVariable NetworkId netId) {
		final Optional<NetworkDTO> network = netOrc.updateNet(netId, net);
		if (network.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		logger.info(UPDATED_NETWORK_LOG, network.get().getId());
		return ResponseEntity.ok(network.get());
	}

	@Operation(summary = "Delete the network")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Deleted the network"),
			@ApiResponse(responseCode = "404", description = "Railway network not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteNetwork(@Parameter(description = "Id of the network") @PathVariable("id") NetworkId id) {

		netOrc.deleteNet(id);
		logger.info(DELETED_NETWORK_LOG, id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/nodes")
	public ResponseEntity<PageDTO<NodeDTO>> getAllNodes(@NotNull @Parameter(description = "Id of the network") @PathVariable("id") NetworkId id,
			@RequestBody @Valid NodeSearchCommandDTO command) {
		PageDTO<NodeDTO> nodes = netOrc.getAllNodes(id, command);

		if (nodes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(nodes);
	}

	@Operation(summary = "Get a node from the net")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Returning the selected node", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NodeDTO.class))),
			@ApiResponse(responseCode = "404", description = "Network or node not found", content = @Content) })
	@GetMapping("/{netId}/nodes/{nodeId}")
	public ResponseEntity<NodeDTO> getNodeById(@Parameter(description = "Id of the network") @PathVariable("netId") NetworkId netId,
			@Parameter(description = "Id of the node or station") @PathVariable("nodeId") String nodeId) {

		NodeDTO node = netOrc.getNodeFromNet(netId, nodeId);

		if (node == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(node);
	}

	@GetMapping("/{netId}/nodes")
	public ResponseEntity<NodeDTO> getNodeByAbbr(@PathVariable("id") NetworkId netId, @RequestParam("abbr") String abbr) {
		NodeDTO node = netOrc.getNodeFromAbbr(netId, abbr);
		if (node == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(node);
	}

	@GetMapping("/{netId}/nodes/abbr/oc")
	public ResponseEntity<Boolean> isAbbrOccupied(@PathVariable("netId") NetworkId id, @RequestParam("abbr") String abbr) {
		return ResponseEntity.ok(netOrc.isAbbrUsed(id, abbr));
	}

	@GetMapping("/{netId}/nodes/abbr")
	public ResponseEntity<PageDTO<AbbrDTO>> getUsedAbbr(@PathVariable("netId") NetworkId id, 
			@RequestParam("pageNo") int page,@RequestParam("pageSize") int pageSize, 
			@RequestParam("sortBy") String sortBy, @RequestParam("sortDir") String sortDir) {

		Optional<PageDTO<AbbrDTO>> result = netOrc.getUsedAbbr(id, page, pageSize, sortBy, Sort.Direction.valueOf(sortDir));
		if (result.isEmpty())
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(result.get());
	}

	/**
	 * @param netId   id of net to add
	 * @param NodeDTO the node data to add.
	 * 
	 * @return representation of the added node
	 */
	@PostMapping("/{netId}/nodes")
	public ResponseEntity<NodeDTO> addNode(@PathVariable("netId") NetworkId netId,
			@RequestBody CreateNodeCommand command) {
		
		NodeDTO createdNode = nodeOrc.createNode(netId, command);
		logger.info(NEW_NODE_LOG, createdNode.toString());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdNode.getNetId()).toUri();

		return ResponseEntity.created(uri).body(createdNode);
	}

	@DeleteMapping("/{netId}/nodes/{nodeid}")
	public ResponseEntity<Object> deleteNode(@PathVariable("netId") NetworkId netId, 
			@PathVariable("nodeId") Long nodeId) {
		
		Boolean wasOk = nodeOrc.deleteNode(netId, nodeId);

		if (wasOk) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	@PostMapping("/{netId}/lines")
	public ResponseEntity<LineDTO> addLine(@NotNull @PathVariable("netId") NetworkId netId,
			@NotNull @Parameter(description = "New line to add") @RequestParam("Line") LineDTO lineDTO) {

		LineDTO createdLine = lineOrc.createLine(netId, lineDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdLine).toUri();

		return ResponseEntity.created(uri).body(createdLine);
	}

	@GetMapping("/{netId}/lines/{lineId}")
	public ResponseEntity<LineDTO> getLineById(@PathVariable("netId") Long netId, @PathVariable("lineId") Long lineId) {
		Optional<LineDTO> result = lineOrc.getLine(netId, lineId);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(result.get());
	}

	@GetMapping("/{netId}/lines")
	public ResponseEntity<LineDTO> getLineByCode(@PathVariable("netId") Long netId, @RequestParam("code") String code) {
		Optional<LineDTO> result = lineOrc.getLineByCode(netId, code);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(result.get());
	}

	@GetMapping("/{netId}/line/")
	public ResponseEntity<LineDTO> getLineBetweenNodes(@PathVariable("netId") Long netId,
			@RequestParam(name = "fromNodeId", required = false) Optional<Long> fromNodeId,
			@RequestParam(name = "fromNodeAbbrv", required = false) Optional<String> fromNodeAbbrv,
			@RequestParam(name = "fromNodeCode", required = false) Optional<String> fromNodeCode,
			@RequestParam(name = "toNodeId", required = false) Optional<Long> toNodeId,
			@RequestParam(name = "toNodeAbbrv", required = false) Optional<String> toNodeAbbrv,
			@RequestParam(name = "toNodeCode", required = false) Optional<String> toNodeCode) {

		Optional<LineDTO> result;

		if (fromNodeId.isPresent() && toNodeId.isPresent()) {
			result = lineOrc.getLineBetweenNodeId(netId, fromNodeId.get(), toNodeId.get());
		} else if (fromNodeAbbrv.isPresent() && toNodeAbbrv.isPresent()) {
			result = lineOrc.getLineBetweenNodeAbbrv(netId, fromNodeAbbrv.get(), toNodeAbbrv.get());
		} else if (fromNodeCode.isPresent() && toNodeCode.isPresent()) {
			result = lineOrc.getLineBetweenNodeCode(netId, fromNodeCode.get(), toNodeCode.get());
		} else {
			return ResponseEntity.badRequest().build();
		}
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(result.get());
	}

	@PatchMapping("/{netId}/line/{lineId}")
	public ResponseEntity<LineDTO> updateLine(@PathVariable("netId") Long netId, @PathVariable("lineId") Long lineId,
			@RequestParam("lineDTO") LineDTO lineDTO) {

		Optional<LineDTO> result = lineOrc.updateLine(netId, lineId, lineDTO);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		logger.info(LINE_UPDATED_LOG, result.get().toString());
		return ResponseEntity.ok(result.get());
	}

	@DeleteMapping("/{netId}/lines/{lineId}")
	public ResponseEntity<Object> deleteLine(@PathVariable("netId") NetworkId netId, @PathVariable("lineId") UUID lineId) {
		Boolean wasOk = netOrc.deleteLine(netId, lineId);
		if (wasOk) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping("/{netId}/linesclasses")
	public ResponseEntity<PageDTO<LineClassDTO>> getLineClasses(@PathVariable("netId") NetworkId netId,
			@RequestParam(required = false, name = "page", defaultValue = "0") int page,
			@RequestParam(required = false, name = "size", defaultValue = "20") int size,
			@RequestParam(required = false, name = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction,
			@RequestParam(required = false, name = "status") List<String> status) {

		Optional<PageDTO<LineClassDTO>> result = netOrc.getLineClasses(netId, page, size, sortField, direction, status);

		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result.get());
		}
	}

	@PostMapping("/{netId}/lineclass")
	public ResponseEntity<LineClassDTO> addLineClass(@PathVariable("netId") NetworkId netId,
								@RequestParam("lineclasss") @Valid LineClassDTO lineClass) {

		LineClassDTO lineclassCreated = lineClassOrc.createLineClass(netId, lineClass);
		return ResponseEntity.ok(lineclassCreated);
	}

	@DeleteMapping("/{netId}/lineclasses/{lineClassId}")
	public ResponseEntity<Object> deleteLineClass(Long netId, Long lineClassId) {
		Boolean wasOk = lineClassOrc.removeLineClass(netId, lineClassId);
		if (wasOk) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

	}

	public ResponseEntity<List<TrainClassDTO>> getTrainClasses(@PathVariable("netId") Long netId) {
		return ResponseEntity.ok(netOrc.getTrainClasses());
	}

	@PostMapping("/{netId}/trainclasses")
	public ResponseEntity<TrainClassDTO> addTrainClass(@PathVariable("netId") NetworkId netId, @RequestBody Optional<TrainClassDTO> trainClass,
			@RequestParam("trainClassId") Optional<TrainClassId> trainClassId) {

		if (trainClass.isPresent()) {
			TrainClass createdTrainClass = trainClassOrc.createTrainClass(trainClass);
			netOrc.addTrainClass(netId,createdTrainClass.getId());
			URI uri = WebMvcLinkBuilder.linkTo(TrainClassResource.class).slash(createdTrainClass.getId()).toUri();
			return ResponseEntity.created(uri).body(new TrainClassDTO(createdTrainClass));
		} else if (trainClassId.isPresent()) {
			netOrc.addTrainClass(netId, trainClassId.get());
			TrainClassDTO storedTrainClass = trainClassOrc.getTrainClass(trainClassId.get());
			return ResponseEntity.ok(storedTrainClass);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	@DeleteMapping("/{netId}/trainclasses/{trainclassId}")
	public ResponseEntity<Object> removeTrainClass(@PathVariable("netId") Long netId, @PathVariable("trainclassId") Long trainClassId) {
		Boolean wasOk = trainClassOrc.removeTrainClass(trainClassId, netId);
		if (wasOk) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping("/{netId}/owner")
	public ResponseEntity<CompagnyDTO> getOwner(@PathVariable("netId") NetworkId netId) {
		Optional<CompagnyDTO> owner = netOrc.getOwner(netId);
		if (owner.isPresent()) {
			return ResponseEntity.ok(owner.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{netId}/owner")
	public ResponseEntity<CompagnyDTO> setOwner(@PathVariable("netId") NetworkId netId, Long compagnyId) {
		Optional<CompagnyDTO> owner = netOrc.setOwner(netId, compagnyId);
		if (owner.isPresent()) {
			return ResponseEntity.ok(owner.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/{netId}/regions")
	public ResponseEntity<RegionDTO> addRegionToNet(@PathVariable("netId") NetworkId netId, @RequestBody RegionDTO region)
			throws ResourceAlreadyExistException {

		Boolean doesExist = netOrc.doesRegionExist(netId, region);
		if (doesExist) {
			throw new ResourceAlreadyExistException("Region already exist");
		}

		RegionDTO createdRegion = netOrc.addRegion(netId, region);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdRegion.getId()).toUri();

		return ResponseEntity.created(uri).body(createdRegion);
	}

	@GetMapping("/{netId}/regions")
	public ResponseEntity<List<RegionDTO>> getRegions(@PathVariable("netId") NetworkId netId) {
		List<RegionDTO> regions = netOrc.getRegions(netId);
		if (regions.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(regions);
		}
	}

	@GetMapping("/{netId}/regions/{regionId}")
	public ResponseEntity<RegionDTO> getRegionById(@PathVariable("netId") NetworkId netId, @PathVariable("regionId") UUID regionId) {

		Optional<RegionDTO> region = netOrc.getRegionById(netId, regionId);
		if (region.isPresent()) {
			return ResponseEntity.ok(region.get());
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{netId}/regions/{regionId}")
	public ResponseEntity<Object> deleteRegion(@PathVariable("netId") NetworkId netId, @PathVariable("regionId") UUID regionId) {

		Boolean wasOk = netOrc.deleteRegion(netId, regionId);
		if (wasOk) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping("/{netId}/regions/{regionId}")
	public ResponseEntity<RegionDTO> updateRegion(@PathVariable("netId") NetworkId netId, @PathVariable("regionId") UUID regionId,
			@RequestBody RegionDTO region) {

		RegionDTO updatedRegion = netOrc.updateRegion(netId, regionId, region);
		return ResponseEntity.ok(updatedRegion);
	}

	private Sort.Direction getSortDirection(String direction) {
		if (direction.toLowerCase().equals("asc")) {
			return Sort.Direction.ASC;
		} else if (direction.toLowerCase().equals("desc")) {
			return Sort.Direction.DESC;
		}
		return Sort.Direction.ASC;
	}

}
