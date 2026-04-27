package model.academic;

import java.util.*;

public class Mark {
	private Enrollment enrollment;
	private double attestation1;
	private double attestation2;
	private double finalExam;
	
	public Mark(Enrollment enrollment) {
	    this.enrollment = enrollment;
	}
	
	public Enrollment getEnrollment() {
        return enrollment;
    }
	
	public double getTotal() {
		return attestation1 + attestation2 + finalExam;
	}
	
	public void setAttestation1(double score) {
        this.attestation1 = score;
    }

    public void setAttestation2(double score) {
        this.attestation2 = score;
    }

    public void setFinalExam(double score) {
        this.finalExam = score;
    }
	
	public String getLetterGrade() {
		double total = getTotal();
		if(total >= 95 && total <= 100) return "A";
		if(total < 95 && total >= 90) return "A-";
		if(total < 90 && total >= 85) return "B+";
		if(total < 85 && total >= 80) return "B";
		if(total < 80 && total >= 75) return "B-";
		if(total < 75 && total >= 70) return "C+";
		if(total < 70 && total >= 65) return "C";
		if(total < 65 && total >= 60) return "C-";
		if(total < 60 && total >= 55) return "D+";
		if(total < 55 && total >= 50) return "D";
		return "F";
	}
	
	public boolean isPassed() {
		if(attestation1 + attestation2 < 30) return false;
		if(finalExam < 10) return false;
		 return getTotal() >= 50;
	}
	
	@Override
	public boolean equals(Object obj) {
		 if (this == obj) return true;
		    if (!(obj instanceof Mark)) return false;

		    Mark other = (Mark) obj;
		    return Objects.equals(enrollment, other.enrollment);
	}
	
	@Override
	public int hashCode() {
		 return Objects.hash(enrollment, attestation1, attestation2, finalExam);
	}
	
	public String toString() {
		return "\nMark:\n" +
	           "\nAttestation1: " + attestation1 +
	           "\nAttestation2: " + attestation2 +
	           "\nFinal Exam: " + finalExam +
	           "\nTotal: " + getTotal() +
	           "\nGrade: " + getLetterGrade() + "\n";
	}
}

