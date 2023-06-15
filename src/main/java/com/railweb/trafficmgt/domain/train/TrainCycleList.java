package com.railweb.trafficmgt.domain.train;

import java.util.Collections;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import com.railweb.shared.domain.base.ValueObject;

public class TrainCycleList implements ValueObject<TrainCycleList> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="traincycles_order",
			joinColumns=@JoinColumn(name="list_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="item_id",referencedColumnName="id"))
	@MapKey(name="cycleOrder")
	private Map<Integer,TrainsCycleItem> items;
	@ManyToOne
	private TrainCycleMap parent;
	
	public Map<Integer,TrainsCycleItem> getItems(){
		return Collections.unmodifiableMap(items);
	}

	@Override
	public boolean sameValueAs(TrainCycleList other) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
