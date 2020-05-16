package com.redapp.eclo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.UserProgCount;;
@RepositoryRestResource
public interface LessonRepository extends JpaRepository<Lesson, Long> {
@Query("select l from Lesson l where l.program.month=:month and l.program.year=:year and l.appUser.username like :username and l.appUser.categorie like :categorie order by l.date")
List<Lesson> findByPeriodeAndUser(
		@Param("month")Long month,
		@Param("year")Long year,
		@Param("username")String username,
		@Param("categorie")String categorie);


}
