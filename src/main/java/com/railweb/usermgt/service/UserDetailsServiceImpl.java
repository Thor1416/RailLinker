package com.railweb.usermgt.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.railweb.usermgt.model.Privilege;
import com.railweb.usermgt.model.Role;
import com.railweb.usermgt.model.User;
import com.railweb.usermgt.repo.RoleRepository;
import com.railweb.usermgt.repo.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("userDetailService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository; 
	
	
	@Override
	public UserDetails loadUserByUsername(String username){
		
		log.info("Finding user with username:{}",username);
		User user = userRepository.findUserByUsername(username);
		
		if(user==null) {
			List<Role> roles = new ArrayList<>(List.of(roleRepository.findByName("USER")));
			return new org.springframework.security.core.userdetails.User(
					" "," ",true,true,true,true,getAuthorities(roles));
		}
		return new RailUserDetails(user);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(
			List<Role> Roles){
		return getGrantedAuthorities(getPrivileges(Roles));
	}
	
	private List<String> getPrivileges(Collection<Role> roles){
		
		log.debug("Finding privileges for roles:{}", roles.stream()
				.map(r->r.getName()).collect(Collectors.toSet()));
		
		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for(Role role: roles) {
			 privileges.add(role.getName());
			 collection.addAll(role.getPrivileges());
			 privileges.addAll(getPrivileges(role.getChildRoles()));
		}
		for(Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges){
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(String privilege: privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
