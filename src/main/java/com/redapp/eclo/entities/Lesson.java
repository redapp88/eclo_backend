package com.redapp.eclo.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lesson<appUser> {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
@NotNull
private String type;
@NotNull
private String title;
@NotNull
private Date date;
@NotNull
private String time;
@NotNull
private String area;
@ManyToOne
@NotNull
private Program program;
@ManyToOne
@NotNull
private AppUser appUser;
public Lesson(@NotNull String type, @NotNull String title, @NotNull Date date, @NotNull String time,@NotNull String area,
		@NotNull Program program, @NotNull AppUser appUser) {
	super();
	this.type = type;
	this.title = title;
	this.date = date;
	this.time = time;
	this.area=area;
	this.program = program;
	this.appUser = appUser;
}


}
