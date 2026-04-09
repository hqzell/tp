package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (same name, room, or non-empty phone/email).
 */
public class DuplicatePersonException extends RuntimeException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }
}
