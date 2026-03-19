package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private static final Comparator<Person> NAME_COMPARATOR = (p1, p2) -> p1.getName().fullName
            .compareToIgnoreCase(p2.getName().fullName);
    private static final Comparator<Person> ROOM_COMPARATOR = (p1, p2) -> p1.getRoom()
            .compareTo(p2.getRoom());
    private static final Comparator<Person> PHONE_COMPARATOR = (p1, p2) -> p1.getPhone().value
            .compareTo(p2.getPhone().value);
    private static final Comparator<Person> EMAIL_COMPARATOR = (p1, p2) -> p1.getEmail().value
            .compareToIgnoreCase(p2.getEmail().value);

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listSortedByName_success() {
        ListCommand listCommand = new ListCommand("name", NAME_COMPARATOR);
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SORTED, "name");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS, NAME_COMPARATOR);
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByRoom_success() {
        ListCommand listCommand = new ListCommand("room", ROOM_COMPARATOR);
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SORTED, "room");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS, ROOM_COMPARATOR);
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByPhone_success() {
        ListCommand listCommand = new ListCommand("phone", PHONE_COMPARATOR);
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SORTED, "phone");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS, PHONE_COMPARATOR);
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByEmail_success() {
        ListCommand listCommand = new ListCommand("email", EMAIL_COMPARATOR);
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SORTED, "email");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS, EMAIL_COMPARATOR);
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listFirstCommand = new ListCommand();
        ListCommand listSecondCommand = new ListCommand();

        // same object -> returns true
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // same values -> returns true
        assertTrue(listFirstCommand.equals(listSecondCommand));

        // different types -> returns false
        assertFalse(listFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listFirstCommand.equals(null));

        // different sort field -> returns false
        assertFalse(listFirstCommand.equals(new ListCommand("name", NAME_COMPARATOR)));
        assertFalse(new ListCommand("name", NAME_COMPARATOR).equals(
                new ListCommand("room", ROOM_COMPARATOR)));

        // same sort field -> returns true
        assertTrue(new ListCommand("name", NAME_COMPARATOR).equals(
                new ListCommand("name", NAME_COMPARATOR)));
    }

    @Test
    public void hashCode_test() {
        // same fields -> same hashCode
        ListCommand list1 = new ListCommand("name", NAME_COMPARATOR);
        ListCommand list2 = new ListCommand("name", NAME_COMPARATOR);
        assertTrue(list1.hashCode() == list2.hashCode());

        // both default -> same hashCode
        ListCommand list3 = new ListCommand();
        ListCommand list4 = new ListCommand();
        assertTrue(list3.hashCode() == list4.hashCode());

        // different fields -> different hashCode
        ListCommand listName = new ListCommand("name", NAME_COMPARATOR);
        ListCommand listRoom = new ListCommand("room", ROOM_COMPARATOR);
        assertFalse(listName.hashCode() == listRoom.hashCode());

        // sorted vs unsorted -> different hashCode
        ListCommand listDefault = new ListCommand();
        ListCommand listSorted = new ListCommand("phone", PHONE_COMPARATOR);
        assertFalse(listDefault.hashCode() == listSorted.hashCode());
    }
}
