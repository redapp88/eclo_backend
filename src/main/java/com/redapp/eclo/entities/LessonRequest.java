package com.redapp.eclo.entities;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonRequest {
	private String type;
	private String title;
	private String date;
	private String time;
	private String programId;
	private String username;
}
