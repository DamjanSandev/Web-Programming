package mk.ukim.finki.wp.lab.model.exception;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid User Credentials");
    }
}
