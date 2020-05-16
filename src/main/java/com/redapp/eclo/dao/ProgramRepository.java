package com.redapp.eclo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.redapp.eclo.entities.Program;

public interface ProgramRepository extends JpaRepository<Program, String> {
@Query("select p from Program p order by p.year desc,p.month desc")
	public List<Program> findAllSortedByPeriode();

}
