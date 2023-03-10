package com.railweb.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.railweb.usermgt.model.Privilege;
import com.railweb.usermgt.model.Role;
import com.railweb.usermgt.model.User;
import com.railweb.usermgt.model.ids.UserId;
import com.railweb.usermgt.repo.PrivilegeRepository;
import com.railweb.usermgt.repo.RoleRepository;
import com.railweb.usermgt.repo.UserRepository;

@Component
public class SetupDataLoader implements 
									ApplicationListener<ContextRefreshedEvent>{
	
	boolean alreadySetup =true;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ApplicationContext context;
	
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event){
		
		if(alreadySetup) 
			return;
		Privilege readPrivelige = createPrivilegeIfNotFound("READ_PRIVILEGE");
		Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
		Privilege createPrivilege = createPrivilegeIfNotFound("CREATE_PRIVILEGE");
		Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
		
		Set<Privilege> adminPrivileges = new HashSet<>();
		adminPrivileges.add(readPrivelige);
		adminPrivileges.add(writePrivilege);
		adminPrivileges.add(createPrivilege);
		adminPrivileges.add(deletePrivilege);
		
		createRoleIfNotFound("ROLE_ADMIN",adminPrivileges);
		createRoleIfNotFound("ROLE_USER",new HashSet<>(Arrays.asList(readPrivelige)));
		
		Role adminRole = roleRepository.findByName("ROLE_ADMIN");
		User user = new User(context, new UserId());
		user.setUsername("Test");
		user.setFirstname("Test");
		user.setLastname("test");
		user.setPassword(passwordEncoder.encode("test"));
		user.setRoles(new HashSet<>(Arrays.asList(adminRole)));
		user.setEnabled(true);
		userRepository.save(user);
		
		alreadySetup=true;
		}

	private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
		
		Role role = roleRepository.findByName(name);
		if(role == null) {
			role = new Role(name,(Set<Privilege>) privileges);
			roleRepository.save(role);
		}
		return role;
	}

	
	private Privilege createPrivilegeIfNotFound(String name) {
		Privilege privilege = privilegeRepository.findByName(name);
		if(privilege == null) {
			privilege = new Privilege(name);
			privilegeRepository.save(privilege);
		}
		return privilege;
	}
}
