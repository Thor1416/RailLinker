package com.railweb.shared.domain.base;

import java.beans.Transient;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@MappedSuperclass
public abstract class DomainObjectId<R> implements ValueObject<R> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1601701555113470224L;

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	@Column(name="id", nullable = false, updatable=false)
	protected R id;
	@PrePersist
	protected void ensureIdAssigned() {
		if(id == null) {
			assignId();
		}
	}
	
	protected abstract void assignId() ;
	
	@JsonCreator
	protected DomainObjectId(@NonNull R id) {
		this.id = Objects.requireNonNull(id, "id must not be null");
	}


	public DomainObjectId(Class<?> class1) {
		// TODO Auto-generated constructor stub
	}

	public DomainObjectId(Class<?> class1, String value) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract boolean sameValueAs(R other);
	
	public abstract UUID toUUID();
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		@SuppressWarnings("unchecked")
		R that = (R) o;
		return sameValueAs(that);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return String.format("%s[%s]", getClass().getSimpleName(), id);
	}
}
