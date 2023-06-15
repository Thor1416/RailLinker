package com.railweb.trafficmgt.domain.train;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.core.annotation.Order;

import com.railweb.admin.domain.id.OperatingCompagnyId;
import com.railweb.shared.domain.util.Visitable;
import com.railweb.shared.infra.persistence.AbstractEntity;
import com.railweb.trafficmgt.domain.TimetableVisitor;
import com.railweb.trafficmgt.domain.TrainCycleType;
import com.railweb.trafficmgt.domain.ids.EngineClassId;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.ids.TrainCycleId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TrainsCycle extends AbstractEntity<TrainCycleId> 
						implements Iterable<TrainsCycleItem>, Visitable<TimetableVisitor>{
	
	@EmbeddedId
	private TrainCycleId id;
	
	@ManyToOne
	private EngineClassId engineClassId;
	private String name;
	private String description;
	@ManyToOne
	private OperatingCompagnyId compagny;
	@ManyToOne
	private TimetableId timeTableId;
	@ManyToOne
	private TrainCycleType type;
	@OneToOne
	@JoinColumn(name="next_cycle_id", referencedColumnName="id")
	private TrainsCycle next;
	@OneToOne
	@JoinColumn(name="prev_cycle_id", referencedColumnName="id")
	private TrainsCycle previous;
	@OneToMany
	@Order
	private List<TrainsCycleItem> items;
	private RunDays rundays;
	
	public TrainsCycle(TimetableId timeTable) {}
	
	@Override
	public Iterator<TrainsCycleItem> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public TrainsCycleItem getNextItem(TrainsCycleItem trainsCycleItem) {
		// TODO Auto-generated method stub
		return null;
	}

	public TrainsCycleItem getNextItemCyclic(TrainsCycleItem trainsCycleItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accept(TimetableVisitor visitor) {
		visitor.visit(this);
		
	}

	public TrainsCycleItem getPreviousItem(TrainsCycleItem trainsCycleItem) {
		// TODO Auto-generated method stub
		return null;
	}

	public TrainsCycleItem getPreviousItemCyclic(TrainsCycleItem trainsCycleItem) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
