package com.railweb.usermgt.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.context.ApplicationContext;

import com.railweb.shared.domain.base.AbstractAggregateRoot;
import com.railweb.usermgt.model.ids.UserId;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User extends AbstractAggregateRoot<UserId> {
	
	@Column(nullable=false, unique=true, length=45)
	private String username;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false, length=64)
	private String password;
	
	@Column(name="firstname", nullable=false, length=20)
	private String firstname;
	
	@Column(name="lastname", nullable=false, length=20)
	private String lastname;

	
	@Column(name="reset_password_token")
	private String resetPasswordToken; 
	
	@Column
	private String localeOption;
	
	private boolean enabled;
	@Column(nullable=true, length=64)
	private String photo;
	
	@ManyToMany(cascade =CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name ="users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();

	
	public void addRole(Role role) {
		roles.add(role);
	}
	public void removeRole(Role role) {
		roles.remove(role);
	}
	@Override
	protected AbstractAggregateRoot<UserId>.AggregateRootBehavior<?> initialBehavior() {
		// TODO Auto-generated method stub
		return null;
	}

	public User(ApplicationContext applicationContext, UserId entityId) {
		super(applicationContext, entityId);
	}
}
	