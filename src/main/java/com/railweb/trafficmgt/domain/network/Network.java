package com.railweb.trafficmgt.domain.network;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.event.GraphEdgeChangeEvent;
import org.jgrapht.event.GraphListener;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.AsUnmodifiableGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.railweb.admin.domain.InfrastructureManager;
import com.railweb.admin.domain.Region;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.items.ItemWithIdSet;
import com.railweb.shared.domain.util.Tuple;
import com.railweb.shared.domain.util.Visitable;
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
import com.railweb.trafficmgt.exception.NodeNotFoundException;
import com.railweb.usermgt.model.User;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Representation of a rail network
 * 
 * @author Thorbjørn Simonsen
 * @version 1.0
 */

@Entity
@Table(name = "RailNetwork")
@Data
@Slf4j
@Audited
public class Network extends AbstractAggregateRoot<NetworkId> implements Visitable<TimetableVisitor> {

	@NotNull
	private String name;
	@Column(unique = true)
	private String code;
	@ManyToOne
	private InfrastructureManager owner;
	@CreatedBy
	private User creator;
	@CreatedDate
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime created;
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime validFrom;
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime validTo;
	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime lastedit;
	@LastModifiedBy
	private User lastEditedBy;

	private NodePrefix prefix;

	// ForeignKeys to Timetable
	@OneToMany
	private Set<TimetableId> timetables;
	@OneToMany(fetch = FetchType.LAZY)
	private ItemWithIdSet<LineClass,LineClasssId> lineclasses;
	@OneToMany(fetch = FetchType.LAZY)
	private ItemWithIdSet<Region, RegionId> regions;
	@OneToMany(fetch = FetchType.LAZY)
	private ItemWithIdSet<TrainRoute> trainroutes;
	@OneToMany(fetch = FetchType.LAZY)
	private Set<TrackSystem> trackSystems;

	// Database representation of the graph
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<NetEdge> netEdges;
	@OneToMany(mappedBy = "network", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<NetworkNode> netNodes;

	@Transient
	private DefaultListenableGraph<NetworkNode, LinePath> netDelegate;

	public Network(ApplicationContext context) {
		super(context, new NetworkId(UUID.randomUUID()));
		Graph<NetworkNode, LinePath> innerGraph = GraphTypeBuilder.directed().allowingMultipleEdges(true).allowingSelfLoops(false)
				.edgeClass(LinePath.class).vertexClass(NetworkNode.class).buildGraph();
		DefaultListenableGraph<NetworkNode, LinePath> netDelegate = new DefaultListenableGraph<NetworkNode, LinePath>(innerGraph);
		netDelegate.addGraphListener(new NetworkListener());
		this.netDelegate = netDelegate;
	}

	public Network(ApplicationContext context, String name) {
		super(context,new NetworkId(UUID.randomUUID()));
		this.name = name;
		Graph<NetworkNode, LinePath> innerGraph = GraphTypeBuilder.directed().allowingMultipleEdges(true).allowingSelfLoops(false)
				.edgeClass(LinePath.class).vertexClass(NetworkNode.class).buildGraph();
		DefaultListenableGraph<NetworkNode, LinePath> netDelegate = new DefaultListenableGraph<>(innerGraph);
		netDelegate.addGraphListener(new NetworkListener());
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

	@PostLoad
	private void loadGraph() throws NodeNotFoundException {
		for (NetworkNode node : netNodes) {
			netDelegate.addVertex(node);
		}
		for (NetEdge edge : netEdges) {
			NetworkNode fromNode = netNodes.stream().filter(n -> edge.getFromNode().equals(n.getNode())).findFirst()
					.orElseThrow(NodeNotFoundException::new);
			NetworkNode toNode = netNodes.stream().filter(n -> edge.getToNode().equals(n.getNode())).findFirst()
					.orElseThrow(NodeNotFoundException::new);
			netDelegate.addEdge(fromNode, toNode, edge.getLinePath());
		}

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
		
		Set<LinePathId> linePathIds = linePaths.stream()
											.map(lp -> lp.getId())
											.collect(Collectors.toSet());
		
		registerEvent(new LineAdded(line.getId(), Instant.now(), linePathIds));
	}

	public void removeLine(Line line) {
		line.getTracks().clear();
		netDelegate.removeAllEdges(line.getLinePaths());
		line.getLinePaths().clear();
		registerEvent(new LineRemovedEvent( line.getId(),Instant.now(),this.id));
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
		DijkstraShortestPath<NetworkNode, LinePath> alg = new DijkstraShortestPath<>(netDelegate);
		List<NetEdge> netEdgesInRoute = new ArrayList<>();

		for (int i = 0; i < nodes.size() - 1; i++) {
			GraphPath<NetworkNode, LinePath> path = alg.getPath(nodes.get(i), nodes.get(i + 1));
			if (path != null) {
				for (LinePath lp : path.getEdgeList()) {
					Optional<NetEdge> netEdge = netEdges.stream().filter(ne -> ne.getLinePath().equals(lp)).findAny();
					if (netEdge.isPresent()) {
						netEdgesInRoute.add(netEdge.get());
					}
				}
			}

		}
		if (netEdgesInRoute.isEmpty()) {
			return null;
		}

		TrainRoute route = new TrainRoute(name, netEdgesInRoute);
		this.trainroutes.add(route);
		return route;
	}

	public String toString() {
		return String.format("Net[Nodes:%d,LinePath:%s", getNodes().size(), getLinePaths().size());
	}

	@Override
	public void accept(TimetableVisitor visitor) {
		visitor.visit(this);

	}

	class NetworkListener implements GraphListener<NetworkNode, LinePath> {

		@Override
		public void vertexAdded(GraphVertexChangeEvent<NetworkNode> e) {
			if (!netNodes.contains(e.getVertex()))
				netNodes.add(e.getVertex());
		}

		@Override
		public void vertexRemoved(GraphVertexChangeEvent<NetworkNode> e) {
			if (netNodes.contains(e.getVertex()))
				netNodes.remove(e.getVertex());

		}

		@Override
		public void edgeAdded(GraphEdgeChangeEvent<NetworkNode, LinePath> e) {

			NetEdge edge = new NetEdge(Network.this, e.getEdgeSource().getNode(), e.getEdgeTarget().getNode(), e.getEdge());
			if (!netEdges.contains(edge)) {
				netEdges.add(edge);
			}

		}

		@Override
		public void edgeRemoved(GraphEdgeChangeEvent<NetworkNode, LinePath> e) {
			netEdges.removeIf(ne -> ne.getLinePath().equals(e.getEdge()));

		}

	}

	


}
