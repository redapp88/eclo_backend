package com.redapp.eclo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ExtendedUser extends User {
	private String name;
	private String categorie;
	private String area;
	private String sex;
	private String phone;
	private String status;
public ExtendedUser(String username, String password,String name,String categorie,String area,String sex,String phone,String status, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.name=name;
		this.categorie=categorie;
		this.area=area;
		this.sex=sex;
		this.phone=phone;
		this.status=status;
	}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getCategorie() {
	return categorie;
}
public void setCategorie(String categorie) {
	this.categorie = categorie;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}


	

}
