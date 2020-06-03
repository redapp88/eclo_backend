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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.redapp.eclo.dao.ProgramRepository;
import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.FileExport;
import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.LessonRequest;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.entities.ProgramRequest;
import com.redapp.eclo.entities.UserProgCount;
import com.redapp.eclo.entities.UserRequest;
import com.redapp.eclo.export.DownloadService;
import com.redapp.eclo.export.ExcelExportService;
import com.redapp.eclo.export.PdfExportService;
import com.redapp.eclo.security.ExtendedUser;
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
@Autowired
PdfExportService pdfExportService;
@Autowired
DownloadService downloadService;

//////////////////////Users functions //////////////
@PostMapping("/admin/addUser")
public AppUser addUser(
		@RequestBody UserRequest userRequest)
{
	this.testBlocked();
	
return this.usersService.addUser(userRequest);
}

@PostMapping("admin/addManager")
public AppUser addManager(
		@RequestBody UserRequest userRequest)
{
	this.testBlocked();
return this.usersService.addManager(userRequest);
}

@PutMapping("user/editUser")
public AppUser editUser(
		@RequestParam String username,
		@RequestBody UserRequest userRequest)
{
	this.testBlocked();
return this.usersService.editUser(username,userRequest);
}

@PutMapping("user/editPassword")
public AppUser editPassword(
		@RequestParam String username,
		@RequestBody UserRequest userRequest)
{
	this.testBlocked();
return this.usersService.editPassword(username,userRequest);
}

@GetMapping("admin/usersList")
public List<AppUser> usersList(
		@RequestParam String categorie,
		@RequestParam String keyword,
		@RequestParam String status)
{
	this.testBlocked();
return this.usersService.getUsers(keyword, status,categorie);
}

@GetMapping("admin/managersList")
public List<AppUser> managersList(
)
{
	this.testBlocked();
return this.usersService.getManagers();
}

@DeleteMapping("admin/deleteManager")
public void deleteManager(
		@RequestParam String username
)
{
	this.testBlocked();
this.usersService.deleteManager(username);;
}

@DeleteMapping("admin/deleteUser")
public void deleteUser(
		@RequestParam String username
)
{
	this.testBlocked();
this.usersService.deleteUser(username);;
}

////////////////////// programs functions //////////////
@PostMapping("admin/programs/add")
public Program addProgram(
		@RequestBody ProgramRequest programRequest)
{
	this.testBlocked();
return this.programsService.addPrgram(programRequest);
}


@PutMapping("admin/programs/edit")
	public Program editProgram(
			@RequestParam Long month,
			@RequestParam Long year,
			@RequestBody ProgramRequest programRequest)
	{
	this.testBlocked();
	return this.programsService.editProgram(month,year,programRequest);
	}

@DeleteMapping("admin/programs/delete")
public void deleteProgram(
		@RequestParam String id)
{
	this.testBlocked();
	
 this.programsService.deleteProgram(id);
}

@GetMapping("users/programs/list")
public List<Program> programsPage()
{
	this.testBlocked();
return this.programsService.getPrograms();
}

@GetMapping("admin/programs/LessonsCountByUser")
public List<UserProgCount> usersCountLesson(
		@RequestParam String programId,
		@RequestParam String keyword,
		@RequestParam String categorie,
		@RequestParam String status)
{
	this.testBlocked();
return this.lessonsService.usersCountLesson(programId, keyword, categorie,status);
}
	
//////////////////////Lesson functions //////////////
@PostMapping("users/lessons/add")
public Lesson addLesson(
		@RequestBody LessonRequest lessonRequest)
{
	this.testBlocked();
return this.lessonsService.addLesson(lessonRequest);
}

@PutMapping("users/lessons/edit")
	public Lesson editLesson(
			@RequestParam Long id,
			@RequestBody LessonRequest lessonRequest)
	{
	this.testBlocked();
	return this.lessonsService.editLesson(id, lessonRequest);
	}

@DeleteMapping("users/lessons/delete")
public void deleteLesson(
		@RequestParam Long id)
{
	this.testBlocked();
 this.lessonsService.deleteLesson(id);;
}

@GetMapping("users/lessons/list")
public List<Lesson> lessonsPage(
		@RequestParam Long month,
		@RequestParam Long year,
		@RequestParam String username,
		@RequestParam String categorie)
{
	this.testBlocked();
return this.lessonsService.getLessons(month, year, username,categorie);
}
// teste//
	@GetMapping(value = "/exportProgramXls")
	public FileExport excelExportService
	(@RequestParam Long month,
			@RequestParam Long year,
			@RequestParam String username,
			@RequestParam String categorie) throws FileNotFoundException, IOException {
		this.testBlocked();
return this.excelExportService.programExcelExport(month, year, username,categorie);
	}
	
	
	@GetMapping(value = "/exportProgramPdf")
	public FileExport pdfExportService
	(@RequestParam Long month,
			@RequestParam Long year,
			@RequestParam String username) throws FileNotFoundException, IOException, DocumentException {
		this.testBlocked();
return this.pdfExportService.programPdfExport(month,year,username);
	}
	
	@GetMapping(value = "/downloadFile")
	public ResponseEntity downloadFile (@RequestParam String fileName) throws FileNotFoundException, IOException {
		this.testBlocked();
		return this.downloadService.downloadFile(fileName);
	}
	@DeleteMapping(value = "/deleteFile")
	public void deleteFile (@RequestParam String fileName) throws FileNotFoundException, IOException {
		this.testBlocked();
		this.downloadService.deleteFile(fileName);
	}
	
	
	
private void testBlocked() {
	/*UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	AppUser user= this.usersService.get userDetails.ge
		 throw new RuntimeException("403");*/
}	
	}
