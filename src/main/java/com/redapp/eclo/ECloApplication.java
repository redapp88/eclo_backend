package com.redapp.eclo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.redapp.eclo.dao.AppUserRepository;
import com.redapp.eclo.dao.LessonRepository;
import com.redapp.eclo.dao.ProgramRepository;
import com.redapp.eclo.entities.AppRole;
import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.entities.UserRequest;
import com.redapp.eclo.services.UsersService;
import com.redapp.eclo.dao.AppRoleRepository;

@SpringBootApplication

public class ECloApplication implements CommandLineRunner {
	@Autowired
	AppRoleRepository AppRoleRepository;
	@Autowired
	AppUserRepository appUserRepository;
	@Autowired
	ProgramRepository programRepository;
	@Autowired
	LessonRepository lessonRepository;
	@Autowired
	UsersService usersService;

	public static void main(String[] args) {
		SpringApplication.run(ECloApplication.class, args);
	}
	@Bean
	 public BCryptPasswordEncoder generateBcrypte() {
		 return new BCryptPasswordEncoder();
	 }
	@Override
	public void run(String... args) throws Exception {
AppRole role1=new AppRole("USER");
		AppRole role2=new AppRole("MANAGER");
	    
		
	    Program program=new Program(12L,2019L,"moharam");
	   /* this.AppRoleRepository.save(role1);
	    this.AppRoleRepository.save(role2);
	    this.programRepository.save(program);
	    this.usersService.addManager(new UserRequest("admin","1234","","m","","","taounate","active"));
	    //AppUser u=this.usersService.addUser(new UserRequest("red5","1234","reda EL IDRISSI","mal","0909000","w","taounate","active"));
	 /* this.lessonRepository.save(new Lesson("سيبيسبيشسب","ثبثصب",new Date(),"صثقبثصقثصق","ss",program,u));
	 u.getLessons().add(new Lesson("سيبيسبيشسب","ثبثصب",new Date(),"صثقبثصقثصق",program,u));
	    program.getLessons().add(e)*/
	    
	    
	    
		
	}

}
