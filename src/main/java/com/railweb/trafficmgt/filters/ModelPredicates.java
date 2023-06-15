package com.railweb.trafficmgt.filters;

import java.util.function.Predicate;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.trafficmgt.domain.train.TimeInterval;

public class ModelPredicates {

	   public static boolean nodeInterval(TimeInterval interval) {
	        return interval.isNodeOwner();
	    }
	   public static <T extends IdentifiableDomainObject<U>, U extends DomainObjectId<?>> Predicate<T> matchId(final U id){
		   return item -> item.id().equals(id);
	   }
}
