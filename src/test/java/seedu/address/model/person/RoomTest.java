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
        // null address
        assertThrows(NullPointerException.class, () -> Room.isValidRoom(null));

        // invalid addresses
        assertFalse(Room.isValidRoom("")); // empty string
        assertFalse(Room.isValidRoom(" ")); // spaces only

        // valid addresses
        assertTrue(Room.isValidRoom("Blk 456, Den Road, #01-355"));
        assertTrue(Room.isValidRoom("-")); // one character
        assertTrue(Room.isValidRoom("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        Room address = new Room("Valid Room");

        // same values -> returns true
        assertTrue(address.equals(new Room("Valid Room")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Room("Other Valid Room")));
    }
}
