package com.railweb.trafficmgt.infra.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import com.railweb.trafficmgt.domain.TrainNumber;
import com.railweb.trafficmgt.domain.ids.TimetableId;
import com.railweb.trafficmgt.domain.ids.TrainId;

public class TrainIdType implements CompositeUserType{

	@Override
	public String[] getPropertyNames() {
		return new String[] {"timetableId","trainNumber"};
	}

	@Override
	public Type[] getPropertyTypes() {
		return new Type[] {StringType.INSTANCE,LongType.INSTANCE};
	}

	@Override
	public Object getPropertyValue(Object component, int property) throws HibernateException {
		
		TrainId trainId = (TrainId) component;
		switch(property) {
		case 0:
			return trainId.getTimetableId();
		case 1:
			return trainId.getTrainNumber().getTrainNumber();
		}
		throw new IllegalArgumentException(property + " is an invalid property index for class type "
			      + component.getClass().getName());
	}

	@Override
	public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class returnedClass() {
		return TrainId.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		boolean  isEqual = false;
		if(x==y) {
			isEqual = true;
		}if(null==x|| null==y) {
			isEqual = false;
		}else {
			isEqual = x.equals(y);
		}
		return isEqual;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		
		if(rs.wasNull()) return null;
		
		String timetableNum = rs.getString(names[1]);
		TimetableId timeId = new TimetableId(UUID.fromString(timetableNum));
		TrainNumber number = new TrainNumber(rs.getLong(names[2]));
		
		return new TrainId(number,timeId);
		
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
	if(Objects.isNull(value)) {
		st.setNull(index, Types.VARCHAR);
		st.setNull(index+1, Types.BIGINT);
	}else {
		TrainId trainId =(TrainId) value; 
		st.setString(index, trainId.getTimetableId().toString());
		st.setLong(index+1, trainId.getTrainNumber().getTrainNumber());
		}
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable disassemble(Object value, SharedSessionContractImplementor session) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
		
}

