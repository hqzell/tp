package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's room number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room implements Comparable<Room> {

    public static final String MESSAGE_CONSTRAINTS =
        "Room must follow the format #FLOOR-UNIT[-LETTER], where floor is 1-2 digits, "
        + "unit is 2-3 digits, and letter is optional (e.g. #14-203-D or #14-20).";

    /*
     * The first character of the room number must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "#[0-9]{1,2}-[0-9]{2,3}(-[A-Z])?";

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

    @Override
    public int compareTo(Room other) {
        // Format: #FLOOR-UNIT[-LETTER] (letter is optional)
        String[] parts1 = this.value.substring(1).split("-");
        String[] parts2 = other.value.substring(1).split("-");

        int floor1 = Integer.parseInt(parts1[0]);
        int floor2 = Integer.parseInt(parts2[0]);
        if (floor1 != floor2) {
            return floor1 - floor2;
        }

        int unit1 = Integer.parseInt(parts1[1]);
        int unit2 = Integer.parseInt(parts2[1]);
        if (unit1 != unit2) {
            return unit1 - unit2;
        }

        // Handle optional letter part
        String letter1 = parts1.length > 2 ? parts1[2] : "";
        String letter2 = parts2.length > 2 ? parts2[2] : "";
        return letter1.compareTo(letter2);
    }

}
