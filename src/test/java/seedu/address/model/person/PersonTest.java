package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALLERGIES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_ALLERGIES).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, different comment -> returns true
        editedAlice = new PersonBuilder(ALICE).withComment(VALID_COMMENT_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, but same room (and same phone/email) -> conflict
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, phone, email, and room -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case; no shared name, room, phone, or email -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).withPhone("99999999")
                .withEmail("unique@example.com").withRoom("#12-120-F").build();
        assertFalse(BOB.isSamePerson(editedBob));

        // different name (trailing space); no overlapping identity fields -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).withPhone("98888888")
                .withEmail("other@example.com").withRoom("#11-111-B").build();
        assertFalse(BOB.isSamePerson(editedBob));

        // same phone/email but one side is empty -> returns false
        Person personWithEmptyContacts = new PersonBuilder(ALICE).withName("No Contact").withPhone("")
                .withEmail("").withRoom("#10-100-A").build();
        Person personWithFilledContacts = new PersonBuilder(ALICE).withName("Has Contact").withPhone("94351253")
                .withEmail("alice@example.com").withRoom("#10-101-A").build();
        assertFalse(personWithEmptyContacts.isSamePerson(personWithFilledContacts));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different room -> returns false
        editedAlice = new PersonBuilder(ALICE).withRoom(VALID_ROOM_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different comment -> returns false
        editedAlice = new PersonBuilder(ALICE).withComment(VALID_COMMENT_AMY).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_ALLERGIES).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", room=" + ALICE.getRoom() + ", comment=" + ALICE.getComment()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
