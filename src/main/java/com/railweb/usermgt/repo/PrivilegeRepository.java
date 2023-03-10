package com.railweb.usermgt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.railweb.usermgt.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long>{
	
	Privilege findByName(String name);

}
