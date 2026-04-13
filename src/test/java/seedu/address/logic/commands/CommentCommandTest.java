package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMMENT_HANNAH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Comment;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for CommentCommand.
 */
public class CommentCommandTest {

    // HANNAH is the 8th person in the typical address book (see TypicalPersons#getTypicalPersons)
    // and is the only typical person with a non-empty comment.
    private static final Index INDEX_HANNAH = Index.fromOneBased(8);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addCommentToPersonWithNoComment_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withComment(VALID_COMMENT_AMY).build();

        CommentCommand commentCommand = new CommentCommand(INDEX_FIRST_PERSON,
                new Comment(VALID_COMMENT_AMY));

        String expectedMessage = String.format(
                CommentCommand.MESSAGE_ADD_COMMENT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_updateCommentToDifferentComment_success() {
        Person hannah = model.getFilteredPersonList().get(INDEX_HANNAH.getZeroBased());
        assertTrue(!hannah.getComment().value.isEmpty());

        // Use Amy's comment as the "updated" comment
        Person editedPerson = new PersonBuilder(hannah).withComment(VALID_COMMENT_AMY).build();

        CommentCommand commentCommand = new CommentCommand(INDEX_HANNAH,
                new Comment(VALID_COMMENT_AMY));

        String expectedMessage = String.format(
                CommentCommand.MESSAGE_UPDATE_COMMENT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(hannah, editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_updateCommentToSameComment_success() {
        Person hannah = model.getFilteredPersonList().get(INDEX_HANNAH.getZeroBased());
        assertTrue(!hannah.getComment().value.isEmpty());

        Person editedPerson = new PersonBuilder(hannah).withComment(VALID_COMMENT_HANNAH).build();

        CommentCommand commentCommand = new CommentCommand(INDEX_HANNAH,
                new Comment(VALID_COMMENT_HANNAH));

        String expectedMessage = String.format(
                CommentCommand.MESSAGE_UPDATE_COMMENT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(hannah, editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteExistingComment_success() {
        Person hannah = model.getFilteredPersonList().get(INDEX_HANNAH.getZeroBased());
        assertTrue(!hannah.getComment().value.isEmpty());

        Person editedPerson = new PersonBuilder(hannah).withComment("").build();

        CommentCommand commentCommand = new CommentCommand(INDEX_HANNAH, new Comment(""));

        String expectedMessage = String.format(
                CommentCommand.MESSAGE_DELETE_COMMENT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(hannah, editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteEmptyCommentToEmpty_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(firstPerson.getComment().value.isEmpty());

        Person editedPerson = new PersonBuilder(firstPerson).withComment("").build();

        CommentCommand commentCommand = new CommentCommand(INDEX_FIRST_PERSON, new Comment(""));

        String expectedMessage = String.format(
                CommentCommand.MESSAGE_DELETE_COMMENT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addCommentFilteredList_success() {
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Collections.singletonList("Alice")));

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withComment(VALID_COMMENT_AMY).build();

        CommentCommand commentCommand = new CommentCommand(INDEX_FIRST_PERSON,
                new Comment(VALID_COMMENT_AMY));

        String expectedMessage = String.format(
                CommentCommand.MESSAGE_ADD_COMMENT_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        expectedModel.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Collections.singletonList("Alice")));

        assertCommandSuccess(commentCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(editedPerson), model.getFilteredPersonList());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CommentCommand commentCommand = new CommentCommand(outOfBoundIndex, new Comment(VALID_COMMENT_BOB));

        assertCommandFailure(commentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        model.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Collections.singletonList(ALICE.getName()
                .fullName)));
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CommentCommand commentCommand = new CommentCommand(outOfBoundIndex, new Comment(VALID_COMMENT_BOB));

        assertCommandFailure(commentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final CommentCommand standardCommand = new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_AMY));

        // same values -> returns true
        CommentCommand commandWithSameValues = new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CommentCommand(INDEX_SECOND_PERSON, new Comment(VALID_COMMENT_AMY))));

        // different comment -> returns false
        assertFalse(standardCommand.equals(new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_BOB))));
    }
}
