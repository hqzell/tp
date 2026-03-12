package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoomTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Room(null));
    }

    @Test
    public void constructor_invalidRoom_throwsIllegalArgumentException() {
        String invalidRoom = "";
        assertThrows(IllegalArgumentException.class, () -> new Room(invalidRoom));
    }

    @Test
    public void isValidRoom() {
        // null room
        assertThrows(NullPointerException.class, () -> Room.isValidRoom(null));

        // invalid rooms
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom(" ")); // spaces only
        assertFalse(Room.isValidRoom("#14-203-d")); // letter must be uppercase
        assertFalse(Room.isValidRoom("#123-203-D")); // block must be 1-2 digits
        assertFalse(Room.isValidRoom("#14-20-D")); // room must be 3 digits
        assertFalse(Room.isValidRoom("14-203-D")); // missing leading '#'

        // valid rooms
        assertTrue(Room.isValidRoom("#14-203-D"));
        assertTrue(Room.isValidRoom("#1-000-A"));
        assertTrue(Room.isValidRoom("#99-999-Z"));
    }

    @Test
    public void equals() {
        Room room = new Room("#14-203-D");

        // same values -> returns true
        assertTrue(room.equals(new Room("#14-203-D")));

        // same object -> returns true
        assertTrue(room.equals(room));

        // null -> returns false
        assertFalse(room.equals(null));

        // different types -> returns false
        assertFalse(room.equals(5.0f));

        // different values -> returns false
        assertFalse(room.equals(new Room("#3-118-A")));
    }
}
