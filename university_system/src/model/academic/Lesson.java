package model.academic;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import model.users.Teacher;

public class Lesson implements Serializable {
	private static final long serialVersionUID = 1L;
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
	public String getRoom() {return room;}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof Lesson)) return false;

	    Lesson other = (Lesson) obj;

	    return Objects.equals(course, other.course)
	            && Objects.equals(schedule, other.schedule)
	            && Objects.equals(room, other.room);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(course, schedule, room);
	    }
	
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