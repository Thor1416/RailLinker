package com.railweb.usermgt.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.usermgt.model.ids.RoleId;

import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class Role extends AbstractEntity<RoleId> {

	private RoleId id; 
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;
	
	@ManyToMany
	@JoinTable(
			name = "roles_privileges",
			joinColumns = @JoinColumn(
				name="role_id", referencedColumnName = "role_id"),
			inverseJoinColumns = @JoinColumn(
				name = "privilege_id", referencedColumnName = "privilege_id")
			)
	private Set<Privilege> privileges = new HashSet<>();

	@ManyToOne(cascade=CascadeType.ALL, optional = true)
	@JoinColumn(name="parent_id")
	private Role parent;

	@OneToMany(mappedBy="parent")
	private Set<Role> childRoles;

	public Role(String name, Set<Privilege> privileges) {
		super();
		this.name = name;
		this.privileges = privileges;
	}
		
}
