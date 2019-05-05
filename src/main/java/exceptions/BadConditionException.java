package exceptions;

public class BadConditionException extends RuntimeException{
    private int status = 500;
    public BadConditionException(String message) {
        super(message);
    }
    public BadConditionException(String message, int status) {
        super(message);
        this.status = status;
    }
    public int getStatus(){ return status;}
}
