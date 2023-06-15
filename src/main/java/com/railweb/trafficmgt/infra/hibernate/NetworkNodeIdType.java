package com.railweb.trafficmgt.infra.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import com.railweb.trafficmgt.domain.ids.NetworkId;
import com.railweb.trafficmgt.domain.ids.NetworkNodeId;
import com.railweb.trafficmgt.domain.ids.NodeId;
import com.railweb.trafficmgt.domain.network.NodeAbbr;
import com.railweb.trafficmgt.domain.network.NodePrefix;
import com.railweb.trafficmgt.domain.values.NetworkNodeIdTuple;

public class NetworkNodeIdType implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR,Types.VARCHAR, Types.VARCHAR};
	}

	@Override
	public Class<?> returnedClass() {
		return NetworkNodeId.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if(x ==y) return true;
		
		if(Objects.isNull(x) || Objects.isNull(y)) 
			return false;
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		
		if(rs.wasNull()) return null;
		
		String network = rs.getString(names[0]);
		String nodePrefix = rs.getString(names[1]);
		String nodeAbbr = rs.getString(names[2]);
		
		NetworkId netId = new NetworkId(network);
		NodeId nodeId = new NodeId(new NodeAbbr(nodeAbbr), new NodePrefix(nodePrefix));
		
		return new NetworkNodeId(new NetworkNodeIdTuple(netId, nodeId));
 	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		
		if(Objects.isNull(value)) {
			st.setNull(index, Types.VARCHAR);
			st.setNull(index+1, Types.VARCHAR);
			st.setNull(index+2, Types.VARCHAR);
		}else{
			NetworkNodeId id = (NetworkNodeId) value;
			NetworkNodeIdTuple tuple =id.getId();
			st.setString(index, tuple.getNetworkId().getId().toString());
			st.setString(index+1, tuple.getPrefix().getValue());
			st.setString(index+2, tuple.getAbbr().getValue());
		}

	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		
		if(Objects.isNull(value)) return null;
		
		NetworkNodeId id =  (NetworkNodeId) value;
		
		NetworkNodeId newId = new NetworkNodeId(id.getId());
		
		return newId;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
