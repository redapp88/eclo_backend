package com.redapp.eclo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.redapp.eclo.dao.AppRoleRepository;
import com.redapp.eclo.dao.AppUserRepository;
import com.redapp.eclo.entities.AppRole;
import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.UserRequest;
@Service
@Transactional
public class UsersServiceImp implements UsersService {
@Autowired
AppUserRepository appUserRepository;
@Autowired
AppRoleRepository appRoleRepository;
@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	public List<AppUser> getUsers(String keyword,String status,String categorie) {
		keyword="%"+keyword+"%";
		if(categorie.equals("*"))
			categorie="%%";
		if(status.equals("*"))
			status="%%";
	return this.appUserRepository.findUsersByKeyword(keyword,status,"USER",categorie);
	}
	
	@Override
	public List<AppUser> getManagers() {
		
	return this.appUserRepository.findUsersByKeyword("%%","ACTIVE","MANAGER","%%");
	}
	
	@Override
	public void deleteManager(String username) {
		AppUser manager=this.appUserRepository.getOne(username);
		if(manager==null)
			throw new RuntimeException("هذا المستخدم غير موجود");
		if(!manager.getAppRole().getRoleName().equals("MANAGER"))
			throw new RuntimeException("هذا المستخدم ليس مدير");
		appUserRepository.delete(manager);
	}
	@Override
	public void deleteUser(String username) {
		AppUser user=this.appUserRepository.getOne(username);
		if(user==null)
			throw new RuntimeException("هذا المستخدم غير موجود");
		if(!user.getAppRole().getRoleName().equals("USER"))
			throw new RuntimeException("ليس مستخدم عادي");
		user.setStatus("deleted");
		
	}

	@Override
	public AppUser addUser(UserRequest userRequest) {
		System.out.println("add : "+userRequest.getUsername());
		if(this.appUserRepository.existsById(userRequest.getUsername()))
				throw new RuntimeException("اسم المستخدم مستعمل المرجو اختيار اسم اخر");
		AppUser user=new AppUser(userRequest.getUsername(),this.bCryptPasswordEncoder.encode(userRequest.getPassword()),
				userRequest.getName(),userRequest.getSex(),userRequest.getPhone(),userRequest.getCategorie(),userRequest.getArea());
		AppRole role=this.appRoleRepository.findByRoleName("USER");
		user.setAppRole(role);
return this.appUserRepository.save(user);
	}
	
	@Override
	public AppUser addManager(UserRequest userRequest) {
		if(this.appUserRepository.existsById(userRequest.getUsername()))
			throw new RuntimeException("اسم المستخدم مستعمل المرجو اختيار اسم اخر");;
		AppUser manager=new AppUser(userRequest.getUsername(),this.bCryptPasswordEncoder.encode(userRequest.getPassword()),
				userRequest.getName(),userRequest.getSex(),userRequest.getPhone(),userRequest.getCategorie(),userRequest.getArea());

		AppRole role=this.appRoleRepository.findByRoleName("MANAGER");
		manager.setAppRole(role);
return this.appUserRepository.save(manager);
	}

	@Override
	public AppUser editUser(String username,UserRequest userRequest) {
		System.out.println("edit : "+username);
		System.out.println(userRequest);
		Optional<AppUser> oldAppUserOpt=this.appUserRepository.findById(username);
		if(!oldAppUserOpt.isPresent())
			throw new RuntimeException("Utilisateur Introuvable");
		AppUser oldAppUser = oldAppUserOpt.get();
		oldAppUser.setArea(userRequest.getArea());
		oldAppUser.setCategorie(userRequest.getCategorie());
		oldAppUser.setName(userRequest.getName());
		oldAppUser.setPhone(userRequest.getPhone());
		oldAppUser.setSex(userRequest.getSex());
		oldAppUser.setStatus(userRequest.getStatus());
		return oldAppUser;
	}

	@Override
	public AppUser editPassword(String username, UserRequest userRequest) {
		Optional<AppUser> oldAppUserOpt=this.appUserRepository.findById(username);
		if(!oldAppUserOpt.isPresent())
			throw new RuntimeException("هذا المستخدم غير موجود");
		AppUser oldAppUser = oldAppUserOpt.get();
		oldAppUser.setPassword(this.bCryptPasswordEncoder.encode(userRequest.getPassword()));
		return oldAppUser;
	}

	@Override
	public AppUser findByUsername(String username) {
		Optional<AppUser> appUserOpt = this.appUserRepository.findById(username);
		if(!appUserOpt.isPresent())
			return null;
		return appUserOpt.get();
	}



}
