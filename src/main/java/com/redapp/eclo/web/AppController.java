package com.redapp.eclo.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jxls.template.SimpleExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.redapp.eclo.dao.ProgramRepository;
import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.FileExport;
import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.LessonRequest;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.entities.ProgramRequest;
import com.redapp.eclo.entities.UserProgCount;
import com.redapp.eclo.entities.UserRequest;
import com.redapp.eclo.export.ExcelExportService;
import com.redapp.eclo.services.LessonsService;
import com.redapp.eclo.services.ProgramsService;
import com.redapp.eclo.services.UsersService;


@RestController
public class AppController {
@Autowired
LessonsService lessonsService;
@Autowired
ProgramsService programsService;
@Autowired
UsersService usersService;
@Autowired
ProgramRepository programRepository;
@Autowired
ExcelExportService excelExportService;


//////////////////////Users functions //////////////
@PostMapping("/admin/addUser")
public AppUser addUser(
		@RequestBody UserRequest userRequest)
{
return this.usersService.addUser(userRequest);
}

@PostMapping("admin/addManager")
public AppUser addManager(
		@RequestBody UserRequest userRequest)
{
return this.usersService.addManager(userRequest);
}

@PutMapping("admin/editUser")
public AppUser editUser(
		@RequestParam String username,
		@RequestBody UserRequest userRequest)
{
return this.usersService.editUser(username,userRequest);
}

@PutMapping("user/editPassword")
public AppUser editPassword(
		@RequestParam String username,
		@RequestBody UserRequest userRequest)
{
return this.usersService.editPassword(username,userRequest);
}

@GetMapping("admin/usersList")
public List<AppUser> usersList(
		@RequestParam String categorie,
		@RequestParam String keyword,
		@RequestParam String status)
{

return this.usersService.getUsers(keyword, status,categorie);
}

@GetMapping("admin/managersList")
public List<AppUser> managersList(
)
{
return this.usersService.getManagers();
}

@DeleteMapping("admin/deleteManager")
public void deleteManager(
		@RequestParam String username
)
{
this.usersService.deleteManager(username);;
}

@DeleteMapping("admin/deleteUser")
public void deleteUser(
		@RequestParam String username
)
{
this.usersService.deleteUser(username);;
}

////////////////////// programs functions //////////////
@PostMapping("admin/programs/add")
public Program addProgram(
		@RequestBody ProgramRequest programRequest)
{
return this.programsService.addPrgram(programRequest);
}


@PutMapping("admin/programs/edit")
	public Program editProgram(
			@RequestParam Long month,
			@RequestParam Long year,
			@RequestBody ProgramRequest programRequest)
	{
		
	return this.programsService.editProgram(month,year,programRequest);
	}

@GetMapping("users/programs/list")
public List<Program> programsPage()
{
return this.programsService.getPrograms();
}

@GetMapping("admin/programs/LessonsCountByUser")
public List<UserProgCount> usersCountLesson(
		@RequestParam String programId,
		@RequestParam String keyword,
		@RequestParam String categorie,
		@RequestParam String status)
{
return this.lessonsService.usersCountLesson(programId, keyword, categorie,status);
}
	
//////////////////////Lesson functions //////////////
@PostMapping("users/lessons/add")
public Lesson addLesson(
		@RequestBody LessonRequest lessonRequest)
{
return this.lessonsService.addLesson(lessonRequest);
}

@PutMapping("users/lessons/edit")
	public Lesson editLesson(
			@RequestParam Long id,
			@RequestBody LessonRequest lessonRequest)
	{
		
	return this.lessonsService.editLesson(id, lessonRequest);
	}

@DeleteMapping("users/lessons/delete")
public void deleteLesson(
		@RequestParam Long id)
{
	
 this.lessonsService.deleteLesson(id);;
}

@GetMapping("users/lessons/list")
public List<Lesson> lessonsPage(
		@RequestParam Long month,
		@RequestParam Long year,
		@RequestParam String username,
		@RequestParam String categorie)
{
return this.lessonsService.getLessons(month, year, username,categorie);
}
// teste//
	@GetMapping(value = "/exportProgram")
	public FileExport excelExportService
	(@RequestParam Long month,
			@RequestParam Long year,
			@RequestParam String username,
			@RequestParam String categorie) throws FileNotFoundException, IOException {
return this.excelExportService.programExcelExport(month, year, username,categorie);
	}
	
	@GetMapping(value = "/downloadFile")
	public ResponseEntity downloadFile (@RequestParam String fileName) throws FileNotFoundException, IOException {
return this.excelExportService.downloadFile(fileName);
	}
	
	
	}
