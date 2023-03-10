
package com.railweb.trafficmgt.domain;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.railweb.admin.domain.OperatingCompagny;
import com.railweb.shared.converters.ZoneIdConverter;
import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.RandomUUID;
import com.railweb.shared.domain.items.ItemWithIdSet;
import com.railweb.trafficmgt.domain.engines.EngineClass;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.train.Train;
import com.railweb.trafficmgt.domain.train.TrainClass;
import com.railweb.trafficmgt.domain.train.TrainGroup;
import com.railweb.trafficmgt.domain.train.TrainRoute;
import com.railweb.trafficmgt.domain.train.TrainsData;
import com.railweb.trafficmgt.domain.train.WeightTableRow;
import com.railweb.usermgt.model.User;
import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Entity
public class Timetable extends AbstractAggregateRoot<TimetableId> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1032935963550995777L;
	@Column(columnDefinition="TIMESTAMP")
	private OffsetDateTime validTo;
	@Column(columnDefinition="TIMESTAMP")
	private OffsetDateTime validFrom;
	
	private String name;
	@NotNull
	@ManyToOne
	private User createdBy;
	@LastModifiedDate
	@Column(columnDefinition="TIMESTAMP")
	private OffsetDateTime lastEdit;
	
	@ManyToOne
	@LastModifiedBy
	private User lastEditedBy;
	
	@Enumerated(EnumType.STRING)
	private TimetableStatus status;
	
	@ManyToMany(cascade = {
			CascadeType.MERGE,
			CascadeType.PERSIST
	})
	@JoinTable(name="timetable_routes",
		joinColumns=@JoinColumn(name="timetable_id"),
		inverseJoinColumns=@JoinColumn(name="trainroute_id")
	)
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<TrainRoute> trainroutes;
	
	@OneToMany
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<Train> trains;
	
	@OneToMany
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<TrainCycleType> cyclesTypes;
	
	@OneToMany
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<TimetableImages> timetableImages;
	
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name="timetables_trainclass",
		joinColumns=@JoinColumn(name="timetable_id"),
		inverseJoinColumns=@JoinColumn(name="trainClass_id"))
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<TrainClass> trainclasses;
	
	@OneToMany
	private TrainsData trainsData;
	
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name="timetables_engineclass",
		joinColumns=@JoinColumn(name="timetable_id"),
		inverseJoinColumns=@JoinColumn(name="engineClass_id"))
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<EngineClass> engineclasses;
	
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<TrainGroup> groups;
	
	@ManyToMany(cascade= CascadeType.ALL)
	@JoinTable(name="timetables_treainclass",
		joinColumns=@JoinColumn(name="timetable_id"),
		inverseJoinColumns=@JoinColumn(name="engineClass_id"))
	@Type(type="itemWithIdSet")
	private ItemWithIdSet<OperatingCompagny> operatingCompagnies;
	
	@Convert(converter=ZoneIdConverter.class)
	private ZoneId defaultTimeZone;
	
	@Basic(optional=false)
	private Locale defaultLocale;
	
	@ManyToMany
	@JoinTable(name="weightTable", 
	joinColumns=@JoinColumn(name="timetable_id"),
	inverseJoinColumns=@JoinColumn(name="weightTableRow_id"))
	private Set<WeightTableRow> weightTable;
	

	public void addTrain(Train train) {
		trains.add(train);	
	}

	public Timetable(Timetable source) {
		super(new TimetableId());
		this.validTo = source.validTo;
		this.validFrom = source.validFrom;
		this.createdBy = source.createdBy;
		
	}

	public Timetable(ApplicationContext applicationContext, TimetableId entityId) {
		super(applicationContext,entityId);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected AbstractAggregateRoot<TimetableId>.AggregateRootBehavior<?> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TrainCycleType getCycleTypeByKey(String key) {}
	
	public boolean hasNumber(Long number) {
		TrainNumber trainNumber = new TrainNumber(number);
		for(Train train:trains) {
			if(train.getId().getTrainNumber() == trainNumber) {
				return true;
			}
		}
		return false;
	}
}
