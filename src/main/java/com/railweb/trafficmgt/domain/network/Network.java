package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsUnmodifiableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.railweb.admin.domain.Region;
import com.railweb.admin.domain.id.InfrastructureManagerId;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.base.AbstractDomainEntity;
import com.railweb.shared.domain.util.Tuple;
import com.railweb.shared.domain.util.Visitable;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.application.command.CopyNetworkCommandExecutor;
import com.railweb.trafficmgt.application.command.CreateNetworkCommandExecutor;
import com.railweb.trafficmgt.domain.TimetableVisitor;
import com.railweb.trafficmgt.domain.TrackSystem;
import com.railweb.trafficmgt.domain.commands.CopyNetwork;
import com.railweb.trafficmgt.domain.commands.CreateNetwork;
import com.railweb.trafficmgt.domain.events.LineAdded;
import com.railweb.trafficmgt.domain.events.LineRemovedEvent;
import com.railweb.trafficmgt.domain.events.NodeAddedEvent;
import com.railweb.trafficmgt.domain.events.NodeRemovedEvent;
import com.railweb.trafficmgt.domain.ids.LinePathId;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.train.TrainRoute;
import com.railweb.trafficmgt.infra.persistence.network.NetworkEntity;
import com.railweb.usermgt.model.User;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Representation of a rail network
 * 
 * @author Thorbj�rn Simonsen
 * @version 1.0
 */

@Data
@Slf4j
public class Network extends AbstractAggregateRoot<NetworkId> implements Visitable<TimetableVisitor> {

	@NotNull
	private String name;
	private String code;
	private InfrastructureManagerId owner;
	@CreatedBy
	private User creator;
	@CreatedDate
	private OffsetDateTime created;
	private OffsetDateTime validFrom;
	private OffsetDateTime validTo;
	@LastModifiedDate
	private OffsetDateTime lastedit;
	@LastModifiedBy
	private User lastEditedBy;

	private NodePrefix prefix;

	// ForeignKeys to Timetable
	private Set<TimetableId> timetables;
	private Set<LineClass> lineclasses;
	private Set<Region> regions;
	private Set<TrainRoute> trainroutes;
	private Set<TrackSystem> trackSystems;

	private DefaultListenableGraph<NetworkNode, LinePath> netDelegate;
	private List<NetworkValidator> networkValidators;

	public Network(ApplicationContext context) {
		super(context, new NetworkId(UUID.randomUUID()));
		Graph<NetworkNode, LinePath> innerGraph = GraphTypeBuilder.directed()
				.allowingMultipleEdges(true).allowingSelfLoops(false)
				.edgeClass(LinePath.class).vertexClass(NetworkNode.class)
				.buildGraph();
		DefaultListenableGraph<NetworkNode, LinePath> netDelegate = new DefaultListenableGraph<NetworkNode, LinePath>(innerGraph);
		this.netDelegate = netDelegate;
		// addvalidators
	}

	public Network(ApplicationContext context, String name) {
		super(context, new NetworkId(UUID.randomUUID()));
		this.name = name;
		Graph<NetworkNode, LinePath> innerGraph = GraphTypeBuilder.directed().allowingMultipleEdges(true).allowingSelfLoops(false).edgeClass(LinePath.class).vertexClass(NetworkNode.class)
				.buildGraph();
		DefaultListenableGraph<NetworkNode, LinePath> netDelegate = new DefaultListenableGraph<>(innerGraph);
		this.netDelegate = netDelegate;
	}

	/**
	 * Copy constructor
	 * 
	 * @param network
	 */
	public Network(Network network) {
		super(network);
		this.name = network.name + "_copy";
		this.code = network.code + "_c";
		this.owner = network.owner;
		this.prefix = network.prefix;
		this.timetables = network.timetables;
		this.lineclasses = network.lineclasses;
		this.regions = network.regions;
		this.trainroutes = network.trainroutes;
		this.trackSystems = network.trackSystems;
		this.netDelegate = network.netDelegate;
	}

