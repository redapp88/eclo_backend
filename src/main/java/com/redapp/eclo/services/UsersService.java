package com.redapp.eclo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.UserRequest;


public interface UsersService {
	public List<AppUser> getUsers(String keyword,String staturs,String categorie);
	public List<AppUser> getManagers();
	public AppUser addUser(UserRequest userRequest);
	public AppUser editUser(String username,UserRequest userRequest);
	public AppUser addManager(UserRequest userRequest);
	public AppUser findByUsername(String username);
	public void deleteManager(String username);
	public void deleteUser(String username);
	public AppUser editPassword(String username, UserRequest userRequest);
	
	
	
}
