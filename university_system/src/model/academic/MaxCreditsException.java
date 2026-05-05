package model.academic;

public class MaxCreditsException extends RuntimeException {

    public MaxCreditsException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}