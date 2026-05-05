package model.academic;

public class CourseFailLimitException extends RuntimeException {

    public CourseFailLimitException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}