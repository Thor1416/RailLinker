package com.railweb.trafficmgt.domain.network;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.railweb.trafficmgt.infra.repo.NetworkRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class NetworkReopsitoryTest {

	@Autowired
	private NetworkRepository repo;
	@Autowired
	private ApplicationContext context;
	
	@Test
	public void testCreateNewNetwork(){
		Network net = new Network(context);
		net.setName("Oktober");
		net.setPrefix(new NodePrefix("Test_"));
		repo.save(net);
		Iterable<Network> networks = repo.findAll();
		Assertions.assertThat(networks).extracting(n-> n.getName()).containsOnly("Oktober");
	}
}
