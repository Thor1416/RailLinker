package com.railweb.admin.domain;

import com.railweb.admin.domain.id.OperatingCompagnyId;
import com.railweb.shared.infra.persistence.AbstractEntity;

public class OperatingCompagny extends AbstractEntity<OperatingCompagnyId> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5046443828351852500L;


	@Override
	public OperatingCompagnyId getId() {
		return this.id();
	}



}
