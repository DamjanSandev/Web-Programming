package mk.ukim.finki.wp.lab.model.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(long id) {
        super(String.format("Location with id %d is not found!", id));
    }
}
