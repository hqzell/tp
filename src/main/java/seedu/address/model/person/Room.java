package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's room number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_CONSTRAINTS = "Room must follow the format #BLOCK-ROOM-LETTER (e.g. #14-203-D).";

    /*
     * The first character of the room number must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "#[0-9]{1,2}-[0-9]{3}-[A-Z]";

    public final String value;

    /**
     * Constructs an {@code Room}.
     *
     * @param room A valid room number.
     */
    public Room(String room) {
        requireNonNull(room);
        checkArgument(isValidRoom(room), MESSAGE_CONSTRAINTS);
        value = room;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidRoom(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Room)) {
            return false;
        }

        Room otherRoom = (Room) other;
        return value.equals(otherRoom.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
