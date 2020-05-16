package com.redapp.eclo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Program {
	@Id
	private String programId;
	@NotNull
	private Long month;
	@NotNull
	private Long year;
	@NotNull
	private String hijri;
	@NotNull
	private String status;
	@OneToMany(mappedBy = "program")
	@JsonIgnore
	private List<Lesson> lessons = new ArrayList<>();


	@Override
	public String toString() {
		return this.getMonth() + "/" + this.getYear() + "///" + this.getHijri() + ":"
				+ this.getStatus();
	}


	public Program(Long month, Long year, @NotNull String hijri) {
		super();
		this.programId=month.toString()+year.toString();
		this.month = month;
		this.year = year;
		this.hijri = hijri;
		this.status="disabled";
	}
	
	

}
