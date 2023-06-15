package com.railweb.shared.domain.base;

import java.util.Objects;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.NonNull;

import com.railweb.shared.infra.persistence.AbstractEntity;
import com.sun.istack.Nullable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractDomainEntity<ID extends DomainObjectId<?>>
										implements IdentifiableDomainObject<ID>, Persistable<ID> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -600189302278907048L;

	public Long version;

	protected ID id;
	
	protected AbstractDomainEntity() {}
	
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	@Transient
	public @Nullable ID getId() {
		return this.id;
	}
	
	 /**
     * Copy constructor
     *
     * @param source the entity to copy from.
     */
    protected AbstractDomainEntity(@NonNull AbstractDomainEntity<ID> source) {
    	Objects.requireNonNull(source, "source must not be null");
    	this.id = source.id;
    }
    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractDomainEntity(@NonNull ID id) {
    	this.id = id;
    }

    @NonNull
    public ID id() {
    	return id;
    }
    
    public boolean sameIdentityAs(AbstractDomainEntity<ID> other) {
    	return this.id == other.getId();
    }
    
    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
    
    public Long getVersion() {
    	return version;
    }
    
    public abstract AbstractDomainEntity<ID> fromJpaEntity(AbstractEntity<ID> jpaEntity);
    
    public abstract AbstractEntity<ID> toJpaEntity(AbstractDomainEntity<ID> domainEntity);
}