	@Override
	protected AbstractAggregateRoot<NetworkId>.AggregateRootBehavior<?> initialBehavior() {
		AggregateRootBehaviorBuilder<NetworkId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
		behaviorBuilder.setCommandHandlers(CreateNetwork.class, getHandler(CreateNetworkCommandExecutor.class));
		behaviorBuilder.setCommandHandlers(CopyNetwork.class, getHandler(CopyNetworkCommandExecutor.class));
		return behaviorBuilder.build();
	}

	public Tuple<Node> getNodes(LinePath linepath) {
		return new Tuple<>(netDelegate.getEdgeSource(linepath).getNode(), netDelegate.getEdgeTarget(linepath).getNode());
	}

	public Set<Node> getNodes() {
		return netDelegate.vertexSet().stream().map(NetworkNode::getNode).collect(Collectors.toSet());
	}

	public void addNode(Node node) {
		netDelegate.addVertex(new NetworkNode(this, node));
		log.info("Added node {} to net{}", node.getName(), this.name);
		registerEvent(new NodeAddedEvent(this, node, Instant.now()));
	}

	public void removeNode(Node node) {
		netDelegate.removeVertex(new NetworkNode(this, node));
		log.info("Removed node{} for net {}", node.getName(), this.name);
		registerEvent(new NodeRemovedEvent(this, node, Instant.now()));
	}

	public Set<LinePath> getLinePaths() {
		return netDelegate.edgeSet();
	}

	public Set<Line> getLines() {
		Set<Line> result = new HashSet<>();
		for (LinePath lp : netDelegate.edgeSet()) {
			if (!result.contains(lp.getLine())) {
				result.add(lp.getLine());
			}
		}
		return result;
	}

	public Set<LinePath> getLinePathOf(Node node) {
		return netDelegate.edgesOf(new NetworkNode(this, node));
	}

	public Set<Line> getLinesOf(Node node) {
		Set<Line> result = new HashSet<>();
		for (LinePath lp : netDelegate.edgesOf(new NetworkNode(this, node))) {
			if (!result.contains(lp.getLine())) {
				result.add(lp.getLine());
			}
		}
		return result;
	}

	public LinePath getLinePath(Node from, Node to) {
		return netDelegate.getEdge(new NetworkNode(this, from), new NetworkNode(this, to));
	}

	public Line getLine(Node from, Node to) {
		return netDelegate.getEdge(new NetworkNode(this, from), new NetworkNode(this, to)).getLine();
	}

	Node getFrom(LinePath linepath) {
		NetworkNode node = netDelegate.containsEdge(linepath) ? netDelegate.getEdgeSource(linepath) : null;
		return node.getNode();
	}

	Node getTo(LinePath linePath) {
		NetworkNode node = netDelegate.containsEdge(linePath) ? netDelegate.getEdgeTarget(linePath) : null;
		return node.getNode();
	}

	public void addLine(Line line, Node from, Node to) {

		NetworkNode toNode = netDelegate.vertexSet().stream().filter(n -> n.getNode().getId().equals(to.getId())).findAny().get();
		NetworkNode fromNode = netDelegate.vertexSet().stream().filter(n -> n.getNode().getId().equals(from.getId())).findAny().get();

		Set<LinePath> linePaths = new HashSet<>();

		if (line.isBiDirectional()) {
			LinePath linePath1 = new LinePath(line, from, to);
			LinePath linePath2 = new LinePath(line, to, from);
			netDelegate.addEdge(fromNode, toNode, linePath1);
			netDelegate.addEdge(toNode, fromNode, linePath2);
			linePaths.add(linePath1);
			linePaths.add(linePath2);
		} else {
			LinePath linePath1 = new LinePath(line, from, to);
			netDelegate.addEdge(fromNode, toNode, linePath1);
			linePaths.add(linePath1);
		}

		Set<LinePathId> linePathIds = linePaths.stream().map(lp -> lp.getId()).collect(Collectors.toSet());

		registerEvent(new LineAdded(line.getId(), Instant.now(), linePathIds));
	}

	public void removeLine(Line line) {
		line.getTracks().clear();
		netDelegate.removeAllEdges(line.getLinePaths());
		line.getLinePaths().clear();
		registerEvent(new LineRemovedEvent(line.getId(), Instant.now(), this.id));
	}

