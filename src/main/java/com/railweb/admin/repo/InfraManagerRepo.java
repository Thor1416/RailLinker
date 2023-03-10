package com.railweb.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railweb.admin.domain.InfrastructureManager;
import com.railweb.admin.domain.id.InfrastructureManagerId;

public interface InfraManagerRepo extends JpaRepository<InfrastructureManager, InfrastructureManagerId> {

}
