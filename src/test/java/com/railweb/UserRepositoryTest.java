package com.railweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;

import com.railweb.usermgt.model.User;
import com.railweb.usermgt.model.ids.UserId;
import com.railweb.usermgt.repo.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void testCreateUser() {
		User user = new User(context,new UserId(UUID.randomUUID()));
		user.setUsername("tsimon17");
		user.setPassword("qqe57nd");
		user.setFirstname("Thorbjørn");
		user.setLastname("Simonsen");
		
		User savedUser = repo.save(user);
		
		User existUser = repo.findUserByUsername("tsimon");
		
		assertThat(savedUser).isEqualTo(existUser);
	}
}
