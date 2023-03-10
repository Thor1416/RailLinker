package com.railweb.trafficmgt.application.orc.v1;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import static java.util.concurrent.CompletableFuture.completedFuture;

import java.time.Clock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railweb.dto.RegionDTO;
import com.railweb.shared.domain.command.CommandFailure;
import com.railweb.shared.repo.GenericSpecificationBuilder;
import com.railweb.shared.web.CriteriaParser;
import com.railweb.trafficmgt.application.orc.NetworkOrchestrator;
import com.railweb.trafficmgt.domain.commands.CopyNetwork;
import com.railweb.trafficmgt.domain.commands.CreateNetwork;
import com.railweb.trafficmgt.domain.commands.NodeSearchCommandDTO;
import com.railweb.trafficmgt.domain.events.NetworkCreated;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.TrainClassId;
import com.railweb.trafficmgt.domain.network.LineClass;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.domain.network.NodeAbbr;
import com.railweb.trafficmgt.dto.AbbrDTO;
import com.railweb.trafficmgt.dto.CompagnyDTO;
import com.railweb.trafficmgt.dto.LineClassDTO;
import com.railweb.trafficmgt.dto.NetworkDTO;
import com.railweb.trafficmgt.dto.NetworkVersionDTO;
import com.railweb.trafficmgt.dto.NodeDTO;
import com.railweb.trafficmgt.dto.PageDTO;
import com.railweb.trafficmgt.dto.TrainClassDTO;
import com.railweb.trafficmgt.dto.impl.AbbrDTOImpl;
import com.railweb.trafficmgt.exception.CommandValidationException;
import com.railweb.trafficmgt.exception.network.NetworkCreationException;
import com.railweb.trafficmgt.exception.network.NetworkNotFoundException;
import com.railweb.trafficmgt.infra.repo.NetworkRepository;
import com.railweb.trafficmgt.infra.repo.NetworkSpecification;
import com.railweb.trafficmgt.infra.repo.NodeRepository;
import com.railweb.trafficmgt.infra.repo.NodeRepository.AbbrAndName;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;

@Service
@Transactional
public class NetworkOrchestratorImpl implements NetworkOrchestrator {

	private static final Logger logger = LoggerFactory.getLogger(NetworkOrchestratorImpl.class);
	private final NetworkRepository networkRepository;
	private ModelMapper mapper;
	private final NodeRepository nodeRepository;
	private final ApplicationContext context;
	private final Clock clock;
	
	
	@Autowired
	public NetworkOrchestratorImpl(NetworkRepository networkRepo, ModelMapper mapper,
			NodeRepository nodeRepository, ApplicationContext context, Clock clock) {
		this.networkRepository = networkRepo;
		this.mapper = mapper;
		this.context = context;
		this.clock = clock;
		this.nodeRepository = nodeRepository;
	}

	@Override
	public PageDTO<NetworkDTO> getAllNets(int page, int size, String sortField, 
						Direction direction, List<String> status, String search) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
		Page<NetworkDTO> networks;
		
