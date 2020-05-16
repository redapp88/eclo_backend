package com.redapp.eclo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.redapp.eclo.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
@Query("select u from AppUser u where (u.name like :keyword or u.username like :keyword or u.area like :keyword) and u.categorie like :categorie  and u.appRole.roleName=:role and u.status like :status")
	List<AppUser> findUsersByKeyword(
			@Param(value = "keyword") String keyword,
			@Param(value = "status") String status,
			@Param(value = "role") String role,
			@Param(value = "categorie") String categorie);

}
