package com.railweb.trafficmgt.infra.persistence.network;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.geo.Point;

import com.railweb.admin.domain.id.InfrastructureManagerId;
import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.Location;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;
import com.railweb.trafficmgt.domain.network.NodeTrack;
import com.railweb.trafficmgt.domain.network.Track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Audited
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NodeEntity extends AbstractEntity<NodeId>  {

	
	@ElementCollection
	private Set<NetworkId> nets;
	private String name;
	private InfrastructureManagerId owner;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@MapKey(name = "net_id")
	private Map<NetworkId, Location> locations;
	private Double longitude;
	private Double lattitude;
	private Point GPSLocation;
	
	@OneToMany
	@OrderColumn(name = "track_order")
	private List<NodeTrack> tracks;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="nodetrack_interval",
			joinColumns=@JoinColumn(name="node_id", referencedColumnName = "id"),
			inverseJoinColumns=@JoinColumn(name="track_id", referencedColumnName ="id"))
	@MapKey(name="timeIntervalId")
	private Map<TimeIntervalId,Track<NodeId>> timeintervals;
	
	@ManyToOne
	@LastModifiedBy
	private String lastEditedBy;

	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private OffsetDateTime lastEdit;

	@ManyToOne
	@CreatedBy
	private String createdBy;
	private ZoneId defaultTimezone;
	@Convert(converter = LengthConverter.class)
	private Quantity<Length> length;
	

}
