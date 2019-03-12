package exceptions;

public class NotFoundException extends RuntimeException {
    private int status = 404;
    public NotFoundException(String message, int status) {
        super(message);
        this.status = status;
    }
    public NotFoundException(String message) {
        super(message);
    }
    public int getStatus(){ return status;}
}
