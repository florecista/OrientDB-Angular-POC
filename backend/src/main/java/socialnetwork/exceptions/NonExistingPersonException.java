package socialnetwork.exceptions;

public class NonExistingPersonException extends RuntimeException {
    public NonExistingPersonException(String id) {
        super("Could not find Person : " + id);
    }
}
