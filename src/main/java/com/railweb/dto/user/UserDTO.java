package com.railweb.dto.user;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain=true)
@JsonInclude(value=JsonInclude.Include.USE_DEFAULTS)
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserDTO {
	
	@NotNull 
	private Long id;
	@NotNull
	private String username;
	private String password;
	@NotNull
	private String firstname;
	private String lastname;
	@NotNull
	private Collection<Long> roles;
	@NotNull
	private String email;
	private String photo;

	public String getFullname() {
		return firstname != null ? firstname.concat(" ").concat(lastname):  "";
	}
	
}
