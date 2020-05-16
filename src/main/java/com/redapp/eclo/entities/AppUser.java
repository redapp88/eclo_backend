package com.redapp.eclo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor

public class AppUser implements Serializable {
@Id
	private String username;
	private String password;
@NotNull
private String name;
@NotNull
private String sex;
@NotNull
	private Date subsDate;
	private String phone;
	@NotNull
	private String status;
	@NotNull
	private String categorie;
	@NotNull
	private String area;
	@NotNull
	@ManyToOne
	@JsonIgnore
	private AppRole appRole;
	@OneToMany(mappedBy="appUser")
	@JsonIgnore
	private List<Lesson> lessons=new ArrayList<>();
	public AppUser(String username, @NotNull String password, @NotNull String name, @NotNull String sex, String phone, @NotNull String categorie,@NotNull String area) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.sex = sex;
		this.subsDate = new Date();
		this.phone = phone;
		this.categorie = categorie;
		this.area=area;
		this.status="active";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
    @JsonSetter
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getSubsDate() {
		return subsDate;
	}
	public void setSubsDate(Date subsDate) {
		this.subsDate = subsDate;
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
	public AppRole getAppRole() {
		return appRole;
	}
	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}
	public List<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	@Override
	public String toString() {
		return this.getName();
	}

	
	
}
