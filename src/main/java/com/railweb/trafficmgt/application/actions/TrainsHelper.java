package com.railweb.trafficmgt.application.actions;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Speed;

import com.railweb.trafficmgt.domain.network.LineGradient;
import com.railweb.trafficmgt.domain.train.TrainType;
import com.railweb.trafficmgt.domain.train.WeightTableRow;

import lombok.extern.slf4j.Slf4j;
import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

@Slf4j
/**
 * Helper actions for trains
 */
public class TrainsHelper {

	private TrainsHelper() {}
	
	public enum NextType{
		LAST_STATION, FIRST_STATION, BRANCH_STATION
	}
	
	 public static Quantity<Speed> getSpeedForWeight(List<TrainType> trainTypes, LineGradient gradient, Quantity<Mass> weight) {
		 if(trainTypes.isEmpty() || gradient==null) {
			 return null;
		 }
		 
		 TreeSet<Quantity<Speed>> speeds = trainTypes.stream().
				 flatMap(trainType -> trainType.getWeightTable().stream())
				 .map(WeightTableRow::getSpeed)
				 .collect(Collectors.toCollection(()->new TreeSet<Quantity<Speed>>()));
		 
		 Quantity<Speed> result = null;
//		 for(Quantity<Speed> speed: speeds) {
//			 ComparableQuantity<Mass> totalWeight = trainTypes.stream()
//					 .map(tt->tt.getWeightTableRowForSpeed(speed))
//					 .reduce(Quantities.getQuantity(0, Units.KILOGRAM), (w,rfs)->{
//						ComparableQuantity<Mass> ew = rfs != null ? rfs.getWeight(gradient):null;
//						return ew == null ? w : w.add(ew);
//					 },ComparableQuantity::add);
//			 
//			 if(totalWeight.isGreaterThanOrEqualTo(weight)) {
//				 break;
//			 }
//		 }
		 return result;
	 }
	 
}
