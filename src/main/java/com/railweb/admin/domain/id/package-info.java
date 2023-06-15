@TypeDefs({
	@TypeDef(defaultForType=RegionId.class, typeClass=RegionIdType.class),
	@TypeDef(defaultForType=InfrastructureManagerId.class, typeClass=InfrastructureManagerIdType.class),
	@TypeDef(defaultForType=OperatingCompagnyId.class, typeClass=OperatingCompagnyIdType.class),
	})
/**
 * @author Thorbjoern Simonsen
 * 
 */
package com.railweb.admin.domain.id;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.railweb.admin.infra.InfrastructureManagerIdType;
import com.railweb.admin.infra.OperatingCompagnyIdType;
import com.railweb.admin.infra.RegionIdType;
