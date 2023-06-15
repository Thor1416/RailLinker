package com.railweb.shared.infra.persistence;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.shared.domain.base.DomainObjectId;
import com.sun.istack.NotNull;

@NoRepositoryBean
public interface BaseRepository<Aggregate extends AbstractAggregateRoot<ID>, ID  extends DomainObjectId<?>>
				extends JpaRepository<Aggregate, ID>,
				JpaSpecificationExecutor<Aggregate>{
	
	default @NotNull Aggregate getById(@NotNull ID id) {
		return findById(id).orElseThrow(()->new EmptyResultDataAccessException(1));
	}
}
