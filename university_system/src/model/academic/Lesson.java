package model.academic;

import java.time.LocalDateTime;

import enums.LessonType;
import model.users.Teacher;

public class Lesson {
	private Teacher teacher;
	private Course course;
	private String room;
	private LocalDateTime schedule;
	private LessonType lessonType;
	
	public Lesson(Teacher teacher, Course course, String room, LocalDateTime schedule, LessonType lessonType) {
	    this.teacher = teacher;
	    this.course = course;
	    this.room = room;
	    this.schedule = schedule;
	    this.lessonType = lessonType;
	}
	
	public Teacher getTeacher() { return teacher; }
	public Course getCourse() { return course; }
	public LocalDateTime getSchedule() { return schedule; }
	public LessonType getLessonType() { return lessonType; }
	
	@Override 
	public String toString() {
		return "\nLesson:" +
		       "\nCourse: " + course +
		       "\nTeacher: " + teacher +
		       "\nType: " + lessonType +
		       "\nRoom: " + room +
		       "\nTime: " + schedule + "\n";
	}
}