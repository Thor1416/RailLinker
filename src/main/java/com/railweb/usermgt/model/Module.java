package com.railweb.usermgt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="modules")
public class Module {

	@Id
	@GeneratedValue
	private Long moduleId;
	
	private String moduleName;
}
