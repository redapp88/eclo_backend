package com.redapp.eclo.entities;

import java.io.Serializable;

public class UserProgCount implements Serializable  {
private AppUser user;
private int lessonsCount;
public UserProgCount(AppUser user, int lessonsCount) {
	super();
	this.user = user;
	this.lessonsCount = lessonsCount;
	
}
public AppUser getUser() {
	return user;
}
public void setUser(AppUser user) {
	this.user = user;
}
public int getLessonsCount() {
	return lessonsCount;
}
public void setLessonsCount(int lessonsCount) {
	this.lessonsCount = lessonsCount;
}

}
