package com.railweb.trafficmgt.infra.persistence.network;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.railweb.admin.domain.Region;
import com.railweb.admin.domain.id.InfrastructureManagerId;
import com.railweb.trafficmgt.domain.TrackSystem;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.network.LineClass;
import com.railweb.trafficmgt.domain.network.NetEdge;
import com.railweb.trafficmgt.domain.network.NetworkNode;
import com.railweb.trafficmgt.domain.network.NodePrefix;
import com.railweb.trafficmgt.domain.train.TrainRoute;
import com.railweb.usermgt.model.User;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "RailNetwork")
@Audited
@Data
@AllArgsConstructor
@Builder
public class NetworkEntity {

	@NotNull
	private String name;
	@Column(unique = true)
	private String code;
	private InfrastructureManagerId owner;
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
	private Set<LineClass> lineclasses;
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Region> regions;
	@OneToMany(fetch = FetchType.LAZY)
	private	Set<TrainRoute> trainroutes;
	@OneToMany(fetch = FetchType.LAZY)
	private Set<TrackSystem> trackSystems;

	// Database representation of the graph
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<NetEdge> netEdges;
	@OneToMany(mappedBy = "network", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<NetworkNode> netNodes;
}
