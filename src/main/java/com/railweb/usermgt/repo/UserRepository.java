package com.railweb.usermgt.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.railweb.shared.infra.persistence.BaseRepository;
import com.railweb.usermgt.model.User;
import com.railweb.usermgt.model.ids.UserId;


public interface UserRepository extends BaseRepository<User,UserId> {
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User findUserByUsername(@Param("username") String username);

	public User findByResetPasswordToken(String token);

}
