package com.redapp.eclo.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private String username;
	private String password;
    private String name;
    private String sex;
	private String phone;
	private String categorie;
	private String area;
	private String status;
}
