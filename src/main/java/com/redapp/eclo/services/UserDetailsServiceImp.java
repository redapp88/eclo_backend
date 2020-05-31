package com.redapp.eclo.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.security.ExtendedUser;



@Service
@Transactional

public class UserDetailsServiceImp implements UserDetailsService {
@Autowired
private UsersService usersService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser= this.usersService.findByUsername(username);
		if (appUser== null) throw new UsernameNotFoundException(username);
		if(appUser.getStatus().equals("deleted"))  throw new RuntimeException("Compte supprimé");
		if(appUser.getStatus().equals("disabled")) throw new RuntimeException("compte desactivé");
		Collection<GrantedAuthority> authorities=new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority(appUser.getAppRole().getRoleName()));
	    ExtendedUser user=new ExtendedUser(appUser.getUsername(),
	    		appUser.getPassword(),
	    		appUser.getName(),
	    		appUser.getCategorie(),
	    		appUser.getArea(),
	    		appUser.getSex(),
	    		appUser.getPhone(),
	    		appUser.getStatus(),
	    		authorities);
		return user;
	}



}
