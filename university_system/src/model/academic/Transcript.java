package model.academic;

import java.util.*;

import model.users.Student;

public class Transcript {
	private Student student;
	private List<Mark> marks = new ArrayList<>();
	private double gpa;
	
	public Transcript(Student student) {
	    this.student = student;
	    this.marks = new ArrayList<>();
	}
	
	public void generate(List<Enrollment> enrollments) {}
	
	 private double convert(String g) {//will be used in generate
	        switch (g) {
	            case "A": return 4.0;
	            case "A-": return 3.7;
	            case "B+": return 3.3;
	            case "B": return 3.0;
	            case "B-": return 2.7;
	            case "C+": return 2.3;
	            case "C": return 2.0;
	            case "C-": return 1.7;
	            case "D+": return 1.3;
	            case "D": return 1.0;
	            default: return 0;
	        }
	    }
	
	public void print() {
		System.out.println(this);
	}
	@Override
	public String toString() {
		 return "Transcript: " + student + " GPA: " + gpa;
	}
}
