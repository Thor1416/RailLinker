package com.railweb.trafficmgt.infra.persistence.train;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

import org.hibernate.envers.Audited;
import org.springframework.lang.NonNull;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.TrainCycleType;
import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.TrainId;
import com.railweb.trafficmgt.domain.train.RunDays;
import com.railweb.trafficmgt.domain.train.TimeInterval;
import com.railweb.trafficmgt.domain.train.Train;
import com.railweb.trafficmgt.domain.train.TrainCycleMap;

import lombok.Data;

@Data
@Entity
@Audited
public class TrainEntity extends AbstractEntity<TrainId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8859188941490019805L;
	@NonNull
	private NetworkId networkId;
	@NonNull
	private String code;

	/* Weight and tractive force */
	@OrderColumn(name = "train_segment_order")
	private List<TrainSegmentEntity> trainSegments;

	@OneToMany
	@JoinTable(name = "trains_trainStop", joinColumns = { @JoinColumn(name = "train_number", referencedColumnName = "train_number"),
			@JoinColumn(name = "timetableId", referencedColumnName = "timtableId") }, inverseJoinColumns = @JoinColumn(name = "trainStop_id", referencedColumnName = "id"))
	@MapKeyColumn(name = "StopNumber")
	@OrderBy("StopNumber ASC")
	private Map<Integer, TrainStopEntity> trainStops;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "train_id")
	private List<TimeInterval<?>> timeIntervalList;

	@OneToOne(fetch = FetchType.LAZY)
	private Train previousJoinedTrain;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Train nextJoinedTrain;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "trains_trainsCyleMap", joinColumns = { @JoinColumn(name = "train_number", referencedColumnName = "train_number"),
			@JoinColumn(name = "timetableId", referencedColumnName = "timetableId") }, inverseJoinColumns = @JoinColumn(name = "cycleMapId", referencedColumnName = "id"))
	@MapKeyJoinColumn(name = "trainCycleTypeId")
	private Map<TrainCycleType, TrainCycleMap> cycles;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Train_RunDay", joinColumns = { @JoinColumn(name = "train_number", referencedColumnName = "train_number"),
			@JoinColumn(name = "timetableId", referencedColumnName = "timetableId") }, inverseJoinColumns = @JoinColumn(name = "rundayId", referencedColumnName = "id"))
	private Set<RunDays> rundays;
}
