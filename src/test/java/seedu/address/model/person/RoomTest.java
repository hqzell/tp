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
        assertFalse(Room.isValidRoom("#123-203-D")); // floor must be 1-2 digits
        assertFalse(Room.isValidRoom("#14-2034-D")); // unit must be 1-3 digits
        assertFalse(Room.isValidRoom("14-203-D")); // missing leading '#'

        // valid rooms
        assertTrue(Room.isValidRoom("#14-203-D"));
        assertTrue(Room.isValidRoom("#14-2-D")); // unit can be 1, 2, or 3 digits
        assertTrue(Room.isValidRoom("#14-20-D"));
        assertTrue(Room.isValidRoom("#1-000-A"));
        assertTrue(Room.isValidRoom("#14-20")); // letter is optional
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

        // equivalent unit formatting -> returns true
        assertTrue(new Room("#12-02").equals(new Room("#12-2")));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        Room room1 = new Room("#14-203-D");
        Room room2 = new Room("#14-203-D");
        assertTrue(room1.hashCode() == room2.hashCode());

        Room equivalentRoom1 = new Room("#12-02");
        Room equivalentRoom2 = new Room("#12-2");
        assertTrue(equivalentRoom1.hashCode() == equivalentRoom2.hashCode());
    }

    @Test
    public void compareTo() {
        Room roomLower = new Room("#01-101-A");
        Room roomHigherBlock = new Room("#02-101-A");
        Room roomHigherUnit = new Room("#01-102-A");
        Room roomHigherLetter = new Room("#01-101-B");

        // same values -> returns 0
        assertTrue(roomLower.compareTo(new Room("#01-101-A")) == 0);

        // higher block -> returns negative
        assertTrue(roomLower.compareTo(roomHigherBlock) < 0);
        // lower block -> returns positive
        assertTrue(roomHigherBlock.compareTo(roomLower) > 0);

        // higher unit -> returns negative
        assertTrue(roomLower.compareTo(roomHigherUnit) < 0);
        // lower unit -> returns positive
        assertTrue(roomHigherUnit.compareTo(roomLower) > 0);

        // equivalent unit formatting -> returns 0
        assertTrue(new Room("#12-02").compareTo(new Room("#12-2")) == 0);

        // higher letter -> returns negative
        assertTrue(roomLower.compareTo(roomHigherLetter) < 0);
        // lower letter -> returns positive
        assertTrue(roomHigherLetter.compareTo(roomLower) > 0);
    }
}
