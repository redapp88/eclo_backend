package com.redapp.eclo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class AppRole {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
Long id;
	@NonNull
String roleName;
	
	public AppRole(String roleName) {
		super();
		this.roleName = roleName;
	}
	
}