		if (search != null || !search.isBlank()) {
			Specification<Network> spec = resolveSpecificationFromInfixExpr(search);
			networks = networkRepository.findAll(spec,pageable).map(n->convertToNetworkDTO(n));
		}else {
			networks = networkRepository.findAll(pageable).map(n->convertToNetworkDTO(n));
		}
		return new PageDTO<NetworkDTO>(networks);
	}

	protected Specification<Network> resolveSpecificationFromInfixExpr(String searchParameters) {
		CriteriaParser parser = new CriteriaParser();
		GenericSpecificationBuilder<Network> specBuilder = new GenericSpecificationBuilder<>();
		return specBuilder.build(parser.parse(searchParameters), NetworkSpecification::new);
	}

	protected NetworkDTO convertToNetworkDTO(Network n) {
		return this.mapper.map(n, NetworkDTO.class);
	}

	@Override
	public NetworkDTO getNetById(NetworkId id) {
		return getNetworkById(id);
	}

	@Override
	public Optional<NetworkDTO> updateNet(NetworkId id, @Valid NetworkDTO net) {
		return networkRepository.findById(id).map(n -> convertToNetworkDTO(n));
	}

	@Override
	public void deleteNet(NetworkId id) {
		networkRepository.deleteById(id);

	}

	@Override
	public PageDTO<NodeDTO> getAllNodes(NetworkId id, @Valid NodeSearchCommandDTO command) {
		return null;

	}

	@Override
	public NodeDTO getNodeFromAbbr(NetworkId id, String abbr) {
		Optional<Network> net = networkRepository.findById(id);
		if (net.isPresent()) {
			return NodeDTO.fromModel(net.get().getByAbbr(new NodeAbbr(abbr)));
		}
		return null;
	}

	@Override
	public CompletionStage<Either<CommandFailure,Tuple2<UUID, NetworkDTO>>> createNetwork(@Valid CreateNetwork createNetwork)
							throws CommandValidationException, NetworkCreationException {
		
		logger.debug("Creating network{}", createNetwork);
		Network network = new Network(context);
		CompletionStage<Either<CommandFailure, NetworkCreated>> createNetworkPromise = network.handle(createNetwork);
		return createNetworkPromise.thenCompose(networkRequested-> networkRequested.fold(
				rejectNetwork -> completed(rejectNetwork),
				acceptNetwork -> completed(acceptNetwork)
				));	
	}

	@Override
	public Boolean netExist(NetworkId netId) {
		return networkRepository.existsById(netId);
	}

	@Override
	public Boolean isAbbrUsed(NetworkId id, String abbr) {
		Network network = networkRepository.getById(id);
		return (network.getByAbbr(new NodeAbbr(abbr)) == null) ? true : false;
	}

	@Override
	public NetworkDTO getNetByCode(String code) {
		Network network = networkRepository.findByCode(code);
		return convertToNetworkDTO(network);
	}

	@Override
	public Optional<PageDTO<AbbrDTO>> getUsedAbbr(NetworkId netId, int page, int size, String sortField, Direction direction) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
		Page<AbbrAndName> result = nodeRepository.findAbbrsUsed(netId, pageable);
		
		if (!result.hasContent()) {
			return Optional.empty();
		}
		
		List<AbbrDTO> dtos = result.stream().map(n -> new AbbrDTOImpl(n)).collect(Collectors.toList());
		Page<AbbrDTO> resPage = new PageImpl<>(dtos);
		return Optional.of(new PageDTO<AbbrDTO>(resPage));
	}

	@Override
	public Optional<PageDTO<LineClassDTO>> getLineClasses(NetworkId netId, int page, int size, String sortField, String direction,
			List<String> status) {

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
		Page<LineClass> result = networkRepository.findLineClasses(netId, pageable);
		List<LineClassDTO> dtos = result.stream().map(lp->new LineClassDTO(lp)).collect(Collectors.toList());
		Page<LineClassDTO> resPage = new PageImpl<>(dtos);
		return Optional.of(new PageDTO<LineClassDTO>(resPage));
	}

	@Override
	public CompletionStage<Either<CommandFailure, Tuple2<UUID, NetworkDTO>>> copyNetwork(NetworkId originNetId, String newName) {
		
		CopyNetwork command = new CopyNetwork(originNetId, newName);
		Network network = new Network(context);
		
		CompletionStage<Either<CommandFailure,NetworkCreated>> createNetworkPromise =  network.handle(command);
		return createNetworkPromise.thenCompose(networkRequested-> networkRequested.fold(
				rejectNetwork -> completed(rejectNetwork),
				acceptNetwork -> completed(acceptNetwork)
				));	
	}

	@Override
	public PageDTO<NetworkVersionDTO> getVersionsOfNetwork(NetworkId netId, int page, int size, String sortField, Direction direction) {
		return null;
	}

	@Override
	public Optional<NetworkVersionDTO> getNetworkVersion(NetworkId netId, Long versionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteLine(NetworkId netId, UUID lineId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTrainClass(NetworkId netId, TrainClassId id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TrainClassDTO> getTrainClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CompagnyDTO> getOwner(NetworkId netId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<CompagnyDTO> setOwner(NetworkId netId, Long compagnyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegionDTO> getRegions(NetworkId netId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<RegionDTO> getRegionById(NetworkId netId, UUID regionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doesRegionExist(NetworkId netId, RegionDTO region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegionDTO addRegion(NetworkId netId, RegionDTO region) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteRegion(NetworkId netId, UUID regionId) {
		// TODO Auto-generated method stub
		return null;
	}

	private CompletableFuture<Either<CommandFailure,Tuple2<UUID,NetworkDTO>>> completed(CommandFailure rejectCreation){
		return completedFuture(left(rejectCreation));
	}
	private CompletableFuture<Either<CommandFailure,Tuple2<UUID,NetworkDTO>>> completed(NetworkCreated network){
		NetworkId id = network.getId();
		return completedFuture(right(Tuple.of(id.getId(), getNetworkById(id))));
	}

	private NetworkDTO getNetworkById(NetworkId id) {
		Optional<Network> network = networkRepository.findById(id);
		if (network.isEmpty()) {
			throw new NetworkNotFoundException("Network with id:" + id + " could not be found");
		}
		return convertToNetworkDTO(network.get());
	}

	@Override
	public NodeDTO getNodeFromNet(NetworkId netId, String nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegionDTO updateRegion(NetworkId netId, UUID regionId, RegionDTO region) {
		// TODO Auto-generated method stub
		return null;
	}
}
