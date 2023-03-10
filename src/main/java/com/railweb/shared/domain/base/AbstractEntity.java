package com.railweb.shared.domain.base;

import java.util.Objects;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.NonNull;

import com.sun.istack.Nullable;

import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode
public abstract class AbstractEntity<ID extends DomainObjectId<?>>
										implements IdentifiableDomainObject<ID>, Persistable<ID> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -600189302278907048L;
	@Version
	public Long version;
	@Id
	protected ID id;
	
	protected AbstractEntity() {}
	
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
    protected AbstractEntity(@NonNull AbstractEntity<ID> source) {
    	Objects.requireNonNull(source, "source must not be null");
    	this.id = source.id;
    }
    /**
     * Constructor for creating new entities.
     *
     * @param id the ID to assign to the entity.
     */
    protected AbstractEntity(@NonNull ID id) {
    	this.id = id;
    }

    @NonNull
    public ID id() {
    	return id;
    }
    
    public boolean sameIdentityAs(AbstractEntity<ID> other) {
    	return this.id == other.getId();
    }
    
    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), id);
    }
    
    public Long getVersion() {
    	return version;
    }
}
