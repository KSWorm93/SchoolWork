package exception;


public class NoServerConnectionFoundException extends Exception {

    public NoServerConnectionFoundException() {
    }

    
    public NoServerConnectionFoundException(String msg) {
        super(msg);
    }
}