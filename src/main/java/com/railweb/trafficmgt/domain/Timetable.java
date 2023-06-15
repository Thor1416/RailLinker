
package com.railweb.trafficmgt.domain;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.google.common.collect.Iterables;
import com.railweb.admin.domain.OperatingCompagny;
import com.railweb.shared.converters.DurationConverter;
import com.railweb.shared.converters.LengthConverter;
import com.railweb.shared.converters.MassConverter;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.base.ConcurrencySafeDomainObject;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.shared.domain.events.DomainEvent;
import com.railweb.shared.domain.util.Visitable;
import com.railweb.trafficmgt.domain.engines.EngineClass;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.ids.TrainCycleId;
import com.railweb.trafficmgt.domain.train.Train;
import com.railweb.trafficmgt.domain.train.TrainClass;
import com.railweb.trafficmgt.domain.train.TrainGroup;
import com.railweb.trafficmgt.domain.train.TrainRoute;
import com.railweb.trafficmgt.domain.train.TrainsCycle;
import com.railweb.trafficmgt.domain.train.TrainsData;
import com.railweb.trafficmgt.domain.train.WeightTableRow;
import com.railweb.trafficmgt.domain.validators.TimetableValidator;
import com.railweb.trafficmgt.domain.visitors.TimetableTransversalVisitor;
import com.railweb.trafficmgt.domain.visitors.TimetableVisitor;
import com.railweb.trafficmgt.filters.ModelPredicates;
import com.railweb.trafficmgt.util.SortPattern;
import com.railweb.usermgt.model.User;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Timetable extends AbstractAggregateRoot<TimetableId> implements Visitable<TimetableVisitor>,
	ConcurrencySafeDomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1032935963550995777L;
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime validTo;
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime validFrom;

	private String name;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;
	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP")
	private OffsetDateTime lastEdit;

	@ManyToOne
	@LastModifiedBy
	private User lastEditedBy;

	@Enumerated(EnumType.STRING)
	private TimetableStatus status;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "timetable_routes", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "trainroute_id"))
	private Set<TrainRoute> trainroutes;

	@OneToMany
	private Set<Train> trains;

	@OneToMany
	private Set<TrainCycleType> cyclesTypes;

	@OneToMany
	private Set<TimetableImage> timetableImage;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "timetables_trainclass", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "trainClass_id"))
	private Set<TrainClass> trainclasses;

	@OneToOne
	@JoinColumn(name = "timetable_id")
	private TrainsData trainsData;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "timetables_engineclass", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "engineClass_id"))
	private Set<EngineClass> engineclasses;

	@OneToMany
	private Set<TrainGroup> groups;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "timetables_treainclass", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "engineClass_id"))
	private Set<OperatingCompagny> operatingCompagnies;

	private ZoneId defaultTimeZone;

	@Basic(optional = false)
	private Locale defaultLocale;

	@ManyToMany
	@JoinTable(name = "weightTable", joinColumns = @JoinColumn(name = "timetable_id"), inverseJoinColumns = @JoinColumn(name = "weightTableRow_id"))
	private Set<WeightTableRow> weightTable;

	@Transient
	private List<TimetableValidator> validators;

	@Convert(converter=MassConverter.class)
	private Quantity<Mass> weightPerAxis;
	@Convert(converter=MassConverter.class)
	private Quantity<Mass> weightPerAxisEmpty;
	@Convert(converter=LengthConverter.class)
	private Quantity<Length> lengthPerAxis;
	@Convert(converter=DurationConverter.class)
	private Duration stationTransferTime;

	@Transient
	private SortPattern sortPattern;

	public void addTrain(Train train) {
		trains.add(train);
	}

	public Timetable(Timetable source) {
		super(source);
		this.validTo = source.validTo;
		this.validFrom = source.validFrom;
		this.createdBy = source.createdBy;

	}

	public Timetable(ApplicationContext applicationContext, TimetableId entityId) {
		super(applicationContext, entityId);
	}

	@Override
	protected AggregateRootBehavior<TimetableId> initialBehavior() {
		AggregateRootBehaviorBuilder<TimetableId> behaviorBuilder = new AggregateRootBehaviorBuilder<TimetableId>();
		// TODO add command handlers
		return behaviorBuilder.build();
	}

	public TrainCycleType getCycleTypeByKey(String key) {
		for (TrainCycleType type : cyclesTypes) {
			if (key.equals(type.getKey())) {
				return type;
			}
		}
		return null;
	}

	public boolean hasNumber(Long number) {
		TrainNumber trainNumber = new TrainNumber(number);
		for (Train train : trains) {
			if (train.getId().getTrainNumber() == trainNumber) {
				return true;
			}
		}
		return false;
	}

	public Optional<TrainsCycle> getCycleById(TrainCycleId id) {
		for (TrainCycleType type : cyclesTypes) {
			for (TrainsCycle cycle : type.getCycles()) {
				if (cycle.getId().sameValueAs(id)) {
					return Optional.of(cycle);
				}
			}
		}
		return Optional.empty();
	}

	public Optional<TrainsCycle> getCyclebyIdAndTypes(TrainCycleId id, TrainCycleType type) {
		return getById(id, type.getCycles());
	}

	private <T extends IdentifiableDomainObject<U>, U extends DomainObjectId<?>> Optional<T> getById(U id, Iterable<T> items) {
		return Optional.ofNullable(Iterables.tryFind(items, ModelPredicates.matchId(id)::test).orNull());
	}

	@Override
	public void accept(TimetableVisitor visitor) {
		visitor.visit(this);
		
	}
	
	public void accept(TimetableTransversalVisitor visitor) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void processValidators(DomainEvent event) {
		if(!event.isConsumed()) {
			for(TimetableValidator validator: validators) {
				validator.validate(event);
				if(event.isConsumed()) break;
			}
		}
	}

	@Override
	public Long version() {
		// TODO Auto-generated method stub
		return null;
	}
}
