package exception;

public class NoFlightsFoundException extends Exception {
    
    public NoFlightsFoundException() {
    }
    
    public NoFlightsFoundException(String msg) {
        super(msg);
    }
}
