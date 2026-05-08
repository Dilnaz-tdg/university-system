package model.academic;

import java.util.*;

import core.DataStorage;
import model.users.Student;

public class Transcript {
	private Student student;
	private List<Mark> marks = new ArrayList<>();
	private double gpa;
	
	public Transcript(Student student) {
	    this.student = student;
	    this.marks = new ArrayList<>();
	}
	
	public void generate() {
	    marks.clear();

	    double total = 0;
	    int count = 0;

	    for (Mark m : DataStorage.getInstance().getMarks()) {
	        if (m.getEnrollment().getStudent().equals(student)) {
	            marks.add(m);
	            total += convert(m.getLetterGrade());
	            count++;
	        }
	    }

	    if (count > 0) {
	        gpa = total / count;
	    }
	}
	
	 private double convert(String g) {
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
		    System.out.println("Transcript: " + student);
		    System.out.println("+-----------------------------------------------------------+");
		    System.out.println("| Course | Att1 | Att2 | Final | Total | Grade |");
		    System.out.println("+-----------------------------------------------------------+");

		    for (Mark m : marks) {
		        System.out.printf(
		            "| %-6s | %-4.1f | %-4.1f | %-5.1f | %-5.1f | %-5s |\n",
		            m.getEnrollment().getCourse().getCourseId(),
		            m.getAttestation1(),
		            m.getAttestation2(),
		            m.getFinalExam(),
		            m.getTotal(),
		            m.getLetterGrade()
		        );
		    }

		    System.out.println("+-----------------------------------------------------------+");
		    System.out.println("GPA: " + gpa);
		}
	 
	@Override
	public String toString() {
		 return "Transcript: " + student + " GPA: " + gpa;
	}
}

