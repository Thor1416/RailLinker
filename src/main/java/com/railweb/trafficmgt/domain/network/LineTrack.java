package com.railweb.trafficmgt.domain.network;

import javax.persistence.Entity;

import org.hibernate.envers.Audited;

import com.railweb.shared.domain.util.Visitable;
import com.railweb.shared.domain.util.Visitor;

import lombok.extern.slf4j.Slf4j;

@Audited
@Slf4j
@Entity(name="LineTrack")
public class LineTrack extends Track implements Visitable {

	protected LineTrack(Line owner, String number) {
		super(owner,number);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(Visitor Visitor) {
		// TODO Auto-generated method stub
		
	}


}
