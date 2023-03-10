package com.railweb.trafficmgt.domain.train;

import static tech.units.indriya.unit.Units.KILOMETRE_PER_HOUR;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.measure.Quantity;
import javax.measure.quantity.Speed;
import javax.measure.quantity.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.railweb.trafficmgt.domain.network.Line;
import com.railweb.trafficmgt.domain.network.LineGradient;
import com.railweb.trafficmgt.domain.network.Node;

import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

public class TimeIntervalCalculation {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeIntervalCalculation.class);
	private static final Quantity<Speed> fallbackSpeed = 
			Quantities.getQuantity(10, KILOMETRE_PER_HOUR);
	
	private final List<TimeInterval> list;
	private final TimeInterval interval;
	private final PenaltySolver penaltySolver;
	private final LineGradient gradient;
	
	public TimeIntervalCalculation(Train train, TimeInterval timeInterval) {
		this.list = train.getTimeIntervalList();
		this.interval = timeInterval;
		this.gradient = timeInterval.getTrack().getGradient();
		this.penaltySolver = new PenaltySolver() {
			@Override
			public Quantity<Time> getAccPenalty(Quantity<Speed> speed){
				return PenaltyTable.getAccPenalty(train,gradient,speed);
			}
			@Override
			public Quantity<Time> getDecPenalty(Quantity<Speed> speed){
				return PenaltyTable.getDecPenalty(train,gradient,speed);
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	public Quantity<Speed> computeLineSpeed(){
		 if (!interval.isLineOwner()) {
	            throw new IllegalStateException("Not allowed for node interval.");
	     }
		 
		 Line line = interval.getOwnerAsLine();
		 return this.computeLineSpeed(interval, min(interval.getSpeedLimit(), line.getSpeed()));
	}

	@SuppressWarnings("unchecked")
	public Quantity<Speed> computeNodeSpeed(TimeInterval lineInterval, boolean from, Quantity<Speed> defaultNotStraightSpeed){
		boolean straight = from ? interval.isFromStraight() : interval.isToStraight();
		Node node = interval.getOwnerAsNode();
		Line line = lineInterval.getOwnerAsLine();
		Quantity<Speed> speed = straight ? node.getSpeed() : node.getNotStraightSpeed();
		if(speed != null || speed.isEquivalentTo(Quantities.getQuantity(0L,KILOMETRE_PER_HOUR))){
			speed = straight ? line.getSpeed() : defaultNotStraightSpeed;
		}
		speed = min(lineInterval.getSpeedLimit(), speed);
		speed = this.computeLineSpeed(lineInterval, speed);
		return speed;
	}
	
	@SuppressWarnings("unchecked")
	private Quantity<Speed> computeLineSpeed(TimeInterval lineInterval, Quantity<Speed> speedLimit) {
		if(speedLimit != null && speedLimit.getValue().doubleValue() <1) {
			throw new IllegalArgumentException("Speed has to be greater than 0.");
		}
		
		Train train = lineInterval.getTrain();
		
		//apply speed limit
		Quantity<Speed> speed = min(train.getTopSpeed(), speedLimit);
		
		// adjust for trackactiveForce class

		
		return null;
	}

	public Quantity<Speed> min(Quantity<Speed>... s){
		Optional<Quantity<Speed>> min = Arrays.stream(s).filter(t->Objects.nonNull(t)).min(new Comparator<Quantity<Speed>>() {
			
			public int compare(Quantity<Speed> speed1, Quantity<Speed> speed2) {
				int speed1C = speed1.to(KILOMETRE_PER_HOUR).getValue().intValue();
				int speed2C = speed2.to(KILOMETRE_PER_HOUR).getValue().intValue();
				return speed2C - speed1C;
			}
		});
		return min.orElse(null);
	}
	public interface PenaltySolver{
		Quantity<Time> getAccPenalty(Quantity<Speed> speed);
		Quantity<Time> getDecPenalty(Quantity<Speed> speed);
	}

	/**
     * computes running time.
     *
     * @param usedSpeed speed on line
     * @return pair running time and speed
     */
	public Quantity<Time> computeRunningTime(Quantity<Speed> speed){
		
		ComparableQuantity<Speed> dnss = Quantities.getQuantity(40, Units.KILOMETRE_PER_HOUR);
		
		Train train = interval.getTrain();
		
		TimeInterval ini = interval.getPreviousTrainInterval();
		TimeInterval ino = interval.getNextTrainInterval();
		TimeInterval ili = ini.getPreviousTrainInterval() != null ? ini.getPreviousTrainInterval(): interval;
		TimeInterval ilo = ino.getPreviousTrainInterval() != null ?  ino.getNextTrainInterval() : interval;
		
		Quantity<Speed> s0 = ini.getCalculation().computeNodeSpeed(ili, false, dnss);
		Quantity<Speed> s4 = ino.getCalculation().computeNodeSpeed(ilo, false, dnss);
		Quantity<Speed> s2 = interval.getCalculation().computeLineSpeed();
		Quantity<Speed> s1 = ini.getCalculation().computeNodeSpeed(interval, false, speed);
		
		//TODO Implement rest of method
		return null;
	}

}
