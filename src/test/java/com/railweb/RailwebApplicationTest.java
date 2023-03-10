package com.railweb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.railweb.usermgt.controller.UserController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RailwebApplicationTest {

	@Autowired
	UserController userController;

	  @Test
	  public void contextLoads() {
	    Assertions.assertThat(userController).isNot(null);
	  }
}
