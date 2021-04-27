package commands.exceptions;

public class ExitException extends RuntimeException{
    public ExitException() {
        super("goodbye...");
    }
    /*public ExitException(String message) {
        super(message);
    }*/
}