	public List<LinePath> getRoute(Node from, Node to) {
		NetworkNode toNode = netDelegate.vertexSet().stream().filter(n -> n.getNode().getId().equals(to.getId())).findAny().get();
		NetworkNode fromNode = netDelegate.vertexSet().stream().filter(n -> n.getNode().getId().equals(from.getId())).findAny().get();
		GraphPath<NetworkNode, LinePath> path = DijkstraShortestPath.findPathBetween(netDelegate, fromNode, toNode);
		return path == null ? null : path.getEdgeList();
	}

	public Node getByAbbr(NodeAbbr abbr) {
		NodeId nodeId = new NodeId(abbr, prefix);
		return getNodeById(nodeId);
	}

	public Node getNodeById(NodeId id) {
		for (NetworkNode node : netDelegate.vertexSet()) {
			if (node.getNode().getId() == id) {
				return node.getNode();
			}
		}
		return null;
	}

	public Node getNodeByName(String name) {
		for (NetworkNode node : netDelegate.vertexSet()) {
			if (node.getNode().getName().equals(name)) {
				return node.getNode();
			}
		}
		return null;
	}

	public Graph<NetworkNode, LinePath> getGraph() {
		return new AsUnmodifiableGraph<NetworkNode, LinePath>(netDelegate);
	}

	public TrainRoute createTrainRoute(String name, List<NetworkNode> nodes) {
		List<NetworkNode> routeNodes = new ArrayList<>();

		// Find all nodes in route
		for (int i = 0; i < nodes.size() - 1; i++) {
			NetworkNode source = nodes.get(i);
			NetworkNode target = nodes.get(i + 1);

			// Use a graph traversal algorithm to find the route between source
			// and target and collect all the nodes on the route
			List<NetworkNode> intermediateNodes = traverseGraph(source, target);
			routeNodes.addAll(intermediateNodes);
		}

		// Add the last node in the provided list (the destination node)
		routeNodes.add(nodes.get(nodes.size() - 1));

		TrainRoute route = new TrainRoute(name, routeNodes);
		this.trainroutes.add(route);
		return route;
	}

	private List<NetworkNode> traverseGraph(NetworkNode source, NetworkNode target) {

		List<NetworkNode> intermediateNodes = new ArrayList<>();

		// Perform a breadth-first search to find the route between source and target
		// nodes
		Queue<NetworkNode> queue = new LinkedList<>();
		Map<NetworkNode, NetworkNode> parentMap = new HashMap<>();
		queue.add(source);

		while (!queue.isEmpty()) {

			NetworkNode current = queue.poll();

			if (current.equals(target)) {
				// Found the target node, construct the route by traversing the parent map
				NetworkNode node = current;
				while (node != null) {
					intermediateNodes.add(node);
					node = parentMap.get(node);
				}
				Collections.reverse(intermediateNodes);
				break;
			}

			// Visit Neighboring nodes
			for (NetworkNode neighbor : getNeighbors(current)) {
				if (!parentMap.containsKey(neighbor)) {
					parentMap.put(neighbor, current);
					queue.add(neighbor);
				}
			}
		}

		return intermediateNodes;
	}

	private List<NetworkNode> getNeighbors(NetworkNode node) {
		List<NetworkNode> neighbors = new ArrayList<>();

		// Iterate over the outgoing edges from the given node and collect the target
		// nodes
		for (LinePath edge : netDelegate.outgoingEdgesOf(node)) {
			NetworkNode neighbor = netDelegate.getEdgeTarget(edge);
			neighbors.add(neighbor);
		}

		return neighbors;
	}

	public String toString() {
		return String.format("Net[Nodes:%d,LinePath:%s", getNodes().size(), getLinePaths().size());
	}

	@Override
	public void accept(TimetableVisitor visitor) {
		visitor.visit(this);

	}

	@Override
	public Network fromJpaEntity(AbstractEntity<NetworkId> jpaEntity) {
		if()
		return null;
	}

	@Override
	public NetworkEntity toJpaEntity(AbstractDomainEntity<NetworkId> domainEntity) {
		// TODO Auto-generated method stub
		return null;
	}

}
