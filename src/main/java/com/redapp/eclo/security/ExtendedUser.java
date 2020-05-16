package com.redapp.eclo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ExtendedUser extends User {
public ExtendedUser(String username, String password,String name, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.name=name;
	}
private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
