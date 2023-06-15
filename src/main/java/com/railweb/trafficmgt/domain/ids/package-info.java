@TypeDefs({
	@TypeDef(defaultForType=EngineClassId.class, typeClass=EngineClassIdType.class),
	@TypeDef(defaultForType=EngineGroupId.class, typeClass=EngineGroupIdType.class),
	@TypeDef(defaultForType=LineId.class, typeClass=LineIdType.class),
	@TypeDef(defaultForType=LinePathId.class, typeClass=LinePathIdType.class),
	@TypeDef(defaultForType=NetworkId.class, typeClass=NetworkIdType.class),
	@TypeDef(defaultForType=NetworkNodeId.class, typeClass=NetworkNodeIdType.class),
	@TypeDef(defaultForType=NodeId.class, typeClass=NodeIdType.class),
	@TypeDef(defaultForType=PenaltyTableRowId.class, typeClass=PenaltyTableRowIdType.class),
	@TypeDef(defaultForType=PlatformId.class, typeClass=PlatformIdType.class),
	@TypeDef(defaultForType=PowerSystemId.class, typeClass=PowerSystemIdType.class),
	@TypeDef(defaultForType=RundayId.class, typeClass=RundayIdType.class),
	@TypeDef(defaultForType=SwitchId.class, typeClass=SwitchIdType.class),

	@TypeDef(defaultForType=TimeIntervalListId.class, typeClass=TimeIntervalListIdType.class),
	@TypeDef(defaultForType=TimetableId.class, typeClass=TimetableIdType.class),
	@TypeDef(defaultForType=TrackConnectorId.class, typeClass=TrackConnectorIdType.class),
	@TypeDef(defaultForType=TrackId.class, typeClass=TrackIdType.class),
	@TypeDef(defaultForType=TrainClassId.class, typeClass=TrainClassIdType.class),
	@TypeDef(defaultForType=TrainCycleId.class, typeClass=TrainCycleIdType.class),
	@TypeDef(defaultForType=TrainCycleTypeId.class, typeClass=TrainCycleTypeIdType.class),
	@TypeDef(defaultForType=TrainGroupId.class, typeClass=TrainGroupIdType.class),
	@TypeDef(defaultForType=TrainId.class, typeClass=TrainIdType.class),
	@TypeDef(defaultForType=TrainRouteId.class, typeClass=TrainRouteIdType.class),
	})
/**
 * @author Thorbjoern Simonsen
 * 
 */
package com.railweb.trafficmgt.domain.ids;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.railweb.trafficmgt.infra.hibernate.EngineClassIdType;
import com.railweb.trafficmgt.infra.hibernate.EngineGroupIdType;
import com.railweb.trafficmgt.infra.hibernate.ImageIdType;
import com.railweb.trafficmgt.infra.hibernate.LineIdType;
import com.railweb.trafficmgt.infra.hibernate.LinePathIdType;
import com.railweb.trafficmgt.infra.hibernate.NetworkIdType;
import com.railweb.trafficmgt.infra.hibernate.NetworkNodeIdType;
import com.railweb.trafficmgt.infra.hibernate.NodeIdType;
import com.railweb.trafficmgt.infra.hibernate.PenaltyTableRowIdType;
import com.railweb.trafficmgt.infra.hibernate.PlatformIdType;
import com.railweb.trafficmgt.infra.hibernate.PowerSystemIdType;
import com.railweb.trafficmgt.infra.hibernate.RundayIdType;
import com.railweb.trafficmgt.infra.hibernate.SwitchIdType;
import com.railweb.trafficmgt.infra.hibernate.TimeIntervalIdType;
import com.railweb.trafficmgt.infra.hibernate.TimeIntervalListIdType;
import com.railweb.trafficmgt.infra.hibernate.TimetableIdType;
import com.railweb.trafficmgt.infra.hibernate.TrackConnectorIdType;
import com.railweb.trafficmgt.infra.hibernate.TrackIdType;
import com.railweb.trafficmgt.infra.hibernate.TrainClassIdType;
import com.railweb.trafficmgt.infra.hibernate.TrainCycleIdType;
import com.railweb.trafficmgt.infra.hibernate.TrainCycleTypeIdType;
import com.railweb.trafficmgt.infra.hibernate.TrainGroupIdType;
import com.railweb.trafficmgt.infra.hibernate.TrainIdType;
import com.railweb.trafficmgt.infra.hibernate.TrainRouteIdType;
