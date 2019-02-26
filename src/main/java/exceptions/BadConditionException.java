package exceptions;

public class BadConditionException extends RuntimeException{
    public BadConditionException(String message) {
        super(message);
    }
}
