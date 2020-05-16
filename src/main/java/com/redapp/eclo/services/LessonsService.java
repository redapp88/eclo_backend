package com.redapp.eclo.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.redapp.eclo.entities.Lesson;
import com.redapp.eclo.entities.LessonRequest;
import com.redapp.eclo.entities.UserProgCount;

public interface LessonsService {
public List<Lesson> getLessons(Long month,Long year,String username,String categorie);
public Lesson addLesson(LessonRequest lessonRequest);
public void deleteLesson(Long id);
public Lesson editLesson(Long id,LessonRequest lessonRequest);
public List<UserProgCount> usersCountLesson(String programId, String keyword,String categorie,String status);
}
