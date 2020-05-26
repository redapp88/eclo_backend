package com.redapp.eclo.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redapp.eclo.dao.AppUserRepository;
import com.redapp.eclo.dao.LessonRepository;
import com.redapp.eclo.dao.ProgramRepository;
import com.redapp.eclo.entities.AppUser;
import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.LessonRequest;
import com.redapp.eclo.entities.Program;
import com.redapp.eclo.entities.UserProgCount;

@Service
@Transactional
public class LessonsServicesImp implements LessonsService {
@Autowired
LessonRepository lessonRepository;
@Autowired
AppUserRepository appUserRepository;
@Autowired
UsersService UsersService;
@Autowired
ProgramRepository programRepository;

	@Override
	public List<Lesson> getLessons(Long month, Long year, String username,String categorie) {
		if(username.equals("*"))
			username="%%";
		if(categorie.equals("*"))
			categorie="%%";
		
		return this.lessonRepository.findByPeriodeAndUser(month,year, username,categorie);
	}

	@Override
	public Lesson addLesson(LessonRequest lessonRequest) {
		Optional<AppUser> appUserOpt=this.appUserRepository.findById(lessonRequest.getUsername());
		if(!appUserOpt.isPresent())
			throw new RuntimeException("مستخدم غير موجود");
		Optional<Program> programOpt=this.programRepository.findById(lessonRequest.getProgramId());
		if(!programOpt.isPresent())
			throw new RuntimeException("برنامج غير موجود");
		Date date=validateDate(lessonRequest.getDate());
		Lesson lesson=new Lesson(lessonRequest.getType(),lessonRequest.getTitle(),date,
				lessonRequest.getTime(),lessonRequest.getArea(),programOpt.get(),appUserOpt.get());
		return this.lessonRepository.save(lesson);
	}

	@Override
	public void deleteLesson(Long id) {
		Optional<Lesson> lessonOpt=this.lessonRepository.findById(id);
		if(!lessonOpt.isPresent())
			throw new RuntimeException("درس غير موجود");
	this.lessonRepository.deleteById(id);

	}

	@Override
	public Lesson editLesson(Long id,LessonRequest lessonRequest) {
	Optional<Lesson> oldLessonOpt=this.lessonRepository.findById(id);
	if(!oldLessonOpt.isPresent())
		throw new RuntimeException("درس غير موجود");
	
	Date date=validateDate(lessonRequest.getDate());
	Lesson oldLesson=oldLessonOpt.get();
	oldLesson.setDate(date);
	oldLesson.setTime(lessonRequest.getTime());
	oldLesson.setTitle(lessonRequest.getTitle());
	oldLesson.setType(lessonRequest.getType());
	oldLesson.setArea(lessonRequest.getArea());
	return oldLesson;
	}
	
	private Date validateDate(String dateString) {
		Date date=null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateString+" 12:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(date==null)
			throw new RuntimeException("تاريخ غير دقيق");
		return date;
	}

	@Override
	public List<UserProgCount> usersCountLesson(String programId, String keyword,String categorie,String status) {

		List<AppUser> users=this.UsersService.getUsers(keyword,status,categorie);
		
		List<UserProgCount> usersWithLessCount=new ArrayList<UserProgCount>();
		int count=0;
		for(int i=0;i<users.size();i++) {
			count=0;
			AppUser u=users.get(i);
			List<Lesson>lessons=users.get(i).getLessons();
			for(int j=0;j<lessons.size();j++) {
				if(lessons.get(j).getProgram().getProgramId().equals(programId))
					count++;
				}
			usersWithLessCount.add(new UserProgCount(u, count));
				
		}
		System.out.println(usersWithLessCount);
		return usersWithLessCount;
		
	}

}
