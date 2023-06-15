package com.railweb.trafficmgt.infra.persistence.network;

import java.util.Map;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Speed;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;

import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.converters.SpeedConverter;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.ids.LineId;
import com.railweb.trafficmgt.domain.ids.TimeIntervalId;
import com.railweb.trafficmgt.domain.network.LineClass;
import com.railweb.trafficmgt.domain.network.LineGradient;
import com.railweb.trafficmgt.domain.network.LinePath;
import com.railweb.trafficmgt.domain.network.Network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Builder
public class LineEntity extends AbstractEntity<LineId> {
	
	private Boolean bidirectional;
	@OneToMany
	private Set<LinePath> linePaths;
	@OneToOne
	private LineClass lineClass;
	@Convert(converter=SpeedConverter.class)
	private Quantity<Speed> speed;
	@Convert(converter=LengthConverter.class)
	private Quantity<Length> length;
	@Enumerated(EnumType.STRING)
	private LineGradient gradient;
	@ManyToOne
	private Network net;
	
	@OneToMany
	@JoinTable(name = "line_timeinterval_linepath",
				joinColumns = @JoinColumn(name="line_id", referencedColumnName="id"),
				inverseJoinColumns = @JoinColumn(name="linepath_id",referencedColumnName="id"))
	@MapKey(name="timeInterval_id")
	private Map<TimeIntervalId,LinePath> timeIntervals;
	
	@Column(name="created_by")
	@CreatedBy
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;


}
