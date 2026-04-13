package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (same name ignoring case, room, or non-empty
 * phone or case-insensitively equal email).
 */
public class DuplicatePersonException extends RuntimeException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }
}
