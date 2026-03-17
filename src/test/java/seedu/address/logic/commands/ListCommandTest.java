package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

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
        ListCommand listCommand = new ListCommand("name", (p1, p2) -> p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName));
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SORTED, "name");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS, (p1, p2) -> p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName));
        assertCommandSuccess(listCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listSortedByRoom_success() {
        ListCommand listCommand = new ListCommand("room", (p1, p2) -> p1.getRoom().compareTo(p2.getRoom()));
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SORTED, "room");
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS, (p1, p2) -> p1.getRoom().compareTo(p2.getRoom()));
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
        assertFalse(listFirstCommand.equals(new ListCommand("name", null)));
        assertFalse(new ListCommand("name", null).equals(new ListCommand("room", null)));
        
        // same sort field -> returns true
        assertTrue(new ListCommand("name", null).equals(new ListCommand("name", null)));
    }
}
