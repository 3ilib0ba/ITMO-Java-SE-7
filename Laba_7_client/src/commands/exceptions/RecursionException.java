package commands.exceptions;

public class RecursionException extends RuntimeException {
    /*public RecursionException(){
        super();
    }*/

    public RecursionException(String message){
        super(message);
    }
}
