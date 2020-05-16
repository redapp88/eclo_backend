package com.redapp.eclo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redapp.eclo.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

	AppRole findByRoleName(String string);

}
